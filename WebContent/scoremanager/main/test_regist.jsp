<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

      <!-- ****** 検索フォーム ****** -->
      <form action="${pageContext.request.contextPath}/TestRegistExecute.action" method="get">
        <div class="border rounded p-3 mx-3 mb-3">
          <div class="row align-items-end gy-2 gx-3">

            <!-- 入学年度 -->
            <div class="col-12 col-lg-2">
              <label class="form-label fw-bold">入学年度</label>
              <select name="entYear" class="form-select">
                <option value="">------</option>
                <c:forEach var="y" items="${years}">
                  <option value="${y}" <c:if test="${selEntYear == y}">selected</c:if>>${y}</option>
                </c:forEach>
              </select>
            </div>

            <!-- クラス -->
            <div class="col-12 col-lg-2">
              <label class="form-label fw-bold">クラス</label>
              <select name="classNum" class="form-select">
                <option value="">------</option>
                <c:forEach var="c" items="${classNums}">
                  <option value="${c.classNum}" <c:if test="${selClassNum == c.classNum}">selected</c:if>>${c.classNum}</option>
                </c:forEach>
              </select>
            </div>

            <!-- 科目 -->
            <div class="col-12 col-lg-3">
              <label class="form-label fw-bold">科目</label>
              <select name="subjectCd" class="form-select">
                <option value="">------</option>
                <c:forEach var="s" items="${subjects}">
                  <option value="${s.cd}" <c:if test="${selSubjectCd == s.cd}">selected</c:if>>${s.name}</option>
                </c:forEach>
              </select>
            </div>

            <!-- 回数 -->
            <div class="col-12 col-lg-2">
              <label class="form-label fw-bold">回数</label>
              <select name="no" class="form-select">
                <c:forEach var="n" begin="1" end="2">
                  <option value="${n}" <c:if test="${selNo == n}">selected</c:if>>${n}</option>
                </c:forEach>
              </select>
            </div>

            <!-- 検索 -->
            <div class="col-auto">
              <button type="submit" class="btn btn-secondary">検索</button>
            </div>
          </div>
        </div>
      </form>

      <!-- メッセージ -->
      <c:if test="${not empty error}">
        <p class="text-warning mx-3">${error}</p>
      </c:if>
      <c:if test="${not empty message}">
        <p class="text-success mx-3">${message}</p>
      </c:if>

      <!-- ****** 学生一覧＋得点入力 ****** -->
      <c:if test="${not empty students}">
        <form action="${pageContext.request.contextPath}/TestRegistExecute.action" method="post">
          <!-- 検索条件を hidden で持ち回る -->
          <input type="hidden" name="mode"      value="register">
          <input type="hidden" name="entYear"   value="${selEntYear}">
          <input type="hidden" name="classNum"  value="${selClassNum}">
          <input type="hidden" name="subjectCd" value="${selSubjectCd}">
          <input type="hidden" name="no"        value="${selNo}">

          <div class="mx-3 mt-3">
            <p class="fw-bold">
              科目：<c:forEach var="s" items="${subjects}" varStatus="st">
                    <c:if test="${s.cd == selSubjectCd}">${s.name}</c:if>
                  </c:forEach>
              （${selNo}回）
            </p>

            <table class="table table-bordered align-middle">
              <thead class="table-light text-center">
                <tr>
                  <th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>点数</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="st" items="${students}">
                  <tr>
                    <td>${st.entYear}</td>
                    <td>${st.classNum}</td>
                    <td>
                      ${st.no}
                      <input type="hidden" name="studentNo" value="${st.no}">
                    </td>
                    <td>${st.name}</td>
                    <td>
                      <input type="text" name="point_${st.no}" class="form-control text-end"
                             style="max-width:6rem;"
                             value="${pointMap[st.no]}">
                      <div class="form-text">0～100の範囲</div>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>

            <button type="submit" class="btn btn-secondary">登録して終了</button>
          </div>
        </form>
      </c:if>
    </section>
  </c:param>
</c:import>
