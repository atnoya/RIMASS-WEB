<%-- 
    Document   : loadQueryForm
    Created on : 29-jun-2010, 17:58:56
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="loadQueryMessage">
    <table class="formvisualtable">
        <tr>
            <td class="formfield">
                <div class="showStoredQueriesBox" id="tablaLoadingQueries">
                    <table width ="100%" class="showStoredQueriesBoxTable">
                        <thead class="showStoredQueriesBoxTableHeader"><tr><td></td><td>Name</td><td>Date</td><td>Nº Results</td><td>Description</td></tr></thead>
                        <tbody>
                            <c:forEach items="${loadQueryMessage.queries}" var="query">
                                <tr onclick="$(${query.id}).checked=true; $('tablaLoadingQueries').selected=${query.id};">
                                    <td><form:radiobutton path="selected" value="${query.id}" id="${query.id}"/></td>
                                    <td><c:out value="${query.name}" /></td>
                                    <td><c:out value="${query.date}" /></td>
                                    <td><c:out value="${query.numberOfResults}" /></td>
                                    <td><c:out value="${query.description}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        <tr><td colspan="3" class="formerrors"><form:errors path="selected" /></tr>
        <tr>
            <td colspan="3" class="formerrors"><c:out value="${error}" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="button" class="formButton" value="Load" onclick="document.usermanager.loadQuery('selected=' + $('tablaLoadingQueries').selected);"/>
                <input type="button" class="formButton" value="Cancel" onclick="document.userForm.close();" />
            </td>
        </tr>
    </table>
</form:form>
