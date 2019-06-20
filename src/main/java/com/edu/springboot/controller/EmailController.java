package com.edu.springboot.controller;

import com.edu.springboot.model.SendEmailLog;
import com.edu.springboot.repository.SendEmailLogRepository;
import com.edu.springboot.service.IMailService;
import com.edu.springboot.service.SendEmailLogService;
import com.edu.springboot.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@RestController
public class EmailController {
    @Autowired
    private IMailService mailService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private SendEmailLogService sendEmailLogService;

    @RequestMapping("/simpleEmail")
    public JsonResult index(){
        try {
            mailService.sendSimpleMail("644875343@qq.com","SpringBoot Email","这是一封普通的SpringBoot测试邮件");
            SendEmailLog sendEmailLog = new SendEmailLog();
            sendEmailLog.setUserId(1L);
            sendEmailLog.setContent("send email log test");
            sendEmailLog.setEmail("644875343@qq.com");
            sendEmailLog.setCreateTime(new Date());
            sendEmailLog.setStatus(1);
            sendEmailLogService.save(sendEmailLog);
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/htmlEmail")
    public JsonResult htmlEmail(){
        try {
            mailService.sendHtmlMail("644875343@qq.com","测试邮件","<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n"
                    + "	<div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n"
                    + "		<div\n"
                    + "			style=\"text-align: center; padding: 10px\"><a style=\"text-decoration: none;\" href=\"https://github.com/yishengxiaoyao\" target=\"_bank\" ><strong>欢迎Start支持项目发展:)</strong></a></div>\n"
                    + "	</body>");
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/attachmentsMail")
    public JsonResult attachmentsMail(){
        try {
            String filePath = "test.jpg";
            mailService.sendAttachmentsMail("644875343@qq.com", "这是一封带附件的邮件", "邮件中有附件，请注意查收！", filePath);
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/resourceMail")
    public JsonResult resourceMail(){
        try {
            String rscId = "IJPay";
            String content = "<html><body>这是有图片的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
            String imgPath = "/Users/Javen/Desktop/IJPay.png";
            mailService.sendResourceMail("644875343@qq.com", "这邮件中含有图片", content, imgPath, rscId);

        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }

    @RequestMapping("/templateMail")
    public JsonResult templateMail(){
        try {
            Context context = new Context();
            context.setVariable("project", "springbootemaildemo");
            context.setVariable("author", "yishengxiaoyao");
            context.setVariable("url", "https://github.com/yishengxiaoyao/");
            String emailContent = templateEngine.process("emailTemp", context);

            mailService.sendHtmlMail("644875343@qq.com", "这是模板邮件", emailContent);
        }catch (Exception ex){
            ex.printStackTrace();
            return new JsonResult(-1,"邮件发送失败!!");
        }
        return new JsonResult();
    }
}
