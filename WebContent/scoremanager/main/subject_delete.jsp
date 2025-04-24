<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目削除</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <!-- タイトル -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

      <!-- エラーメッセージ -->
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <!-- 確認フォーム -->
      <form action="SubjectDeleteExecute.action" method="post" class="px-4">
        <input type="hidden" name="code" value="${subject.code}" />

        <!-- 削除メッセージ -->
        <div class="mb-3">
          <p class="fw-bold">
            「${subject.name}（${subject.code}）」を削除してもよろしいですか？
          </p>
        </div>

        <!-- ボタンあ -->
        <div class="mb-3">
          <button type="submit" class="btn btn-danger">削除</button>
        </div>

        <div class="mb-3">
          <a href="SubjectList.action">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>
