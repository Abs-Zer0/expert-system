<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row m-0 pb-2">
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/expert">Назад</a>
    </div>
    <div class="col-2 text-center">
        <a class="btn btn-primary w-100" href="/expert/facts/add">Добавить</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>
<c:if test="${isSuccess}">
    <div class="alert alert-success">${successMsg}</div>
</c:if>

<div class="row m-0">
    <div class="col-4 border border-primary pt-1 pb-1 text-center">Название</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center">Кол-во параметров</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center">Является целью</div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
    <div class="col-2 border border-primary pt-1 pb-1 text-center"></div>
</div>
<c:forEach items="${facts}" var="fact">
    <div class="row m-0 align-items-stretch">
        <div class="col-4 border border-secondary pt-1 pb-1 text-center">${fact.getName()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">${fact.getArgsAmount()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">${fact.getIsTarget()}</div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <c:if test="${fact.getCanRemoved()}">
                <a class="btn btn-secondary w-100" href="/expert/facts/edit/${fact.getName()}">&#128393;</a>
            </c:if>
        </div>
        <div class="col-2 border border-secondary pt-1 pb-1 text-center">
            <c:if test="${fact.getCanRemoved()}">
                <a class="btn btn-secondary w-100" href="/expert/facts/remove/${fact.getName()}">&#128465;</a>
            </c:if>
        </div>
    </div>
</c:forEach>