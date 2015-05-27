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
    
});

