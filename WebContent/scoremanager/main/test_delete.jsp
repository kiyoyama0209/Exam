<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績削除</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績削除</h2>

      <!-- 削除対象一覧 -->
      <form action="TestDeleteExecute.action" method="post">
        <input type="hidden" name="subjectCd" value="${subjectCd}">
        <input type="hidden" name="no" value="${no}">
        <div class="table-responsive px-4">
          <table class="table table-striped table-bordered">
            <thead>
              <tr>
                <th>削除</th>
                <th>学生番号</th>
                <th>クラス</th>
                <th>点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="t" items="${list}">
                <tr>
                  <td><input type="checkbox" name="studentNo" value="${t.studentNo}"></td>
                  <td>${t.studentNo}</td>
                  <td>${t.classNum}</td>
                  <td>${t.point}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="text-center my-4">
          <button type="submit" class="btn btn-danger">削除する</button>
        </div>
      </form>

      <!-- 戻るリンク -->
      <div class="text-start px-4">
        <a href="menu.jsp">メニューへ戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
