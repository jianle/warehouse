$(document).ready(function(){
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
        url = contextPath + '/consumer/save';
        $('#insertDt').datebox('enable', true);
        if(flagTitle=="编辑"){
            $('#modalForm').form('clear');
            $('#conName').textbox('setValue', '');
            $('#remark').textbox('setValue', '');
        }
        $('#insertDt').datebox('setValue', curDate);
        flagTitle="新增";
    });
});


function edit(Id){
    url = contextPath + '/consumer/update';
    flagTitle="编辑";
    $('#insertDt').datebox('disable', true);
    //初始化 Modal 
    $('#conId').val(Id);    
    $.getJSON(contextPath+"/consumer/getInfo", {conId:Id}, function(data, status){
        console.log(data);
        $('#modalForm').form('load',data);
        $('#dlg').dialog('open').dialog('setTitle','编辑');
    });
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
                    window.location.href=contextPath + '/consumer';
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


function deleteInfo(conId){
    $.messager.confirm('提示', '确认是否删除', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/consumer/delete',
                data: {conId:conId},
                async:true,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    if(result.value){
                        $.messager.show({
                            title: '提示信息',
                            msg: '删除成功'
                        });
                        $("#"+conId+"_tr").remove()
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

