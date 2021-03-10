<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp" %> <%-- include를 해버리기--%>

<div class="container">
<%-- header에  jstl을 넣어서 forEach 문을 사용할 수 있다.--%>
<c:forEach var="board" items="${boards.content}">
<div class="card m-3" > <%-- 카드 마진주기 m-1 추가, width를 없애서 넓따란 카드로 만들기, 이런걸 복붙해서 여러개 만들어 버리기 --%>
  <div class="card-body">
    <h4 class="card-title">${board.title}</h4>
    <p class="card-text">태그 내용</p>
    <a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
  </div>
</div>
</c:forEach>
<%-- paging 처리 --%>
<ul class="pagination justify-content-center">
  <c:choose>
    <c:when test="${boards.first}">
      <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
    </c:when>
    <c:otherwise>
      <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
    </c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${boards.last}">
      <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
    </c:when>
    <c:otherwise>
      <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
    </c:otherwise>    
  </c:choose>
</ul>

</div>
<%@ include file="layout/footer.jsp" %>     <%-- include를 해버리기--%>

