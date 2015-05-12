

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
		url = contextPath + '/ledgerReceivable/save';
		if(flagTitle=="编辑"){
			$('#modalForm').form('clear');
			$('#payDate').datebox('setValue', curDate);
			$('#amount').numberbox('setValue', 0);
			$('#verification').numberbox('setValue', 0);
			$('#remark').textbox('setValue', '');
		}
		flagTitle="新增";
	});
});


function edit(Id){
	url = contextPath + '/ledgerReceivable/update';
	$('#dlg').dialog('open').dialog('setTitle','编辑');
	var trId = Id+"_tr";
	flagTitle="编辑";
	var tdDate = $("#"+trId+" td");
	//初始化 Modal 
	$('#lrId').val(Id);
	//$('.content #name').attr('readonly','readonly');
	$('#payCompany').textbox('setValue', tdDate[1].innerHTML );
	$('#amount').numberbox('setValue', tdDate[2].innerHTML );
	$('#payDate').datebox('setValue', tdDate[3].innerHTML );
	$('#verification').numberbox('setValue', tdDate[4].innerHTML );
	$('#remark').textbox('setValue', tdDate[5].innerHTML );
}


function save(){
	$('#modalForm').form('submit',{
		url: url,
		onSubmit: function(){
            return $(this).form('validate');
        },
        dataType:'text',
        success: function(data){
        	var result = eval('('+data+')');
            if (result){
            	$('#dlg').dialog('close');
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
					console.log('ok');
					window.location.href=contextPath + '/ledgerReceivable';
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


function deleteInfo(lrId){
	$.messager.confirm('提示', '确认是否删除', function(r){
		if(r) {
			// 异步执行删除
			$.ajax({
				cache: true,
				type:"POST",
				url :contextPath + '/ledgerReceivable/delete',
				data: {lrId:lrId},
				async:true,
				dataType: 'text',
				success: function(result) {
					if(result=='true'){
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

