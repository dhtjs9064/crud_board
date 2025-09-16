package com.study.connection.dto;

public class Board {
    private int boardID;
    private String category;
    private String title;
    private String writer;
    private String views;
    private java.sql.Timestamp createdAt;
    private java.sql.Timestamp updatedAt;

    // 객체 생성을 위한 기본 생성자
    public Board(){
        
    }
    
    public Board(int boardID, String category, String title, String writer, String views) {
        this.boardID = boardID;
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.views = views;
        this.createdAt = new java.sql.Timestamp(System.currentTimeMillis());
        this.updatedAt = new java.sql.Timestamp(System.currentTimeMillis());
    }


    // getter / setter 생성
    public int getBoardID() {
        return boardID;
    }
    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String Category) {
        this.category = category;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getWriter() {
        return writer;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }


    public String getViews() {
        return views;
    }
    public void setViews(String views) {
        this.views = views;
    }


    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
