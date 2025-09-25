package com.study.connection.dao;

import com.study.connection.dto.BoardDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// db와 통신하고 데이터를 crud하기 위해서 생성
public class BoardDAO {

    // db 접근
    private static final String dbURL = "jdbc:mysql://localhost:3306/ebrainsoft_study";
    private static final String USER = "ebsoft";
    private static final String PASS = "ebsoft";
    static {
        System.out.println();
    }

    // jdbc로 db 연결 및 생성자 생성 (추후 객체 생성 가능)
    public BoardDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 게시글 작성 시
    public int write(BoardDTO boardDTO) {
        // BoardDTO 객체에 사용자가 입력한 정보들을 board테이블에 저장
        String SQL = "INSERT INTO board (category, title, writer, password, content, available) VALUES (?, ?, ?, ?, ?, ?)";
        // 동시에 db 작업을 수행하기 위해 이렇게 따로 다시 생성함
        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             // sql쿼리 실행 전 미리 변수 값을 채워둠(여기서는 사용자가 입력한 데이터를 의미함)
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setString(1, boardDTO.getCategory());
            ps.setString(2, boardDTO.getTitle());
            ps.setString(3, boardDTO.getWriter());
            ps.setString(4, boardDTO.getPassword());
            ps.setString(5, boardDTO.getContent());
            ps.setInt(6, boardDTO.getAvailable());
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        // catch문이 이뤄질경우 = 입력안됨 = 반환 실패 = -1 반환
        return -1;

    }

    // 게시글 목록 조회
    // 목록을 조회할때는 이 3가지가 필요하다고 느낌
    public List<BoardDTO> getList(int pageNumber, String boardCategory, String searchKeyword) {
        // 활성화된 게시글만 조회
        String SQL = "SELECT * FROM board WHERE available = 1";

        // 값이 비지 않다면 이전 조건(available) + 카테고리에 대한 바인딩 즉, 값이 적절히 연결될때를 추가
        if (boardCategory != null && !boardCategory.isEmpty() && !"전체".equals(boardCategory)) {
            SQL += " AND category = ?";
        }
        // 검색창에서는 앞선 조건에 추가로 제목이나 작성자가 포함되면 추가
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            SQL += " AND (title LIKE ? OR writer LIKE ?)";
        }

        // 이제 이를 바탕으로 번호순으로 게시글을 10개씩 나타냄
        SQL += " ORDER BY boardID DESC LIMIT 10 OFFSET ?";

        // 게시글 목록을 담을 list 생성
        List<BoardDTO> list = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            int paramIndex = 1;

            //카테고리 조건 바인딩
            if (boardCategory != null && !boardCategory.isEmpty() && !"전체".equals(boardCategory)) {
                ps.setString(paramIndex++, boardCategory);
            }
            // 키워드 조건 바인딩
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchKeyword + "%");
                ps.setString(paramIndex++, "%" + searchKeyword + "%");
            }

            //1부터 시작해서 10개씩 가져옴
            ps.setInt(paramIndex, (pageNumber - 1) * 10);

            // 결과집합의 모든 행을 순회하면서 BoardDTO 객체로 변환하고 리스트에 추가
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BoardDTO dto = new BoardDTO();
                    dto.setBoardID(rs.getInt("boardID"));
                    dto.setCategory(rs.getString("category"));
                    dto.setTitle(rs.getString("title"));
                    dto.setWriter(rs.getString("writer"));
                    dto.setViews(rs.getInt("views"));
                    dto.setCreatedAt(rs.getTimestamp("createdAt"));
                    dto.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    dto.setContent(rs.getString("content"));
                    dto.setAvailable(rs.getInt("available"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 다음 페이지가 있는지 확인
    public boolean nextPage(int pageNumber) {
        String SQL = "SELECT EXISTS(SELECT 1 FROM board WHERE available = 1 ORDER BY boardID DESC LIMIT 1 OFFSET ?) as hasNext";
        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, pageNumber * 10);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("hasNext");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //보기
    public BoardDTO getBoard(int boardID) {
        String SQL = "SELECT * FROM board WHERE boardID = ?";
        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setInt(1, boardID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BoardDTO dto = new BoardDTO();
                    // DB테이블에서 boardID라는 컬럼의 값을 가져와서 dto의 BoardID 필드에 그 값을 넣음
                    dto.setBoardID(rs.getInt("boardID"));
                    dto.setCategory(rs.getString("category"));
                    dto.setTitle(rs.getString("title"));
                    dto.setWriter(rs.getString("writer"));
//                    dto.setPassword(rs.getString("password"));
                    dto.setViews(rs.getInt("views"));
                    dto.setCreatedAt(rs.getTimestamp("createdAt"));
                    dto.setUpdatedAt(rs.getTimestamp("updatedAt"));
                    dto.setContent(rs.getString("content"));
                    dto.setAvailable(rs.getInt("available"));
                    return dto;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 게시글을 찾지 못함 = 객체이므로 null반환
        return null;
    }

    // 해당 ID를 가지고 작성자, 비밀번호, 제목, 내용 4가지를 수정 가능 = write메서드와 약간 비슷함
    public int modify(int boardId, String boardWriter, String boardPassword, String boardTitle, String boardContent) {
        // BoardDTO 객체에 사용자가 입력한 정보들을 board테이블에 저장
        String SQL = "UPDATE board SET writer = ?, password = ?, title = ?, content = ? WHERE boardID = ?";
        // 동시에 db 작업을 수행하기 위해 이렇게 따로 다시 생성함
        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             // sql쿼리 실행 전 미리 변수 값을 채워둠(여기서는 사용자가 입력한 데이터를 의미함)
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setString(1, boardWriter);
            ps.setString(2, boardPassword);
            ps.setString(3, boardTitle);
            ps.setString(4, boardContent);
            ps.setInt(5, boardId);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        // catch문이 이뤄질경우 = 입력안됨 = 반환 실패 = -1 반환
        return -1;
    }

    public int delete (int boardID) {
        // BoardDTO 객체에 사용자가 입력한 정보들을 board테이블에 저장
        // available = 0을 만듦으로써 db에는 데이터가 남아있어도 화면에서는 안보이게 됨
        String SQL = "UPDATE board SET available = 0 WHERE boardID = ?";
        // 동시에 db 작업을 수행하기 위해 이렇게 따로 다시 생성함
        try (Connection conn = DriverManager.getConnection(dbURL, USER, PASS);
             // sql쿼리 실행 전 미리 변수 값을 채워둠(여기서는 사용자가 입력한 데이터를 의미함)
             PreparedStatement ps = conn.prepareStatement(SQL)) {

            ps.setInt(1, boardID);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        // catch문이 이뤄질경우 = 입력안됨 = 반환 실패 = -1 반환
        return -1;
    }
}