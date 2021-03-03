<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %> <%-- include를 해버리기--%>

<div class="container">

    <%-- <form action="/user/join" method="POST"> <%-- 요건 옛날 방식 요즘은 json으로 데이터를 전달한다. --%>
    <form>
        <div class="form-group">
            <label for="email">Email address:</label>
            <input type="email" class="form-control" placeholder="Enter email" id="email">
        </div>
        
        <div class="form-group">
            <label for="username">username:</label>
            <input type="text" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="pwd">Password:</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>
    </form>
     <button id="btn-save" class="btn btn-primary">회원가입 완료</button>
</div>

<script src="/js/user/user.js"></script>


<%@ include file="../layout/footer.jsp" %>     <%-- include를 해버리기--%>

