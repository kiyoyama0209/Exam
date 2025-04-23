<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報削除</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <!-- タイトル -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        科目情報削除
      </h2>

      <!-- 削除確認メッセージ -->
      <div class="text-center my-5">
        <p>
          「<strong>${subject.name}(${subject.cd})</strong>」を削除してもよろしいですか
        </p>
      </div>

      <!-- 削除ボタン -->
      <div class="text-center mb-4">
        <form action="${pageContext.request.contextPath}/SubjectDeleteExecute.action" method="post">
          <input type="hidden" name="cd" value="${subject.cd}">
          <button type="submit" class="btn btn-danger">削除</button>
        </form>
      </div>

      <!-- 戻るリンク -->
      <div class="text-center">
        <a href="${pageContext.request.contextPath}/SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
