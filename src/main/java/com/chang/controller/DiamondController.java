package com.chang.controller;

import com.chang.mapper.AccountMapper;
import com.chang.mapper.PersonMapper;
import com.chang.model.AccountStatement;
import com.chang.model.Person;
import com.chang.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/diamond")
public class DiamondController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PersonMapper personMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MailService mailService;

    /**
     * mybatis 注解查询全部
     */
    @RequestMapping("/trump")
    public List<Person> getPersons(){
        List<Person> personList = personMapper.getAll();
        log.info(personList.toString());
        return personList;
    }

    /**
     * mybatis 注解根据主键查询
     */
    @RequestMapping("/economy")
    public Person getPersonById(Long id){
        Person person = personMapper.getOne(id);
        return person;
    }

    /**
     * mybatis 注解插入
     */
    @RequestMapping("/custom")
    public void insertOne(){
        Person person = new Person();
        person.setId(6L);
        person.setName("ccc");
        person.setAge(28);
        person.setAddress("香港");
        personMapper.insertOne(person);
    }

    /**
     * mybatis 注解修改
     */
    @RequestMapping("/landlord")
    public void updateOne() {
        Person person = personMapper.getOne(6L);
        person.setName("dddd");
        personMapper.updateOne(person);
    }

    /**
     * mybatis 注解删除
     */
    @RequestMapping("/ambition")
    public void deleteOne() {
        personMapper.deleteOne(6L);
    }

    /**
     * redisTemplate存缓存
     */
    @RequestMapping("/hello")
    public String hello() {
        log.info("hello log");
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().set(i + "", "缓存测试" + i);
        }
        return "hello diamond";
    }

    /**
     * redisTemplate取缓存
     */
    @RequestMapping(value = "redisTest", method = RequestMethod.GET)
    public void redisTest() {
        for (int i = 0; i < 10; i++) {
            log.info(redisTemplate.opsForValue().get(i + "") + "");
        }
    }

    /**
     * mybatisXML查询全部
     */
    @RequestMapping("/lombok")
    public List<AccountStatement> getAllAccount(){
        return accountMapper.getAll();
    }

    /**
     * mybatisXML根据主键获取
     */
    @RequestMapping("/shiXiong")
    public AccountStatement getOneAccount(){
        return accountMapper.getOne("123454676532");
    }

    /**
     * mabatis XML插入
     */
    @RequestMapping("/constructor")
    public void insertOneAccount(){
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setPid("0987654321");
        accountStatement.setOutTradeNo("0029138123912238");
        accountStatement.setOrderState("1");
        accountMapper.insert(accountStatement);
    }

    /**
     * mybatis XML修改
     */
    @RequestMapping("/updateAccount")
    public void updateAccount(){
        AccountStatement accountStatement = accountMapper.getOne("0987654321");
        accountStatement.setOrderState("2");
        accountStatement.setOutTradeNo("123");
        accountStatement.setOrderType("876543");
        accountMapper.update(accountStatement);
    }

    /**
     * mybatis XML删除
     */
    @RequestMapping("/deleteAccount")
    public void deleteAccount(){
        accountMapper.delete("0987654321");
    }

    /**
     * 发送简单邮件
     */
    @RequestMapping("/sendSimpleMail")
    public void sendSimpleMail(){
        mailService.sendSimpleMail("changhexiang110@163.com","简单邮件","这里是程序发送的简单邮件");
    }

    /**
     * 发送html邮件
     */
    @RequestMapping("/sendHtmlMail")
    public void sendHtmlMail(){
        String htmlContent = "<html>\n" +
                "<body>\n" +
                "<h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("changhexiang110@163.com","html邮件",htmlContent);
    }

    /**
     * 发送附件邮件
     */
    @RequestMapping("/sendAttachmentsMail")
    public void sendAttachmentsMail(){
        String filePath="D:\\images\\IMG_20181004_144100.jpg";
        mailService.sendAttachmentsMail("changhexiang110@163.com", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    /**
     * 发送静态邮件
     */
    @RequestMapping("/sendInlineResourceMail")
    public void sendInlineResourceMail(){
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "D:\\images\\IMG_20181004_144100.jpg";

        mailService.sendInlineResourceMail("changhexiang110@163.com", "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @RequestMapping("/redisObject")
    public void redisObject(){
        Person person = new Person(111L,"放缓存的人",18,"中国");
        ValueOperations<String,Person> operations = redisTemplate.opsForValue();
        operations.set("com.chang",person);
        operations.set("com.chang.he",person,1, TimeUnit.SECONDS);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean chang = redisTemplate.hasKey("com.chang");
        boolean he = redisTemplate.hasKey("com.chang.he");
        if (chang){
            log.info("应该的");
        }else{
            log.warn("不应该出现");
        }
        if (he){
            log.info("未超时，还存在");
        }else{
            log.info("超时，消失了");
        }
    }

    @RequestMapping("/redisGetPerson")
    @Cacheable(value="person-key")
    public Person getPersonFromRedis(){
        Person person = new Person(111L,"放缓存的人",18,"中国");
        log.info("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return person;
    }

    @RequestMapping("/sessonAndRedis")
    public String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if(uid==null){
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid",uid);
        return session.getId();
    }
}
