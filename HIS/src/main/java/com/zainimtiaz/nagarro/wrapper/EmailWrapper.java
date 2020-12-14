package com.zainimtiaz.nagarro.wrapper;

public class EmailWrapper {
    private String senderEmail;
    private String recepient;
    private String subject;
    private String content;

    public EmailWrapper() {
    }

    public EmailWrapper(String recepient, String subject, String content) {

        this.recepient = recepient;
        this.subject = subject;
        this.content = content;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
