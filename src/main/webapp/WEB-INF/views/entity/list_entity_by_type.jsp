<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Contenu de la page définit dans la variable "${_page_body}}--%>
<c:set var="_page_body">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4>${session.getPredicatLabel(type_entities)} de la conférence ${conference_name}</h4>
        </div>

        <div class="panel-body">
            <c:forEach items="${list_entity}" var="entity">
                <c:set var="entity" value="${entity}" scope="request"/>
                <c:set var="isAlone" value="${true}" scope="request"/>
                <jsp:include page="/WEB-INF/views/entity/entity_viewer_fragment.jsp"/>
            </c:forEach>
        </div>
    </div>
</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
</jsp:include>