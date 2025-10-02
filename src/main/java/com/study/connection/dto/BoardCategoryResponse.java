package com.study.connection.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCategoryResponse {
    // TODO: entity와 필드이름을 다르게 정하는게 좋을까, 같게하는게 좋을까
    private final String categoryId;
    private final String categoryName;
}
