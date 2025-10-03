<%@ page import="java.util.ArrayList" %>
<%@ page import="com.study.connection.dto.BoardListItemResponse" %>
<%@ page import="com.study.connection.dto.BoardCategoryResponse" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
    <title>JSP 게시판 웹 사이트</title>
    <style type="text/css">
        body {
            background-color: #f0f0f0; /* 전체 배경색 */
            font-family: 'Malgun Gothic', '맑은 고딕', sans-serif;
            font-size: 13px; /* 전체 폰트 크기 조정 */
        }

        .container-custom {
            width: 800px; /* 고정 폭 */
            margin: 20px auto; /* 중앙 정렬 및 상하 여백 */
            padding: 20px;
            background-color: white;
            border: 1px solid #ddd;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 그림자 */
        }

        .header-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
            margin-bottom: 20px;
        }

        .header-section h2 {
            font-size: 18px;
            font-weight: bold;
            margin: 0;
        }

        .search-area {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 20px;
            background-color: #f9f9f9; /* 검색 영역 배경색 */
            display: flex;
            flex-wrap: wrap; /* 작은 화면에서 줄바꿈 */
            align-items: center;
            gap: 10px; /* 요소 간 간격 */
        }

        .search-area .form-group {
            display: flex;
            align-items: center;
            white-space: nowrap; /* 줄바꿈 방지 */
        }

        .search-area .form-group label {
            margin-right: 5px;
            font-weight: bold;
        }

        .search-area input[type="text"],
        .search-area select {
            border: 1px solid #ccc;
            padding: 5px;
            font-size: 12px;
            height: 28px; /* 입력 필드 높이 조정 */
        }

        .search-area .btn-search {
            background-color: #28a745; /* 초록색 버튼 */
            color: white;
            border: 1px solid #28a745;
            padding: 5px 12px;
            font-size: 12px;
            cursor: pointer;
            height: 28px;
            line-height: 1; /* 텍스트 중앙 정렬 */
        }

        .search-area .search-count {
            margin-left: auto; /* 우측 정렬 */
            font-weight: bold;
            color: #555;
        }

        .board-table {
            width: 100%;
            border-collapse: collapse; /* 셀 간격 없애기 */
            margin-bottom: 20px;
        }

        .board-table th, .board-table td {
            border: 1px solid #ddd; /* 모든 셀 테두리 */
            padding: 8px;
            text-align: center;
            vertical-align: middle; /* 세로 중앙 정렬 */
            font-size: 12px;
        }

        .board-table th {
            background-color: #e6e6e6; /* 헤더 배경색 */
            font-weight: bold;
            white-space: nowrap; /* 헤더 텍스트 줄바꿈 방지 */
        }

        .board-table tbody tr:nth-child(even) { /* 짝수 행 배경색 */
            background-color: #f8f8f8;
        }

        .board-table td:nth-child(2) { /* 제목 컬럼 */
            text-align: left;
            padding-left: 10px; /* 왼쪽 여백 */
        }

        .board-table a {
            color: black; /* 링크 기본 색상 */
            text-decoration: none; /* 밑줄 제거 */
        }

        .board-table a:hover {
            text-decoration: underline; /* 호버 시 밑줄 */
        }

        .highlight-title {
            color: red;
            font-weight: bold;
        }

        .pagination-area {
            display: flex;
            justify-content: center; /* 중앙 정렬 */
            align-items: center;
            margin-top: 20px;
            gap: 5px; /* 페이지 링크 간 간격 */
        }

        .pagination-area a {
            display: inline-block;
            padding: 5px 10px;
            border: 1px solid #ccc;
            text-decoration: none;
            color: #333;
            border-radius: 3px;
            font-size: 12px;
        }

        .pagination-area a.current-page {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
            font-weight: bold;
        }

        .pagination-area a:hover:not(.current-page) {
            background-color: #eee;
        }

        .btn-register {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 13px;
            cursor: pointer;
            margin-top: 20px; /* 테이블과 분리 */
            float: left; /* 좌측 정렬 */
        }

        .navbar-default {
            background-color: #f8f8f8;
            border-color: #e7e7e7;
        }

        .navbar-brand, .navbar-nav > li > a {
            color: #777;
        }

        .navbar-nav > .active > a, .navbar-nav > .active > a:hover, .navbar-nav > .active > a:focus {
            color: #555;
            background-color: #e7e7e7;
        }
    </style>
</head>
<body>
<!--TODO : 이제 "전체 카테고리" 항목을 추가하고 실제 카테고리 로직을 수행할 수 있도록 넘겨주거나 받아야함 -->
<%
    ArrayList<BoardListItemResponse> list = (ArrayList<BoardListItemResponse>) request.getAttribute("list");
    Integer pageNumberObject = (Integer) request.getAttribute("pageNumber");
    int pageNumber = (pageNumberObject != null) ? pageNumberObject : 1;
    Boolean hasNextPage = (Boolean) request.getAttribute("nextPage");

    // 폼 상태 유지를 위한 검색 파라미터 가져오기
    String currentCategory = (String) request.getAttribute("boardCategory");
    String currentKeyword = (String) request.getAttribute("searchKeyword");

    // 이 예시에서는 총 게시물 수를 가져오지 않으므로, '총 5건'은 임시로 고정
    int totalCount = (list != null) ? list.size() : 0; // 실제로는 DAO에서 count를 가져와야 함

    // 컨트롤러가 전달한 카테고리 리스트를 categories 객체에 담음
    List<BoardCategoryResponse> categories = (List<BoardCategoryResponse>) request.getAttribute("categories");
%>

<div class="container-custom">
    <div class="header-section">
        <h2>자유 게시판 - 목록</h2>
    </div>

    <div class="search-area">
        <div class="form-group">
            <span class="search-count">총 <%= totalCount %>건</span>
        </div>

        <div class="form-group">
            <label for="regDateFrom">등록일</label>
            <input type="text" id="regDateFrom" name="regDateFrom" value="2019.12.23" style="width: 80px;">
            <span>-</span>
            <input type="text" id="regDateTo" name="regDateTo" value="2019.12.23" style="width: 80px;">
        </div>

        <div class="form-group">
            <!-- 사용자가 무언가 선택시 "boardCategory"로 controller에 전달-->
            <select name="boardCategory" style="width: 100px;">
                <!-- 사용자가 "전체 카테고리를 고르면 controller에는 빈 값이 전달되며, 카테고리는 그 항목으로 유지됨-->
                <option value=""
                    <c:if test="${empty boardCategory || boardCategory == ''}">selected</c:if>>
                </option>

                <!-- 사용자가 고른 카테고리(id)가 이전에 선택했던 카테고리인지 확인 후 같으면 그대로 유지 (= 새로고침 시 유지)-->
                <c:forEach var="category" items="<%= categories %>">
                    <option value="${category.id}"
                            <c:if test="${category.id == boardCategory}">selected</c:if>>
                            ${category.name}
                    </option>
                </c:forEach>

            </select>
        </div>

        <div class="form-group">
            <input type="text" name="searchKeyword" placeholder="제목/작성자/내용"
                   value="<%= currentKeyword != null ? currentKeyword : "" %>" style="width: 150px;">
        </div>

        <input type="submit" value="검색" class="btn-search">
    </div>

    <table class="board-table">
        <thead>
        <tr>
            <th>카테고리</th>
            <th>제목</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>등록 일시</th>
            <th>수정 일시</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (list != null && !list.isEmpty()) {
                for (BoardListItemResponse board : list) {
        %>
        <tr>
            <td><%= board.getBoardCategory() %>
            </td>
            <td>
                <%-- 요청에 맞는 boardID를 가져와서 controller에 요청함 --%>
                <a href="<%= request.getContextPath() %>/boards/free/view?boardID=<%= board.getBoardId() %>"
                   class="<%= "게시된 화면".equals(board.getBoardTitle()) ? "highlight-title" : "" %>">
                    <%= board.getBoardTitle() %>
                </a>
            </td>
            <td><%= board.getBoardWriter()%>
            </td>
            <td><%= board.getBoardViews()%>
            </td>
            <td><%= board.getBoardCreatedAt()%>
            </td>
            <td><%= board.getBoardUpdatedAt()%>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6">게시물이 없습니다.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="pagination-area">
        <%
            String pageLinkPrefix = request.getContextPath() + "/boards/free/list?";
            if (currentCategory != null && !currentCategory.isEmpty())
                pageLinkPrefix += "&boardCategory=" + currentCategory;
            if (currentKeyword != null && !currentKeyword.isEmpty())
                pageLinkPrefix += "&searchKeyword=" + currentKeyword;

            // '이전' 버튼
            if (pageNumber > 1) {
        %>
        <a href="<%= pageLinkPrefix %>&pageNumber=<%= pageNumber - 1 %>"> << </a>
        <%
        } else {
        %>
        <a href="#" style="color: #ccc; cursor: default;"> << </a>
        <%
            }

            // 1부터 10까지 페이지 번호 출력
            for (int i = 1; i <= 10; i++) {
        %>
        <a href="<%= pageLinkPrefix %>&pageNumber=<%= i %>"
           class="<%= (pageNumber == i) ? "current-page" : "" %>"><%= i %>
        </a>
        <%
            }

            // '다음' 버튼
            if (hasNextPage != null && hasNextPage) {
        %>
        <a href="<%= pageLinkPrefix %>&pageNumber=<%= pageNumber + 1 %>"> >> </a>
        <%
        } else {
        %>
        <a href="#" style="color: #ccc; cursor: default;"> >> </a>
        <%
            }
        %>
    </div>

    <a href="<%= request.getContextPath() %>/boards/free/write" class="btn-register">등록</a>

</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js"
        integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y"
        crossorigin="anonymous"></script>
</body>
</html>