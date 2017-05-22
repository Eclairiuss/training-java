<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" >
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" >
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" >
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/2.3.1/css/flag-icon.min.css" rel="stylesheet"/>
</head>

<body>

<mylib:header redirection="./dashboard" language="${language}" id="0" search="${search}" order="${order}" size="${size}" page="${page}"/>

<script>
    var message = '<spring:message code="computer.validator" text="default text" />';
    var view = '<spring:message code="computer.view" text="default text" />';
    var deleteButton = '<spring:message code="computer.delete" text="default text" />';
</script>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            ${numberComputers} <spring:message code="computer.list" text="default text" />
        </h1>

        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="./dashboard" method="GET" class="form-inline">
                    <input type="hidden" name="ACTION" value="search">
                    <input type="hidden" name="size" value="${size}">
                    <input type="hidden" name="language" value="${language}">
                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="computer.search" text="default text" />" />
                    <input type="submit" id="searchsubmit" value="<spring:message code="computer.filter" text="default text" />"
                           class="btn btn-primary" />
                </form>
            </div>
            <div class="pull-right">
                <a class="btn btn-success" id="addComputer" href="add_computer?language=${language}"><spring:message code="computer.addComputer" text="default text" /></a>
                <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="computer.delete" text="default text" /></a>
            </div>
        </div>
    </div>

    <form id="deleteForm" action="./dashboard" method="POST">
        <input type="hidden" name="ACTION" value="delete">
        <input type="hidden" name="language" value="${language}">
        <input type="hidden" name="size" value="${size}">
        <input type="hidden" name="page" value="${page}">
        <input type="hidden" name="selection" value="">
        <input type="hidden" name="order" value="${order}">
        <input type="hidden" name="search" value="${search}">
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->

                <th class="editMode" style="width: 60px; height: 22px; display: none">
                    <input type="checkbox" id="selectall" />
                    <span style="vertical-align: top; ">
                        <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                            <i class="fa fa-trash-o fa-lg"></i>
                        </a>
                    </span>
                </th>

                <th>
                    <spring:message code="computer.name" text="default text" />
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=nu" class="fa fa-arrow-up" aria-hidden="true"></a>
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=nd" class="fa fa-arrow-down" aria-hidden="true"></a>
                </th>
                <th>
                    <spring:message code="computer.introduced" text="default text" />
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=iu" class="fa fa-arrow-up" aria-hidden="true"></a>
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=id" class="fa fa-arrow-down" aria-hidden="true"></a>
                </th>
                <!-- Table header for Discontinued Date -->
                <th>
                    <spring:message code="computer.discontinued" text="default text" />
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=du" class="fa fa-arrow-up" aria-hidden="true"></a>
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=dd" class="fa fa-arrow-down" aria-hidden="true"></a>
                </th>
                <!-- Table header for Company -->
                <th>
                    <spring:message code="computer.company" text="default text" />
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=cu" class="fa fa-arrow-up" aria-hidden="true"></a>
                    <a href="./dashboard?language=${language}&size=${size}&search=${search}&order=cd" class="fa fa-arrow-down" aria-hidden="true"></a>
                </th>
            </tr>
            </thead>
            <!-- Browse attribute computers -->
            <tbody id="results">

            <c:forEach var="computer" items="${computers}">
                <tr>
                    <td class="editMode" style="display: none">
                        <input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
                    </td>
                    <td>
                        <a href="edit_computer?language=${language}&id=${computer.getId()}" onclick="">
                            <c:choose>
                                <c:when test="${computer.getName() == null}">NAME IS NULL ??</c:when>
                                <c:when test="${computer.getName().equals('')}">NO NAME :(</c:when>
                                <c:otherwise>${computer.getName()}</c:otherwise>
                            </c:choose>
                        </a>
                    </td>
                    <td> ${computer.getIntroduced()}</td>
                    <td> ${computer.getDiscontinued()}</td>
                    <td> ${computer.getCompanyName()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<footer class="navbar-fixed-bottom">
    <div class="container text-center">
        <mylib:pagination redirection="./dashboard" numberComputers="${numberComputers}" page="${page}" size="${size}" search="${search}" order="${order}" language="${language}" />
    </div>
</footer>
<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/dashboard.js"/>"></script>

</body>
</html>