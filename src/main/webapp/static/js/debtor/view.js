function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

function monthSub(startDate, endDate){
    var sy = startDate.getFullYear();
    var sm = startDate.getMonth()+1;
    
    var ey = endDate.getFullYear();
    var em = endDate.getMonth()+1;
    
    if(sy>ey) {
        return -1;
    } else if(sy == ey) {
        return (em - sm) + 1;
    } else {
        var len = 0;
        len = (12 - sm) + 1;
        len = len + em
        return len;
    }
    
}

function monthDiff(startDate, months){
    var sy = startDate.getFullYear();
    var sm = startDate.getMonth()+1;
    
    if(months>11) {
        return -1;
    } else if(months == sm) {
        return new Date(sy-1,11,01);
    } else if(months >= sm) {
        sy = sy - 1;
        var m = 12 - (months-sm);
        return new Date(sy,m-1,01);
    } else {
        return new Date(sy,sm-1-months,01);
    }
    
}

function monthAdd(startDate, months){
    var sy = startDate.getFullYear();
    var sm = startDate.getMonth()+1;
    
    sm = sm + months;
    
    if(sm>12) {
        return new Date(sy+1,sm-13,01);
    } else {
        return new Date(sy,sm-1,01);
    }
    
}


$(document).ready(function(){
    
    $('#startDate').datepicker({
        format: "yyyy-mm-dd",
        startView: 1,
        minViewMode: 1,
        language: "zh-CN",
        orientation: "auto",
        autoclose: true
    });
    

    $('#endDate').datepicker({
        format: "yyyy-mm-dd",
        startView: 1,
        minViewMode: 1,
        language: "zh-CN",
        orientation: "auto right",
        autoclose: true
    });
    
    $('#btnSearch').click(function(){
        document.getElementById('firstFromId').submit();
    });
    /*
    $('#startDate').datepicker()
    .on("changeDate", function(e, n){
        console.log(myformatter(e.date));
        var startDate = $('#startDate').datepicker('getDate');
        var endDate = $('#endDate').datepicker('getDate');
        var monthdiff = monthSub(startDate, endDate)
        console.log(monthdiff);
        if(monthdiff<=0) {
            $.messager.confirm('提示', '开始日期必须比结束日期大', function(r){
                $('#startDate').datepicker('setDate', endDate);
            });
        } else if(monthdiff>8) {
            $.messager.confirm('提示', '只能查看8各月内的汇总', function(r){
                $('#startDate').datepicker('setDate', endDate);
            });
        }
    });
    */
    
    $('#upButton').click(function(){
        
        var startDate = $('#startDate').datepicker('getDate');
        var endDate = $('#endDate').datepicker('getDate');
        
        $('#startDate').datepicker('setDate', monthDiff(startDate,1));
        $('#endDate').datepicker('setDate', monthDiff(endDate,1));
        
        document.getElementById('firstFromId').submit();
    });
    
    $('#downButton').click(function(){
        
        var startDate = $('#startDate').datepicker('getDate');
        var endDate = $('#endDate').datepicker('getDate');
        
        $('#startDate').datepicker('setDate', monthAdd(startDate,1));
        $('#endDate').datepicker('setDate', monthAdd(endDate,1));
        
        document.getElementById('firstFromId').submit();
    });
    
});


function detail(amount, verification) {
	$('#dlg').dialog('open').dialog('setTitle','提示');
	$('#amountDlg').html(amount);
	$('#verificationDlg').html(verification);
}
