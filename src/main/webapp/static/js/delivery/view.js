
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

var status=0;
var statusType= new Array();
var cssType= new Array();
statusType[0]="待出库";
cssType[0]="mini-label-primary";

statusType[1]="已出库";
cssType[1]="mini-label-success";
function formatterStatus(val,row,index){
	status=val;
	return '<font color="red" class="mini-label '+ cssType[val] +'">' + statusType[val] + '</font>';
}

function formsubmit() {
	var oId=$('#curoId').val();
	if(status==1) {
		$.messager.show({    // show error message
            title: '提示',
            msg: '订单已出库！'
        });
		return;
	}
	$.messager.confirm('提示','确认订单<code>'+ oId +'</code>是否出库?',function(r){
        if (r){
        	$.ajax({
    			cache: true,
    			type:"POST",
    			url :contextPath + '/delivery',
    			data: {oId:oId},
    			async:true,
    			dataType: 'json',
    			success: function(data) {
    				var result = eval('('+data+')');
    				console.log(result);
                    if (result){
                    	$.messager.show({    // show error message
                            title: '提示',
                            msg: '出库成功！'
                        });
                        $('#dg').datagrid('reload', {oId:oId});    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: '提示',
                            msg: '出库失败！'
                        });
                    }
    				
    			},
    			error:function(result) {
    				 $.messager.show({    // show error message
                         title: '提示',
                         msg: '出库失败！'
                     });
    			}
    		});
        }
    });
}
