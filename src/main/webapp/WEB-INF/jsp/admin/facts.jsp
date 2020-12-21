<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row m-0 pb-2">
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/admin">Назад</a>
    </div>
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/admin/facts/add">Добавить</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>
<c:if test="${isSuccess}">
    <div class="alert alert-success">${successMsg}</div>
</c:if>

<div class="row m-0">
    <div class="col-3 border border-primary pt-1 pb-1 text-center">Название</div>
    <div class="col-5 border border-primary pt-1 pb-1 text-center">Параметры</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
</div>
<c:forEach items="${facts}" var="fact">
    <div class="row m-0 align-items-stretch">
        <div class="col-3 border border-secondary pt-1 pb-1 text-center">${fact.getName()}</div>
        <div class="col-5 border border-secondary pt-1 pb-1 text-center">${fact.getArgs()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <a class="btn btn-secondary w-100" href="/admin/facts/edit/${figure.getName()}">&#128393;</a>
        </div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <a class="btn btn-secondary w-100" href="/admin/facts/remove/${figure.getName()}">&#128465;</a>
        </div>
    </div>
</c:forEach>