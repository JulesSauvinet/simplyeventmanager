<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Contenu de la page définit dans la variable "${_page_body}}--%>
<c:set var="_page_body">

    <c:if test="${session.isChairConf(conference_name)}">
        <div class="well">
            <a href="<spring:url value="/website/conference/${conference_name}/upload"/> " class="btn btn-primary">
                <span class="glyphicon glyphicon-upload"></span> Importer des données
            </a>

            <a href="<spring:url value="/ressource/${conference_name}/editeur-entite"/> " class="btn btn-primary">
                <span class="glyphicon glyphicon-plus"></span> Ajouter une entité
            </a>

            <a href="<spring:url value="/website/conference/${conference_name}/editer"/>" class="btn btn-primary">
                <span class="glyphicon glyphicon-pencil"></span> Editer
            </a>

            <div class="btn-group">
                <a type="button" class="dropdown-toggle btn btn-primary" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false" id="dataset-download">
                    <span class="glyphicon glyphicon-cloud-download"></span> Dataset
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" aria-labelledby="dataset-download">
                    <spring:url value="/ressource/${conference_name}" var="dataset_base_url"/>
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
            </div>
        </div>
    </c:if>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>Conférence : ${conference_name}</h4>
        </div>

        <c:set var="entity" value="${conference}" scope="request"/>
        <jsp:include page="/WEB-INF/views/entity/entity_viewer_fragment.jsp"/>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading">
            <h5>Parcourir les entités de la conférence</h5>
        </div>
        <div class="list-group">
            <c:forEach items="${list_type_entity}" var="type">
                <spring:url value="/ressource/${conference_name}" var="resourceViewerUrl">
                    <spring:param name="type" value="${type.iri}"/>
                </spring:url>
                <a href="${resourceViewerUrl}" class="list-group-item">
                        ${session.getPredicatLabel(type.iri)}
                </a>
            </c:forEach>
        </div>
    </div>
</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>