$(document).ready(function(){
    
    $('#startDate').datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        orientation: "auto",
        autoclose: true
    });
    

    $('#endDate').datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        orientation: "auto right",
        autoclose: true
    });
    
    $('#btnSearch').click(function(){
        document.getElementById('firstFromId').submit();
    });
    
});

var url;
var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
    //初始化 Modal --by 
    $('#addButton').click(function(){
        //$('#modal').modal('show');
        $('#dlg').dialog('open').dialog('setTitle','新增');
        url = contextPath + '/billReceivable/save';
        if(flagTitle=="编辑"){
            $('#modalForm').form('clear');
            $('#brDate').datebox('setValue', curDate);
            $('#amount').numberbox('setValue', 0);
            $('#remark').textbox('setValue', '');
        }
        flagTitle="新增";
    });
});


function edit(Id, conId){
    url = contextPath + '/billReceivable/update';
    $('#dlg').dialog('open').dialog('setTitle','编辑');
    var trId = Id+"_tr";
    flagTitle="编辑";
    var tdDate = $("#"+trId+" td");
    //初始化 Modal 
    $('#brId').val(Id);
    //$('.content #name').attr('readonly','readonly');
    $('#conId').combobox('setValue', conId);
    $('#brDate').datebox('setValue', tdDate[2].innerHTML );
    $('#amount').numberbox('setValue', tdDate[3].innerHTML );
    $('#remark').textbox('setValue', tdDate[4].innerHTML );
}


function save(){
    $('#modalForm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            $('#dlg').dialog('close');
            console.log(result);
            console.log(result.indexOf("true"));
            var hasTrue = result.indexOf("true");
            if (hasTrue>=0){
                $('#dlg').dialog('close');
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
                    console.log('ok');
                    window.location.href=contextPath + '/billReceivable';
                });
            } else {
                $.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
        }
    }); 
    $('#dlg').dialog('close');
}


function deleteInfo(brId){
    $.messager.confirm('提示', '确认是否删除', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/billReceivable/delete',
                data: {brId:brId},
                async:true,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    if(result.value){
                        $.messager.show({
                            title: '提示信息',
                            msg: '删除成功'
                        });
                        $("#"+brId+"_tr").remove()
                    }else{
                        $.messager.alert("提示", "删除失败");
                    }
                    
                },
                error:function(result) {
                    $.messager.alert("提示", "删除失败");
                }
            });
        }
    });
}

