$(document).ready(function(){
	$('#checkVerification').click(
	 function(){
		var amount = $('#amount').html();
		var invIds = new Array();
		var lrId = $('#lrId').val();
		$("input[name = invId]:checkbox").each(function(){
			if ($(this).is(":checked")) {
				var invId = $(this).attr("value");
				var tdData = $('#'+invId+'_tr td');
				var tmpamount = tdData[2].innerHTML;
				amount = amount - tmpamount;
	            invIds.push(invId);
	        }
		});
		
		if(amount>0 && invIds.length>0) {
			
			$.messager.confirm('提示', '确认是否核销', function(r){
				if(r) {
					// 异步执行删除
					$.ajax({
						cache: true,
						type:"POST",
						url :contextPath + '/ledgerReceivable/verification/confirm',
						data: {invId:JSON.stringify(''+ invIds),lrId:lrId},
						async:true,
						dataType: 'json',
						success: function(result) {
							console.log(result);
							if(result.value){
								$.messager.confirm('提示', '核销成功，是否刷新页面查看', function(r){
									window.location.reload();
				                });
								
							}else{
								$.messager.alert("提示", "核销失败");
							}
							
						},
						error:function(result) {
							$.messager.alert("提示", "核销失败");
						}
					});
				}
			});
			
		}else {
			if(invIds.length==0) $.messager.alert('提示', '请勾选需核销发票');
			else $.messager.alert('提示', '可销金额不够，请重新选择');
		}
		
		console.log(invIds);
		console.log(JSON.stringify(''+ invIds));
		console.log(JSON.stringify(invIds));
		
	});
});

//check all
function checkAllMonitor(){
	var checkAll = $('#checkAll')[0].checked;
	if(checkAll) {
		$("input[name = invId]:checkbox").prop("checked", true);
	}else {
		$("input[name = invId]:checkbox").prop("checked", false);
	}
}

