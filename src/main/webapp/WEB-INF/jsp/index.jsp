<%--
    Document   : index
    Created on : 01-oct-2009, 21:32:30
    Author     : Adrián Tubío Noya
    Description:
        This page represents the main View for the welcome site.
--%>

<%@include file="/WEB-INF/jsp/include.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="estilos.css" />
        <script type="text/javascript" src="ajax.js"></script>
        <script type="text/javascript" src="Query.js"></script>
        <script type="text/javascript" src="Queue.js"></script>
        <script type="text/javascript" src="QueryManager.js"></script>
        <script type="text/javascript" src="UserManager.js"></script>
        <script type="text/javascript" src="treeNode.js"></script>
        <script type="text/javascript" src="menu.js"></script>
        <script type="text/javascript" src="inicializarManagers.js"></script>
    </head>
    <body>
        <div id="menuNavigation">
            <script type="text/javascript">
                document.menuNavigation = $("menuNavigation");
                document.usermanager.getMenu(document.menuNavigation);
            </script>
        </div>
        <center>
            <div id="content">
                <div id="header">
                    <!--<div id="titlebox">
                    </div>-->
                    <div id="searchEngine">
                        <form action="index.html" method="GET">
                            <div>
                                <span><input type="text" size="50" id="searchBasicField" name="phrase" /></span>
                                <span><input type="submit" id="searchBasicButton" name="SearchButton" value="Search" onclick="startSearch();"/></span>
                            </div>
                        </form>
                    </div>
                </div>
                <div id="contentbody">
                    <!--<div id="sidebar"></div>-->
                    <table border="0px" cellpadding="0px" cellspacing="0px" width="100%">
                    <tr>
                    <td id="searchGraph">
                        <div id="searchGraphMenu">
                            <div>
                                <form action="#" method="POST" name="formNewRoot" onsubmit="document.manager.addNewRoot('term=' + this.addrootfield.value, document.treeBox); return false;">
                                    <span class="addNewRoot">
                                        New Root...
                                        <input type="text" value="Type here new root..." name="addrootfield" id="addNewRootField" autocomplete="off"
                                               onfocus="onFocusTextBox(this);" onblur="onBlurTextBox(this, 'Type here new root...');"/>
                                    </span>
                                </form>
                                <span class="undo" onclick="document.manager.undo(null, document.treeBox)">Undo</span>
                                <span class="redo" onclick="document.manager.redo(null, document.treeBox)">Redo</span>
                            </div>
                            <div id="draggingRootArea" onmouseup="createNewRootFromNode();">
                                Drop a node here...
                                <script type="text/javascript">
                                    document.droppingNewRootArea = $("draggingRootArea");
                                </script>
                            </div>
                        </div>
                        <div id="searchGraphContent">
                            <script type="text/javascript">
                                document.treeBox = $("searchGraphContent");
                                document.manager.getWholeTree(null, document.treeBox);
                            </script>
                        </div>
                    </td>
                    <td id="searchResults">
                        <script type="text/javascript">
                            document.docBox = $("searchResults");
                            document.manager.getDocuments(null, document.docBox);
                        </script>
                    </td>
                    </tr>
                    </table>
                </div>
                <div id="footer">All rights reserved to Tubio's Programming Company.</div>
            </div>
        </center>
    </body>
</html>

