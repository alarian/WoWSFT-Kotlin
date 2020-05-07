var index = 0;
var chartDiv1 = document.getElementById('chart_div1');
var chartDiv2 = document.getElementById('chart_div2');
var data1;
var data2;
var materialChart1;
var materialChart2;
var chart1Title = 'Penetration (mm) and Flight Time (s) over distance'
var chart2Title = 'Penetration (mm) and Impact Angle (deg) over distance'
var penetration = 'Penetration (mm)';
var flight = 'Flight Time (s)';
var impact = 'Impact Angle (deg)';
var waitTime = 1500;
var materialOptionsBase = {
    // width: 800,
    height: 600,
    hAxis: {format: '#,###.###', viewWindow: {min: 0}},
    vAxis: {format: '#,###.###'}
};

$(function () {
    doInit();
    nationChange();
});

function nationChange()
{
    var selectedNation = $('#nationSelect option:selected').val();
    var list1 = $('.nationShipType');
    for (var i = 0; i < list1.length; i++) {
        list1.eq(i).addClass('hide');
    }
    var $thisNation = $('#' + selectedNation);
    $thisNation.removeClass('hide');
    $thisNation.addClass('show');
    changeShipType(selectedNation);
}

$('#nationSelect').on('change', function () {
    nationChange();
});

$('.nationShipType').on('change', function () {
    var selectedNation = $('#nationSelect option:selected').val();
    changeShipType(selectedNation);
});

$('.shipList').on('change', function () {
    var selectedNation = $('#nationSelect option:selected').val();
    var selectedShipType = $('#' + selectedNation + ' option:selected').val();
    changeArty($('.' + selectedNation + '.' + selectedShipType + ' option:selected').attr('ship-id'));
});

function changeShipType(selectedNation)
{
    var selectedShipType = $('#' + selectedNation + ' option:selected').val();
    var list1 = $('.shipList');
    for (var i = 0; i < list1.length; i++) {
        list1.eq(i).addClass('hide');
    }
    $('.' + selectedNation + '.' + selectedShipType).removeClass('hide');
    changeArty($('.' + selectedNation + '.' + selectedShipType + ' option:selected').attr('ship-id'));
}

function changeArty(shipId)
{
    var list = $('.artyList.show');
    for (var i = 0; i < list.length; i++) {
        list.eq(i).removeClass('show');
        list.eq(i).addClass('hide');
    }
    $('#' + shipId).removeClass('hide');
    $('#' + shipId).addClass('show');
}

$(document).on('click', '#addSelect', function () {
    var selectedNation = $('#nationSelect option:selected').val(),
        selectedShipType = $('#' + selectedNation + ' option:selected').val(),
        selectedShip = $('.' + selectedNation + '.' + selectedShipType).val(),
        shipId = $('.' + selectedNation + '.' + selectedShipType + ' option:selected').attr('ship-id'),
        selectedArty = $('#' + shipId  + ' option:selected'),
        artyName = selectedArty.val(),
        artyId = selectedArty.attr('arty-id'),
        url = '/arty?index=' + shipId + '&artyId=' + artyId;

    $.ajax({
        url: url,
        type: 'post',
        success: function (data) {
            var apshell = data.status === '200' ? data.result : null;
            if (apshell !== null) {
                var list1 = [];
                var list2 = [];
                for (var i = 0; i < apshell.distanceList.length; i++) {
                    var dist = Number(apshell.distanceList[i]);

                    if (dist > 21000) {
                        break;
                    }

                    if (index === 0) {
                        list1.push([dist, apshell.penetration[dist], apshell.flightTime[dist]]);
                        list2.push([dist, apshell.penetration[dist], apshell.impact[dist]]);
                    } else {
                        var curIndex = index * 2;
                        var tempList1 = [dist];
                        var tempList2 = [dist];
                        for (var j = 1; j <= curIndex; j++) {
                            tempList1.push(null);
                            tempList2.push(null);
                        }
                        tempList1.push(apshell.penetration[dist]);
                        tempList1.push(apshell.flightTime[dist]);
                        tempList2.push(apshell.penetration[dist]);
                        tempList2.push(apshell.impact[dist]);
                        list1.push(tempList1);
                        list2.push(tempList2);
                    }
                }
                drawMaterialChart(data1, materialChart1, chartDiv1, selectedShip + ' ' + artyName, list1, 'Penetration (mm)', 'Flight Time (s)');
                drawMaterialChart(data2, materialChart2, chartDiv2, selectedShip + ' ' + artyName, list2, 'Penetration (mm)', 'Impact Angle (deg)');
                $('#chart_list').append('<button class="button_arty select_arty" data-index="' + index + '">' + selectedShip + ' ' + artyName + '</button>');
                index++;

                // window.setTimeout(function() {
                //     $('#arty circle').attr('r', 3);
                // }, waitTime);
            }
        }
    });
});

function drawMaterialChart(data, materialChart, chartDiv, artyName, list, y1, y2)
{
    data.addColumn('number', artyName + '\n' + y1);
    data.addColumn('number', artyName + '\n' + y2);
    var tempSeries = {};
    for (var i = 0; i <= index; i++) {
        tempSeries[i * 2] = {
            axis: 'y1',
            targetAxisIndex: 0
        };
        tempSeries[i * 2 + 1] = {
            axis: 'y2',
            targetAxisIndex: 1
        };
    }
    data.addRows(list);

    // materialChart = new google.charts.Line(chartDiv);

    var materialOptions = materialOptionsBase
    materialOptions.chart = {title: y1 + ' and ' + y2 + ' over distance'};
    materialOptions.series = tempSeries;
    materialOptions.axes = {y: {'y1': {label: y1}, 'y2': {label: y2}}}
    materialChart.draw(data, google.charts.Line.convertOptions(materialOptions));
}

function drawStuff()
{
    data1 = new google.visualization.DataTable();
    data1.addColumn('number', 'Distance (m)');
    data2 = new google.visualization.DataTable();
    data2.addColumn('number', 'Distance (m)');
    materialChart1 = new google.charts.Line(chartDiv1);
    materialChart2 = new google.charts.Line(chartDiv2);
}

function doInit()
{
    google.charts.load('current', {'packages': ['corechart', 'scatter', 'line']});
    google.charts.setOnLoadCallback(drawStuff);
}

function resetChart()
{
    $('#chart_div1').html('');
    $('#chart_div2').html('');
    $('#chart_list').html('');
    index = 0;
    doInit();
}

$('#resetChart').on('click', function () {
    resetChart()
});

function removeData(idx)
{
    data1.Ff.splice(idx * 2 + 1, 2);
    data2.Ff.splice(idx * 2 + 1, 2);
    for (var i = 0; i < data1.eg.length; i++) {
        data1.eg[i].c.splice(idx * 2 + 1, 2);
        data2.eg[i].c.splice(idx * 2 + 1, 2);
    }
    index = index - 1;

    var tempSeries = {};
    for (var i = 0; i <= index; i++) {
        tempSeries[i * 2] = {axis: 'y1', targetAxisIndex: 0};
        tempSeries[i * 2 + 1] = {axis: 'y2', targetAxisIndex: 1};
    }
    var materialOptions = materialOptionsBase;
    materialOptions.chart = {title: penetration + ' and ' + flight + ' over distance'};
    materialOptions.series = tempSeries;
    materialOptions.axes = {y: {'y1': {label: penetration }, 'y2': {label: flight}}}
    materialChart1.draw(data1, google.charts.Line.convertOptions(materialOptions));

    materialOptions.chart = {title: penetration + ' and ' + impact + ' over distance'};
    materialOptions.axes = {y: {'y1': {label: penetration}, 'y2': {label: impact}}}
    materialChart2.draw(data2, google.charts.Line.convertOptions(materialOptions));
}

$(document).on('click', '.button_arty.select_arty', function () {
    var $this = $(this),
        position = parseInt($this.attr('data-index')),
        $buttons = $('.button_arty.select_arty'),
        count = $buttons.length;

    $this.remove();
    if (count <= 1) {
        resetChart();
    } else {
        removeData(position);

        for (var i = position + 1; i < count; i++) {
            $buttons.eq(i).attr('data-index', $buttons.eq(i).attr('data-index') - 1);
        }
    }
})