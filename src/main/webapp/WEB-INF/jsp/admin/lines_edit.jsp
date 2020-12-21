<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/admin/lines">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<form:form class="w-100 text-center" action="/admin/lines/${command}" method="POST" modelAttribute="line">
    <form:input path="id" type="hidden"/>
    <div class="form-group row">
        <form:label class="col-2" path="label">Короткое описание</form:label>
        <form:input class="form-control col-8" path="label" type="text" placeholder="description" value="${line.getLabel()}"/>
        <form:errors class="text-danger col-2" path="label"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="dx">dX</form:label>
        <form:input class="form-control col-8" path="dx" type="number" step="1" placeholder="dX" value="${line.getDx()}"/>
        <form:errors class="text-danger col-2" path="dx"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="dy">dY</form:label>
        <form:input class="form-control col-8" path="dy" type="number" step="1" placeholder="dY" value="${line.getDy()}"/>
        <form:errors class="text-danger col-2" path="dy"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="maxLength">Максимальная длина</form:label>
        <form:input class="form-control col-8" path="maxLength" type="number" min="1" step="1" placeholder="max length" value="${line.getMaxLength()}"/>
        <form:errors class="text-danger col-2" path="maxLength"/>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${commandMsg}</button>
    </div>
</form:form>