<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <!-- base.jsp の <main class="flex-fill"> 内にそのまま埋め込まれる -->
    <section class="container mt-4">

      <!-- 見出し -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

      <!-- ****** 検索フォーム ****** -->
      <form action="TestRegistExecute.action" method="get">
        <div class="border rounded p-3 shadow-sm">
          <div class="row gx-3 gy-2 align-items-end">

            <!-- 入学年度 -->
            <div class="col-6 col-lg-2">
              <label class="form-label fw-bold">入学年度</label>
              <select name="entYear" class="form-select">
                <option value="">------</option>
                <c:forEach var="y" items="${years}">
                  <option value="${y}" <c:if test="${selEntYear == y}">selected</c:if>>${y}</option>
                </c:forEach>
              </select>
            </div>

            <!-- クラス -->
            <div class="col-6 col-lg-2">
              <label class="form-label fw-bold">クラス</label>
              <select name="classNum" class="form-select">
                <option value="">------</option>
                <c:forEach var="c" items="${classNums}">
                  <option value="${c.classNum}" <c:if test="${selClassNum == c.classNum}">selected</c:if>>
                    ${c.classNum}
                  </option>
                </c:forEach>
              </select>
            </div>

            <!-- 科目 -->
            <div class="col-6 col-lg-3">
              <label class="form-label fw-bold">科目</label>
              <select name="subjectCd" class="form-select">
                <option value="">------</option>
                <c:forEach var="s" items="${subjects}">
                  <option value="${s.cd}" <c:if test="${selSubjectCd == s.cd}">selected</c:if>>
                    ${s.name}
                  </option>
                </c:forEach>
              </select>
            </div>

            <!-- 回数 -->
            <div class="col-6 col-lg-2">
              <label class="form-label fw-bold">回数</label>
              <select name="no" class="form-select">
                <c:forEach var="n" begin="1" end="2">
                  <option value="${n}" <c:if test="${selNo == n}">selected</c:if>>${n}</option>
                </c:forEach>
              </select>
            </div>

            <!-- 検索ボタン -->
            <div class="col-auto">
              <button type="submit" class="btn btn-secondary px-4">検索</button>
            </div>
          </div>
        </div>
      </form>

      <!-- メッセージ -->
      <c:if test="${not empty error}">
        <p class="text-warning mt-2">${error}</p>
      </c:if>
      <c:if test="${not empty message}">
        <p class="text-success mt-2">${message}</p>
      </c:if>

      <!-- ****** 学生一覧 + 点数入力 ****** -->
      <c:if test="${not empty students}">
        <form action="TestRegistExecute.action" method="post" class="mt-4">
          <!-- 検索条件保持 -->
          <input type="hidden" name="mode"      value="register">
          <input type="hidden" name="entYear"   value="${selEntYear}">
          <input type="hidden" name="classNum"  value="${selClassNum}">
          <input type="hidden" name="subjectCd" value="${selSubjectCd}">
          <input type="hidden" name="no"        value="${selNo}">

          <p class="fw-bold">
            科目：
            <c:forEach var="s" items="${subjects}">
              <c:if test="${s.cd == selSubjectCd}">${s.name}</c:if>
            </c:forEach>
            （${selNo}回）
          </p>

          <!-- テーブル -->
          <div class="table-responsive-sm">
            <table class="table text-center">
				<tr>
                  <th scope="col">入学年度</th>
                  <th scope="col">クラス</th>
                  <th scope="col">学生番号</th>
                  <th scope="col">氏名</th>
                  <th scope="col" class="w-25">点数</th>
                </tr>
              <tbody>
                <c:forEach var="st" items="${students}">
                  <tr>
                    <td>${st.entYear}</td>
                    <td>${st.classNum}</td>
                    <td>
                      ${st.no}
                      <input type="hidden" name="studentNo" value="${st.no}">
                    </td>
                    <td class="text-start ps-3">${st.name}</td>
                    <td>
                      <input type="number" name="point_${st.no}" class="form-control text-end"
                             style="max-width:7rem;" min="0" max="100"
                             value="${pointMap[st.no]}">
                      <small class="text-warning">0～100の範囲で入力してください</small>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>

          <button type="submit" class="btn btn-secondary mt-2 px-4">登録して終了</button>
        </form>
      </c:if>

    </section>
  </c:param>
</c:import>
