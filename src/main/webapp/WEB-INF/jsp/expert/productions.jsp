<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row m-0 pb-2">
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/expert">Назад</a>
    </div>
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/expert/productions/add">Добавить</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>
<c:if test="${isSuccess}">
    <div class="alert alert-success">${successMsg}</div>
</c:if>

<div class="row m-0">
    <div class="col-1 border border-primary pt-1 pb-1 text-center">ID</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center">Цель</div>
    <div class="col-3 border border-primary pt-1 pb-1 text-center">Факты</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center">Краткое описание</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
</div>
<c:forEach items="${productions}" var="production">
    <div class="row m-0 align-items-stretch">
        <div class="col-1 border border-secondary pt-1 pb-1 text-center">${production.getId()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">${production.getTarget()}</div>
        <div class="col-3 border border-secondary pt-1 pb-1 text-center">${production.getFacts()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">${production.getLabel()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <a class="btn btn-secondary w-100" href="/expert/productions/edit/${production.getId()}">&#128393;</a>
        </div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <a class="btn btn-secondary w-100" href="/expert/productions/remove/${production.getId()}">&#128465;</a>
        </div>
    </div>
</c:forEach>