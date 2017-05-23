<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="redirection" required="true" type="java.lang.String" description="redirection for the new page" %>
<%@ attribute name="numberComputers" required="true" type="java.lang.String" description="number of Computers" %>
<%@ attribute name="page" required="true" type="java.lang.String" description="number of the current page" %>
<%@ attribute name="size" required="true" type="java.lang.String" description="size of the current page" %>
<%@ attribute name="search" required="true" type="java.lang.String" description="search of the current page" %>
<%@ attribute name="order" required="true" type="java.lang.String" description="order of the current page" %>
<%@ attribute name="language" required="true" type="java.lang.String" description="order of the current page" %>


<ul class="pagination">
    <c:if test="${page > 3}">
        <li>
            <a href="${redirection}?language=${language}&page=0&size=${size}&search=${search}&order=${order}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
            </a>
        </li>
    </c:if>
    <c:if test="${page > 2}">
        <li><a href="${redirection}?language=${language}&page=${page-2}&size=${size}&search=${search}&order=${order}">${page-2}</a></li>
    </c:if>
    <c:if test="${page > 1}">
        <li><a href="${redirection}?language=${language}&page=${page-1}&size=${size}&search=${search}&order=${order}">${page-1}</a></li>
    </c:if>
    <li><a href="${redirection}?language=${language}&page=${page}&size=${size}&search=${search}&order=${order}" class="btn active">${page}</a></li>
    <c:if test="${(page*size) < numberComputers}">
        <li><a href="${redirection}?language=${language}&page=${page+1}&size=${size}&search=${search}&order=${order}">${page+1}</a></li>
    </c:if>
    <c:if test="${(page + 1)*size < numberComputers}">
        <li><a href="${redirection}?language=${language}&page=${page+2}&size=${size}&search=${search}&order=${order}">${page+2}</a></li>
    </c:if>
    <c:if test="${(page + 2)*size < numberComputers}">
        <li>
            <a href="${redirection}?language=${language}&page=${((numberComputers-1)/size)+1}&size=${size}&search=${search}&order=${order}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
            </a>
        </li>
    </c:if>
</ul>

<div class="btn-group btn-group-sm pull-right" role="group" >
    <button type="button" onclick="window.location.href='${redirection}?language=${language}&page=0&size=10&search=${search}&order=${order}'" <c:choose><c:when test="${size == 10}">class="btn active"</c:when><c:otherwise>class="btn btn-default"</c:otherwise></c:choose>>10</button>
    <button type="button" onclick="window.location.href='${redirection}?language=${language}&page=0&size=20&search=${search}&order=${order}'" <c:choose><c:when test="${size == 20}">class="btn active"</c:when><c:otherwise>class="btn btn-default"</c:otherwise></c:choose>>20</button>
    <button type="button" onclick="window.location.href='${redirection}?language=${language}&page=0&size=50&search=${search}&order=${order}'" <c:choose><c:when test="${size == 50}">class="btn active"</c:when><c:otherwise>class="btn btn-default"</c:otherwise></c:choose>>50</button>
    <button type="button" onclick="window.location.href='${redirection}?language=${language}&page=0&size=100&search=${search}&order=${order}'" <c:choose><c:when test="${size == 100}">class="btn active"</c:when><c:otherwise>class="btn btn-default"</c:otherwise></c:choose>>100</button>
</div>