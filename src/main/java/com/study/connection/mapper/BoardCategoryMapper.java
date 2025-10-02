package com.study.connection.mapper;

import com.study.connection.domain.entity.BoardCategory;
import com.study.connection.dto.BoardCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardCategoryMapper {

    // entity의 category필드와 dto의 id필드를 매핑함
    BoardCategoryResponse toResponse(BoardCategory boardCategory);
    List<BoardCategoryResponse> toResponseList(List<BoardCategory> boardCategories);
}
