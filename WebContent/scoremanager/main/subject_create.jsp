<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">科目登録</c:param>
  <c:param name="scripts">
    <!-- クライアントサイドバリデーション用のスクリプト -->
    <script>
      document.addEventListener("DOMContentLoaded", function() {
        const form = document.querySelector("form");
        const codeInput = document.querySelector("input[name='code']");
        const errorNo = document.querySelector("#codeError");

        form.addEventListener("submit", function(event) {
          // 科目コードが3文字でない場合、エラーメッセージを表示
          if (codeInput.value.length !== 3) {
            event.preventDefault(); // フォーム送信をキャンセル
            errorNo.style.display = "block";
          } else {
            errorNo.style.display = "none"; // 正しい場合、エラーメッセージを非表示
          }
        });
      });
    </script>
  </c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目登録</h2>

      <form action="SubjectCreateExecute.action" method="post" class="px-4">
        <!-- 科目コード -->
        <div class="mb-3">
          <label class="form-label fw-bold">科目コード</label>
          <div class="input-group">
            <input type="text" name="code" class="form-control" required value="${code}">
          </div>
          <!-- エラーメッセージ表示エリア -->
          <div id="codeError" class="form-text text-warning" style="display: none;">
            ※科目コードは3文字で入力してください
          </div>
          <c:if test="${not empty errorNo}">
            <div class="form-text text-warning">${errorNo}</div>
          </c:if>
        </div>

        <!-- 科目名 -->
        <div class="mb-3">
          <label class="form-label fw-bold">科目名</label>
          <div class="input-group">
            <input type="text" name="name" class="form-control" required value="${name}">
          </div>
          <c:if test="${not empty errorName}">
            <div class="form-text text-warning">${errorName}</div>
          </c:if>
        </div>

        <!-- ボタン -->
        <div class="text-center">
          <button type="submit" class="btn btn-primary px-5">登録して保存</button>
        </div>
      </form>

      <!-- 戻るリンク -->
      <div class="text-start mt-3">
        <a href="SubjectList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
