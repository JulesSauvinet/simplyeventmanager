<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="_page_body">
    <form action="<spring:url value="/website/user/creer-compte.do"/>" method="post">
        <div class="row">
            <div class="col-md-4 col-md-push-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4>Création d'un compte</h4>
                    </div>
                    <div class="panel-body">
                        <c:if test="${not empty session.messages.get('creation_compte.error')}">
                            <div class="alert alert-danger">
                                <c:forEach var="error" items="${session.messages.pop('creation_compte.error')}">
                                    ${error} <br>
                                </c:forEach>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="firstname">Prénom</label>
                            <input type="text" class="form-control" id="firstname" name="firstname"
                                   placeholder="Entrer votre prénom">
                        </div>
                        <div class="form-group">
                            <label for="lastname">Nom</label>
                            <input type="text" class="form-control" id="lastname" name="lastname"
                                   placeholder="Entrer votre nom">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="Email">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Mot de passe</label>
                            <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Mot de passe">
                        </div>
                        <div class="form-group">
                            <label for="pwd-check">Vérification du mot de passe</label>
                            <input type="password" class="form-control" id="pwd-check" name="pwd-check"
                                   placeholder="Confirmer votre mot de passe">
                        </div>

                        <button type="submit" class="btn btn-success">Valider</button>
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