package com.edu.springboot.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "send_email_log")
public class SendEmailLog {
    /**
     * 尽量不要将生成主键策略设置为AUTO，因为需要查找hibernate_sequence表
     * 需要使用IDENTITY策略，在手动创建表的时候，需要将主键变成自增的
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //用户编号
    @Column(name = "user_id")
    private Long userId;
    //邮件
    @Column(name = "email")
    private String email;
    //发送的内容
    @Column(name = "content")
    private String content;
    //发送邮件状态, 1 成功 0 失败
    @Column(name = "status")
    private Integer status;
    //发送时间
    @Column(name = "create_time")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
