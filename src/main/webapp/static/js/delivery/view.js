
var url;

function add(){
    $('#dlg').dialog('open').dialog('setTitle','新增');
    $('#fm').form('clear');
    $('#expressName').textbox('setValue', '中通快递');
    $('#weight').textbox('setValue', '0');
    $('#length').textbox('setValue', '0');
    $('#wide').textbox('setValue', '0');
    $('#high').textbox('setValue', '0');
    url = contextPath + '/order/save';
}

function edit(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
    	console.log(row);
        $('#dlg').dialog('open').dialog('setTitle','修改');
        $('#fm').form('clear');
        $('#fm').form('load',row);
        url = contextPath + '/order/update';
    }
}