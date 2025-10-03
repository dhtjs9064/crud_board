package com.study.connection.dto;

import lombok.Builder;
import lombok.Getter;

// Repository에 전달할 검색 및 페이징 조건을 담는 DTO
@Getter
@Builder

// 검색 조건에 관한 service에서 필요한 응답 데이터
public class BoardSearchCondition {

    // 1. 페이징 관련 정보 (Service에서 계산)
    private final int skipPage; // 건너뛸 페이지
    private final int limit; // LIMIT (몇 개를 가져올지, PAGE_SIZE와 동일)

    // 2. 검색 관련 정보
    private final String boardCategory;
    private final String searchKeyword;
}