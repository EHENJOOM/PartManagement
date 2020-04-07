package email;

import javax.mail.MessagingException;

/**
 * @author 赵洪苛
 * @date 2020/03/19 18:25
 * @description 发送电子邮件接口。使用此接口需要注意的是，首先需要调用{@code init()}方法进行配置的初始化，
 *              然后再调用{@code setEmailContent(EmailBean)}方法进行邮件内容的配置，最后再调用{@code sendMessage()}方法发送邮件。
 *              若无法确定当前邮件发送器是否已初始化，可以调用{@code isInitial()}方法判断当前发送器是否已初始化。
 */
public interface MessageSender {

    /**
     * 初始化配置信息，只需要初始化一次即可
     */
    void init();

    /**
     * 设置email邮件的内容信息
     * @param content 邮件数据实体
     * @param <T> 邮件数据实体类型。可根据需要自由定制
     * @throws MessagingException 邮件发送失败的异常
     */
    <T> void setEmailContent(T content) throws MessagingException;

    /**
     * 邮件配置完之后，发送邮件
     * @throws MessagingException 邮件发送失败的异常
     */
    void sendMessage() throws MessagingException;

}
