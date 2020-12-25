<%-- 
    Document   : main
    Created on : 18 дек. 2020 г., 00:46:17
    Author     : Абс0лютный Н0ль
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>${title}</title>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/jquery-ui.min.css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap-multiselect.min.css"/>
    </head>
    <body>
        <script src="${contextPath}/resources/js/jquery-3.5.1.min.js"></script>
        <script src="${contextPath}/resources/js/jquery-ui.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.bundle.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap-multiselect.min.js"></script>
        <div class="container-fluid pt-2 pb-2">
            <jsp:include page="${viewName}.jsp"/>
        </div>
    </body>
</html>
