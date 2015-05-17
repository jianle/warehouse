var url;
var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
	//初始化 Modal --by 
	$('#addButton').click(function(){
		//$('#modal').modal('show');
		$('#dlg').dialog('open').dialog('setTitle','新增');
		url = contextPath + '/invoiceIncome/save';
		$("#invId2").remove();
		$('#invId').numberbox('enable','true');
		$('#number').numberbox('enable','true');
		if(flagTitle=="编辑"){
			$('#modalForm').form('clear');
			$('#invId').numberbox('enable','true');
			$('#number').numberbox('enable','true');
			$('#number').numberbox('setValue', 1);
			$('#valoremTax').numberbox('setValue', 0.0);
			$('#amount').numberbox('setValue', 0.0);
			$('#amountTax').numberbox('setValue', 0.0);
			$('#rateTax').numberbox('setValue', 0.0);
			$('#invType').combobox('setValue', 0);
			$('#remark').textbox('setValue', '');
			$('#invDate').datebox('setValue', curDate);
			$('#rateRebate').numberbox('setValue', 0.0);
			$('#isDeleted').combobox('setValue', 0);
		}
		flagTitle="新增";
	});
});


function edit(Id,proId,conId,invType,isDeleted){
	url = contextPath + '/invoiceIncome/update';
	$('#dlg').dialog('open').dialog('setTitle','编辑');
	var trId = Id+"_tr";
	var tdDate = $("#"+trId+" td");
	flagTitle="编辑";
	$("#modalForm").append('<input type="hidden" id="invId2" value="" name="invId" />')
	//初始化 Modal 
	$('#invId').numberbox('setValue', $("#"+trId+" th")[0].innerHTML);
	$('#invId2').val($("#"+trId+" th")[0].innerHTML);
	//$('#invId').numberbox('editable','false');
	$('#invId').numberbox('disable','true');
	$('#number').numberbox('disable','true');
	$('#proId').combobox('setValue', proId);
	$('#valoremTax').numberbox('setValue', tdDate[1].innerHTML);
	$('#amount').numberbox('setValue', tdDate[2].innerHTML);
	$('#amountTax').numberbox('setValue', tdDate[3].innerHTML);
	$('#rateTax').numberbox('setValue', tdDate[4].innerHTML);
	$('#invDate').datebox('setValue', tdDate[5].innerHTML);
	$('#remark').textbox('setValue', tdDate[8].innerHTML);
	$('#rateRebate').numberbox('setValue', tdDate[9].innerHTML);
	$('#conId').combobox('setValue', conId);
	$('#invType').combobox('setValue', invType);
	$('#isDeleted').combobox('setValue', isDeleted);
	
}


function save(){
	$('#modalForm').form('submit',{
		url: url,
		onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        	console.log(result.indexOf("true"));
        	var hasTrue = result.indexOf("true");
        	
            if (hasTrue>=0){
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
	$('#dlg').dialog('close');
}


function deleteInfo(invId){
	$.messager.confirm('提示', '确认是否删除', function(r){
		if(r) {
			// 异步执行删除
			$.ajax({
				cache: true,
				type:"POST",
				url :contextPath + '/invoiceIncome/delete',
				data: {invId:invId},
				async:true,
				dataType: 'json',
				success: function(result) {
					console.log(result);
					if(result.value){
						$.messager.show({
		                    title: '提示信息',
		                    msg: '删除成功'
		                });
						$("#"+invId+"_tr").remove()
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
