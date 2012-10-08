<%-- 
    Document   : menuNavigation
    Created on : 22-dic-2009, 23:58:53
    Author     : hadriw
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="menuTitle" id="menuTitle">
    <span>Menu</span>
    <span class="buttonMinimizeWin" onclick="actionMenu('menuContents','menuTitle');"></span>
</div>
<div id="menuContents">
    <div class="menuLogin">
        <c:if test="${logged eq true}">
            <c:out value="Welcome ${email}" />
        </c:if>
        <c:if test="${logged eq false}">
            <form:form commandName="loginMessage">
                <div class="formfield">Username</div>
                <div><form:input path="email" cssClass="formInput" id="formLoginEmail"/></div>
                <div class="formerrors"><form:errors path="email" /></div>
                <div class="formfield">Password</div>
                <div><form:password path="password" cssClass="formInput" id="formLoginPass" /></div>
                <div class="formerrors"><form:errors path="password" /></div>
                <div class="formerrors"><c:out value="${error}" /></div>
                <div style="text-align:center"><input type="button" class="formButton" value="Login" onclick="document.usermanager.login('email=' + $('formLoginEmail').value + '&password=' + $('formLoginPass').value, document.menuNavigation);"/></div>
            </form:form>
        </c:if>
    </div>
    <c:if test="${logged eq true}">
        <div class="menuItem"><span id="menuModifyUser" class="menuButton" onclick="document.usermanager.changePassForm();">Change Password...</span></div>
    </c:if>
    <c:if test="${logged eq true}">
        <div class="menuItem"><span id="menuLogout" class="menuButton" onclick="document.usermanager.logout(document.menuNavigation);">Logout</span></div>
    </c:if>
    <c:if test="${logged eq false}">
        <div class="menuItem"><span id="menuNewUser" class="menuButton" onclick="document.usermanager.newUserForm();">Register...</span></div>
    </c:if>
    <div class="menuItem"><span id="menuNewSearch" class="menuButton" onclick="actionMenuSearch('searchEngine');">New...</span></div>
    <c:if test="${logged eq true}">
        <div class="menuItem"><span id="menuStoreSearch" class="menuButton" onclick="document.usermanager.newUserQueryForm();">Save...</span></div>
    </c:if>
    <c:if test="${logged eq true}">
        <div class="menuItem"><span id="menuLoadSearch" class="menuButton" onclick="document.usermanager.loadQueryForm();">Load...</span></div>
    </c:if>
</div>