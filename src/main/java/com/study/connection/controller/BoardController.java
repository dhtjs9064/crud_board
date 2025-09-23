package com.study.connection.controller;

import com.study.connection.BoardUtils;
import com.study.connection.dao.BoardDAO;
import com.study.connection.dto.BoardDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
// command로 분리
// 모든 진입점은 controller
// doget, dopost = service로


public class BoardController extends HttpServlet {
    // 직렬화된 객체의 버전을 식별하는 고유 ID
    // 직렬화 : 객체를 바이트 스트림으로 변환해서 전송 가능한 형태로 만드는 것
    // 만약 명시적 선언하지 않는다면 호환 문제가 발생할 수 있다고 한다.
    private static final long serialVersionUID = 1L;

    public BoardController() {
        super();
    }

    // 원하는 경로만 추출하는 메서드
    private String getCommand(HttpServletRequest request) {
        /* localhost를 제외한 전체 경로를 얻는다
        ex) http://localhost:8080/my-app/boards/free/list이면 
        /my-app/boards/free/list를 얻음*/
        String uri = request.getRequestURI();
        // 루트 경로를 얻는다 즉, /my-app을 얻는다
        String path = request.getContextPath();
        // uri에서 루트 경로를 제외한 나머지를 반환한다. 즉, 원하는 /boards/free/list만 얻게된다.
        String command = uri.substring(path.length());
        return command;
    }


    // get, post 병합. 앞으로 모든 요청은 이 곳을 통하고 각 핸들러에 조건에 부합한 요청을 뿌려주게 된다.
    /**
     * : 하지만 AI는 doGet, doPost로 나누는 것이 더 좋다고 한다...
     **/
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String command = getCommand(request);

        // 메서드를 얻어서 무슨 요청인지 확인한다.
        String method = request.getMethod();
        // 브라우저는 처음에는 GET요청을 보낼것이므로 자연스럽게 web.xml을 통해 list 화면을 보게됨
        // 이곳에서 브라우저의 단순 정보 요청을 받는다.
        if (method.equals("GET")) {
            switch (command) {
                // 초기페이지면 list페이지로
                case "/":
                case "/boards/free/list":
                    handleListRequest(request, response);
                    break;
                case "/boards/free/write":
                    handleWriteRequest(request, response);
                    break;
                case "/boards/free/view":
                    handleViewRequest(request, response);
                    break;
                case "/boards/free/modify":
                    handleModifyRequest(request, response);
                    break;
                default:
                    // 알 수 없는 요청일 경우 405
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "해당 페이지를 찾을 수 없습니다.");
                    break;
            }

            // 브라우저가 데이터 생성, 수정, 삭제등을 원한다면 이곳에서 받는다.
        } else if (method.equals("POST")) {
            if ("/boards/free/writeAction".equals(command)) {
                handleWriteActionRequest(request, response);
            } else if ("/boards/free/modifyAction".equals(command)) {
                handleModifyActionRequest(request, response);
            } else {
                // 알 수 없는 요청일 경우 405
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "해당 페이지를 찾을 수 없습니다.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "허용되지 않는 HTTP 메서드입니다.");
        }
    }

    // 목록 조회 요청에 대한 처리를 하는 핸들러
    private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDAO dao = new BoardDAO();
        int pageNumber = 1;

        try {
            String pageParam = request.getParameter("pageNumber");
            if (pageParam != null) {
                pageNumber = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String boardCategory = request.getParameter("boardCategory");
        String searchKeyword = request.getParameter("searchKeyword");

        List<BoardDTO> list = dao.getList(pageNumber, boardCategory, searchKeyword);
        request.setAttribute("list", list);
        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("boardCategory", boardCategory);
        request.setAttribute("searchKeyword", searchKeyword);

        boolean nextPage = dao.nextPage(pageNumber + 1);
        request.setAttribute("nextPage", nextPage);

        request.getRequestDispatcher("/boards/free/list/board_list.jsp").forward(request, response);
    }

    // 만약 제목을 눌러 보기페이지로 가려한다면 이제 boardID로 구분해야함
    private void handleViewRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDTO board = BoardUtils.getValidBoard(request, response);
        if (board != null) {
            request.setAttribute("board", board);
            request.getRequestDispatcher("/boards/free/view/board_view.jsp").forward(request, response);
        }
    }

    private void handleWriteRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("/boards/free/write/board_write.jsp").forward(request, response);
    }

    // 사용자가 작성하려는것인지 수정하려는것인지에 대한 isWrite 플래그 추가
    private BoardDTO buildBoardFromRequest(HttpServletRequest request, boolean isWrite) {
        BoardDTO board = new BoardDTO();

        // write할 경우에는 카테고리를 작성하므로 적용됨 (물론 지금은 modify에서도 열어놨기에 같이 적용될듯)
        String category = request.getParameter("boardCategory");
        if (isWrite && category != null && !category.isEmpty()) {
            board.setCategory(category);
        }

        board.setWriter(request.getParameter("boardWriter"));
        board.setPassword(request.getParameter("boardPassword"));
        board.setTitle(request.getParameter("boardTitle"));
        board.setContent(request.getParameter("boardContent"));
        board.setAvailable(1);

        return board;
    }

    // 실제 동작은 아직 안하지만 나중에 필요할지 고민
    private void handleWriteActionRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        BoardDTO board = new BoardDTO();
        board.setCategory(request.getParameter("boardCategory"));
        board.setWriter(request.getParameter("boardWriter"));
        board.setPassword(request.getParameter("boardPassword"));
        board.setTitle(request.getParameter("boardTitle"));
        board.setContent(request.getParameter("boardContent"));
        board.setAvailable(1);

        if (board.getCategory() == null || board.getWriter() == null || board.getPassword() == null ||
                board.getTitle() == null || board.getContent() == null ||
                board.getCategory().isEmpty() || board.getWriter().isEmpty() || board.getPassword().isEmpty() ||
                board.getTitle().isEmpty() || board.getContent().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/boards/free/list");
            return;
        }

        BoardDAO boardDAO = new BoardDAO();
        int result = boardDAO.write(board);

        if (result == 1) {
            response.sendRedirect(request.getContextPath() + "/boards/free/list");
        } else {
            request.setAttribute("errorMessage", "글 작성에 실패했습니다.");
            request.getRequestDispatcher("/boards/free/write/board_write.jsp").forward(request, response);
        }

    }

    private void handleModifyRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        BoardDTO board = BoardUtils.getValidBoard(request, response);
        if (board != null) {
            request.setAttribute("board", board);
            request.getRequestDispatcher("/boards/free/modify/board_modify.jsp").forward(request, response);
        }
    }

    private void handleModifyActionRequest(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        try {
            int boardID = Integer.parseInt(request.getParameter("boardID"));
            BoardDTO boardFromRequest = buildBoardFromRequest(request, false);
            boardFromRequest.setBoardID(boardID);

            if (boardFromRequest.getWriter() == null || boardFromRequest.getPassword() == null ||
                    boardFromRequest.getTitle() == null || boardFromRequest.getContent() == null ||
                    boardFromRequest.getWriter().isEmpty() || boardFromRequest.getPassword().isEmpty() ||
                    boardFromRequest.getTitle().isEmpty() || boardFromRequest.getContent().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/boards/free/modify");
                return;
            }

            BoardDAO boardDAO = new BoardDAO();
            int result = boardDAO.modify(boardFromRequest.getBoardID(), boardFromRequest.getWriter(), boardFromRequest.getPassword(),
                    boardFromRequest.getTitle(), boardFromRequest.getContent());
            if (result == 1) {
                response.sendRedirect(request.getContextPath() + "/boards/free/list");
            } else {
                // TODO : 에러메시지는 나중에
                request.setAttribute("errorMessage", "수정에 실패했습니다.");
                request.getRequestDispatcher("/boards/free/modify/board_modify.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 게시글 ID입니다.");
        }
    }
}