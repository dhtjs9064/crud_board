package com.study.connection.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
// 서버에서 응답으로 보여줄 단일 게시글 목록 데이터들
public class BoardListItemResponse {
    private int boardId;
    private String boardCategory;
    private String boardTitle;
    private String boardWriter;
    private int boardViews;
    private LocalDateTime boardCreatedAt;
    private LocalDateTime boardUpdatedAt;
}
