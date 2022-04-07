<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 定数クラスの定数を利用するためにimport --%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>
<%-- このJSPで使用する変数をc:setで定義しておく --%>
<c:set var="action" value="${ForwardConst.ACT_AUTH.getValue()}" />
<c:set var="command" value="${ForwardConst.CMD_LOGIN.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <%-- loginErrorがtrue、つまり存在しているなら、以下のエラーメッセージを表示する。 --%>
        <c:if test="${loginError}">
            <div id="flush_error">
                社員番号かパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ログイン</h2>
        <%-- AuthActionクラスのloginメソッドへ値を送る --%>
        <form method="POST" action="<c:url value='/?action=${action}&command=${command}' />">
            <label for="${AttributeConst.EMP_CODE.getValue()}">社員番号</label><br />
            <%-- 社員番号のname属性を持ち、初期値がcodeなので、パスワード間違えた場合は、社員番号が入力された状態でこの画面に戻る --%>
            <input type="text" name="${AttributeConst.EMP_CODE.getValue()}" value="${code}" />
            <br /><br />

            <label for="${AttributeConst.EMP_PASS.getValue()}">パスワード</label><br />
            <input type="password" name="${AttributeConst.EMP_PASS.getValue()}" />
            <br /><br />

            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
    </c:param>
</c:import>