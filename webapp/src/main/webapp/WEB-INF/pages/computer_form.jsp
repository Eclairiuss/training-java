<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form:errors cssClass="text-danger alert alert-danger" element="div"/>
<form:hidden path="id"/>
<fieldset>
    <div class="form-group">
        <form:label path="name" for="name"><spring:message code="computer.name" text="default text"/></form:label>
        <form:input path="name" type="text" class="form-control" acceptCharset="true" placeholder="${holder_name}"/>
        <form:errors path="name" cssClass="text-danger" element="div"/>
    </div>
    <div class="form-group">
        <form:label path="introduced" for="introduced"><spring:message code="computer.introduced" text="default text" /></form:label>
        <c:if test="${formComputer.getIntroduced() != null}">
            <c:set var="valueIntroduced" value="${formComputer.getIntroduced()}"/>
        </c:if>
        <form:input path="introduced" type="text" class="form-control" acceptCharset="true" placeholder="${holder_intro}"/>
        <form:errors path="introduced" cssClass="text-danger" element="div"/>
    </div>
    <div class="form-group">
        <form:label path="discontinued" for="discontinued"><spring:message code="computer.discontinued" text="default text" /></form:label>
        <c:if test="${formComputer.getDiscontinued() != null}">
            <c:set var="valueDiscontinued" value="${formComputer.getDiscontinued()}"/>
        </c:if>
        <form:input path="discontinued" type="text" class="form-control" acceptCharset="true" placeholder="${holder_disco}"/>
        <form:errors path="discontinued" cssClass="text-danger" element="div"/>
    </div>
    <div class="form-group">
        <form:label path="companyId" for="companyId"><spring:message code="computer.company" text="default text" /></form:label>
        <form:select path="companyId" class="form-control" >
            <option value="44" <c:if test="${formComputer.getCompanyId() == null}">selected</c:if>>--</option>
            <c:forEach var="company" items="${companies}">
                <c:if test="${company.getId() != 44}">
                    <option value="${company.getId()}" <c:if test="${company.getId().equals(formComputer.getCompanyId())}">selected</c:if> >${company.getName()}</option>
                </c:if>
            </c:forEach>
        </form:select>
        <form:errors path="companyId" cssClass="text-danger"/>
    </div>
</fieldset>