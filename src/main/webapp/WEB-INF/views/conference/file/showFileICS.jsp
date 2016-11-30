<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="_page_body">
    <div class="panel panel-default">

    <spring:url value="/website/conference/${conference_name}/upload/validateICS" var="form_action_url"/>

        <form:form action ="${form_action_url}" method="post">
        <c:forEach items="${session.entities}" var="entity" varStatus="loop">
            <div class="panel-body">

                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th style="min-width: 200px;">Attribut</th>
                        <th style="width: 100%;">Valeur</th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr class="info">
                        <td colspan="2"><h5>Propriétés</h5></td>
                    </tr>
                    <c:forEach items="${entity.properties}" var="p">
                        <tr>
                            <td>${p.predicate}</td>
                            <td>${p.value}</td>
                        </tr>
                    </c:forEach>

                    <tr class="info">
                        <td colspan="2"><h5>Relations</h5></td>
                    </tr>
                    <c:forEach items="${entity.relations}" var="p">
                        <tr>
                            <td>${p.predicate}</td>
                            <td>
                                <spring:url value="/ressource/${conference_name}/?iri=${p.iri}" var="linkedIri"/>
                                <a href="${linkedIri}">${p.iri}</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                    <table id="eventTable" class="table table-bordered table-striped">

                        <c:if test="${empty entity.label}">
                            <tr>
                                <label for="eventName">Nom de l'evenement: </label>
                                <input type="text" name="eventName" id="eventName" class="form-control">
                            </tr>
                        </c:if>
                        <c:if test="${not empty entity.label}">
                            <tr>
                                <input type="hidden" name="eventName" id="eventNamehidden" value =" " class="form-control">
                            </tr>
                        </c:if>
                            <tr>
                                    Type de l'évènement :
                                    <select class="selectpicker" data-style="btn-inverse" id="selectType" data-live-search="true"
                                        data-size="auto" name = "typeEvent">
                                        <option data-content="<span class='glyphicon glyphicon-plus'></span> Choisir le type d'évènement"></option>
                                        <c:forEach items="${listTypes}" var="typeEvent">
                                            <option value="${typeEvent.iri}">${typeEvent.name}</option>
                                        </c:forEach>
                                    </select>
                            </tr>

                    </table>
            </div>
        </c:forEach>

        <div id = "btnshow">
            <div class="btn-group">
                <button type="submit" class = "btn btn-success" name ="btn_validate" value = "validate">Valider
                </button>
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-warning" name ="btn_validate" value="cancel">Annuler
                </button>
            </div>
        </div>
        </form:form>



        <div class="panel-heading">
            <h4>Fichiers chargés avec succes</h4>
        </div>
        <div class="panel-body">
            <c:forEach items="${files}" var="file">
                <li>${file}</li>
            </c:forEach>
        </div>
    </div>
</c:set>

<c:set var="_page_js">
    <script src="<spring:url value="/static/js/csv-validator.js"/>"></script>
</c:set>
<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>