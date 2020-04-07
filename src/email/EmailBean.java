package email;

import java.util.Date;

/**
 * @author 赵洪苛
 * @date 2020/03/19 18:37
 * @description email邮件内容实体
 */
public class EmailBean {

    /**
     * 收件人邮箱账号，不含后缀域名
     */
    private String receiveAccount;

    /**
     * 收件人邮箱后缀
     */
    private String suffix;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文内容
     */
    private String content;

    /**
     * 邮件发送日期
     */
    private Date date;

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
