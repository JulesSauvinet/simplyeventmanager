$(function () {
    var dpConf = {
        sideBySide: true,
        showTodayButton: true,
        showClear: true,
        showClose: true,
        allowInputToggle: true,
        format: 'ddd D MMM YY - HH[h]mm',
        locale: 'fr'
    };

    $dpStart = $('#start_date');
    $dpEnd = $('#end_date');

    $dpStart.datetimepicker(dpConf);
    $dpEnd.datetimepicker(dpConf);

    var startOld = $dpStart.attr("data-oldval");
    if (startOld) {
        $dpStart.data("DateTimePicker").date(moment(startOld));
        affectTime();
    }
    var endOld = $dpEnd.attr("data-oldval");
    if (endOld) {
        $dpEnd.data("DateTimePicker").date(moment(endOld));
        affectTime();
    }

    $dpStart.on("dp.change", function (e) {
        $dpEnd.data("DateTimePicker").minDate(e.date);
        affectTime();
    });

    $dpEnd.on("dp.change", function (e) {
        $dpStart.data("DateTimePicker").maxDate(e.date);
        affectTime();
    });
});
var affectTime = function () {
    try {
        $dpStart.find('input[type=hidden]').val(
            $dpStart.data("DateTimePicker").date().toISOString()
        );
    } catch (e) {
    }
    try {
        $dpEnd.find('input[type=hidden]').val(
            $dpEnd.data("DateTimePicker").date().toISOString()
        );
    } catch (e) {
    }
};