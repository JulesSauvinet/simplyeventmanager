<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="_page_body">
    <form action="<spring:url value="/ressource/${conference_name}/editer-cpi.do"/>" method="post"
          enctype="multipart/form-data">
        <div class="row">
            <div class="col-md-4 col-md-push-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Renseigner le champ Plus d'Infos</h4>
                    </div>
                    <div class="panel-body">
                        <c:if test="${not empty session.messages.get('update_cpi.error')}">
                            <div class="alert alert-danger">
                                <c:forEach var="error" items="${session.messages.pop('update_cpi.error')}">
                                    ${error} <br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="nameField">Nom du lien</label>
                            <input type="text" class="form-control" id="nameField" name="nameField"
                                   placeholder="Entrez le nom du lien">
                        </div>

                        <div class="form-group">
                            <label for="nameLink">Valeur du lien</label>
                            <input type="text" class="form-control" id="nameLink" name="nameLink"
                                   placeholder="http://...">
                        </div>

                        <div class="form-group">
                            <input type="hidden" value="${iriEntity}" class="form-control" id="iriEntity"
                                   name="iriEntity"
                                   placeholder="Entrez le nom du champ Plus d'infos">
                        </div>

                        <div class="form-group text-center">
                            <spring:url value="/ressource/${conference_name}" var="consultEitityUrl">
                                <spring:param name="iri" value="${iriEntity}"/>
                            </spring:url>
                            <a href="${consultEitityUrl}" class="btn btn-warning">
                                <span class="glyphicon glyphicon-remove-circle"></span>
                                Annuler
                            </a>
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-ok-circle"></span>
                                Enregistrer
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
</jsp:include>
