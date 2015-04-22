
var url;

function add(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
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
        url = contextPath + '/delivery/update';
    }
}


function save(){
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
                $('#dg').datagrid('reload');     // reload the user data
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
					console.log('ok');
					window.location.href=contextPath + '/delivery';
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