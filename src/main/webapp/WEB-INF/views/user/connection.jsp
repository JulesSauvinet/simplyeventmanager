<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="_page_body">
    <div class="row">
        <div class="col-md-4 col-md-push-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Connexion à Symple Event Manager</h4>
                </div>
                <div class="panel-body">
                    <c:if test="${not empty session.messages.get('connection.error')}">
                        <div class="alert alert-danger">
                            <c:forEach var="error" items="${session.messages.pop('connection.error')}">
                                ${error} <br>
                            </c:forEach>
                        </div>
                    </c:if>
                    <form action="<spring:url value="/website/user/connexion.do"/>" method="post">
                        <div class="form-group">
                            <label for="id">Identifiant</label>
                            <input type="text" class="form-control" id="id" name="id"
                                   placeholder="Entrer votre adresse email">
                        </div>
                        <div class="form-group">
                            <label for="mdp">Mot de passe</label>
                            <input type="password" class="form-control" id="mdp" name="mdp"
                                   placeholder="Entrer votre mot de passe">
                        </div>
                        <button type="submit" class="btn btn-success loggin-btn">Connexion</button>
                    </form>

                    <c:if test="${empty session.user.fb_id}">
                        <div id="facebook_button">
                            <fb:login-button size="large" max-rows="1" scope="public_profile,email" onlogin="checkLoginState();">
                                Connexion avec Facebook
                            </fb:login-button>
                        </div>
                    </c:if>
                    <c:if test="${not empty session.user.fb_id}">
                        <div id="facebook_button">
                            <fb:login-button size="large" show-faces="true" max-rows="1" scope="public_profile,email" onlogin="checkLoginState();">
                                Connexion avec Facebook
                            </fb:login-button>
                        </div>
                    </c:if>
                    <div id="status">
                    </div>

                </div>
            </div>
        </div>
    </div>

</c:set>


<c:set var="_page_js">
    <script src="<spring:url value="/static/js/facebook.js"/>"></script>
</c:set>


<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>
