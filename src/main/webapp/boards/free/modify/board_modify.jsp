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
<!-- view와 마찬가지로 modify도 Controller에게 boardID 확인을 맡기고 그 객체를 가져옴 -->
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

        <form method="post" action="<%= request.getContextPath() %>/boards/free/modifyAction">
            <table class="table table-striped" style="text-align:center; border:1px solid #dddddd">
                <!-- thead는 테이블의 제목부분에 해당함 -->
                <thead>
                <tr>
                    <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 수정</th>
                </tr>
                <!-- 테이블의 하나의 행을 말함(한 줄)-->
                </thead>
                <!-- tbody 같은 경우는 위에 지정해준 속성 아래에 하나씩 출력해주는 역할 -->
                <tbody>
                <!-- TODO: 수정불가한 내용은 나중에 -->
<%--                <tr>--%>
<%--                    <td><input type="text" class="form-control" placeholder="카테고리" name="boardCategory" maxlength="50"--%>
<%--                               value="<%= boardDTO.getCategory() %>">--%>
<%--                    </td>--%>
<%--                </tr>--%>


<%--                <tr>--%>
<%--                    <td><input type="text" class="form-control" placeholder="등록 일시" name="boardCreatedAt" maxlength="50"--%>
<%--                               value="<%= boardDTO.getCreatedAt() %>">--%>
<%--                    </td>--%>
<%--                </tr>--%>


<%--                <tr>--%>
<%--                    <td><input type="text" class="form-control" placeholder="수정 일시" name="boardUpdatedAt" maxlength="50"--%>
<%--                               value="<%= boardDTO.getUpdatedAt() %>">--%>
<%--                    </td>--%>
<%--                </tr>--%>

<%--                <tr>--%>
<%--                    <td><input type="text" class="form-control" placeholder="조회수" name="boardViews" maxlength="50"--%>
<%--                               value="<%= boardDTO.getViews() %>">--%>
<%--                    </td>--%>
<%--                </tr>--%>
                <!-- 어떤 boardID를 수정해야하는지 알려줌 -->
                <input type="hidden" name="boardID" value="<%= boardDTO.getBoardID() %>">

                <tr>
                    <td><input type="text" class="form-control" placeholder="작성자" name="boardWriter" maxlength="50"
                               value="<%= boardDTO.getWriter() %>">
                    </td>
                </tr>

                <tr>
                    <td><input type="text" class="form-control" placeholder="비밀번호" name="boardPassword"
                               maxlength="50">
                    </td>
                </tr>

                <tr>
                    <td><input type="text" class="form-control" placeholder="제목" name="boardTitle" maxlength="50"
                               value="<%= boardDTO.getTitle() %>">
                    </td>
                </tr>

                <tr>
                    <!-- textarea이기 때문에 value없음 -->
                    <td><textarea class="form-control" placeholder="내용" name="boardContent" maxlength="2048">
                            <%= boardDTO.getContent()%></textarea></td>
                </tr>
                </tbody>
            </table>
            <input type="submit" class="btn btn-primary pull-right" value="저장">
        </form>
        <!-- 테이블 자체는 글의 목록을 보여주는 역할밖에 하지않는다. 글을 쓸수있는 화면으로 넘어갈 수 있는 태그 설정-->
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