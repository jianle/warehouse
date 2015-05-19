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

function deleteInfo(incpayid){
	$.messager.confirm('提示', '确认是否删除', function(r){
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
						$("#"+incpayid+"_tr").remove()
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

