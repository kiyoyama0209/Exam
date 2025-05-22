<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">クラス管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <style>
      .table-hover tbody tr:hover {
        background-color: #ffffff !important; /* ← ホバーしても白固定 */
      }
    </style>

    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>

      <!-- 上部操作（件数・新規登録） -->
      <div class="d-flex justify-content-between align-items-center px-4 mb-3">
        <div>

        </div>
        <div>
          <a href="ClassNumCreate.action">新規登録</a>
        </div>
      </div>

      <!-- 一覧表示 -->
      <div class="px-4">
        <table class="table">
          <thead style="background-color: #ffffff;">
            <tr>
              <th scope="col">クラス番号</th>
              <th scope="col" class="text-center">操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="c" items="${list}">
              <tr>
                <td class="align-middle">${c.classNum}</td>
                <td class="text-center align-middle">
                  <a href="ClassNumUpdate.action?classNum=${c.classNum}">変更</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </section>
  </c:param>
</c:import>
