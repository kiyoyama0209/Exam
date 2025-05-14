<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生情報変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
        学生情報変更
      </h2>

      <!-- エラーメッセージ -->
      <c:if test="${not empty error}">
        <div class="alert alert-danger text-center mb-3">
          <c:out value="${error}" escapeXml="false"/>
        </div>
      </c:if>

      <!-- ===== フォーム ===== -->
      <form action="${pageContext.request.contextPath}/StudentUpdateExecute.action"
            method="post"
            class="mx-4">

        <!-- 入学年度（閲覧のみ） -->
        <div class="row mb-3">
          <label class="col-2 col-form-label">入学年度</label>
          <div class="col">
            <input type="text" class="form-control-plaintext"
                   value="${student.entYear}" readonly>
          </div>
        </div>

        <!-- 学生番号（閲覧のみ／送信用） -->
        <div class="row mb-3">
          <label class="col-2 col-form-label">学生番号</label>
          <div class="col">
            <input type="text" name="no" class="form-control-plaintext"
                   value="${student.no}" readonly>
          </div>
        </div>

        <!-- 氏名（編集可） -->
        <div class="row mb-3">
          <label class="col-2 col-form-label">氏名</label>
          <div class="col">
            <input type="text" name="name" class="form-control"
                   value="${student.name}" required maxlength="30">
          </div>
		          <!-- ↓ これを追記（黄色エラー） -->
		    <c:if test="${not empty errorName}">
		      <div class="form-text text-warning">${errorName}</div>
		    </c:if>
		        </div>

        <!-- クラス（編集可） -->
        <div class="row mb-3">
          <label class="col-2 col-form-label">クラス</label>
          <div class="col">
            <select name="classNum" class="form-select">
              <c:forEach var="c" items="${classNums}">
                <option value="${c.classNum}"
                  <c:if test="${student.classNum == c.classNum}">selected</c:if>>
                  ${c.classNum}
                </option>
              </c:forEach>
            </select>
          </div>
			          <!-- ↓ これを追記（黄色エラー） -->
			    <c:if test="${not empty errorName}">
			      <div class="form-text text-warning">${errorName}</div>
			    </c:if>
			  </div>


        <!-- 在学中（編集可に変更） -->
        <div class="row mb-4">
          <div class="col-2">在学中</div>
          <div class="col d-flex align-items-center">
            <input type="checkbox" name="isAttend" value="true"
                   class="form-check-input"
                   <c:if test="${student.isAttend}">checked</c:if> />
          </div>
        </div>

        <!-- ボタン -->
        <div class="text-end">
          <button type="submit" class="btn btn-primary me-2">変更</button>
          <a href="${pageContext.request.contextPath}/StudentList.action"
             class="btn btn-secondary">戻る</a>
        </div>
      </form>
    </section>
  </c:param>
</c:import>
