<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="redirection" required="true" type="java.lang.String" description="redirection for the new page" %>
<%@ attribute name="language" required="true" type="java.lang.String" description="order of the current page" %>
<%@ attribute name="id" required="true" type="java.lang.String" description="order of the current page" %>
<%@ attribute name="page" required="true" type="java.lang.String" description="number of the current page" %>
<%@ attribute name="size" required="true" type="java.lang.String" description="size of the current page" %>
<%@ attribute name="search" required="true" type="java.lang.String" description="search of the current page" %>
<%@ attribute name="order" required="true" type="java.lang.String" description="order of the current page" %>

<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="./dashboard?language=${language}"> Application - Computer Database </a>
        <a href="${redirection}?language=fr&id=${id}&page=${page}&size=${size}&order=${order}&search=${search}" class="flag-icon flag-icon-fr" style="float: right; horiz-align: left"></a>
        <a href="${redirection}?language=en&id=${id}&page=${page}&size=${size}&order=${order}&search=${search}" class="flag-icon flag-icon-gb" style="float: right; horiz-align: left"></a>
    </div>
</header>