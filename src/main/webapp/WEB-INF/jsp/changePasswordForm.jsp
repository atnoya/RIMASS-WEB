<%-- 
    Document   : modifyUserForm
    Created on : 23-dic-2009, 23:40:13
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<form:form commandName="changePasswordMessage">
    <table class="formvisualtable">
        <tr>
            <td class="formfield">Old Password:</td>
            <td><form:password path="oldpassword" size="40" cssClass="formInput" cssErrorClass="formInputError" id="changeOldPassword"/></td>
            <td class="formerrors"><form:errors path="oldpassword" /></td>
        </tr>
        <tr>
            <td class="formfield">New Password:</td>
            <td><form:password path="newpassword" size="40" cssClass="formInput" cssErrorClass="formInputError" id="changeNewPassword"/></td>
            <td class="formerrors"><form:errors path="newpassword" /></td>
        </tr>
        <tr>
            <td class="formfield">Confirm Password:</td>
            <td><form:password path="confirmnewpassword" size="40" cssClass="formInput" cssErrorClass="formInputError" id="changeConfirmNewPassword"/></td>
            <td class="formerrors"><form:errors path="confirmnewpassword" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="button" class="formButton" value="Send" onclick="document.usermanager.changePass('oldpassword=' + $('changeOldPassword').value + '&newpassword=' + $('changeNewPassword').value + '&confirmnewpassword=' + $('changeConfirmNewPassword').value);"/>
                <input type="button" class="formButton" value="Cancel" onclick="document.userForm.close();" />
            </td>
        </tr>
    </table>
</form:form>