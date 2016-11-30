<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="_page_body">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>${entity == null ? 'Création' : 'Modification'} d'une entité</h4>
        </div>

        <div class="panel-body">
            <spring:url value="/ressource/${confName}/editeur-entite.do" var="form_url"/>
            <form:form action="${form_url}" modelAttribute="entity" id="entity-editor-form" method="post">

                <c:if test="${entity != null}">
                    <input type="hidden" name="idIri" value="${entity.idIri}">
                    <input type="hidden" name="cpi.first" value="${entity.cpi.first}">
                    <input type="hidden" name="cpi.second" value="${entity.cpi.second}">
                </c:if>

                <c:if test="${not empty session.messages.get('entity.error')}">
                    <div class="alert alert-danger">
                        <c:forEach var="error" items="${session.messages.pop('entity.error')}">
                            ${error} <br>
                        </c:forEach>
                    </div>
                </c:if>

                <%-- Libellé --%>
                <div class="form-group" id="entityName">
                    <label for="entity-name">Libellé</label>
                    <input type="text" name="label" id="entity-name" class="form-control">
                </div>

                <%-- Type --%>
                <div class="form-group" id="entityType">
                    <label for="entity-type">Type :</label>
                    <select name="typeIri" class="selectpicker form-control" id="entity-type"
                            data-live-search="true" hideDisabled="true" title="Séléctionnez le type de l'entité...">
                        <c:forEach items="${listTypes}" var="type">
                            <option value="${type.iri}">
                                    ${type.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <%-- Ajout d'une relation --%>
                <select class="selectpicker" data-style="btn-primary" id="addRelationSelect"
                        data-live-search="true" title="+ Ajouter une relation..." data-header="Sélectionnez la relation"></select>

                <%-- Ajout d'une propriété --%>
                <select class="selectpicker" data-style="btn-primary" id="addPropertySelect"
                        data-live-search="true" title="+ Ajouter une propriété..." data-header="Sélectionnez la propriété"></select>

                <%-- Conteneur de propriétés/relations --%>
                <div id="propertiesContainer"></div>

                <div class="form-group">
                    <div class="form-group text-center">
                        <a href="<spring:url value="/ressource/${conference_name}"/>" class="btn btn-warning">
                            <span class="glyphicon glyphicon-remove-circle"></span>
                            Annuler
                        </a>
                        <button type="submit" class="btn btn-success">
                            <span class="glyphicon glyphicon-ok-circle"></span>
                                ${entity == null ? 'Enregistrer' : 'Modifier'}
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

    <!-- Templates -->
    <div id="basic-input" class="input-template">
        <div class="form-group basic-input property-line">
            <a href="#" class="delete-line-btn">
                <span class="glyphicon glyphicon-trash"></span>
            </a>
            <label>
                <span class="property-label"></span>
            </label>
            <input name="properties[].predicate" type="hidden" class="property-predicate">
            <input name="properties[].value" class="property-value form-control" type="text">
        </div>
    </div>

    <div id="relation-input" class="input-template">
        <div class="form-group relation-input property-line">
            <a href="#" class="delete-line-btn">
                <span class="glyphicon glyphicon-trash"></span>
            </a>
            <label>
                <span class="relation-label"></span>
            </label>
            <input name="relations[].predicate" type="hidden" class="relation-predicate">

            <div class="row">
                <div class="col-md-6">
                    <label>Type d'entité</label>
                    <select class="relation-type-select form-control" data-live-search="true">
                        <option value=""></option>
                        <c:forEach items="${listTypes}" var="type">
                            <option value="${type.iri}">${type.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6">
                    <label>Entité</label>
                    <select name="relations[].iri" class="relation-entity-select form-control"
                            data-live-search="true"></select>
                </div>
            </div>
        </div>
    </div>
</c:set>

<c:set var="_page_js">
    <script src="<spring:url value="/static/js/entity-editor.js"/>"></script>
    <script>
        <c:if test="${entity != null}">
            loadEntityForUpdate('${entity.idIri}');
        </c:if>
    </script>
</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>


