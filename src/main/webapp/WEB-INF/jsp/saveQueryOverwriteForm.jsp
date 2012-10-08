<%-- 
    Document   : saveQueryOverwriteForm
    Created on : 06-jul-2010, 14:48:21
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="saveQueryMessage">
    <table class="formvisualtable">
         <tr>
            <td colspan="2" align="left">
                The following query already exists.
            </td>
         </tr>
         <tr>
            <td class="formfield">Name:</td>
            <td align="left"><form:hidden path="name" id="saveQueryName"/><c:out value="${saveQueryMessage.name}"/></td>
        </tr>
        <tr>
            <td class="formfield">Description:</td>
            <td align="left" style="max-width:500px"><form:hidden path="description" id="saveQueryDescription" /><c:out value="${saveQueryMessage.description}"/></td>
        </tr>
        <tr>
            <td colspan="2" align="left">
                Do you wish to overwrite it?
            </td>
         </tr>
        <tr>
            <td colspan="3">
                <input type="button" class="formButton" value="Save" onclick="document.usermanager.doOverwriteQuery('name=${saveQueryMessage.name}&description=${saveQueryMessage.description}');"/>
                <input type="button" class="formButton" value="Cancel" onclick="document.usermanager.doCancelOverwriteQuery();" />
            </td>
        </tr>
    </table>
</form:form>