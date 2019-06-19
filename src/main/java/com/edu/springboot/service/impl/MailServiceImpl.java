package com.edu.springboot.service.impl;

import com.edu.springboot.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.from")
    private String from;

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        buildSimpleMail(to,subject,content,message,null);
        sender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        buildSimpleMail(to, subject, content, message, cc);
        sender.send(message);
    }

    private void buildSimpleMail(String to, String subject, String content, SimpleMailMessage message, String[] cc) {
        message.setFrom(from);
        message.setTo(to);
        if(!StringUtils.isEmpty(cc)){
            message.setCc(cc);
        }
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);
    }

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        buildHtmlEmail(to, subject, content, helper, null,true);
        sender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        buildHtmlEmail(to, subject, content, helper, cc,true);

        sender.send(message);
    }

    private void buildHtmlEmail(String to, String subject, String content, MimeMessageHelper helper, String[] cc,boolean flag) throws MessagingException {
        helper.setFrom(from);
        helper.setTo(to);
        if (!StringUtils.isEmpty(cc)){
            helper.setCc(cc);
        }
        helper.setSubject(subject);
        if (flag) {
            helper.setText(content, true);
        }else{
            helper.setText(content);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        FileSystemResource file=buildBodyAndFile(to,subject,content,filePath,helper,null,true);
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        sender.send(message);
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc)throws MessagingException{
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        FileSystemResource file = buildBodyAndFile(to, subject, content, filePath, helper, cc,true);
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        sender.send(message);
    }

    private FileSystemResource buildBodyAndFile(String to, String subject, String content, String filePath, MimeMessageHelper helper, String[] cc,boolean flag) throws MessagingException {
        buildHtmlEmail(to, subject, content, helper, cc, flag);

        return new FileSystemResource(new File(filePath));
    }

    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        FileSystemResource res = buildBodyAndFile(to,subject,content,rscPath,helper,null,true);
        helper.addInline(rscId, res);

        sender.send(message);
    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        FileSystemResource res = buildBodyAndFile(to,subject,content,rscPath,helper,cc,true);
        helper.addInline(rscId, res);

        sender.send(message);
    }
}
