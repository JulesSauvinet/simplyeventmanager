var $propsContainer;
var $typeEntitySelect;
var $relationSelect;
var $propertySelect;
var $entityLabel;
var predicates = {};

$(function () {
    $relationSelect = $('#addRelationSelect');
    $propertySelect = $('#addPropertySelect');
    $propsContainer = $('#propertiesContainer');
    $typeEntitySelect = $('#entity-type');
    $entityLabel = $('#entity-name');

    $typeEntitySelect.change(function(){
       loadPredicatsOfType($(this).val(), $relationSelect, $propertySelect);
    });

    $('#entity-editor-form').submit(cleanInputNumerotation);

    // Boutons suppression propriété
    $propsContainer.on('click', '.delete-line-btn', function(){
       $(this).closest('.property-line').remove();
    });

    $relationSelect.change(function(){addRelationLine();});
    $propertySelect.change(function(){addPropertyLine();});
});

/**
 * Affecte une numérotation correcte sur les champs
 * input pour l'envoi du formulaire
 * @param e
 */
function cleanInputNumerotation(e) {

    var setNumber = function (selectors) {

        for (var i = 0; i < selectors.length; i++) {

            var counter = 0;

            $(selectors[i]).each(function () {
                $(this).find('input,select').each(function () {
                    var name = $(this).attr('name');

                    if(name){
                        name = name.replace('[]', '[' + counter + ']');
                        $(this).attr('name', name);
                    }
                });
                counter++;
            });
        }
    };

    setNumber(['.basic-input', '.relation-input']);
}

function addRelationLine(predicate, entity) {

    var predicate = predicate || $relationSelect.val();
    var label = entity ? getPredicateLabel(entity.typeIri) : $relationSelect.find('option:selected').text();
    var $tmpl = $($('#relation-input').html());

    $tmpl.find('.relation-label').text(label);
    $tmpl.find('.relation-predicate').val(predicate);
    $tmpl.find('select').selectpicker('refresh');

    var $typeSelect = $tmpl.find('select.relation-type-select');
    var $entitySelect = $tmpl.find('select.relation-entity-select');

    if(entity){
        $typeSelect.find('option[value="' + entity.typeIri + '"]').attr('selected', true);
    }

    $typeSelect.change(function() {
        var typeIri = $(this).val();
        loadEntityOfType(typeIri, $entitySelect, entity ? entity.idIri : undefined);
    });

    $typeSelect.trigger('change');

    addLine($relationSelect, $tmpl);
}

function addPropertyLine(entityPredicate, entityValue, entityLabel) {
    var predicate = entityPredicate || $propertySelect.val();
    var label = entityPredicate ? getPredicateLabel(entityPredicate) : $propertySelect.find('option:selected').text();
    var value = entityValue || '';

    var $tmpl = $($('#basic-input').html());
    $tmpl.find('.property-label').text(label);
    $tmpl.find('.property-predicate').val(predicate);
    $tmpl.find('.property-value').val(value);

    addLine($propertySelect, $tmpl);
}

function addLine($select, content) {
    $propsContainer.prepend(content);
    $select.find('option:first').attr({'selected': true});
    $select.selectpicker('refresh');
}

function loadEntityForUpdate(iri){
    $.getJSON('ontologies', function(data){
        predicates = data;
        var path = location.pathname.match(/(.*)\//)[1];

        $.ajax({
            type: "GET",
            url: path,
            data: 'iri=' + encodeURIComponent(iri),
            headers: {
                Accept: 'application/json'
            },
            complete: function(data){
                var entity = data.responseJSON;
                $entityLabel.val(entity.label);
                $typeEntitySelect.find('option[value="' + entity.typeIri + '"]').attr('selected', true);;
                $typeEntitySelect.trigger('change');
                var i;
                var predicate, relationEntity;
                for(i in entity.relations){
                    predicate = entity.relations[i].predicate;
                    relationEntity = entity.relations[i].entity;
                    addRelationLine(predicate, relationEntity);
                }

                for(i in entity.properties){
                    currEntity = entity.properties[i];
                    addPropertyLine(currEntity.predicate, currEntity.value);
                }
            }
        });
    });
}

function loadEntityOfType(typeIri, $select, defaultEntityIri){

    if(!typeIri || typeIri.trim() == ''){
        return;
    }

    $.getJSON('listResource', 'typeIri=' + encodeURIComponent(typeIri), function(data){
        $select.html('');

        for(var i in data){
            var $opt = $('<option>');
            $opt.attr('value', data[i].iri);
            $opt.text(data[i].label);
            $select.append($opt);

            if(defaultEntityIri == data[i].iri){
                $opt.attr('selected', true);
            }
        }

        $select.selectpicker('refresh');
    });
}

function loadPredicatsOfType(typeIri, $relationsSelect, $propertiesSelect){

    if(!typeIri || typeIri.trim() == ''){
        return;
    }

    $.getJSON('listPredicat', 'typeIri=' + encodeURIComponent(typeIri), function(data){

        $relationsSelect.html("");
        $propertiesSelect.html("");

        for(var i in data.relations){
            var $opt = $('<option>');
            $opt.attr('value', data.relations[i].predicat);
            $opt.text(data.relations[i].label);
            $relationsSelect.append($opt);
        }
        for(i in data.properties){
            $opt = $('<option>');
            $opt.attr('value',data.properties[i].predicat);
            $opt.text(data.properties[i].label);
            $propertiesSelect.append($opt);
        }

        $propertiesSelect.selectpicker('refresh');
        $relationsSelect.selectpicker('refresh');
    });
}

function getPredicateLabel(iri){
    if(predicates && predicates[iri]){
        return predicates[iri].name;
    }

    return iri;
}