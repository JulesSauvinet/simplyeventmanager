<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="panel-body">

    <div class="panel panel-default">
        <div class="panel-heading">
            <span style="font-size: 20px; font-weight: lighter;">${entity.label}</span>
            <spring:url value="/ressource/${conference_name}" var="linkedIri">
                <spring:param name="type" value="${entity.typeIri}"/>
            </spring:url>
            :
            <small><a href="${linkedIri}">${session.getPredicatLabel(entity.typeIri)}</a></small>

            <c:if test="${conference_name != entity.label || isAlone}">
                <div>
                        <%--Sil'utilisateur est Chair--%>
                    <c:if test="${session.isChairEntity(conference_name,entity.idIri)}">
                        <spring:url value="/ressource/${conference_name}/editeur-entite" var="editEntityUrl">
                            <spring:param name="iri" value="${entity.idIri}"/>
                        </spring:url>
                        <a href="${editEntityUrl}" class="btn btn-primary btn-xs">
                            <span class="glyphicon glyphicon-pencil"></span> Editer
                        </a>

                        <c:if test="${session.hasFieldMoreInfo(entity)}">
                            <spring:url value="/ressource/${conference_name}/editer-cpi" var="iriEntity">
                                <spring:param name="iri" value="${entity.idIri}"/>
                            </spring:url>
                            <a href="${iriEntity}" class="btn btn-primary btn-xs">
                                <span class="glyphicon glyphicon-info-sign"></span> Plus d'infos
                            </a>
                        </c:if>

                        <spring:url value="/ressource/${conference_name}/supprimer-entite" var="deleteEntityUrl">
                            <spring:param name="iri" value="${entity.idIri}"/>
                        </spring:url>
                        <a class="btn btn-danger btn-delete-entity btn-xs" href="${deleteEntityUrl}">
                            <span class="glyphicon glyphicon-trash"></span> Supprimer
                        </a>
                    </c:if>

                        <%--Sil'utilisateur est n'est pas chair mais author--%>
                    <c:if test="${!session.isChairEntity(conference_name,entity.idIri) && session.hasFieldMoreInfo(entity)
    && (session.isAuthor(conference_name,entity.idIri)|| session.isSamePerson(conference_name,entity.idIri))}">
                        <spring:url value="/ressource/${conference_name}/editer-cpi" var="iriEntity">
                            <spring:param name="iri" value="${entity.idIri}"/>
                        </spring:url>
                        <a href="${iriEntity}" class="btn btn-primary btn-xs">
                            <span class="glyphicon glyphicon-pencil"></span> Plus d'infos
                        </a>
                    </c:if>
                </div>
            </c:if>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <tbody>

                <tr class="info">
                    <td colspan="2">Propriétés</td>
                </tr>

                <%--<tr>--%>
                <%--<td>IRI</td>--%>
                <%--<td>${entity.idIri}</td>--%>
                <%--</tr>--%>

                <c:forEach items="${entity.properties}" var="p">
                    <tr>
                        <td style="min-width: 200px">${session.getPredicatLabel(p.predicate)}</td>
                        <td style="width: 100%">${p.value}</td>
                    </tr>
                </c:forEach>

                <c:if test="${session.hasFieldMoreInfo(entity)}">
                    <tr>
                        <td>Plus d'Infos : ${entity.cpi.first}</td>
                        <td>${entity.cpi.second}</td>
                    </tr>
                </c:if>

                <tr class="info">
                    <td colspan="2">Relations</td>
                </tr>
                <c:forEach items="${entity.relations}" var="p">
                    <tr>
                        <td>${session.getPredicatLabel(p.predicate)}</td>
                        <td>
                            <spring:url value="/ressource/${conference_name}" var="linkedIri">
                                <spring:param name="iri" value="${p.iri}"/>
                            </spring:url>
                            <a href="${linkedIri}">${p.iri}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>