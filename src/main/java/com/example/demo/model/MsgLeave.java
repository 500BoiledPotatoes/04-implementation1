package com.example.demo.model;

import javax.persistence.*;

@Entity
public class MsgLeave {

    private Long MsgId;
    private String name;
    private String Email;
    private String phoneNumber;
    private String title;
    private String msgContent;

    public MsgLeave() {
    }

    public MsgLeave(String name, String email, String phoneNumber, String title, String msgContent) {
        this.name = name;
        Email = email;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.msgContent = msgContent;
    }

    @Id
    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Msg_id", nullable = false)
    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }

    @Column(name = "msg_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "msg_email")
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Column(name = "msg_phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "msg_title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "msg_content")
    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }


}
