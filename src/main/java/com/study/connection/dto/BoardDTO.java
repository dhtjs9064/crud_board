package com.study.connection.dto;

import java.time.LocalDateTime;

public class BoardDTO {
    private int boardID;
    private String category;
    private String title;
    private String writer;
    private String password;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String content;
    private int available;

    // 객체 생성을 위한 기본 생성자
    public BoardDTO() {

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

    public void setCategory(String category) {
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt.toLocalDateTime();
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt.toLocalDateTime();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
