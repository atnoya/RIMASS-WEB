<?xml version="1.0" encoding="UTF-8"?>
<%--
    Document   : listGen.jsp
    Created on : 02-dic-2009, 21:07:55
    Author     : root
--%>
<%@page pageEncoding="UTF-8" contentType="text/xml; charset=UTF-8" %>
<%@page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<generalizations shown="${model.nshowed}" total="${model.ngen}" nodeID="${model.nodeID}">
    <c:forEach items="${model.gens}" var="gen">
        <generalization termID="${gen.termID}" termLabel="${gen.label}" />
    </c:forEach>
</generalizations>
