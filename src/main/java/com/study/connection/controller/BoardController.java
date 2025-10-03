package com.study.connection.controller;

import com.study.connection.dto.BoardCategoryResponse;
import com.study.connection.dto.BoardListPageResponse;
import com.study.connection.dto.BoardListRequest;
import com.study.connection.service.BoardCategoryService;
import com.study.connection.service.BoardListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

// TODO : 작명 고치기
// TODO : 제 3자 관점에서 무슨 로직인지 알 수 있도록
@RequiredArgsConstructor()
@Controller
@RequestMapping("/boards/free")
// 클라이언트의 요청을 받고 service에 넘겨주고 다시 받을 것임
public class BoardController {

    // 1. service에서 정보를 주고 받음
    private final BoardListService boardListService;
    private final BoardCategoryService boardCategoryService;

    @GetMapping({"/", "/list"})
    public String list(BoardListRequest request, Model model) {

        // 2. Service가 페이징과 목록을 모두 계산하여 반환함
        BoardListPageResponse result = boardListService.getBoardList(request);
        // 3. Model에 Result DTO의 정보를 담음
        List<BoardCategoryResponse> categories = boardCategoryService.getAllCategories();

        // 카테고리 추가
        model.addAttribute("categories", categories);

        // 게시글 목록
        model.addAttribute("list", result.getList());

        // 검색 및 페이징 상태 유지
        model.addAttribute("pageNumber", result.getPageNumber());
        model.addAttribute("boardCategory", request.getBoardCategory());
        model.addAttribute("searchKeyword", request.getSearchKeyword());

        // 페이징 정보
        model.addAttribute("nextPage", result.isHasNextPage());

        // application.properties의 prefix와 suffix를 통해 앞에 자동으로 붙여 읽기에 문제없음
        return "list/board_list";
    }
/*    // mybatis에서 구현한 BoardDAO 객체를 Spring(Autowired로)이 주입해줌 = new BoardDAO 안해도됨
    // 이제 Mapper가 붙은 BoardDAO를 통해 xml파일의 sql쿼리를 읽어서 그 안의 실제 구현코드를 생성할 수 있음
    private final BoardRepository boardDAO;

    @GetMapping({"/", "/list"})
    // RequestParam : Http 요청 쿼리 파라미터(url의 ?뒤 키-값)를 자바 메서드의 변수로 자동 연결해줌
    public String list(BoardListRequest request,
                       // required = false로 함으로써 혹시라도 사용자가 검색을 사용하지 않아도 검색에 오류가 없음
                       // Model : 컨트롤러와 jsp연결
                       Model model) {

        *//** 아래와 같은 로직들은 service 계층에 있으면 좋을 것 같음 **//*

        int pageSize = 10;

        // 건너뛸 페이지 수
        int pageNumber = (request.getPageNumber() == 0) ? 1 : request.getPageNumber();
        int skipPage = (pageNumber - 1) * pageSize;

        // getlist를 통해 여러 행의 데이터들이 있을것이므로 이를 list객체에 담아 하나로 사용한다.
        List<BoardDTO> list = boardDAO.getList(skipPage, request.getBoardCategory(), request.getSearchKeyword());

        // addAttribute = setAttribute
        model.addAttribute("list", list);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("boardCategory", request.getBoardCategory());
        model.addAttribute("searchKeyword", request.getSearchKeyword());

        // application.properties의 prefix와 suffix를 통해 앞에 자동으로 붙여 읽기에 문제없음
        return "list/board_list";
        }*/


/*    // TODO : RequestParam 쿼리들은 responseDTO로
    @GetMapping("/view")
    public String view(@RequestParam int boardID, Model model) {
        BoardDTO boardDTO = boardDAO.getBoard(boardID);

        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            return "view/board_view";
        } else {
            model.addAttribute("errorMessage", "해당 게시글을 찾을 수 없음");
            return "error/error_page";
        }
    }

    // write페이지를 보여줄때
    @GetMapping("/write")
    public String write() {
        return "write/board_write";
    }

    @PostMapping("/writeAction")
    public String writeAction(@ModelAttribute BoardDTO boardDTO, Model model) {
        // TODO : @vaild 사용
        if (boardDTO.getCategory() == null || boardDTO.getWriter() == null || boardDTO.getPassword() == null || boardDTO.getTitle() == null || boardDTO.getContent() == null ||
                boardDTO.getCategory().isEmpty() || boardDTO.getWriter().isEmpty() || boardDTO.getPassword().isEmpty() || boardDTO.getTitle().isEmpty() || boardDTO.getContent().isEmpty()) {

            *//** errorMessage는 일단 하나로**//*
            // TODO : 에러 핸들러 사용
            model.addAttribute("errorMessage", "모든 항목을 입력해주세요.");
            return "write/board_write";
        }

        boardDTO.setAvailable(1);

        int result = boardDAO.write(boardDTO);

        // 정상 글쓰기 시 목록페이지로 이동
        if (result == 1) {
            return "redirect:/boards/free/list";
        } else {
            model.addAttribute("errorMessage", "글 작성에 실패했습니다.");
            return "write/board_write";
        }
    }

    @GetMapping("/modify")
    public String modify(@RequestParam int boardID, Model model) {
        BoardDTO boardDTO = boardDAO.getBoard(boardID);

        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            return "modify/board_modify";
        } else {
            model.addAttribute("errorMessage", "수정할 게시글을 찾을 수 없습니다.");
            return "error/error_page";
        }
    }

    @PostMapping("/modifyAction")
    // ModelAttribute를 통해 예를 들어 input name이 boardTitle이면 자동으로 DTO의 setTitle로 연결됨
    public String modifyAction(@ModelAttribute BoardDTO boardDTO, Model model) {
        if (boardDTO.getBoardID() == 0 || boardDTO.getWriter() == null || boardDTO.getPassword() == null || boardDTO.getTitle() == null || boardDTO.getContent() == null ||
                boardDTO.getWriter().isEmpty() || boardDTO.getPassword().isEmpty() || boardDTO.getTitle().isEmpty() || boardDTO.getContent().isEmpty()) {
            model.addAttribute("errorMessage", "모든 정보를 입력하세요.");
            return "modify/board_modify";
        }

        int result = boardDAO.modify(
                boardDTO.getBoardID(),
                boardDTO.getWriter(),
                boardDTO.getPassword(),
                boardDTO.getTitle(),
                boardDTO.getContent()
        );

        // 정상적으로 값이 전부 담겼다면..
        if (result == 1) {
            return "redirect:/boards/free/view?boardID=" + boardDTO.getBoardID();
        } else {
            model.addAttribute("errorMessage", "수정에 실패했습니다.");
            model.addAttribute("board", boardDTO);
            return "modify/board_modify";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int boardID, Model model) {
        BoardDTO boardDTO = boardDAO.getBoard(boardID);
        if (boardDTO != null) {
            model.addAttribute("board", boardDTO);
            return "delete/board_checkPassword";
        } else {
            model.addAttribute("errorMessage", "삭제할 게시글을 찾을 수 없습니다.");
            return "error/error_page";
        }
    }

    @PostMapping("/deleteAction")
    public String deleteAction(@RequestParam int boardID, @RequestParam String password, Model model) {

        BoardDTO boardDTO = boardDAO.getBoard(boardID);

        // id가 null이면..
        if (boardDTO == null) {
            model.addAttribute("errorMessage", "삭제할 게시글을 찾을 수 없습니다.");
            return "error/error_page";
        }

        *//** equals를 쓸 때 먼저 조건을 따져야하는부분을 앞으로 하기 ex) "a".equals(~) vs ~.equals("a")**//*
        // 비밀번호가 일치하지 않으면..
        if (!boardDTO.getPassword().equals(password)) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("board", boardDTO);
            return "delete/board_checkPassword";
        }

        boardDAO.delete(boardID); //TODO: throwable exception
        return "redirect:/boards/free/list";

*//*
        int result = boardDAO.delete(boardID);
        if (result == 1) {
            return "redirect:/boards/free/list";
        } else {
            model.addAttribute("errorMessage", "삭제 처리에 실패했습니다.");
            return "view/board_view";
        }

    }*/
}