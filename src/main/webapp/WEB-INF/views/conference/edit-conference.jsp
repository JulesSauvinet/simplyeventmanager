<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="_page_body">
    <spring:url value="/website/conference/${conference_name}/editer.do" var="form_action_url"/>
    <form:form action="${form_action_url}" modelAttribute="conference" method="post" id="conference-form">
        <div class="row">
            <div class="col-md-6 col-centered">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Edition d'une conférence : ${conference.name}</h4>
                    </div>
                    <div class="panel-body">
                        <c:if test="${not empty session.messages.get('edit_conf.error')}">
                            <div class="alert alert-danger">
                                <c:forEach var="error" items="${session.messages.pop('edit_conf.error')}">
                                    ${error} <br>
                                </c:forEach>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <label>Lieu de la conférence</label>

                            <div id="map"></div>
                        </div>

                        <div class="row form-group">
                            <div class="col-md-6">
                                <label for="latitude">Latitude</label>
                                <input type="text" name="latitude" id="latitude" class="form-control"
                                       readonly="readonly" value="${conference.latitude}">
                            </div>
                            <div class="col-md-6">
                                <label for="latitude">Longitude</label>
                                <input type="text" name="longitude" id="longitude" class="form-control"
                                       readonly="readonly" value="${conference.longitude}">
                            </div>
                        </div>

                        <div class="form-group row">
                            <div class="col-md-6">
                                <label>Date/Heure de début</label>

                                <div class="input-group date" id="start_date" data-oldval="${conference.start_date}">
                                    <input type="text" class="form-control"/>
                                    <input name="start_date" type="hidden" class="form-control"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label>Date/Heure de fin</label>

                                <div class="input-group date" id="end_date" data-oldval="${conference.end_date}">
                                    <input type="text" class="form-control" />
                                    <input name="end_date" type="hidden" class="form-control"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea name="description" id="description" class="form-control">${conference.description}</textarea>
                        </div>

                        <div class="form-group text-center">
                            <a href="<spring:url value="/ressource/${conference_name}"/>" class="btn btn-warning">
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
    </form:form>
</c:set>

<c:set var="_page_js">
    <script src="<spring:url value="/static/js/map.js"/>"></script>
    <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAHPaXlLPkTuF5W94XCDdJwz_FAVQ2aCwU&callback=initMap">
    </script>
    <script src="<spring:url value="/static/js/conference-editor.js"/>"></script>
</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>
