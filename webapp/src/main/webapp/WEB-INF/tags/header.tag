<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="redirection" required="true" type="java.lang.String" description="redirection for the new page" %>
<%@ attribute name="id" required="true" type="java.lang.String" description="order of the current page" %>
<%@ attribute name="page" required="true" type="java.lang.String" description="number of the current page" %>
<%@ attribute name="size" required="true" type="java.lang.String" description="size of the current page" %>
<%@ attribute name="search" required="true" type="java.lang.String" description="search of the current page" %>
<%@ attribute name="order" required="true" type="java.lang.String" description="order of the current page" %>

<header class="navbar navbar-inverse navbar-fixed-top">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/2.3.1/css/flag-icon.min.css" rel="stylesheet"/>
    <div class="container" >
        <a class="navbar-brand" href="./dashboard"> Application - Computer Database </a>
        <table class="navbar-brand" style="float: right">
            <tr>
                <td style="width: 30px"><a href="${redirection}?lang=fr&id=${id}&page=${page}&size=${size}&order=${order}&search=${search}" class="flag-icon flag-icon-fr"></a></td>
                <td style="width: 30px"><a href="${redirection}?lang=en&id=${id}&page=${page}&size=${size}&order=${order}&search=${search}" class="flag-icon flag-icon-gb"></a></td>
                <td style="width: 30px"><a href="${redirection}?lang=es&id=${id}&page=${page}&size=${size}&order=${order}&search=${search}" class="flag-icon flag-icon-es"></a></td>
                <td style="width: 20px"><a href="./logout" class="fa fa-sign-out"></a></td>
            </tr>
           </table>
    </div>
</header>