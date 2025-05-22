<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>

      <!-- 上部操作（件数・新規登録） -->
      <div class="d-flex justify-content-between align-items-center px-4 mb-2">
        <div>
        </div>
        <div>
          <a href="SubjectCreate.action">新規登録</a>
        </div>
      </div>

      <!-- 一覧表示 -->
      <div class="px-4">
        <c:choose>
          <c:when test="${empty subject}">
            <div class="border rounded p-4 text-center bg-light">科目が登録されていません。</div>
          </c:when>
          <c:otherwise>
            <table class="table">
              <thead>
                <tr>
                  <th>科目コード</th>
                  <th>科目名</th>
                  <th></th>
                  <th class="text-center"></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="s" items="${subject}">
                  <tr>
                    <td>${s.cd}</td>
                    <td>${s.name}</td>
                    <td class="text-center">
                      <a href="SubjectUpdate.action?cd=${s.cd}">変更</a>

                    </td>
                    <td class="text-center">
                    	<a href="SubjectDelete.action?code=${s.cd}">削除</a>
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