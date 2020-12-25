<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/expert/facts">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<form:form class="w-100 text-center" action="/expert/facts/${command}" method="POST" modelAttribute="fact">
    <form:checkbox path="canRemoved" class="d-none" value="${fact.getCanRemoved()}"/>
    <div class="form-group row">
        <form:label class="col-2" path="name">Название факта</form:label>
        <form:input class="form-control col-8" path="name" type="text" placeholder="name" value="${fact.getName()}"/>
        <form:errors class="text-danger col-2" path="name"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="argsAmount">Кол-во параметров</form:label>
        <form:input class="form-control col-8" path="argsAmount" type="number" min="1" step="1" placeholder="arguments" value="${fact.getArgsAmount()}"/>
        <form:errors class="text-danger col-2" path="argsAmount"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="isTarget">Является целью</form:label>
        <form:checkbox class="form-control col-1" path="isTarget" value="${fact.getIsTarget()}"/>
        <form:errors class="text-danger col-2" path="isTarget"/>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${commandMsg}</button>
    </div>
</form:form>