<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %> <%-- include를 해버리기--%>

<div class="container">

        <button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
        <c:if test="${board.user.id == principal.user.id}">
            <a href="/board/${board.id}/updateForm" class="btn btn-warning">글 수정</a>
            <button id="btn-delete" class="btn btn-danger">글 삭제</button>
        </c:if>
        <br/>        
        글 번호 : <span id="id"><i>${board.id} </i></span>
        작성자 : <i>${board.user.username} </i></span>
        <br/><br/>

        <div class="form-group">
            <h3>${board.title}</h3>
        </div>


        <hr/>
        <div class="form-group">
          <label for="content">Content</label>
          <div>
            ${board.content}
          </div>
        </div>
  <hr/>


</div>


<script src="/js/user/board.js"></script>
<%@ include file="../layout/footer.jsp" %>     <%-- include를 해버리기--%>

