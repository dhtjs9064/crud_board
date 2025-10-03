package com.study.connection.service;

import com.study.connection.dto.BoardListRequest;
import com.study.connection.dto.BoardListItemResponse;
import com.study.connection.dto.BoardListPageResponse;
import com.study.connection.dto.BoardSearchCondition;
import com.study.connection.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardListService {
    private final BoardRepository boardRepository;
    private static final int PAGE_SIZE = 10;

    public BoardListPageResponse getBoardList(BoardListRequest request) {

        // 1. 페이지 관련 변수 계산
        int pageNumber = (request.getPageNumber() == 0) ? 1 : request.getPageNumber();
        int skipPage = (pageNumber - 1) * PAGE_SIZE;

        // 2. Repository에 전달할 BoardSearchCondition 생성
        // Service에서 계산한 값과 Request의 값을 묶어 하나의 객체로 만듦
        BoardSearchCondition condition = BoardSearchCondition.builder()
                .skipPage(skipPage)
                .limit(PAGE_SIZE)
                .boardCategory(request.getBoardCategory())
                .searchKeyword(request.getSearchKeyword())
                .build();

        // 3. 전체 게시글 개수 조회
        int totalCount = boardRepository.getTotalCount(condition);

        // 4. 실제 목록 조회 요청
        List<BoardListItemResponse> list = boardRepository.getList(condition);

        // 5. 다음 페이지 존재 여부 계산
        boolean hasNextPage = (skipPage + PAGE_SIZE) < totalCount;

        // 6. 모든 정보를 최종 Result DTO에 담아 반환
        return BoardListPageResponse.builder()
                .list(list)
                .pageNumber(pageNumber)
                .pageSize(PAGE_SIZE)
                .totalCount(totalCount)
                .hasNextPage(hasNextPage)
                .build();
    }
}