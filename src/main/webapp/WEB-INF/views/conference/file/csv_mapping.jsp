<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="_page_body">
  <div class="panel panel-default">

    <spring:url value="/website/conference/${conference_name}/upload/validate_csv" var="form_action_url"/>

    <form:form action ="${form_action_url}" method="post">
      <c:if test="${not empty csvData.nomColones}">

        <div class="panel-body">

          <div id ="form-group">
              <select class="selectpicker" data-style="btn-inverse" id="selectType" data-live-search="true"
                                                  data-size="auto" name = "type">
              <option data-content="<span class='glyphicon glyphicon-plus'></span> Choisir le type de l'entité"></option>
                <c:forEach items="${listTypes}" var="type">
                  <option value="${type.iri}">${type.name}</option>
                </c:forEach>
              </select>
          </div>

          <div class="table-responsive" style="min-height : 400px;">
            <table class="table table-bordered table-fixedheader table-striped">
              <thead>
                <tr class="info">
                    <c:forEach items="${csvData.nomColones}" var="colonne" varStatus="loop">
                        <th><h5>Propriété</h5></th>
                    </c:forEach>
                </tr>
              </thead>
                <tbody>
                  <tr>
                    <c:forEach items="${csvData.nomColones}" var="colonne" varStatus="loop">
                      <td>
                          ${colonne}
                      </td>
                    </c:forEach>
                  <tr>
                    <c:forEach items="${csvData.nomColones}" var="colonne" varStatus="loop">
                            <td>
                              <select class="selectpicker" data-style="btn-inverse" id="selectProperties" data-live-search="true"
                                      data-size="auto" name = "types">
                                <option data-content="<span class='glyphicon glyphicon-plus'></span> Choisir le type"></option>

                                <optgroup label="Propriétés">
                                    <c:forEach items="${listProperties}" var="types">
                                      <option value="${types.name}">${types.name}</option>
                                    </c:forEach>
                                </optgroup>

                              </select>
                            </td>
                    </c:forEach>
                  </tr>

                  <c:forEach items="${csvData.lignes}" var="ligne">
                    <tr>
                      <c:forEach items="${ligne}" var="colonne">
                        <td >${colonne}</td>
                      </c:forEach>
                    </tr>
                  </c:forEach>
                </tbody>
            </table>
        </div>

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

      </div>

      </c:if>

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