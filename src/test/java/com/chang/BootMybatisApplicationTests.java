package com.chang;

import com.chang.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootMybatisApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;


    @Test
    public void contextLoads() {
        System.out.println("单元测试");
    }

    @Test
    public void sendTemplateMail(){
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailService.sendHtmlMail("changhexiang110@163.com","主题：这是模板邮件",emailContent);
    }
}
