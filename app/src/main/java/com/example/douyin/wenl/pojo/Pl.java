package com.example.douyin.wenl.pojo;

public class Pl {
    private Long id;

    private Long dz;

    private String comment;

    private String userid;

    private Long videoid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDz() {
        return dz;
    }

    public void setDz(Long dz) {
        this.dz = dz;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Long getVideoid() {
        return videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }
}