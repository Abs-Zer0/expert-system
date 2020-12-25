<%-- any content can be specified here e.g.: --%>
<%@ page pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row m-0 pb-2">
    <div class="col2 text-center">
        <a class="btn btn-primary" href="/expert/productions">Назад</a>
    </div>
</div>

<c:if test="${isError}">
    <div class="alert alert-danger">${errorMsg}</div>
</c:if>

<form:form class="w-100 text-center" action="/expert/productions/${command}" method="POST" modelAttribute="production">
    <form:input path="id" type="hidden" value="${production.getId()}"/>
    <div class="form-group row">
        <form:label class="col-2" path="target">Установить цель</form:label>
        <form:input class="form-control col-8" path="target" type="text" placeholder="target" autocomplete="false" value="${production.getTarget()}"/>
        <form:errors class="text-danger col-2" path="target"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="facts">Установить факты для достижения цели</form:label>
        <form:input class="form-control col-8" path="facts" type="text" placeholder="facts" value="${production.getFacts()}"/>
        <form:errors class="text-danger col-2" path="facts"/>
    </div>
    <div class="form-group row">
        <form:label class="col-2" path="label">Краткое описание</form:label>
        <form:input class="form-control col-8" path="label" type="text" value="${production.getLabel()}"/>
        <form:errors class="text-danger col-2" path="label"/>
    </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${commandMsg}</button>
    </div>
</form:form>

<script>
    $(document).ready(function () {
        let configTarget = {
            source: getFacts,
            focus: function (event, ui) {
                event.preventDefault();
            },
            select: function (event, ui) {
            }
        }
        let configFacts = {
            source: getFacts,
            focus: function (event, ui) {
                event.preventDefault();
            },
            select: function (event, ui) {
                event.preventDefault();
                let val = $('#facts').val();
                $('#facts').val((val.substring(0, val.lastIndexOf(',') + 1) + ' ' + ui.item.value).trim());
            }
        };

        $('#target').autocomplete(configTarget);
        $('#facts').autocomplete(configFacts);
    });

    function getFacts(request, response) {
        $.ajax({
            method: 'GET',
            url: '/expert/productions/facts/filtered?tmp=' + request.term.replace(request.term.substring(0, request.term.lastIndexOf(',') + 1), '').trim(),
            dataType: 'json'
        }).done(function (res) {
            response($.map(res.facts, function (fact) {
                var val = fact.name + "(";
                for (var i = 0; i < fact.argsAmount; i++) {
                    val += "X";
                    if (i < fact.argsAmount - 1)
                        val += ", ";
                }
                val += ")";

                return{
                    label: fact.name,
                    value: val
                }
            }));
        });
    }
</script>