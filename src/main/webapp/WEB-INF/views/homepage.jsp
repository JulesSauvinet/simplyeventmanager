<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Contenu de la page définit dans la variable "${_page_body}}--%>
<c:set var="_page_body">

    <div class="row">

        <div class="col-sm-6">
            <div class="jumbotron">
                <h2>Symp'le Event Manager</h2>

                <h4>Projet Multimif 2015 - 2016</h4>

                <ul class="project-members">
                    <li>Clément Blaise</li>
                    <li>Tom Dusseaux</li>
                    <li>Alix Gonnot</li>
                    <li>Jordan Martin</li>
                    <li>Landry Mélaine Lebatto</li>
                    <li>Jules Sauvinet</li>
                </ul>
            </div>
        </div>
        <div class="col-sm-6">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Liste des conférences</h4>

                    <c:if test="${session.isUserLogged()}">
                    <a href="<spring:url value="/website/conference/creer"/>"
                       class="btn btn-primary btn-xs">
                        <span class="glyphicon glyphicon-plus"></span>
                        Nouvelle conférence
                    </a>
                    </c:if>
                </div>

                <div class="panel-body">
                    <c:if test="${empty conferences}">
                        <div class="label-empty-list">Aucune conférences</div>
                    </c:if>

                    <div class="list-group">
                        <c:forEach items="${conferences}" var="conf">
                            <div class="list-group-item">
                                <div class="list-group-item-heading">
                                    <h4>${conf}</h4>
                                </div>

                                <a href="<spring:url value="/ressource/${conf}"/>"
                                   style="margin-right: 5px;">
                                    <span class="glyphicon glyphicon-eye-open"></span> Explorer
                                </a>

                                    <c:if test="${session.isChairConf(conf)}">
                                    <%--<c:if test="${session.isUserLogged()}">--%>
                                <a href="<spring:url value="/website/conference/${conf}/editer"/>"
                                   style="margin-right: 5px;">
                                    <span class="glyphicon glyphicon-pencil"></span> Editer
                                </a>
                                    </c:if>

                                <div class="btn-group btn-group-xs">
                                    <c:if test="${session.isChairConf(conf)}">
                                        <%--<c:if test="${session.isUserLogged()}">--%>
                                    <a type="button" class="dropdown-toggle download-dataset-btn" data-toggle="dropdown"
                                       aria-haspopup="true" aria-expanded="false" id="dataset-download">
                                        <span class="glyphicon glyphicon-cloud-download"></span> Dataset
                                        <span class="caret"></span>
                                    </a>

                                    <ul class="dropdown-menu" aria-labelledby="dataset-download">
                                        <spring:url value="/ressource/${conf}" var="dataset_base_url" />
                                        <li>
                                            <a href="${dataset_base_url}/dataset.json">RDF/JSON</a>
                                        </li>
                                        <li>
                                            <a href="${dataset_base_url}/dataset.jsonld">JSON/LD</a>
                                        </li>
                                        <li role="separator" class="divider"></li>
                                        <li>
                                            <a href="${dataset_base_url}/dataset.rdf">XML/RDF</a>
                                        </li>
                                    </ul>
                                    </c:if>
                                </div>

                                <c:if test="${session.isChairConf(conf)}">
                                <a class="btn-delete-entity" href="<spring:url value="/ressource/${conf}/supprimer.do"/>"
                                   style="margin-right: 5px;">
                                    <span class="glyphicon glyphicon-trash"></span> Supprimer
                                </a>
                                </c:if>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>

</c:set>
<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>

<%--
Non utilisé pour cette page mais il est possible
d'inclure des feuilles de styles ou scripts en plus
(à définir avant <jsp:include>) :

<c:set var="_page_css">
    <link href="<spring:url value="/static/css/..."/>" rel="stylesheet">
    ...
</c:set>
<c:set var="_page_js">
    <script src="<spring:url value="/static/js/..."/>"></script>
    ...
</c:set>

Il faudra alors rajouter dans <jsp:include> :
    <jsp:param name="_page_js" value="${_page_js}"/>
    <jsp:param name="_page_css" value="${_page_css}"/>
--%>