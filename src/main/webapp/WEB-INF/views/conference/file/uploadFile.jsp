<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%-- Contenu de la page définit dans la variable "${_page_body}}--%>
<c:set var="_page_body">
    <div class="row">
        <div class="col-md-5 col-centered">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>Importer des données pour la conférence ${conference_name}</h4>
                </div>

                <div class="panel-body">
                    <div class=".form-control">
                        <c:if test="${not empty session.messages.get('file_import.error')}">
                            <div class="alert alert-danger">
                                <c:forEach var="error" items="${session.messages.pop('file_import.error')}">
                                    ${error} <br>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                    <spring:url value="/website/conference/${conference_name}/upload.do" var="uploadFormUrl"/>
                    <form:form modelAttribute="uploadedFile" method="post"
                               enctype="multipart/form-data"
                               action="${uploadFormUrl}">

                        <div class="form-group">
                            <label for="typeSelect">Type du fichier :</label>
                            <select class="form-control selectpicker" name="type" id="typeSelect">
                                <c:forEach items="${listOfTypes}" var="type">
                                    <option value="${type}">${type.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="file1">Fichier à importer : </label>
                            <input name="files[0]" id="file1" type="file" class="form-control"/>
                        </div>

                        <div class="form-group">

                            <div style="display:none" id="form-xslt">
                                <label for="file2">Fichier XSLT :</label>
                                <input name="files[1]" id="file2" type="file" class="form-control"/>
                            </div>

                            <div style="display:none" id="form-rdf">
                                Le fichier RDF doit respecter les vocabulaires RDF suivant :
                                <ul>
                                    <li>SWC</li>
                                    <li>SWRC</li>
                                    <li>FOAF</li>
                                </ul>
                            </div>

                            <div style="display:none" id="form-ics">

                            </div>

                            <div style="display:none" id="form-csv">
                                <label for="separateur">Separateur :</label>
                                <select class="form-control" name="separateur" id="separateur">
                                    <option>|</option>
                                    <option>,</option>
                                    <option>;</option>
                                    <option>;</option>
                                    <option>.</option>
                                    <option>\</option>
                                    <option>/</option>
                                    <option>#</option>
                                    <option>&</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-success">
                                <span class="glyphicon glyphicon-upload"></span> Envoyer
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</c:set>
<c:set var="_page_js">
    <script src="<spring:url value="/static/js/upload.js"/>"></script>
</c:set>
<%-- Inclusion de template le contenu en paramètre --%>
<jsp:include page="/WEB-INF/views/master.jsp">
    <jsp:param name="_page_body" value="${_page_body}"/>
    <jsp:param name="_page_js" value="${_page_js}"/>
</jsp:include>