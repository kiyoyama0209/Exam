<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

      <form action="SubjectUpdateExecute.action" method="post" class="px-4">

		<!-- 科目コード -->
        <div class="mb-3">
          <label class="form-label fw-bold">科目コード</label>
          <p class="form-control-plaintext ps-0 mb-0"><c:out value="${subject.cd}"/></p>
          <input type="hidden" name="cd" value="${subject.cd}">
        </div>

        <c:if test="${not empty errorGeneral}">
          <div class="mb-3"> <%-- 他のフォームグループとの間隔を合わせるためにdivで囲み、mb-3クラスを適用 --%>
            <%-- 黄色い文字でエラーメッセージを表示します --%>
            <p class="text-warning mb-0"><c:out value="${errorGeneral}"/></p>
          </div>
        </c:if>
        <div class="mb-3">
          <label class="form-label fw-bold" >科目名</label>
          <input type="text" name="name" class="form-control" placeholder="科目名を入力してください"
                 value="<c:out value="${subject.name}"/>" maxlength="60" required>
          <c:if test="${not empty errorName}">
            <div class="form-text text-warning"><c:out value="${errorName}"/></div>
          </c:if>
        </div>

        <button type="submit" class="btn btn-primary">変更</button>
      </form>

      <div class="text-start ps-4 mt-4">
        <a href="SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>