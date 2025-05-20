<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
<c:param name="title">
    得点管理システム
</c:param>

	<c:param name="scripts"/>

	<c:param name="content">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
.login-title {
  background-color: #f8f9fa;
  padding: 0.75rem 1rem;
  font-size: 1.25rem;
}
.login-input {
  background-color: #eef5ff;
}
</style>

<section class="me-4">
<!-- ログインタイトル -->
<h2 class="login-title text-center">ログイン</h2>

  <!-- action先を必ずLoginExecute.actionにし、methodはPOST -->
<form class="px-4 my-5 mx-auto" style="max-width: 400px;"
        action="LoginExecute.action" method="post">
<div class="mb-3">
<label for="id" class="form-label">ID</label>
<!-- 以前の「value="${id}"」を移行 -->
<input type="text" class="form-control login-input" id="id" name="id"
             value="${id}" required>
</div>

    <div class="mb-3">
<label for="password" class="form-label">パスワード</label>
<input type="password" class="form-control login-input" id="password" name="password" required>
</div>

    <div class="mb-4 form-check">
<input type="checkbox" class="form-check-input" id="showPassword">
<label for="showPassword" class="form-check-label">パスワードを表示</label>
</div>

    <button type="submit" class="btn btn-primary w-100">ログイン</button>
</form>

  <!-- エラーメッセージ表示（エラーがあるときだけ） -->
<c:if test="${not empty error}">
<p style="color:red;" class="text-center">${error}</p>
</c:if>
</section>

<script>
const showPasswordCheckbox = document.getElementById("showPassword");
const passwordField = document.getElementById("password");
showPasswordCheckbox.addEventListener("change", () => {
  if (showPasswordCheckbox.checked) {
    passwordField.type = "text";
  } else {
    passwordField.type = "password";
  }
});
</script>

</c:param>
</c:import>