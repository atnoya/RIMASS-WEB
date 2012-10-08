<%-- 
    Document   : listStoredQueriesSmall
    Created on : 24-dic-2009, 6:15:46
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<c:forEach items="${storedQueries}" var="sq">
    <div class="shortStoredQuery">
        <div class="name"><c:out value="${sq.name}" /></div>
        <div class="number"><c:out value="${sq.numberOfResults}" />&nbsp;results</div>
    </div>
</c:forEach>
