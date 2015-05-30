$(document).ready(function(){
    var idType = '0';
    $('#type').combobox({
        onChange : function(n,o){
            idType = n;
            id = "type" + n;
            document.getElementById("type0").style.display="none";
            document.getElementById("type1").style.display="none";
            document.getElementById("type2").style.display="none";
            document.getElementById(id).style.display="block";//显示
        }  
    });
    
    $('#addButton').click(function(){
        var formId = "modalForm" + idType;
        var type = $("#"+"typeId"+idType).textbox('getValue').trim();
        if(type.length<=0) {
            $.messager.alert('提示','请输入类型名称');
            return;
        }
        $.messager.confirm('提示', '确认保存', function(r){
            if(r) {
                // 异步执行删除
                $.ajax({
                    cache: true,
                    type:"POST",
                    url :contextPath + '/incomepayment/save',
                    data: $('#'+formId).serialize(),
                    async:true,
                    dataType: 'json',
                    success: function(result) {
                        console.log(result);
                        if(result.value){
                            $.messager.confirm('提示', '保存成功，是否刷新页面', function(r){
                                window.location.reload();
                            });
                        }else{
                            $.messager.alert("提示", "保存失败");
                        }
                        
                    },
                    error:function(result) {
                        $.messager.alert("提示", "保存失败");
                    }
                });
            }
        });
    });
    
});

//删除目录
function getChecked(){
    var nodes = $('#tt').tree('getChecked');
    var s = '';
    var flag = nodes.length;
    for(var i=0; i<nodes.length; i++){
        if (s != '') s += ',';
        s += nodes[i].id;
    }
    if(flag==0) {
        $.messager.alert('提示','请勾选要删除的目录');
    } else if(flag > 1) {
        $.messager.alert('提示','一次只能删除一个目录，请重新勾选');
    } else {
        deleteInfo(nodes[0].id, nodes[0].text);
    }
}

function deleteInfo(incpayid, text){
    $.messager.confirm('提示', '确认是否删除<code>' + text + '</code>', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/incomepayment/delete',
                data: {incpayid:incpayid},
                async:true,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    if(result.value){
                        $.messager.show({
                            title: '提示信息',
                            msg: '删除成功'
                        });
                        $('#tt').tree('reload');
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

