package com.study.connection.dto;

import lombok.Getter;
import lombok.Setter;
// TODO : requestDTO, responseDTO
// TODO : Entity 분리
// TODO : MapStruct
@Getter
@Setter
// 목록 페이지를 보고 싶을때 사용자가 요청하는 데이터값
public class BoardListRequest {
    private int pageNumber = 1;
    private String boardCategory;
    private String searchKeyword;
}