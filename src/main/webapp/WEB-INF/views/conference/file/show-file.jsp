<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="_page_body">
    <div class="panel panel-default">
        <c:forEach items="${session.entities}" var="entity">
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
        </div>
        </c:forEach>
        <form:form>
            <button type="submit" formaction="<spring:url value="/website/conference/${conference_name}/upload/validate"/>">Valider</button>
        </form:form>
        <form:form>
            <button type="submit"
                    formaction="<spring:url value="/website/conference/${conference_name}/upload/cancel"/>">Annuler
            </button>
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

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
  <jsp:param name="_page_body" value="${_page_body}"/>
</jsp:include>