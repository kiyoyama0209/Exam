<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス登録完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス登録完了</h2>

      <div class="px-4">
        <div class="alert alert-success text-center" role="alert">
          登録が完了しました。
        </div>

        <!-- 戻るリンク -->
      <div class="text-start ps-4 mt-4">
        <!-- 新規登録に戻る -->
        <a href="ClassNumCreate.action" class="me-4">
          戻る
        </a>
        <!-- 学生一覧に戻る -->
        <a href="ClassNumList.action">
          クラス一覧
        </a>
      </div>
    </section>
  </c:param>
</c:import>
