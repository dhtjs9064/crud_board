<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.study.connection.repository.BoardRepository" %>
<%@ page import="com.study.connection.dto.BoardDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- BbsDAO 함수를 사용하기때문에 가져오기 -->
<!-- DAO쪽을 사용하면 당연히 javaBeans도 사용되니 들고온다.-->
<!-- ArrayList같은 경우는 게시판의 목록을 가져오기위해 필요한 것 -->
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">  <!-- 반응형 웹에 사용하는 메타태그 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"> <!-- 참조  -->
    <title>게시판 등록</title>
    <style type="text/css">
    </style>
</head>
<body>
<!-- Controller에게 boardID 검증을 마치고 그 체를 얻음 -->
<%
    BoardDTO boardDTO = (BoardDTO) request.getAttribute("board");
%>
<!-- 웹사이트 공통메뉴 -->
<nav class="navbar navbar-default">
    <div class="navbar-header"> <!-- 홈페이지의 로고 -->
        <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
                aria-expand="false">
            <span class="icon-bar"></span><!-- 줄였을때 옆에 짝대기 -->
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<%= request.getContextPath() %>/boards/free/list">게시판 - 등록</a>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
            <li><a href="<%= request.getContextPath() %>/boards/free/list">메인</a></li>
            <!-- 현재의 게시판 화면이라는 것을 사용자에게 보여주는 부분 -->
            <li class="active"><a href="<%= request.getContextPath() %>/boards/free/list">게시판</a></li>
        </ul>
    </div>
    <!-- 공통메뉴 끝 -->
</nav>
<!-- 특정한 내용들을 담을 공간을 확보 해준다.-->
<div class="container">
    <!-- 테이블이 들어갈 수 있는 공간 구현 -->
    <div class="row">
        <!-- striped는 게시판 글목록을 홀수와 짝수로 번갈아가며 색이 변하게 해주는 하나의 요소  -->
        <table class="table table-striped" style="text-align:center; border:1px solid #dddddd">
            <!-- thead는 테이블의 제목부분에 해당함 -->
            <thead>
            <tr>
                <th colspan="3" style="background-color: #eeeeee; text-align: center;">게시글 - 보기</th>
            </tr>
            <!-- 테이블의 하나의 행을 말함(한 줄)-->
            </thead>
            <!-- tbody 같은 경우는 위에 지정해준 속성 아래에 하나씩 출력해주는 역할 -->
            <tbody>
            <!-- TODO:CrossSiteScript문제 해결해야함 -->
            <tr>
                <td style="width: 20%;">글 제목</td>
                <td colspan="2"><%= boardDTO.getTitle() %>
                </td>
            </tr>
            <tr>
                <td style="width: 20%;">작성자</td>
                <td colspan="2"><%= boardDTO.getWriter() %>
                </td>
            </tr>
            <tr>
                <td style="width: 20%;">등록 일시</td>
                <td colspan="2"><%= boardDTO.getCreatedAt() %>
                </td>
            </tr>
            <tr>
                <td style="width: 20%;">수정 일시</td>
                <td colspan="2"><%= boardDTO.getUpdatedAt() %>
                </td>
            </tr>
            <tr>
                <td style="width: 20%;">글 내용</td>
                <td colspan="2"><%= boardDTO.getContent() %>
                </td>
            </tr>
            </tbody>
        </table>
        <!-- 항상 컨트롤러에게 요청해야하기에 직접 jsp를 요청안함-->
        <!-- Todo : list도 id를 붙여야할까 -->
        <a href="<%= request.getContextPath() %>/boards/free/list" class="btn btn-primary">목록</a>
        <a href="<%= request.getContextPath() %>/boards/free/modify?boardID=<%= boardDTO.getBoardID() %>" class="btn btn-primary">수정</a>
        <a href="<%= request.getContextPath() %>/boards/free/delete?boardID=<%= boardDTO.getBoardID() %>" class="btn btn-primary">삭제</a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js"
        integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y"
        crossorigin="anonymous"></script>
</body>
</html>