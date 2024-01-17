package com.sahil.tmailapp.Model;

public class EmailMessage {
    private String id;
    private long receivedAt;
    private String from;
    private String subject;
    private String bodyPreview;
    private int attachmentsCount;

    public EmailMessage(String id, long receivedAt, String from, String subject, String bodyPreview, int attachmentsCount) {
        this.id = id;
        this.receivedAt = receivedAt;
        this.from = from;
        this.subject = subject;
        this.bodyPreview = bodyPreview;
        this.attachmentsCount = attachmentsCount;
    }

    public EmailMessage() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyPreview() {
        return bodyPreview;
    }

    public void setBodyPreview(String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }

    public int getAttachmentsCount() {
        return attachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        this.attachmentsCount = attachmentsCount;
    }
}

