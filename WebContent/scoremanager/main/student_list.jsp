<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>

<!-- 上部操作（件数・新規登録） -->
      <div class="d-flex justify-content-between align-items-center px-4 mb-2">
        <div>
        </div>
        <div>
<a href="${pageContext.request.contextPath}/StudentCreate.action">新規登録</a>

        </div>
      </div>
<!-- 絞り込みフォーム -->
+ <form action="${pageContext.request.contextPath}/StudentList.action" method="post">
  <div class="border rounded p-3 mx-3 mb-3">
    <div class="row align-items-center gy-2 gx-3">

      <!-- 入学年度 -->
      <div class="col-12 col-sm-4"><!-- ★ 幅を広げた -->
        <label for="f1" class="col-form-label mb-1 fw-bold">入学年度</label>
        <select name="f1" id="f1"
                class="form-select w-100"
                style="min-width:200px;">   <!-- ★ 200px キープ -->
          <option value="">--</option>
          <c:forEach var="y" items="${years}">
            <option value="${y}" <c:if test="${param.f1 == y}">selected</c:if>>${y}</option>
          </c:forEach>
        </select>
      </div>

      <!-- クラス -->
      <div class="col-12 col-sm-4">
        <label for="f2" class="col-form-label mb-1 fw-bold">クラス</label>
        <select name="f2" id="f2"
                class="form-select w-100"
                style="min-width:200px;">
          <option value="">--</option>
          <c:forEach var="c" items="${classNums}">
            <option value="${c.classNum}" <c:if test="${param.f2 == c.classNum}">selected</c:if>>${c.classNum}</option>
          </c:forEach>
        </select>
      </div>

      <!-- 在学中 -->
		<div class="col-auto d-flex align-items-center">
		  <!-- ★ チェックボックスに me-3 を追加し、右側に余白を確保 -->
		  <input type="checkbox" name="f3" id="f3" value="true"
		         class="form-check-input me-3"
		         <c:if test="${param.f3 == 'true'}">checked</c:if> />
		  <label for="f3" class="col-form-label fw-bold mb-0">在学中</label>
		</div>


      <!-- 絞り込みボタン -->
      <div class="col-auto ms-auto">
        <button type="submit" class="btn btn-secondary">絞り込み</button>
      </div>
    </div>
  </div>
</form>



		<!-- 上部操作（件数・新規登録） -->
      <div class="d-flex justify-content-between align-items-center px-4 mb-2">
        <div>
          <c:if test="${not empty students}">
            <p class="m-0">検索結果：${fn:length(students)}件</p>
          </c:if>
        </div>
      </div>

      <!-- 一覧表示 -->
      <div class="px-4">
        <c:choose>
          <c:when test="${empty students}">
            <div class="border rounded p-4 text-center bg-light">学生情報が存在しませんでした。</div>
          </c:when>
          <c:otherwise>
 		<table class="table">
			  <thead>
			    <tr>
			      <th>入学年度</th>
			      <th>学生番号</th>
			      <th>氏名</th>
			      <th>クラス</th>
			      <!-- ★ 在学中だけ中央寄せ -->
			      <th class="text-center">在学中</th>
			      <th class="text-center">操作</th>
			    </tr>
			  </thead>
			  <tbody>
			    <c:forEach var="s" items="${students}">
			      <tr>
			        <td>${s.entYear}</td>
			        <td>${s.no}</td>
			        <td>${s.name}</td>
			        <td>${s.classNum}</td>
			        <!-- ★ 在学中セルを中央寄せ -->
			        <td class="text-center">
			          <c:choose>
			            <c:when test="${s.attend}">○</c:when>
			            <c:otherwise>×</c:otherwise>
			          </c:choose>
			        </td>
			        <!-- 操作列は元々中央寄せ -->
			        <td class="text-center">
			          <a href="${pageContext.request.contextPath}/StudentUpdate.action?no=${s.no}">変更</a>
			        </td>
			      </tr>
			    </c:forEach>
			  </tbody>
			</table>
          </c:otherwise>
        </c:choose>
      </div>
    </section>
  </c:param>
</c:import>
