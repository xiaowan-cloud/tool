package com.wan.tool.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import com.wan.tool.model.entity.EmailInfo;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author wan
 * @version 1.0.0
 * @since 2022年03月28日 17:03:40
 */
@Slf4j
@ApiModel("email发送工具类")
public class EmailUtil {

    public static String myEmailAccount = "2439881583@qq.com";
    public static String myEmailPassword = "cgaaxljkvpnvdjhg";
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static String receiveMailAccount = "1832269410@qq.com";

    public static void sendEmail(List<EmailInfo> emailInfos, Object emailBody) {
        sendEmail(emailInfos, emailBody, null, null);
    }

    /**
     * 功能描述：
     * -- 发送邮件
     *
     * @param emailInfos 收件人相关信息
     * @param emailBody  邮件内容
     * @author wan
     * @since 2022/3/28 17:45
     */
    public static void sendEmail(List<EmailInfo> emailInfos, Object emailBody, String proxyHost, Integer proxyPort) {
        try {
            //创建一封邮件
            //参数实体
            Properties props = new Properties();
            // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.transport.protocol", "smtp");
            // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.host", myEmailSMTPHost);
            //需要请求认证
            props.setProperty("mail.smtp.auth", "true");
            //发送加密邮件
//            //使用SSL加密SMTP通过465端口进行邮件发送
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            props.put("mail.smtp.ssl.enable", "true");
//            props.put("mail.smtp.ssl.socketFactory", sf);
//            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            props.put("mail.smtp.port", "465");
//            props.put("mail.smtp.starttls.enable", "true");
            //代理
            if (StringUtils.isNotBlank(proxyHost)) {
                props.setProperty("mail.smtp.socks.host", proxyHost);
                props.setProperty("mail.smtp.socks.port", String.valueOf(proxyPort));
            }

            //创建会话对象
            Session session = Session.getInstance(props);
            //设置成debug模式
            session.setDebug(true);
            //邮件对象
            MimeMessage message = createMimeMessage(session, myEmailAccount, emailInfos, emailBody);
            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 如果连接服务器失败, 都会在控制台输出相应失败原因的log。
            //    仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接,
            //    根据给出的错误类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            transport.connect(myEmailAccount, myEmailPassword);

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
        } catch (Exception e) {
            log.error("邮件发送异常，异常为：{},\n{}", e.getMessage(), e);
        }
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session    和服务器交互的会话
     * @param sendMail   发件人邮箱
     * @param emailInfos 收件人相关信息
     * @param emailBody  邮件内容
     * @return MimeMessage
     * @throws Exception 异常
     */
    private static MimeMessage createMimeMessage(Session session, String sendMail, List<EmailInfo> emailInfos, Object emailBody) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, "小微金融邮箱"));

        List<Address> toAddressList = null;
        List<Address> ccAddressList = null;
        List<Address> bccAddressList = null;
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        for (EmailInfo emailInfo : emailInfos
        ) {
            switch (emailInfo.getType().getType()) {
                case "TO":
                    toAddressList = addMail(toAddressList, emailInfo.getReceiveAccount());
                    break;
                case "CC":
                    ccAddressList = addMail(ccAddressList, emailInfo.getReceiveAccount());
                    break;
                case "BCC":
                    bccAddressList = addMail(bccAddressList, emailInfo.getReceiveAccount());
                    break;
                default:
                    break;
            }
        }
        if (null != toAddressList) {
            message.setRecipients(Message.RecipientType.TO, list2Array(toAddressList));
        }
        if (null != ccAddressList) {
            message.setRecipients(Message.RecipientType.TO, list2Array(ccAddressList));
        }
        if (null != bccAddressList) {
            message.setRecipients(Message.RecipientType.TO, list2Array(bccAddressList));
        }
        // 4. Subject: 邮件主题
        message.setSubject("邮件主题", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        if (emailBody instanceof String) {
            message.setContent(emailBody, "text/html;charset=UTF-8");
        } else if (emailBody instanceof Multipart) {
            message.setContent((Multipart) emailBody);
        }
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }


    /**
     * 功能描述：
     * -- 增加收件人信息
     *
     * @param accountArray   收件人容器
     * @param receiveAccount 收件人账户
     * @author wan
     * @date 2022/3/28 18:20
     */
    private static List<Address> addMail(List<Address> accountArray, String receiveAccount) {
        if (null == accountArray) {
            accountArray = new ArrayList<>();
        }
        try {
            accountArray.add(new InternetAddress(receiveAccount));
            return accountArray;
        } catch (AddressException e) {
            log.error("addMail方法异常：{},\n{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * -- 集合转数组
     *
     * @param addressList 集合
     * @return Address[]
     */
    private static Address[] list2Array(List<Address> addressList) {
        if (null == addressList || CollectionUtils.isEmpty(addressList)) {
            return null;
        }
        Address[] addresses = new Address[addressList.size()];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = addressList.get(i);
        }
        return addresses;
    }

}
