package com.zainimtiaz.nagarro.utill;

public interface IEmailUtil {
     Boolean sendEmail(
            String emailSender,
            String emailRecipient,
            String emailSubject,
            String emailHtmlBody);
}
