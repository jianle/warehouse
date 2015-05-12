
var url;
var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
	//初始化 Modal --by 
	$('#addButton').click(function(){
		//$('#modal').modal('show');
		$('#dlg').dialog('open').dialog('setTitle','新增');
		url = contextPath + '/invoice/save';
		$("#invId2").remove();
		if(flagTitle=="编辑"){
			$('#modalForm').form('clear');
			$('#invId').numberbox('enable','true');
			$('#valoremTax').numberbox('setValue', 0.0);
			$('#amount').numberbox('setValue', 0.0);
			$('#amountTax').numberbox('setValue', 0.0);
			$('#rateTax').numberbox('setValue', 0.0);
			$('#remark').textbox('setValue', '');
			$('#invDate').datebox('setValue', curDate);
			$('#incDate').datebox('setValue', curDate);
			$('#verification').numberbox('setValue', 0);
			$('#isDeleted').combobox('setValue', '0');
		}
		flagTitle="新增";
	});
});


function edit(Id, isDeleted){
	url = contextPath + '/invoice/update';
	$('#dlg').dialog('open').dialog('setTitle','编辑');
	var trId = Id+"_tr";
	var tdDate = $("#"+trId+" td");
	flagTitle="编辑";
	$("#modalForm").append('<input type="hidden" id="invId2" value="" name="invId" />')
	//初始化 Modal 
	$('#invId').numberbox('setValue', tdDate[0].innerHTML);
	$('#invId2').val(tdDate[0].innerHTML);
	//$('#invId').numberbox('editable','false');
	$('#invId').numberbox('disable','true');
	$('#invHead').textbox('setValue', tdDate[1].innerHTML);
	$('#valoremTax').numberbox('setValue', tdDate[2].innerHTML);
	$('#amount').numberbox('setValue', tdDate[3].innerHTML);
	$('#amountTax').numberbox('setValue', tdDate[4].innerHTML);
	$('#rateTax').numberbox('setValue', tdDate[5].innerHTML);
	$('#invDate').datebox('setValue', tdDate[6].innerHTML);
	$('#remark').textbox('setValue', tdDate[7].innerHTML);
	$('#incDate').datebox('setValue', tdDate[8].innerHTML);
	$('#invToCompany').textbox('setValue', tdDate[9].innerHTML);
	$('#verification').numberbox('setValue', tdDate[10].innerHTML);
	$('#isDeleted').combobox('setValue', isDeleted);
	
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
					window.location.reload();
                });
            } else {
            	$.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
            $('#dlg').dialog('close');
        }
	}); 
}


function deleteInfo(invId){
	$.messager.confirm('提示', '确认是否删除', function(r){
		if(r) {
			// 异步执行删除
			$.ajax({
				cache: true,
				type:"POST",
				url :contextPath + '/invoice/delete',
				data: {invId:invId},
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

