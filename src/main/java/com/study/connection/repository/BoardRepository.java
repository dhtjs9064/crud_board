package com.study.connection.repository;

import com.study.connection.dto.BoardListItemResponse;
import com.study.connection.dto.BoardSearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mybatis가 연결관리, 바인딩, 결과 처리등을 전부 자동으로 한다는데 원리를 알아야함
 **/
// mybatis방식
@Mapper
// db와 통신하고 데이터를 crud하기 위해서 생성

public interface BoardRepository {
    List<BoardListItemResponse> getList(BoardSearchCondition condition);

    int getTotalCount(BoardSearchCondition condition);
}

/*    // TODO : 유효성검사 하기전 일단 불러오는 용도..가 아니라 Param해서 불러오면 xml에서 알아서 처리하나
    BoardDTO getBoard(@Param("boardID") int boardID);

    int write(BoardDTO boardDTO);

    int modify(@Param("boardID")int boardId, @Param("boardWriter") String boardWriter, @Param("boardPassword") String boardPassword,
               @Param("boardTitle") String boardTitle, @Param("boardContent") String boardContent);

    int delete(@Param("boardID") int boardID);*/
