<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス番号変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス番号変更</h2>

      <!-- エラーメッセージ -->
      <c:if test="${not empty errorClassNum}">
        <div class="alert alert-warning">${errorClassNum}</div>
      </c:if>

      <!-- 変更フォーム -->
      <form action="ClassNumUpdateExecute.action"
            method="post" class="px-4">

        <!-- 現在のクラス番号（表示のみ／送信用） -->
        <div class="mb-3">
          <label class="form-label fw-bold">現在のクラス番号</label>
          <p class="mb-0">${classNum.classNum}</p>
          <input type="hidden" name="oldClassNum" value="${classNum.classNum}" />
        </div>

        <!-- 新しいクラス番号 -->
        <div class="mb-3">
          <label for="classNum" class="form-label fw-bold">新しいクラス番号</label>
          <input type="text" id="classNum" name="classNum"
                 class="form-control w-25" maxlength="10" required />
        </div>

        <!-- ボタン -->
        <div class="text-start mb-3">
          <button type="submit" class="btn btn-primary">変更</button>
          <a href="ClassNumList.action"
             class="btn btn-secondary ms-2">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>
