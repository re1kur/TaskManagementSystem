package com.example.software.design.service;

public interface MailService {

    void sendVerifyCodeMail(String email, String code);
}
