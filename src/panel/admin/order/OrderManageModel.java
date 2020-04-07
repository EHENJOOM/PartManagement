package panel.admin.order;

import bean.OrderBean;
import bean.UserBean;
import constant.Config;
import mvp.BaseCallBack;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import thread.ThreadPoolEnum;
import util.SessionFactoryEnum;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2020/3/30 15:48
 * @description 定案数据处理器
 */
public class OrderManageModel {

    public void select(BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            try {
                List<OrderBean> list = session.createQuery("from OrderBean ").list();
                list.forEach(temp -> {
                    Hibernate.initialize(temp.getPartBean());
                    Hibernate.initialize(temp.getUserBean());
                    // 将代理对象转化为实体对象
                    temp.setUserBean((UserBean) Hibernate.unproxy(temp.getUserBean()));
                });
                analyze(list, baseCallBack);
            } catch (Exception e) {
                e.printStackTrace();
                baseCallBack.onFailed("查询失败！");
            } finally {
                session.close();
            }
        });
    }

    /**
     * 分析计算每个用户的平均欠款时间
     * @param data 订单数据
     * @param baseCallBack 回调
     */
    private void analyze(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        // 不同客户列表
        List<OrderBean> difUser = data.stream()
            .filter(distinct(OrderBean::getUserBean))
            .collect(Collectors.toList());
        difUser.forEach(temp -> {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            // 计算所有订单总欠款天数
            int allDay = data.stream()
                .filter(un -> un.getUserBean().equals(temp.getUserBean()))
                .mapToInt(un -> {
                    if (un.getPaid() == Config.ORDER_STATUS_PAID) {
                        return un.getPayDate().getTime() - un.getPaymentDate().getTime() <= 0 ? 0 : (int) ((un.getPayDate().getTime() - un.getPaymentDate().getTime()) / 1000 / 60 / 60 / 24);
                    }
                    if (now.getTime() < un.getPaymentDate().getTime()) {
                        return 0;
                    } else {
                        return (int) ((now.getTime() - un.getPaymentDate().getTime()) / 1000 / 60 / 60 / 24);
                    }
                }).sum();
            int day = (int) (allDay / data.stream().filter(un -> un.getUserBean().equals(temp.getUserBean())).filter(un -> now.getTime() >= un.getPaymentDate().getTime()).count());
            data.forEach(orderBean -> {
                if (temp.getUserBean().equals(orderBean.getUserBean())) {
                    if (day <= 3) {
                        orderBean.setSuggest("AAT=" + day + "天，信用非常好");
                    } else if (day <= 5) {
                        orderBean.setSuggest("AAT=" + day + "天，信用良好");
                    } else if (day <= 7) {
                        orderBean.setSuggest("AAT=" + day + "天，信用一般");
                    } else if (day <= 10) {
                        orderBean.setSuggest("AAT=" + day + "天，信用差");
                    } else {
                        orderBean.setSuggest("AAT=" + day + "天，信用非常差");
                    }
                }
            });
        });
        baseCallBack.onSucceed(data);
    }

    private static <T> Predicate<T> distinct(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void delete(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::delete);
                transaction.commit();
                baseCallBack.onSucceed(data.stream().filter(temp -> temp.getState() == Config.SELECTED).collect(Collectors.toList()));
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("删除失败！");
            } finally {
                session.close();
            }
        });
    }

    public void audit(int operation, List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .filter(temp -> temp.getStatus() == Config.ORDER_STATUS_AUDIT)
                    .forEach(temp -> session.createQuery("update OrderBean orderBean set orderBean.status=:status where orderBean.id=:id")
                        .setParameter("status", operation)
                        .setParameter("id", temp.getId())
                        .executeUpdate());
                transaction.commit();
                data.forEach(temp -> {
                    if (temp.getState() == Config.SELECTED) {
                        temp.setStatus(operation);
                    }
                });
                baseCallBack.onSucceed(data);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("审核失败！");
            } finally {
                session.close();
            }
        });
    }

    public void save(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .forEach(session::saveOrUpdate);
                transaction.commit();
                baseCallBack.onSucceed(data);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("保存失败！");
            } finally {
                session.close();
            }
        });
    }

    public void delivery(List<OrderBean> data, BaseCallBack<List<OrderBean>> baseCallBack) {
        ThreadPoolEnum.getInstance().execute(() -> {
            Session session = SessionFactoryEnum.getInstance().getSession();
            if (session == null) {
                return;
            }
            Transaction transaction = session.beginTransaction();
            try {
                for (OrderBean orderBean : data) {
                    if (orderBean.getState() == Config.SELECTED && (orderBean.getStatus() == Config.ORDER_STATUS_UNPAID || orderBean.getStatus() == Config.ORDER_STATUS_PAID || orderBean.getStatus() == Config.ORDER_STATUS_DELIVERY)) {
                        if (orderBean.getPartBean().getCount() < orderBean.getNeedCount()) {
                            baseCallBack.onFailed("库存不足，请先补充库存！");
                            return;
                        }
                    }
                }
                data.stream()
                    .filter(temp -> temp.getState() == Config.SELECTED)
                    .filter(temp -> temp.getStatus() == Config.ORDER_STATUS_UNPAID || temp.getStatus() == Config.ORDER_STATUS_PAID || temp.getStatus() == Config.ORDER_STATUS_DELIVERY)
                    .forEach(temp -> session.createQuery("update OrderBean orderBean set orderBean.status=:status where orderBean.id=:id")
                        .setParameter("status", Config.ORDER_STATUS_TRANSPORTING)
                        .setParameter("id", temp.getId())
                        .executeUpdate());
                transaction.commit();
                data.forEach(orderBean -> {
                    if (orderBean.getState() == Config.SELECTED && (orderBean.getStatus() == Config.ORDER_STATUS_UNPAID || orderBean.getStatus() == Config.ORDER_STATUS_PAID || orderBean.getStatus() == Config.ORDER_STATUS_DELIVERY)) {
                        orderBean.setStatus(Config.ORDER_STATUS_TRANSPORTING);
                    }
                });
                baseCallBack.onSucceed(data);
            } catch (Exception e) {
                transaction.rollback();
                baseCallBack.onFailed("发货失败！");
            } finally {
                session.close();
            }
        });
    }
}
