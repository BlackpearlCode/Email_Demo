package com.example.email_demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


@Slf4j
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送简单文本邮件
     * from：邮件发送者
     * to：邮件接收者
     * subject：邮件主题
     * text：邮件内容
     */

    public String sendSimpleMail(String from,String to,String subject,String text){
        String str="";
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发送者
        message.setFrom(from);
        //设置接收者
        message.setTo(to);
        //设置邮件主题
        message.setSubject(subject);
        //设置邮件内容
        message.setText(text);
        //发送邮件
        try{
            javaMailSender.send(message);
            str="邮件发送成功";
            log.info(str);
            return str;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            str="邮件发送异常";
            return str;
        }finally {
            return str;
        }

    }


    /**
     * 发送带附件的邮件
     * 发送简单文本邮件
     * from：邮件发送者
     * to：邮件接收者
     * subject：邮件主题
     * text：邮件内容
     * uploadFiles：附件（可以传多个附件）
     */
    public String sendMailWithFile(String from, String to, String subject, String text, MultipartFile[] uploadFiles){
        String str="";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper=null;
            mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
            //设置发送者
            mimeMessageHelper.setFrom(from);
            //设置接收者
            mimeMessageHelper.setTo(to);
            //设置邮件主题
            mimeMessageHelper.setSubject(subject);
            //设置邮件内容
            mimeMessageHelper.setText(text);
            //添加附件
            List<File> fileList = this.MultipartFileToFile(uploadFiles);
            for (File file:fileList){
                mimeMessageHelper.addAttachment(file.getName(),file);
            }
            //发送邮件
            javaMailSender.send(mimeMessage);
            str="邮件发送成功";
            log.info(str);
            return str;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            str="邮件发送异常";
            return str;
        }finally {
            return str;
        }
    }


    //MultipartFile文件转File文件
    public List<File> MultipartFileToFile(MultipartFile[] multipartFiles){
        List<File> fileList=new LinkedList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            //获取文件名
            String fileName =multipartFile.getOriginalFilename();
            //获取文件后缀
            String prefix=fileName.substring(fileName.lastIndexOf("."));
            try{
                File file=File.createTempFile(fileName,prefix);
                multipartFile.transferTo(file);
                fileList.add(file);
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        return fileList;

    }





















}
