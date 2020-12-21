<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/admin/figures">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<form:form class="w-100 text-center" action="/admin/figures/${command}" method="POST" modelAttribute="figure">
    <div class="form-group row">
        <form:label class="col-2" path="name">Название фигуры</form:label>
        <form:input class="form-control col-8" path="name" type="text" placeholder="name" value="${figure.getName()}"/>
        <form:errors class="text-danger col-2" path="name"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="rank">Ранг фигуры</form:label>
        <form:input class="form-control col-8" path="rank" type="number" min="1" step="1" placeholder="rank" value="${figure.getRank()}"/>
        <form:errors class="text-danger col-2" path="rank"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="travelLine">Линии хода</form:label>
        <form:select class="form-control col-8" path="travelLine" items="${lines}" multiple="true" itemValue="id" itemLabel="label"/>
        <form:errors class="text-danger col-2" path="travelLine"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="fireLine">Линии атаки</form:label>
        <form:select class="form-control col-8" path="fireLine" items="${lines}" multiple="true" itemValue="id" itemLabel="label"/>
        <form:errors class="text-danger col-2" path="fireLine"/>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${commandMsg}</button>
    </div>
</form:form>

<script type="text/javascript">
    $(document).ready(function () {
        $('#travelLine').multiselect({
            buttonText: function (options, select) {
                return 'Select lines';
            },
            maxHeight: 400,
            buttonWidth: '50vw',
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true
        });
        $('#fireLine').multiselect({
            buttonText: function (options, select) {
                return 'Select lines';
            },
            maxHeight: 400,
            buttonWidth: '50vw',
            enableFiltering: true,
            enableCaseInsensitiveFiltering: true
        });
    });
</script>