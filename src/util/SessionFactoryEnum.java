package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

/**
 * @author 赵洪苛
 * @date 2020/3/19 22:56
 * @description SessionFactory单例类
 */
public enum SessionFactoryEnum {

    /**
     * 枚举实例
     */
    INSTANCE;

    /**
     * hibernate持久化控制层
     */
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            showInfo();
        }
    }

    public static SessionFactoryEnum getInstance() {
        return INSTANCE;
    }

    public SessionFactory getSessionFactory() {
        return factory;
    }

    public Session getSession() {
        try {
            return factory.openSession();
        } catch (Exception e) {
            return null;
        }
    }

    private static void showInfo() {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "无法连接至远程数据库，可能是管理员尚未开启服务。\n请等待管理员开启数据库服务后重试！", "错误", JOptionPane.ERROR_MESSAGE));
    }

}
