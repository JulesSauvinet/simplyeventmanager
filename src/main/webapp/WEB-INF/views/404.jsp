<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="_page_body">
    <div class="panel-heading">

        <h1>
            404 Not Found
        </h1>
        <h2>
            L'url demandée n'a pas été trouvée
        </h2>
    </div>
</c:set>


<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
</jsp:include>