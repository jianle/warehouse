$(document).ready(function(){
    $('#btnSearch').click(function(){
        document.getElementById('firstFromId').submit();
    });
    
});

//check all
function showpwd(){
    var checkAll = $('#checkID')[0].checked;
    if(checkAll) {
    	$('#password').textbox({type:'text'});
    }else {
    	$('#password').textbox({type:'password'});
    }
}



var url;
var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
    //初始化 Modal --by 
    $('#addButton').click(function(){
        //$('#modal').modal('show');
        $('#dlg').dialog('open').dialog('setTitle','新增');
        url = contextPath + '/users/save';
        if(flagTitle=="编辑"){
            $('#modalForm').form('clear');
        }
        flagTitle="新增";
    });
});


function edit(id){
    url = contextPath + '/users/update';
    flagTitle="编辑";
    //初始化 Modal 
    $('#id').val(id);    
    $.getJSON(contextPath+"/users/getInfo", {id:id}, function(data, status){
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
                    window.location.href=contextPath + '/users';
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


function deleteInfo(id){
    $.messager.confirm('提示', '确认是否删除', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/users/delete',
                data: {id:id},
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

