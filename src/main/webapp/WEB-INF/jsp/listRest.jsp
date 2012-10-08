<?xml version="1.0" encoding="UTF-8"?>
<%-- 
    Document   : listRest.jsp
    Created on : 02-dic-2009, 21:07:55
    Author     : root
--%>
<%@page pageEncoding="UTF-8" contentType="text/xml; charset=UTF-8" %>
<%@page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<restrictions shown="${model.nshowed}" total="${model.nrest}" nodeID="${model.nodeID}">
    <c:forEach items="${model.rest}" var="rest">
        <restriction modID="${rest.relation.relationID}" modLabel="${rest.relation.label}" termID="${rest.term.termID}" termLabel="${rest.term.label}" />
    </c:forEach>
</restrictions>
