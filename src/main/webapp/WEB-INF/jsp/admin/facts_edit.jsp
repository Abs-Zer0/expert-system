<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/admin/facts">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<form:form class="w-100 text-center" action="/admin/facts/${command}" method="POST" modelAttribute="fact">
    <div class="form-group row">
        <form:label class="col-2" path="name">Название факта</form:label>
        <form:input class="form-control col-8" path="name" type="text" placeholder="name" value="${fact.getName()}"/>
        <form:errors class="text-danger col-2" path="name"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="args">Список параметров</form:label>
        <form:input class="form-control col-8" path="args" type="text" placeholder="arguments" value="${fact.getArgs()}"/>
        <form:errors class="text-danger col-2" path="args"/>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${commandMsg}</button>
    </div>
</form:form>