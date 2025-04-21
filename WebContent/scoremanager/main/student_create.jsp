<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
  <c:param name="title">学生登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生登録</h2>

      <form action="StudentCreateExecute.action" method="post" class="px-4">

        <!-- 入学年度 -->
        <div class="mb-3">
          <label class="form-label fw-bold">入学年度</label>
          <div class="input-group">
            <select name="entYear" class="form-select" required>
              <option value="" disabled <c:if test="${student.entYear == 0}">selected</c:if>>------</option>
              <c:forEach var="y" items="${years}">
                <option value="${y}" <c:if test="${student.entYear == y}">selected</c:if>>${y}</option>
              </c:forEach>
            </select>
          </div>
          <c:if test="${not empty errorEntYear}">
            <div class="form-text text-warning">${errorEntYear}</div>
          </c:if>
        </div>

        <!-- 学生番号 -->
        <div class="mb-3">
          <label class="form-label fw-bold">学生番号</label>
          <div class="input-group">
            <input type="text" name="no" class="form-control" required
                   value="${student.no}">
          </div>
          <c:if test="${not empty errorNo}">
            <div class="form-text text-warning">${errorNo}</div>
          </c:if>
        </div>

        <!-- 氏名 -->
        <div class="mb-3">
          <label class="form-label fw-bold">氏名</label>
          <div class="input-group">
            <input type="text" name="name" class="form-control" required
                   value="${student.name}">
          </div>
          <c:if test="${not empty errorName}">
            <div class="form-text text-warning">${errorName}</div>
          </c:if>
        </div>

        <!-- クラス -->
        <div class="mb-3">
          <label class="form-label fw-bold">クラス</label>
          <div class="input-group">
            <select name="classNum" class="form-select" required>
              <c:forEach var="c" items="${classNums}">
                <option value="${c.classNum}" <c:if test="${student.classNum == c.classNum}">selected</c:if>>${c.classNum}</option>
              </c:forEach>
            </select>
          </div>
        </div>

        <!-- 在学中 -->
        <div class="form-check mb-4">
          <input class="form-check-input" type="checkbox" name="isAttend" value="true" id="attend"
            <c:if test="${student.attend}">checked</c:if>>
          <label class="form-check-label fw-bold" for="attend">在学中</label>
        </div>

        <!-- ボタン -->
        <div class="text-center">
          <button type="submit" class="btn btn-primary px-5">登録して保存</button>
        </div>
      </form>

      <!-- 戻るリンク -->
      <div class="text-start mt-3">
        <a href="${pageContext.request.contextPath}/StudentList.action">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
