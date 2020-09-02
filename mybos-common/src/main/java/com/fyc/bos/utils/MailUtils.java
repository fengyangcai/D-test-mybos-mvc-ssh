package com.fyc.bos.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author: fyc
 * @Date: 2020/5/5 11:37
 */
public class MailUtils {
    public static void sendMail(String recMail,String mailTitle,String mailContent) {
        try {
            // 1.连接邮箱发送的服务端（登录邮箱）
            /**
             * 参数一: 邮箱服务器的参数 参数二：提供加密后的账户和密码
             */
            Properties props = new Properties();
            // 设置服务器地址网易的就是smtp.163.com，新浪的是smtp.sina.com，139的SMTP服务器smtp.139.com
            props.setProperty("mail.smtp.host", "smtp.139.com");
            // 设置服务器是否需要验证登录（base64加密）
            props.setProperty("mail.smtp.auth", "true");
            Session session = Session.getInstance(props, new Authenticator() {

                // 返回加密后的账户和密码
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("13750015150@139.com", "13750015150");
                }

            });

            // 打开调式,看到发件过程的日志
            session.setDebug(true);

            // 2.创建一封邮件
            MimeMessage mail = new MimeMessage(session);
            // 2.1 设置发件人（和登录账户一致的）
            mail.setFrom(new InternetAddress("13750015150@139.com"));
            // 2.2 设置收件人
            /**
             * 参数一：收件类型（TO: 收件人， CC：抄送人， BCC：密送人）
             *
             */
            mail.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recMail));
            // 2.3 设置邮件的标题
            mail.setSubject(mailTitle);
            // 2.4 设置邮件正文
            mail.setContent(mailContent, "text/html;charset=utf-8");

            // 3.发送邮件
            Transport.send(mail);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件失败");
        }
    }

    public static void main(String[] args) {
        sendMail("593848870@qq.com","测试","哈哈哈");
    }
}
