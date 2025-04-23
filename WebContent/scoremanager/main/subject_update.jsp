<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <!-- ① タイトル行 -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        科目情報変更
      </h2>

      <!-- ======== 変更フォーム ======== -->
      <form action="${pageContext.request.contextPath}/SubjectUpdateExecute.action"
            method="post"
            class="px-4">

        <!-- ② 科目コード (表示のみ) -->
        <label class="form-label fw-bold">科目コード</label>
        <p class="mb-3">${subject.cd}</p>
        <input type="hidden" name="cd" value="${subject.cd}">

        <!-- ③ 科目名 (入力) -->
        <label class="form-label fw-bold">科目名</label>
        <input type="text"
               name="name"
               class="form-control mb-3"
               value="${subject.name}"
               maxlength="60"
               required>
        <c:if test="${not empty errorName}">
          <div class="form-text text-warning mb-2">${errorName}</div>
        </c:if>

        <!-- ④ 変更ボタン -->
        <button type="submit" class="btn btn-primary">変更</button>
      </form>

      <!-- ⑤ 戻るリンク -->
      <div class="text-start mt-4">
        <a href="${pageContext.request.contextPath}/SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
