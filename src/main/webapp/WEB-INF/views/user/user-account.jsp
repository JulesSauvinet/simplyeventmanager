<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="_page_body">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4>Mon compte</h4>
        </div>

          <div class="panel-body">


            <table class="table table-bordered table-striped">
              <tr class="info">
                <td colspan="3"><h5>Mes informations</h5></td>
              </tr>
              <tr>
                <td>Nom</td>
                <td>${user.firstName}</td>
              </tr>
              <tr>
                <td>Prenom</td>
                <td>${user.lastName}</td>
              </tr>
              <tr>
                <td>Adresse mail</td>
                <td>${user.email}</td>
              </tr>
            </table>

            <c:if test="${empty user.fb_id}">
              <div id="facebook_button">
                <fb:login-button size="large"  max-rows="1" scope="public_profile,email" onlogin="linkInfos();">
                  Lier son compte
                </fb:login-button>
              </div>
            </c:if>
            <c:if test="${not empty user.fb_id}">
              <fb:login-button size="large" max-rows="1" scope="public_profile,email">
                Votre compte est lié a Facebook
              </fb:login-button>
            </c:if>



    </div>
  </div>
</c:set>

<c:set var="_page_js">
  <script src="<spring:url value="/static/js/facebook.js"/>"></script>
</c:set>

<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="../master.jsp">
  <jsp:param name="_page_body" value="${_page_body}"/>
  <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>