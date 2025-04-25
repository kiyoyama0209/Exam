<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス新規登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス新規登録</h2>

      <form action="${pageContext.request.contextPath}/ClassNumCreateExecute.action" method="post" class="px-4">
        <div class="mb-3">
          <label for="classNum" class="form-label fw-bold">クラス番号</label>
          <input type="text" name="classNum" id="classNum" class="form-control w-25" required>
        </div>
        <button type="submit" class="btn btn-primary">登録</button>
      </form>
    </section>
  </c:param>
</c:import>
