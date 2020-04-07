package email;

import constant.Config;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author 赵洪苛
 * @date 2020/03/19 18:58
 * @description email发送器
 */
public class DefaultEmailSender implements MessageSender {

    private Message message;

    private Session session;

    @Override
    public void init() {
        // 配置邮件发送的属性
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", Config.SEND_EMAIL_SMTP_HOST);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        // 根据配置创建会话对象, 用于和邮件服务器交互
        session = Session.getDefaultInstance(props);
    }

    @Override
    public <T> void setEmailContent(T emailBean) throws MessagingException {
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress(Config.SEND_EMAIL_ACCOUNT));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(((EmailBean)emailBean).getReceiveAccount() + ((EmailBean)emailBean).getSuffix()));
        message.setSubject(((EmailBean)emailBean).getSubject());
        message.setText(((EmailBean)emailBean).getContent());
        message.setSentDate(((EmailBean)emailBean).getDate());
        message.saveChanges();
    }

    @Override
    public void sendMessage() throws MessagingException {
        // 根据Session获取邮件传输对象
        Transport transport = session.getTransport();
        // 使用邮箱账号和密码连接邮件服务器
        transport.connect(Config.SEND_EMAIL_ACCOUNT, Config.SEND_EMAIL_PASSWORD);
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        message = null;
    }
}
