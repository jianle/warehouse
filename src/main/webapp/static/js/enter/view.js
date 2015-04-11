
$(document).ready(function(){
	$("#enterGoods").validate({
		errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : false,
		rules:{
			gId :{
				required: true,
				digits:true,
				minlength:1
			},
			gName : {
			    required: true,
			},
			chests : {
				required: true,
				digits:true,
			},
			boxes : {
				required: true,
				digits:true,
			},
			amount : {
				required: true,
				digits:true,
			}
		},
		messages: {
			gId :{
				required: "商品ID不能为空",
				digits:"请选择商品",
				minlength:"请选择商品"
			},
			gName : {
			    required: "请选择入库商品",
			},
			chests : {
				required: "请输入多少箱",
				digits:"请输入整数"
			},
			boxes : {
				required: "请输入多少盒",
				digits:"请输入整数"
			},
			amount : {
				required: "请输入多少盒",
				digits:"请输入整数"
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
        	if(element.attr('id')=='gName') {
        		element.parent('div').parent('div').append(error);
        	} else {
        		element.parent('div').append(error);  
        	}
        },  
        
        submitHandler : function(form) {  
        	enterGoodsAddUpdate();
        }  
	});
	
	$('#enterGoods input').keypress(function(e) {  
        if (e.which == 13) {  
            if ($('#enterGoods').validate().form()) {  
            	enterGoodsAddUpdate();
            }  
            return false;  
        }  
    });  
});


function enterGoodsAddUpdate(){
	$("#enterGoods").validate();
	console.log("click....");
	$.ajax({
		cache: true,
		type:"POST",
		url :contextPath + '/enter/save',
		data: $("#enterGoods").serialize(),
		async:true,
		dataType: 'json',
		success: function(result) {
			console.log(result);
			if(result.value){
				$.messager.confirm('提示', '添加成功，是否刷新页面查看', function(r){
					console.log('ok');
					window.location.href=contextPath + '/enter/list';
				});
			}else{
				$.messager.alert("提示", "添加失败，请确保产品名称是点击选择的");
			}
			
		},
		error:function(result) {
			$.messager.alert("提示", "添加失败");
		}
	});
}

//实现翻页提交
function formPaginationSumbmit(currentPage, type) {
	$('#currentPage').val(currentPage);
	document.formPagination.submit();
}




