package com.study.connection.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder // 객체 생성 시 보기 편하도록 사용
// 목록에서 페이징 정보에 관한 데이터에 대한 응답
public class BoardListPageResponse {

    // 1. 실제 게시글 목록 데이터 (BoardListResponse의 리스트)
    private final List<BoardListItemResponse> list;

    // 2. 페이징 정보
    private final int pageNumber;   // 현재 페이지 번호
    private final int pageSize;     // 한 페이지당 게시글 수
    private final int totalCount;   // 전체 게시글 수
    private final boolean hasNextPage; // 다음 페이지 존재 여부
}