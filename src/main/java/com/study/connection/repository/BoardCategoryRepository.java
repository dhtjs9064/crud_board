package com.study.connection.repository;

import com.study.connection.domain.entity.BoardCategory;

import java.util.List;

public interface BoardCategoryRepository {
    List<BoardCategory> findAll();
}
