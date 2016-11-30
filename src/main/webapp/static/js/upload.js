var $typeSelect, $fromIcs, $fromCsv, $fromRdf, $fromXslt;

$(function () {
    $typeSelect = $('#typeSelect');
    $fromIcs = $('#form-ics');
    $fromCsv = $('#form-csv');
    $fromRdf = $('#form-rdf');
    $fromXslt = $('#form-xslt');
    $typeSelect.change(fileTypeChanged);

    fileTypeChanged();
});

function fileTypeChanged() {
    $fromIcs.hide();
    $fromCsv.hide();
    $fromRdf.hide();
    $fromXslt.hide();

    switch ($typeSelect.val()) {
        case 'CSV':
            $fromCsv.show();
            break;
        case 'ICS':
            $fromIcs.show();
            break;
        case 'XML_RDF':
        case 'JSON_LD':
            $fromRdf.show();
            break;
        case 'XML_XSLT':
            $fromXslt.show();
            break;
    }
}