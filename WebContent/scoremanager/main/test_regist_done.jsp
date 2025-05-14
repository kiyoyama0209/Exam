<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>

        <div class="px-4">
          <div class="alert alert-success text-center m-0" role="alert">
            登録完了しました。
          </div>
        </div>

      <div class="text-start ps-4 mt-4">
        <!-- 一覧へ戻る (検索画面) -->
        <a href="${pageContext.request.contextPath}/TestRegist.action">成績登録へ戻る</a>
      </div>
      <div class="text-start ps-4 mt-4">
      	<a href="${pageContext.request.contextPath}/TestList.action">成績参照を表示</a>
      </div>
    </section>
  </c:param>
</c:import>
