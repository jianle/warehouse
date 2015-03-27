$(document).ready(function(){
	$("#fromLogin").validate({
		errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : false,
		rules:{
			username :{
				required: true,
				minlength: 4
			},
			password : {
			    required: true,
			    minlength: 6
			}
		},
		messages: {
			username : {
			    required: "请输入用户名",
			    minlength: $.validator.format("用户名不能小于{0}个字符")
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
        	form.submit();
        }  
	});
	
	$('#fromLogin input').keypress(function(e) {  
        if (e.which == 13) {  
            if ($('#fromLogin').validate().form()) {  
            	$('#fromLogin').submit();  
            }  
            return false;  
        }  
    });  
});

function LoginCommit(){
	$("#fromLogin").validate();
    $('#fromLogin').submit();
}

