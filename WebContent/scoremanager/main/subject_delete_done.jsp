<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報削除完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">

      <!-- タイトル -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        科目情報削除
      </h2>

      <!-- 完了メッセージ -->
      <div class="text-center my-5">
        <div class="alert alert-success text-center m-0" role="alert">
          科目情報を削除しました。
        </div>
      </div>

      <!-- 戻るリンク -->
      <div class="text-center">
        <a href="SubjectList.action">科目一覧</a>
      </div>
    </section>
  </c:param>
</c:import>
