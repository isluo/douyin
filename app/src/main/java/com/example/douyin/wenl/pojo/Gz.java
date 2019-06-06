package com.example.douyin.wenl.pojo;

public class Gz {
    private Long id;

    private String userid;

    private String userid2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUserid2() {
        return userid2;
    }

    public void setUserid2(String userid2) {
        this.userid2 = userid2 == null ? null : userid2.trim();
    }
}