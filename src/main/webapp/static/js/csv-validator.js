

/*
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var eventIndex = $('#eventTable tr').children().length - 1;
        $('#eventTable').append(
            '<tr><td>'+
            '   <input type="file" name="files['+ fileIndex +']" />'+
            '</td></tr>');
    });

        '<tr>'
    +   '<label for="eventName">Nom de l'evenement: </label>'
    +   '<input type="text" name="eventName[i]" id="eventName" class="form-control">'
    +   '</tr>'
    +   '<tr>'
    +   'Type de l\'évènement :'
    +   '<select class="selectpicker" data-style="btn-inverse" id="selectType" data-live-search="true"'
    data-size="auto" name = "typeEvent[i]">
        <option data-content="<span class='glyphicon glyphicon-plus'></span> Choisir le type d'évènement"></option>
        <c:forEach items="${listTypes}" var="typeEvent">
    <option value="${typeEvent.iri}">${typeEvent.name}</option>
        </c:forEach>
    </select>
    </tr>

});*/