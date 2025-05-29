package com.shirobokov.creditpipelinestaff.service;


import com.shirobokov.creditpipelinestaff.entity.Application;
import com.shirobokov.creditpipelinestaff.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Value("${spring.mail.username}")
    String EMAIL_FROM;

    private final JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendApproveNotification(Application application, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(user.getEmail());
        message.setSubject("Ваша заявка на кредит одобрена");

        String messageTxt = String.format("""
                Уважаемый(ая) %s %s %s,

                Ваша заявка на кредит была успешно одобрена!

                Подробности заявки:
                - Сумма кредита: %d руб.
                - Срок кредита: %d мес.
                - Цель кредита: %s
                - Ставка по кредиту: %.2f%%


                """,
                user.getLastName(), user.getFirstName(), user.getMiddleName(),
                application.getAmount(), application.getPeriod(), application.getPurpose(),
                application.getPercentageRate());

        message.setText(messageTxt);

        try {
            mailSender.send(message);
            System.out.println("Письмо отправлено успешно");
        } catch (Exception e) {
            System.out.println("Ошибка при отправке письма");
            return false;
        }

        return true;
    }

    public boolean sendDenyNotification(Application application, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(user.getEmail());
        message.setSubject("Ваша заявка на кредит отклонена");

        String messageTxt = String.format("""
                Уважаемый(ая) %s %s %s,

                К сожалению, ваша заявка на кредит была отклонена.

                Подробности заявки:
                - Сумма кредита: %d руб.
                - Срок кредита: %d мес.
                - Цель кредита: %s

                Причина отказа: %s

                """,
                user.getLastName(), user.getFirstName(), user.getMiddleName(),
                application.getAmount(), application.getPeriod(), application.getPurpose(),
                application.getReasonForRefusal() != null ? application.getReasonForRefusal() : "не указана");

        message.setText(messageTxt);

        try {
            mailSender.send(message);
            System.out.println("Письмо отправлено успешно");
        } catch (Exception e) {
            System.out.println("Ошибка при отправке письма");
            return false;
        }

        return true;
    }
}
