<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Symp'le Event Manger</title>

    <%-- Inclusions des CSS communs --%>
    <link href="<spring:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<spring:url value="/static/css/main.css"/>" rel="stylesheet">
    <link href="<spring:url value="/static/css/librairies.css"/>" rel="stylesheet">
    <link rel="icon" href="<spring:url value="/static/favicon.ico"/>"/>

    <%-- CSS spécifiques (optionels) --%>
    ${param._page_css}
</head>

<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<spring:url value="/website"/>">
                <img class="img-responsive" src="<spring:url  value="/static/img/logo.svg"/>"
                     alt="SEM" style="max-height: 100%">
            </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <%--<ul class="nav navbar-nav">--%>
            <%--</ul>--%>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty session.user}">
                    <li><a href="<spring:url value="/website/myaccount"/>">Mon compte</a></li>
                    <li><a href="#">${session.user.email}</a></li>
                    <li><a href="<spring:url value="/website/deconnexion.do"/>">Déconnexion</a></li>
                </c:if>
                <c:if test="${empty session.user}">
                    <li><a href="<spring:url value="/website/connexion"/>">Connexion</a></li>
                    <li><a href="<spring:url value="/website/creer-compte"/>">Créer compte</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>

<div class="container main-content">
    <ol class="breadcrumb">
        <li>
            <a href="<spring:url value="/website"/>">
                <span class="glyphicon glyphicon-home"></span>
                Accueil
            </a>
        </li>
        <c:if test="${conference_name != null}">
            <li>
                <a href="<spring:url value="/ressource/${conference_name}"/>">
                        ${conference_name}
                </a>
            </li>
        </c:if>
    </ol>
    <c:if test="${not empty session.messages.get('global.error')}">
        <div class="alert alert-danger">
            <c:forEach var="msg" items="${session.messages.pop('global.error')}">
                ${msg} <br>
            </c:forEach>
        </div>
    </c:if>
    <c:if test="${not empty session.messages.get('global.success')}">
        <div class="alert alert-success">
            <c:forEach var="msg" items="${session.messages.pop('global.success')}">
                ${msg} <br>
            </c:forEach>
        </div>
    </c:if>

    <%-- Contenu de la page passé en paramètre --%>
    ${param._page_body}
</div>


<script src="<spring:url value="/static/js/jquery-2.1.4.min.js"/>"></script>
<script src="<spring:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<spring:url value="/static/js/librairies.js"/>"></script>
<script src="<spring:url value="/static/js/global.js"/>"></script>

<script>
    moment.locale('fr');
</script>

<%-- Inclusion de JS spécifique s--%>
${param._page_js}

</body>
</html>