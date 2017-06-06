<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 6/3/17
  Time: 3:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
    <title>Challenge</title>
    <sj:head/>
    <sb:head/>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <br/>
                <br/>
                <br/>
                <center><strong><s:property value="#session.challenge.getValeurChallenge()"/></strong></center>
                <br/>
                <br/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-offset-4 col-md-4">

                <s:form action="password" theme="bootstrap">
                    <s:textfield
                        id="challengeInput"
                        name="challengeInput"
                        placeholder="Captcha"/>
                    <s:submit id="btn-submit" cssClass="btn btn-primary pull-right" value="Valider"/>
                </s:form>

                <s:url action="login" var="back">
                </s:url>

                <s:a href="%{back}">
                    Retour
                </s:a>

            </div>
        </div>
    </div>
</body>
</html>
