<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<%-- labelとinputで各入力項目を作成。inputのname属性でlabelと関連づける。valueで指定したemployeeオブジェクトの従業員番号が初期値。
     空のemployeeインスタンスが存在することで、空の初期値を表示することができる。 --%>
<label for="${AttributeConst.EMP_CODE.getValue()}">社員番号</label><br />
<%-- nameに指定した値をフォームの受け取り先で記述することで、入力された内容を利用できる。nameで指定した値が変数で、その中に入力内容
が格納されているイメージである。 --%>
<input type="text" name="${AttributeConst.EMP_CODE.getValue()}" value="${employee.code}" />
<br /><br />

<label for="${AttributeConst.EMP_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.EMP_NAME.getValue()}" value="${employee.name}" />
<br /><br />

<label for="${AttributeConst.EMP_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.EMP_PASS.getValue()}" />
<br /><br />

<label for="${AttributeConst.EMP_ADMIN_FLG.getValue()}">権限</label><br />
<%-- selectによって、リストにして一般権限と管理者権限を選択できるようにする。 --%>>
<select name="${AttributeConst.EMP_ADMIN_FLG.getValue()}">
    <%-- 選択したのが０なら一般を、１なら管理者とリストに表示するようにする。 --%>
    <option value="${AttributeConst.ROLE_GENERAL.getIntegerValue()}"<c:if test="${employee.adminFlag == AttributeConst.ROLE_GENERAL.getIntegerValue()}"> selected</c:if>>一般</option>
    <option value="${AttributeConst.ROLE_ADMIN.getIntegerValue()}"<c:if test="${employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}"> selected</c:if>>管理者</option>
</select>
<br /><br />
<%-- hidden要素として、従業員idと_tokenを投稿時に送信するようにする。これが、CSRF対策でSessionScopeにtokenidとして格納される。 --%>
<input type="hidden" name="${AttributeConst.EMP_ID.getValue()}" value="${employee.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>