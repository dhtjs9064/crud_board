package com.study.connection.mapper;

import com.study.connection.domain.entity.BoardCategory;
import com.study.connection.dto.BoardCategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

// entity의 category필드와 dto의 id필드를 매핑함
@Mapper(componentModel = "spring")
public interface BoardCategoryMapper {

    // 단일 객체 변환 메서드 -> 지금 필요는 없는데 유지보수용으로 냅두면 좋다고는 함
    BoardCategoryResponse toResponse(BoardCategory boardCategory);
    // 리스트 객체 변환 메서드
    List<BoardCategoryResponse> toResponseList(List<BoardCategory> boardCategories);
}
