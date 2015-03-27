
var flagTitle="编辑";

$(document).ready(function() {
	//初始化accountAdd Modal --by accountAdd
	$('#accountAdd').click(function(){
		
		$('.form-group').removeClass('has-error');
		$('.form-group span').remove();
		
		if(flagTitle=="编辑"){
			$('#accountModal .modal-title').html("新增");
			$('.modal-body #account').removeAttr("readonly");
			$('.modal-body #account').val("");
			$('.modal-body #password').val("");
			$('.modal-body #source').val("baidu");
			$('.modal-body #site').val("pc");
			$('.modal-body #bu_id').val(1);
			$('.modal-body #city_id').val(11);
			$('.modal-body #is_valid').val("true");
		}
		flagTitle="新增";
	});
});


function accountEdit(trId,cityId,buId,isValid){
	flagTitle="编辑";
	$('.form-group').removeClass('has-error');
	$('.form-group span').remove();
	//初始化accountAdd Modal --by accountEdit
	$('#accountModal .modal-title').html("编辑");
	$('.modal-body #id').val(trId);
	$('.modal-body #account').attr('readonly','readonly');
	$('.modal-body #account').val( $("#"+trId+" td")[0].innerHTML );
	$('.modal-body #password').val( $("#"+trId+" td")[1].innerHTML );
	$('.modal-body #source').val( $("#"+trId+" td")[2].innerHTML );
	$('.modal-body #site').val( $("#"+trId+" td")[3].innerHTML );
	$('.modal-body #bu_id').val(buId);
	$('.modal-body #city_id').val(cityId);
	$('.modal-body #is_valid').val(isValid?"true":"false");
}


function accountSearch(value){
	//dosearchForm
	document.getElementById('accountSearchId').submit();
}

$(function(){
    $('#clickSearch').bind('click', function(){
    	document.getElementById('accountSearchId').submit();
    });
});


$(document).ready(function(){
	$("#formAccountAddUpdate").validate({
		errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : false,
		rules:{
			account :{
				required: true,
				minlength: 6
			},
			password : {
			    required: true,
			    minlength: 8
			}
		},
		messages: {
			account : {
			    required: "请输入账户",
			    minlength: $.validator.format("账户不能小于{0}个字符")
			},
			password : {
			    required: "请输入密码",
			    minlength: $.validator.format("密码不能小于{0}个字符")
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
        	accountAddUpdate();
        }  
	});
	
	$('#formAccountAddUpdate input').keypress(function(e) {  
        if (e.which == 13) {  
            if ($('#formAccountAddUpdate').validate().form()) {  
            	accountAddUpdate();
            }  
            return false;  
        }  
    });  
});


function accountAddUpdate(){
	$("#formAccountAddUpdate").validate();
	console.log(contextPath);
	var title = $('#accountModal .modal-title').html().trim() ;
	if(title=="新增"){
		document.getElementById('formAccountAddUpdate').action= contextPath + '/account/attribute/save';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/account/attribute/save',
			data: $("#formAccountAddUpdate").serialize(),
			async:true,
			dataType: 'text',
			success: function(result) {
				if(result){
					$.messager.confirm('提示', '添加成功，刷新页面查看', function(r){
						if (r){
							console.log('ok');
							window.location.reload();
						}else{
							console.log('cancel');
						}
					});
				}else{
					alert("error");
				}
				
			},
			error:function(result) {
				alert("error");
			}
		});
		
	}else if(title=="编辑"){
		document.getElementById('formAccountAddUpdate').action= contextPath + '/account/attribute/update';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/account/attribute/update',
			data: $("#formAccountAddUpdate").serialize(),
			async:true,
			dataType: 'text',
			success: function(result) {
				if(result){
					$.messager.confirm('提示', '修改成功，刷新页面查看', function(r){
						if (r){
							console.log('ok');
							window.location.reload();
						}else{
							console.log('cancel');
						}
					});
				}else{
					alert("error");
				}
				
			},
			error:function(result) {
				alert("error");
			}
		});
	}
	//document.getElementById('formAccountAddUpdate').submit();
	$('#accountModal').modal('hide'); 
}

function accountCommit(){
	$("#formAccountAddUpdate").validate();
    $('#formAccountAddUpdate').submit();
}



