<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/admin">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<div class="alert alert-danger">Перенесено в пользовательскую часть</div>

<form class="w-100 text-center" action="/admin/field/setsize" method="GET">
    <div class="form-group row">
        <input class="form-control col-2" name="width" type="number" min="1" step="1" placeholder="width of field"/>
        <input class="form-control col-2" name="height" type="number" min="1" step="1" placeholder="height of field"/>
        <button class="btn btn-secondary col-2" type="submit">Изменить размер игрового поля</button>
    </div>
</form>