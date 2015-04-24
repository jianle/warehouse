
var url;

function add(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    $('#oId').val($('#curoId').val());
    $('#expressName').textbox('setValue', '中通快递');
    $('#weight').textbox('setValue', '0');
    $('#length').textbox('setValue', '0');
    $('#wide').textbox('setValue', '0');
    $('#high').textbox('setValue', '0');
    url = contextPath + '/delivery/save';
}

function edit(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
    	console.log(row);
        $('#dlg').dialog('open').dialog('setTitle','修改');
        $('#fm').form('clear');
        $('#fm').form('load',row);
        $('#dId').val(row.dId);
        url = contextPath + '/delivery/update';
    }
}


function save(){
	var oIdflag = $('#oId').val();
	if(oIdflag) {
		
	}
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
            return $(this).form('validate');
        },
        dataType:'json',
        success: function(data){
        	var result = eval('('+data+')');
            if (result){
            	$('#dlg').dialog('close');       // close the dialog
                $('#dg').datagrid('reload', {oId:$('#curoId').val()});     // reload the user data
                $.messager.show({
                    title: '提示信息',
                    msg: '操作成功'
                });
            } else {
            	$.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
        }
	});   
}

function destroy(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('提示','确认是否要删除?',function(r){
            if (r){
                $.post(contextPath+'/delivery/delete',{dId:row.dId},function(data){
                	var result = eval('('+data+')');
                    if (result){
                    	$.messager.show({    // show error message
                            title: '提示',
                            msg: '删除成功！'
                        });
                        $('#dg').datagrid('reload', {oId:$('#curoId').val()});    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: '提示',
                            msg: '删除失败'
                        });
                    }
                },'json');
            }
        });
    }
}

function jumpDetail(val,row,index){
    return '<a href="'+contextPath+'/delivery/detail?content='+row.expressId+'" target="_blank">'+row.expressId+'</a>';
  }