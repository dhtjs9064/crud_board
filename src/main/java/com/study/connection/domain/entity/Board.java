package com.study.connection.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
/** Entity 클래스 **/
// entity는 뒤에 접미사를 붙이지 않는다.
@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
public class Board {
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
}
