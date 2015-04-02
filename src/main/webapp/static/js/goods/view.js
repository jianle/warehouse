
var flagTitle="编辑";

$(document).ready(function() {
	//初始化accountAdd Modal --by accountAdd
	$('#goodsAdd').click(function(){
		
		$('.form-group').removeClass('has-error');
		$('.form-group span').remove();
		
		if(flagTitle=="编辑"){
			$('#goodsModal .modal-title').html("新增");
			$('.modal-body #name').removeAttr("readonly");
			//$('.modal-body #gId').val(Id);
			//$('.modal-body #sId').val(sId);
			//$('.modal-body #name').attr('readonly','readonly');
			$('.modal-body #name').val("");
			$('.modal-body #sName').val("");
			$('.modal-body #length').val(0);
			$('.modal-body #width').val(0);
			$('.modal-body #height').val(0);
			$('.modal-body #weight').val(0);
			$('.modal-body #gIdSupplier').val("");
			$('.modal-body #amount').val(0);
		}
		flagTitle="新增";
	});
});


function goodsEdit(Id, isDisabled, sId){
	var trId = Id+"_tr";
	flagTitle="编辑";
	$('.form-group').removeClass('has-error');
	$('.form-group span').remove();
	//初始化goodsModal Modal --by goodsEdit
	$('#goodsModal .modal-title').html("编辑");
	$('.modal-body #gId').val(Id);
	$('.modal-body #sId').val(sId);
	//$('.modal-body #name').attr('readonly','readonly');
	$('.modal-body #name').val( $("#"+trId+" td")[0].innerHTML );
	$('.modal-body #sName').val( $("#"+trId+" td")[1].innerHTML );
	$('.modal-body #length').val( $("#"+trId+" td")[2].innerHTML );
	$('.modal-body #width').val( $("#"+trId+" td")[3].innerHTML );
	$('.modal-body #height').val($("#"+trId+" td")[4].innerHTML);
	$('.modal-body #weight').val($("#"+trId+" td")[5].innerHTML);
	$('.modal-body #gIdSupplier').val($("#"+trId+" td")[6].innerHTML);
	$('.modal-body #amount').val($("#"+trId+" td")[7].innerHTML);
	$('.modal-body #isDisabled').val(isDisabled);
}

function deleteGoods(gId,content){
	$.messager.confirm('提示', '确认是否删除<br/> <code>商品名称</code>：' + content, function(){
		// 异步执行删除
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/goods/delete',
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

$(document).ready(function(){
	$("#formGoodsAddUpdate").validate({
		errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : false,
		rules:{
			name :{
				required: true,
			},
			sId : {
			    required: true,
			}
		},
		messages: {
			name :{
				required: "请输入商品名称",
			},
			sId : {
			    required: "请输入供应商名称",
			}
		},
		highlight : function(element) {  
            $(element).closest('.form-group').addClass('has-error');  
        },  

        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },  

        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },  
        
        submitHandler : function(form) {  
        	goodsAddUpdate();
        }  
	});
	
	$('#formGoodsAddUpdate input').keypress(function(e) {  
        if (e.which == 13) {  
            if ($('#formGoodsAddUpdate').validate().form()) {  
            	goodsAddUpdate();
            }  
            return false;  
        }  
    });  
});


function goodsAddUpdate(){
	$("#formGoodsAddUpdate").validate();
	console.log(contextPath);
	var title = $('#goodsModal .modal-title').html().trim() ;
	if(title=="新增"){
		document.getElementById('formGoodsAddUpdate').action= contextPath + '/goods/save';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/goods/save',
			data: $("#formGoodsAddUpdate").serialize(),
			async:true,
			dataType: 'json',
			success: function(result) {
				console.log(result);
				if(result.value){
					$.messager.confirm('提示', '添加成功，是否刷新页面查看', function(r){
						console.log('ok');
						window.location.reload();
					});
				}else{
					$.messager.alert("提示", "添加失败，请确保供应商是点击选择的");
				}
				
			},
			error:function(result) {
				$.messager.alert("提示", "添加失败");
			}
		});
		
	}else if(title=="编辑"){
		document.getElementById('formGoodsAddUpdate').action= contextPath + '/goods/update';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/goods/update',
			data: $("#formGoodsAddUpdate").serialize(),
			async:true,
			dataType: 'json',
			success: function(result) {
				if(result.value){
					$.messager.confirm('提示', '修改成功，是否刷新页面查看', function(r){
						console.log('ok');
						window.location.reload();
					});
				}else{
					$.messager.alert("提示", "修改失败");
				}
				
			},
			error:function(result) {
				$.messager.alert("提示", "修改失败");
			}
		});
	}
	//document.getElementById('formGoodsAddUpdate').submit();
	$('#goodsModal').modal('hide'); 
}

function goodsCommit(){
	$("#formGoodsAddUpdate").validate();
    $('#formGoodsAddUpdate').submit();
}




