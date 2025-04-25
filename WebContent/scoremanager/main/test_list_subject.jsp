<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績一覧</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧</h2>

      <div class="table-responsive mx-3">
        <table class="table table-bordered table-hover align-middle text-center">
          <thead class="table-light">
            <tr>
              <th>学生番号</th>
              <th>氏名</th>
              <th>テスト種別</th>
              <th>点数</th>
              <th>順位</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty testListSubjectList}">
                <tr>
                  <td colspan="5" class="text-center">該当する成績データがありません</td>
                </tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="test" items="${testListSubjectList}">
                  <tr>
                    <td>${test.student.id}</td>
                    <td>${test.student.name}</td>
                    <td>${test.test.name}</td>
                    <td>${test.point}</td>
                    <td>${test.rank}</td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>

      <!-- 戻るボタン -->
      <div class="mt-4 mx-3">
        <a href="${pageContext.request.contextPath}/TestListSubjectForm.action" class="btn btn-secondary">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>