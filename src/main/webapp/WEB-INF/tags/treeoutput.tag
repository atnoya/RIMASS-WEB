<%-- 
    Document   : treeoutput
    Created on : 27-nov-2009, 0:29:13
    Author     : root
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="branches" type="java.util.List"%>
<%@attribute name="fatherId"%>

<%-- any content can be specified here e.g.: --%>

<c:forEach items="${branches}" var="branch">
    <div class="branch">
        <div class="branchContent">
            <div class="branchTitle"  onmouseover="initializeNode(this, '${fatherId}', '${branch.modifier.id}', '${branch.modifier.term.label}','${branch.relation.relationID}','${branch.relation.label}');">
                <span class="modif"><c:out value="${branch.relation.label}" />:&nbsp;</span>
                <span class="term"><c:out value="${branch.modifier.term.label}"/></span>
            </div>
        </div>
        <c:if test="${not empty branch.modifier.modifiers}">
            <t:treeoutput branches="${branch.modifier.modifiers}" fatherId="${branch.modifier.id}" />
        </c:if>
    </div>
</c:forEach>