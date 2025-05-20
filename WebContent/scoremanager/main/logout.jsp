<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/common/base.jsp">
  <c:param name="title">ログアウト確認</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <div class="container mt-5 text-center">
      <div class="mb-4">
        <i class="bi bi-box-arrow-right" style="font-size: 3rem; color: #dc3545;"></i>
      </div>
      <h2 class="mb-3">ログアウトしますか？</h2>
      <p class="text-muted mb-4">この操作でセッションが終了し、ログイン画面へ戻ります。</p>

<!--  <form action="Logout.action" method="post" class="d-flex justify-content-center gap-3">
        <button type="submit" class="btn btn-danger px-4">ログアウト</button>
        <a href="menu.jsp" class="btn btn-outline-secondary btn-lg px-4">キャンセル</a>
      </form>
 -->
	  <a href="${pageContext.request.contextPath}/Login.action">
	  <button type="submit" class="btn btn-danger px-4">ログアウト</button></a>
        <a href="menu.jsp" class="btn btn-outline-secondary px-4">キャンセル</a>

    </div>
  </c:param>
</c:import>
