<?xml version="1.0" encoding="UTF-8"?>
<%--
    Document   : listEsp.jsp
    Created on : 02-dic-2009, 21:07:55
    Author     : root
--%>
<%@page pageEncoding="UTF-8" contentType="text/xml; charset=UTF-8" %>
<%@page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<specializations shown="${model.nshowed}" total="${model.nesp}" nodeID="${model.nodeID}">
    <c:forEach items="${model.esps}" var="esp">
        <specialization termID="${esp.termID}" termLabel="${esp.label}" />
    </c:forEach>
</specializations>