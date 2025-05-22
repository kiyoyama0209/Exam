<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">ログアウト完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        ログアウト
      </h2>

      <!-- 完了メッセージ -->
      <c:set var="message" value="ログアウトが完了しました。" />
      <c:if test="${not empty message}">
        <div class="px-4">
          <div class="alert alert-success text-center m-0" role="alert">
            ${message}
          </div>
        </div>
      </c:if>

      <!-- 戻るリンク -->
      <div class="text-start ps-4 mt-4">
        <a href="../Login.action">ログイン画面へ</a>
      </div>
    </section>
  </c:param>
</c:import>
