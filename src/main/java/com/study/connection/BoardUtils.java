package com.study.connection;

import com.study.connection.dao.BoardDAO;
import com.study.connection.dto.BoardDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BoardUtils {

    public static BoardDTO getValidBoard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: 아래의 검증 과정은 중복일까 아닐까
        // TODO: 에러일경우 각각 어떻게 처리할건지, 메시지는 어떻게 보여줄것인지
        String boardIDParam = request.getParameter("boardID");

        int boardID;
        try {
            boardID = Integer.parseInt(boardIDParam);
            // 숫자가 아니면 에러
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "유효하지 않은 글입니다.");
            return null;
        }

        // 컨트롤러에서 dao의 getBoard()를 통해 boardID 값을 가져옴
        BoardDAO dao = new BoardDAO();
        // getBoard의 반환 타입 = BoardDTO
        BoardDTO board = dao.getBoard(boardID);

        // DB에 저장되어 있지 않으면 에러
        if (board == null) {
            request.setAttribute("errorMessage", "삭제된 글입니다.");
            return null;
        }

        return board;
    }
}