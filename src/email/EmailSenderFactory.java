package email;

/**
 * @author 赵洪苛
 * @date 2020/03/19 17:27
 * @description 工厂模式，email发送器工厂。可根据自己情况，实现{@link MessageSender}接口，完成一个邮件发送器对象，然后创建静态工厂方法获取对象。
 */
public class EmailSenderFactory {

    /**
     * 静态工厂，创建{@code DefaultEmailSender}对象
     * @return Email发送器对象
     */
    public static MessageSender getDefaultEmailSender() {
        return new DefaultEmailSender();
    }

}
