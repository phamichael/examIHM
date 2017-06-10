<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 6/3/17
  Time: 6:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<html>
<head>
    <title>Stock manager page</title>
    <sj:head/>
    <sb:head/>
</head>
<body>
    <script>
        $(document).ready(function()
        {
            $('.selectItemsType').submit(function(e)
            {
                e.preventDefault();
                var data = $(this).serialize();

                $.ajax({
                    url: "selectItemsType.action?" + data,
                    type: "POST",
                    contentType: "application/json: charset=utf-8",
                    dataType: "json",
                    success: function(result)
                    {
                        var addItemForm =
                                '<form id="addItem" class="addItem" name="addItem" ' +
                                'action="/addItem.action" method="post">' +
                                    '<fieldset>' +
                                        '<input id="title" name="title" placeholder="Titre"> </input> </br>';

                        if(result.typeSelected == 'LIVRE')
                        {
                            addItemForm +=
                                        '<input id="editor" name="editor" placeholder="Éditeur"> </input> </br>' +
                                        '<input id="year" name="year" placeholder="Année d\'édition"> </input> /' +
                                        '<input id="month" name="month" placeholder="Mois"> </input> /' +
                                        '<input id="day" name="day" placeholder="Jour"> </input> </br>' +
                                        '<input id="isbnNumber" name="isbnNumber" placeholder="Numéro ISBN"> </input> </br>' +
                                        '<input id="numberOfPages" name="numberOfPages" placeholder="Nombre de pages"> </input> </br>';

                        } else if(result.typeSelected == 'CD')
                        {
                            addItemForm +=
                                        '<input id="singer" name="singer" placeholder="Chanteur"> </input> </br>';

                        }
                        addItemForm +=
                                        '<input id="price" name="price" placeholder="Prix"> </input> </br></br>' +
                                        '<input type="hidden" id="typeSelected" name="typeSelected" value="' + result.typeSelected + '"> </input>' +
                                        '<input id="addItemSubmit" class="btn btn-primary center-block " value="Ajouter" type="submit"> </input>' +
                                    '</fieldset>' +
                                '</form>';
                        $('#addItemForm').html(addItemForm);
                    },
                    error: function()
                    {
                        alert('Erreur!');
                    }
                });
            });

            $('.selectItem1').submit(function(e)
            {
                e.preventDefault();
                var data = $(this).serialize();

                $.ajax({
                    url: "selectItem.action?" + data,
                    type: "POST",
                    contentType: "application/json: charset=utf-8",
                    dataType: "json",
                    success: function(result)
                    {
                        var orderItemForm =
                                '<form id="orderItem" class="orderItem" name="orderItem" ' +
                                'action="/orderItem.action" method="post">' +
                                    '<fieldset>' +
                                        'Précisez la quantité souhaitée pour l\'article: <strong>' + result.item.libelle + '</strong> </br></br>' +
                                        '<input type="hidden" id="itemSelected" name="itemSelected" value="' + result.itemSelected + '"> </input>' +
                                        '<input id="quantity" name="quantity" placeholder="Quantité"> </input> </br></br>' +
                                        '<input id="orderItemSubmit1" class="btn btn-primary center-block " value="Valider" type="submit"> </input>' +
                                    '</fieldset>' +
                                '</form>';
                        $('#orderItemForm').html(orderItemForm);
                    },
                    error: function()
                    {
                        alert('Erreur!');
                    }
                });
            });

            $('.selectItem2').submit(function(e)
            {
                e.preventDefault();
                var data = $(this).serialize();

                $.ajax({
                    url: "selectItem.action?" + data,
                    type: "POST",
                    contentType: "application/json: charset=utf-8",
                    dataType: "json",
                    success: function(result)
                    {
                        var restockItemForm =
                                '<form id="restockItem" class="restockItem" name="restockItem" ' +
                                'action="/restockItem.action" method="post">' +
                                    '<fieldset>' +
                                        'Précisez la quantité souhaitée pour l\'article: <strong>' + result.item.libelle + '</strong> </br><br/>' +
                                        '<input type="hidden" id="itemSelected" name="itemSelected" value="' + result.itemSelected + '"> </input>' +
                                        '<input id="quantity" name="quantity" placeholder="Quantité"> </input> </br></br>' +
                                        '<input id="orderItemSubmit2" class="btn btn-primary center-block " value="Valider" type="submit"> </input>' +
                                    '</fieldset>' +
                                '</form>';
                        $('#restockItemForm').html(restockItemForm);
                    },
                    error: function()
                    {
                        alert('Erreur!');
                    }
                });
            });
        });
    </script>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-offset-4 col-md-4">
                <center><h1>Menu responsable des stocks</h1></center>
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
        <br/>
        <br/>
        <div class="row">
            <div class="col-md-offset-1 col-md-2">
                <h3>Ajout d'un nouveau produit</h3>
                <br/>
                <s:form action="selectItemsType" theme="bootstrap" cssClass="selectItemsType">
                    <select id="typeSelected" name="typeSelected">
                        <s:iterator var="itemType" value="%{session.itemsTypes}">
                            <option value="<s:property value="%{#itemType}"/>"><s:property value="%{#itemType}"/></option>
                        </s:iterator>
                    </select>
                    <br/>
                    <br/>
                    <s:submit id="btn-submit" cssClass="btn btn-primary" value="Valider"/>
                </s:form>
                <br/>
                <div id="addItemForm"></div>
            </div>
            <div class="col-md-offset-1 col-md-2">
                <h3>Suppression d'une référence</h3>
                <br/>
                <s:form action="deleteItem" theme="bootstrap">
                    <select id="itemToDelete" name="itemToDelete">
                        <s:iterator var="item" value="%{session.emptyStocks}">
                            <option value="<s:property value="%{#item.produit.getId()}"/>"><s:property value="%{#item.produit.getLibelle()}"/></option>
                        </s:iterator>
                    </select>
                    <br/>
                    <br/>
                    <s:submit cssClass="btn btn-primary" value="Supprimer"/>
                </s:form>
            </div>
            <div class="col-md-offset-1 col-md-2">
                <h3>Produits en stock</h3>
                <br/>
                <s:form action="selectItem" theme="bootstrap" cssClass="selectItem1">
                    <select id="itemSelected" name="itemSelected">
                        <s:iterator var="itemInStock" value="%{#session.itemsInStock}">
                            <option value="<s:property value="%{#itemInStock.produit.getId()}"/>">
                                <s:property value="%{#itemInStock.produit.getLibelle()}"/>:
                                <s:property value="%{#itemInStock.getQuantite()}"/>
                            </option>
                        </s:iterator>
                    </select>
                    <br/>
                    <br/>
                    <s:submit cssClass="btn btn-primary" value="Commander"/>
                </s:form>
                <br/>
                <div id="orderItemForm"></div>
            </div>
            <div class="col-md-offset-1 col-md-2">
                <h3>Produits en rupture de stock</h3>
                <br/>
                <s:form action="selectItem" theme="bootstrap" cssClass="selectItem2">
                    <select id="itemSelected" name="itemSelected">
                        <s:iterator var="emptyStock" value="%{#session.emptyStocks}">
                            <option value="<s:property value="%{#emptyStock.produit.getId()}"/>">
                                <s:property value="%{#emptyStock.produit.getLibelle()}"/>
                            </option>
                        </s:iterator>
                    </select>
                    <br/>
                    <br/>
                    <s:submit cssClass="btn btn-primary" value="Commander"/>
                </s:form>
                <br/>
                <div id="restockItemForm"></div>
            </div>
        </div>
    </div>
</body>
</html>
