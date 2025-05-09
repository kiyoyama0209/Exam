<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績参照（学生単位）</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照（学生）</h2>

      <!-- 科目情報フォーム -->
      <form action="${pageContext.request.contextPath}/TestListSubjectExecute.action" method="post">
        <div class="border rounded p-3 mx-3 mb-0">
          <div class="row align-items-end gy-2 gx-3">
            <div class="col-auto">
              <span class="fw-bold">科目情報</span>
            </div>

            <div class="col-12 col-lg-2">
              <label for="entYear" class="form-label fw-bold">入学年度</label>
              <select name="entYear" id="entYear" class="form-select">
                <option value="">------</option>
                <c:forEach var="y" items="${years}">
                  <option value="${y}" <c:if test="${param.entYear == y}">selected</c:if>>${y}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-12 col-sm-2">
              <label for="classNum" class="col-form-label mb-1 fw-bold">クラス</label>
              <select name="classNum" id="classNum" class="form-select w-100" style="min-width:100px;">
                <option value="">--</option>
                <c:forEach var="c" items="${classNums}">
                  <option value="${c.classNum}" <c:if test="${param.classNum == c.classNum}">selected</c:if>>${c.classNum}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-12 col-lg-3">
              <label for="subjectCd" class="form-label fw-bold">科目</label>
              <select name="subjectCd" id="subjectCd" class="form-select">
                <option value="">------</option>
                <c:forEach var="s" items="${subjects}">
                  <option value="${s.cd}" <c:if test="${param.subjectCd == s.cd}">selected</c:if>>${s.name}</option>
                </c:forEach>
              </select>
            </div>

            <div class="col-auto">
              <button type="submit" class="btn btn-secondary">検索</button>
            </div>
          </div>
        </div>
      </form>

      <!-- 区切り線 -->
      <hr class="mx-3 my-0"/>

      <!-- 学生番号で検索 -->
      <form action="${pageContext.request.contextPath}/TestListByStudentId.action" method="post">
        <div class="border rounded p-3 mx-3 mt-0">
          <div class="row align-items-end gy-2 gx-3">
            <div class="col-auto">
              <span class="fw-bold">学生情報</span>
            </div>

            <div class="col-12 col-lg-6">
              <label for="studentId" class="form-label fw-bold">学生番号</label>
              <input type="text" name="studentId" id="studentId" class="form-control" placeholder="学生番号を入力してください" value="${param.studentId}"/>
            </div>

            <div class="col-auto">
              <button type="submit" class="btn btn-secondary">検索</button>
            </div>
          </div>
        </div>
      </form>

      <!-- 成績一覧 -->
      <c:if test="${not empty testListStudent}">
        <div class="mx-3 mt-4">
			<div class="mb-3 fw-bold">
			  氏名：${testListStudent[0].studentName}（${testListStudent[0].studentNo}）
			</div>
          <div class="table-responsive">
            <table class="table align-middle text-center border-bottom">
              <thead>
                <tr>
                  <th>科目コード</th>
                  <th>科目名</th>
                  <th>回数</th>
                  <th>点数</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="t" items="${testListStudent}">
                  <tr>
                    <td>${t.subjectCd}</td>
                    <td>${t.subjectName}</td>
                    <td>${t.num}</td>
                    <td>${t.point}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </c:if>

      <!-- データなし -->
      <c:if test="${empty testListStudent}">
        <div class="mx-3 mt-4 text-danger fw-bold">
          該当する成績は見つかりませんでした。
        </div>
      </c:if>
    </section>
  </c:param>
</c:import>