<%-- 
    Document   : listDocs
    Created on : 18-nov-2009, 17:09:07
    Author     : root
--%>
<%@include file="/WEB-INF/jsp/include.jsp" %>
<c:forEach items="${model.docs}" var="doc">
    <div class="doc">
        <div class="id">ID: <c:out value="${doc.docID}" /></div>
        <div>Location: <c:out value="${doc.location}" /></div>
    </div>
</c:forEach>
