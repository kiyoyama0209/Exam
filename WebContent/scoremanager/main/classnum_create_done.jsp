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
        <div class="mt-4 text-start">
          <a href="${pageContext.request.contextPath}/ClassNumList.action">一覧に戻る</a>
        </div>
      </div>
    </section>
  </c:param>
</c:import>
