package com.study.connection.service;

import com.study.connection.domain.entity.BoardCategory;
import com.study.connection.dto.BoardCategoryResponse;
import com.study.connection.mapper.BoardCategoryMapper;
import com.study.connection.repository.BoardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** 1. service니까 BoardCategoryRepository만 의존할 것
 즉, 그 안의 findAll()메서드를 호출할 수 있음
 2. 비즈니스 로직을 담당하므로 실제 로직을 만들어야하는데,
 db에 없던 "전체 카테고리" 옵션도 넣어줘야함
 -> 그러기 위해서는 "전체 카테고리" 객체를 add()하고 db목록의 맨앞에 오도록해야함
 3. 따라서 반환타입 = List<BoardCategoryResponse>
 -> entity 노출을 막기위해 dto를 반환타입으로 함**/
@Service
@RequiredArgsConstructor
public class BoardCategoryService {
    // repository 주입
    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardCategoryMapper boardCategoryMapper;

    // TODO : 메서드 이름이 적합한 이름인지 고민
    // TODO : MapStruct를 쓰지 않으면 stream().map().collect(Collectors.toList())를 사용해서 "한 리스트의 객체를 다른 타입의 리스트 객체로 변환하는 작업"을 거쳐야하는데 왜 그럴까
    // TODO : MapStruct는 mybatis 것인가?
    public List<BoardCategoryResponse> getAllCategories() {

        // 1. DB에서 Entity 목록 조회
        List<BoardCategory> dbCategories = boardCategoryRepository.findAll();


        // TODO : 이렇게 따로 "전체 카테고리" 옵션을 추가하는 로직을 하나한 작성하는게 맞을까? -> jsp에서 가짜 데이터를 넣고 바로 controller에서 list페이지로 이동시키자

        // 2. MapStruct를 사용한 DTO 변환 (Entity List -> DTO List)
        // 필드명이 같으므로 @Mapping 없이 자동으로 변환됩니다.
        return boardCategoryMapper.toResponseList(dbCategories);
    }
}
