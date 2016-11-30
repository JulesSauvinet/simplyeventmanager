<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="_page_body">
    <form action="<spring:url value="/website/conference/creer.do"/>" method="post" enctype="multipart/form-data">
        <div class="row">
            <div class="col-md-4 col-md-push-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Ajouter une conférence</h4>
                    </div>
                    <div class="panel-body">
                        <c:if test="${not empty session.messages.get('create_conf.error')}">
                            <div class="alert alert-danger">
                                <c:forEach var="error" items="${session.messages.pop('create_conf.error')}">
                                    ${error} <br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="name">Nom de la conférence</label>
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="Entrez le nom de la conférence">
                        </div>

                        <div class="form-group">
                            <label style="font-weight: normal;">
                                <input type="checkbox" name="import_dataset"> Importer un dataset existant
                            </label>
                        </div>

                        <div class="form-group text-center">
                            <a href="<spring:url value="/website/homepage"/>" class="btn btn-warning">
                                <span class="glyphicon glyphicon-remove-circle"></span>
                                Annuler
                            </a>
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-ok-circle"></span>
                                Créer
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
