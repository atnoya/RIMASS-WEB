<%-- 
    Document   : saveQueryForm
    Created on : 29-dic-2009, 5:36:25
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="saveQueryMessage">
    <table class="formvisualtable">
         <tr>
            <td class="formfield" colspan="2">
                <div class="showStoredQueriesBox" id="tablaSavingQueries">
                    <table width ="100%" class="showStoredQueriesBoxTable">
                        <thead class="showStoredQueriesBoxTableHeader"><tr><td>Name</td><td>Date</td><td>Nº Results</td><td>Description</td></tr></thead>
                        <tbody>
                            <c:forEach items="${saveQueryMessage.queries}" var="query">
                                <tr onclick="$('saveQueryName').value='${query.name}'; $('saveQueryDescription').value='${query.description}';">
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
         <tr>
            <td class="formfield">Name:</td>
            <td align="left"><form:input path="name" size="50" cssErrorClass="formInputLongError" cssClass="formInputLong" id="saveQueryName"/><div class="formerrors"><form:errors path="name" /></div></td>
        </tr>
        <tr>
            <td class="formfield">Description:</td>
            <td align="left"><form:textarea path="description" cssClass="formInputLong" cssErrorClass="formInputLongError" id="saveQueryDescription" /><div class="formerrors"><form:errors path="description" /></div></td>
        </tr>
        <tr>
            <td colspan="3" class="formerrors"><c:out value="${error}" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="button" class="formButton" value="Save" onclick="document.usermanager.storeQuery('name=' + $('saveQueryName').value + '&description=' + $('saveQueryDescription').value);"/>
                <input type="button" class="formButton" value="Cancel" onclick="document.userForm.close();" />
            </td>
        </tr>
    </table>
</form:form>
