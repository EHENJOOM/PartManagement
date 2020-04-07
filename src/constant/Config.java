package constant;

/**
 * @author 赵洪苛
 * @date 2020/3/19 19:35
 * @description 全局常量
 */
public class Config {

    /**
     * 学生身份登录
     */
    public static final int USER_LOGIN = 0;

    /**
     * 老师身份登录
     */
    public static final int ADMIN_LOGIN = 1;

    /**
     * 超级管理员身份登录
     */
    public static final int SUPER_ADMIN_LOGIN = 2;

    public static final String[] IDENTITY_LOGIN = {"用户", "管理员", "超级管理员"};

    public static final String[] SEX = {"男", "女"};

    /**
     * 该项处于未选状态
     */
    public static final int UNSELECTED = 0;

    /**
     * 该项已被选择
     */
    public static final int SELECTED = 1;

    /**
     * 注册窗口
     */
    public static final int REGISTER_DIALOG = 9;

    /**
     * 忘记密码窗口
     */
    public static final int FORGET_PASSWORD_DIALOG = 10;

    /**
     * 验证码超时时间，此为1分钟
     */
    public static final long VERIFY_CODE_TIME_OUT = 60000;

    public static final int ORDER_STATUS_AUDIT = 401;

    public static final int ORDER_STATUS_REFUSE_AUDIT = 402;

    public static final int ORDER_STATUS_UNPAID = 403;

    public static final int ORDER_STATUS_PAID = 404;

    public static final int ORDER_STATUS_DELIVERY = 405;

    public static final int ORDER_STATUS_TRANSPORTING = 406;

    public static final int ORDER_STATUS_COMPLETED = 407;

    public static final String[] ORDER_STATUS = {"待审核", "审核未通过", "待支付", "已支付", "待发货", "运输中", "已完成"};

    public static final String DATABASE_CONNECT_FAILED_STRING = "数据库连接失败，请稍后再试！";

    /**
     * 关于作者
     */
    public static final String ABOUT_ME_STRING = "\t关于作者：北京化工大学 信管1701 赵洪苛\t";

    /**
     * 发件人的邮箱地址和密码
     */
    public static final String SEND_EMAIL_ACCOUNT = "884101977@qq.com";

    /**
     * 授权码
     */
    public static final String SEND_EMAIL_PASSWORD = "onmdbvtpkltrbdja";

    /**
     * 发件人邮箱的 SMTP 服务器地址
     */
    public static final String SEND_EMAIL_SMTP_HOST = "smtp.qq.com";

    /**
     * BUCT收件人邮箱后缀，默认邮件仅支持北化邮箱注册。可根据需要定制第三方邮件发送器
     */
    public static final String BUCT_MAIL_SUFFIX = "@mail.buct.edu.cn";

    /**
     * QQ邮箱后缀
     */
    public static final String QQ_MAIL_SUFFIX = "@qq.com";

    /**
     * 网易邮箱后缀
     */
    public static final String WANGYI_AIL_SUFFIX = "@163.com";

}
