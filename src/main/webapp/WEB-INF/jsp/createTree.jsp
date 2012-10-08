<%-- 
    Document   : createTree
    Created on : 19-nov-2009, 20:24:41
    Author     : root
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:forEach items="${model.tree}" var="tree">
    <div class="tree">
            <div class="treeTitle">
                <!--javascript:showButtons(this,undefined,undefined,${tree.id},'searchGraphContent') -->
                <div class="branchContent">
                    <div class="branchTitle" onmouseover="initializeNode(this, undefined, '${tree.id}', '${tree.term.label}',undefined,undefined);">
                        <span></span><span class="term"><c:out value="${tree.term.label}" /></span>
                    </div>
                </div>
                <t:treeoutput branches="${tree.modifiers}" fatherId="${tree.id}"/>
            </div>
    </div>
</c:forEach>