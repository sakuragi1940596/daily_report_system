<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- パッケージ名.クラス名でjavaのクラスをインポートすることで、JSPで使用可能となる --%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>
<%-- このJSPで使用する変数をc:setmで定義し、その値を定数に設定した値とする --%>
<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<%-- 相対パスで共通レイアウトであるapp.jspをimport。employeesの１つ上の階層にlayoutが存在 --%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <%-- 成功時のフラッシュメッセージを表示。${}は、スコープに格納された値や冒頭で定義した変数を使用するためのもの --%>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <%--  表の１行目に、項目名を設定する --%>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <%-- 表の２行目以降に上記各項目の値を入れる。拡張for文で処理 --%>
                <%-- RequestScopneに格納されたemployeesオブジェクトを使って、各従業員の値をemployeeに代入する --%>
                <%-- varStatusとはループ処理の状態を表す変数である。状態とは回数などをいう。 --%>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <%-- クラスにループ回数を２で割った余を指定して、rowが０の時と１の時の２つをこの１行で表現 --%>
                    <tr class="row${status.count % 2}">
                        <%-- c:outでemployeeに順次代入されるemployeesの情報のうち、codeを表の１列目、nameを２列目に表示 --%>
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                            <%-- chooseで従業員の論理削除の有無（０か１）で 場合分け--%>
                            <c:choose>
                                <%-- 定数を利用し、論理削除の値と従業員情報のフィールドのdeleteFlagが同じなら、３行目を削除されましたとする。 --%>
                                <c:when test="${employee.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <%-- 削除されてない場合は、従業員idをURLのparameterに追加して、その従業員情報の詳細画面へのタグを作る --%>
                                    <a href="<c:url value='?action=${actEmp}&command=${commShow}&id=${employee.id}' />">詳細を見る</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
             <%-- RequestScopeのemployeesに格納されている従業員情報の件数を取得 --%>
            （全 ${employees_count} 件）<br />
            <%-- for文を利用し、１ページ目あたりの表示件数の最大値である１５件（maxRow）で従業員情報の件数を割った値の数だけ処理を繰り返す。 --%>
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actEmp}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <%-- 従業員新規登録のurlを定義したタグ --%>
        <p><a href="<c:url value='?action=${actEmp}&command=${commNew}' />">新規従業員の登録</a></p>

    </c:param>
</c:import>