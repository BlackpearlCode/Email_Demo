package com.example.email_demo.controller;
import com.example.email_demo.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class EmailController {


    @Autowired
    private EmailUtil emailUtil;

    /**
     * 发送简单文本邮件
     * @return
     */
    @RequestMapping("/sendSimpleMail")
    public String sendSimpleMail(String from,String to,String subject,String text){
        return emailUtil.sendSimpleMail(from,to,subject,text);
    }

    /**
     * 发送带附件的邮件
     */
    @RequestMapping("/sendMailWithFile")
    public String sendMailWithFile(String from, String to, String subject, String text, MultipartFile[] uploadFiles){
        return emailUtil.sendMailWithFile(from,to,subject,text,uploadFiles);
    }















}
