<%-- 
    Document   : newUserForm
    Created on : 20-dic-2009, 2:21:35
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="newUserMessage">
    <table class="formvisualtable">
        <tr>
            <td class="formfield">Email:</td>
            <td><form:input path="email" size="40" cssErrorClass="formInputError" cssClass="formInput" id="newUserEmail"/></td>
            <td class="formerrors"><form:errors path="email" /></td>
        </tr>
        <tr>
            <td class="formfield">Confirm Email:</td>
            <td><form:input path="confirmemail" size="40" cssClass="formInput" cssErrorClass="formInputError" id="newUserConfirmEmail"/></td>
            <td class="formerrors"><form:errors path="confirmemail" /></td>
        </tr>
        <tr>
            <td class="formfield">Password:</td>
            <td><form:password path="password" size="40" cssErrorClass="formInputError" cssClass="formInput" id="newUserPassword"/></td>
            <td class="formerrors"><form:errors path="password" /></td>
        </tr>
        <tr>
            <td class="formfield">Confirm Password:</td>
            <td><form:password path="confirmpassword" size="40"  cssErrorClass="formInputError" cssClass="formInput" id="newUserConfirmPassword"/></td>
            <td class="formerrors"><form:errors path="confirmpassword" /></td>
        </tr>
        <tr>
            <td colspan="3" class="formerrors"><c:out value="${error}" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="button" class="formButton" value="Send" onclick="document.usermanager.newUser('email=' + $('newUserEmail').value + '&confirmemail=' + $('newUserConfirmEmail').value + '&password=' + $('newUserPassword').value + '&confirmpassword=' + $('newUserConfirmPassword').value);"/>
                <input type="button" class="formButton" value="Cancel" onclick="document.userForm.close();" />
            </td>
        </tr>
    </table>
</form:form>