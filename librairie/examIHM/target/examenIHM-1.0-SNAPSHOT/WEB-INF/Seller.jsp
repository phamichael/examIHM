<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 6/3/17
  Time: 6:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
    <title>Seller page</title>
    <sj:head/>
    <sb:head/>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <center><h1>Menu vendeur</h1></center>
            </div>
            <div class="col-md-offset-2 col-md-2">
                <br/>
                <br/>
                <s:form action="menu" theme="bootstrap">
                    <s:hidden name="password" value="%{#session.password}"/>
                    <s:submit cssClass="btn btn-primary" value="Retour"/>
                </s:form>
            </div>
        </div>
    </div>
</body>
</html>
