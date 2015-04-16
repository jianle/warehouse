
var flagTitle="编辑";

function modalEdit(Id, sId){
	var trId = Id+"_tr";
	flagTitle="编辑";
	$('.form-group').removeClass('has-error');
	$('.form-group span').remove();
	//初始化goodsModal Modal --by goodsEdit
	$('#goodsModal .modal-title').html("编辑");
	$('.modal-body #gId').val(Id);
	$('.modal-body #sId').val(sId);
	//$('.modal-body #name').attr('readonly','readonly');
	$('.modal-body #gName').val( $("#"+trId+" td")[0].innerHTML);
	$('.modal-body #sName').val( $("#"+trId+" td")[1].innerHTML);
	$('.modal-body #amount').val($("#"+trId+" td")[2].innerHTML);
	$('.modal-body #remarks').val($("#"+trId+" td")[3].innerHTML);
}

function modalDelete(gId){
	$.messager.confirm('提示', '确认是否删除', function(){
		// 异步执行删除
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/storage/delete',
			data: {gId:gId},
			async:true,
			dataType: 'json',
			success: function(result) {
				if(result.value){
					$.messager.popup("删除成功.");
					$("#"+gId+"_tr").remove()
				}else{
					$.messager.alert("提示", "删除失败");
				}
				
			},
			error:function(result) {
				$.messager.alert("提示", "删除失败");
			}
		});
	});
}



function modalUpdate(){
	//document.getElementById('formGoodsAddUpdate').action= contextPath + '/storage/update';
	$.ajax({
		cache: true,
		type:"POST",
		url :contextPath + '/storage/update',
		data: $("#formModal").serialize(),
		async:true,
		dataType: 'json',
		success: function(result) {
			if(result.value){
				$.messager.confirm('提示', '修改成功，是否刷新页面查看', function(r){
					console.log('ok');
					window.location.href=contextPath + '/storage';
				});
			}else{
				$.messager.alert("提示", "修改失败");
			}
			
		},
		error:function(result) {
			$.messager.alert("提示", "修改失败");
		}
	});
	//document.getElementById('formGoodsAddUpdate').submit();
	$('#Modal').modal('hide');
}



function modalCommit(){
	modalUpdate();
}
