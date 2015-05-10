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



function modalFormCommit(){
	addAndupdate();
	//document.getElementById('modalForm').action= contextPath + '/billReceivable/save';
//	$('.ui .form').from('validate form').onSuccess(function(){
//		console.log('342342');
//	});
	//addAndupdate();
}

var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
	$('#closeModal').click(function(){
		$('#modal').modal('hide');
	});
	
	//初始化 Modal --by 
	$('#addButton').click(function(){
		$('#modal').modal('show');
		
		$('#brDate').datepicker({
		    format: "yyyy-mm-dd",
		    language: "zh-CN",
		    orientation: "auto right",
		    autoclose: true
		});
		
		
		if(flagTitle=="编辑"){
			$('#modalTitle').html("新增");
			$('#modal .content #customerCompany').removeAttr("readonly");
			$('#modal .content #customerCompany').val("");
			$('#modal .content #brDate').val("");
			$('#modal .content #amount').val("");
			$('#modal .content #remark').val("");
		}
		$('#brDate').datepicker('setDates',curDate);
		flagTitle="新增";
	});
});


function edit(Id){
	document.getElementById('modalForm').action= contextPath + '/billReceivable/update';
	$('#modal').modal('show');
	$('#brDate').datepicker({
	    format: "yyyy-mm-dd",
	    language: "zh-CN",
	    orientation: "auto right",
	    autoclose: true
	});
	var trId = Id+"_tr";
	flagTitle="编辑";
	//初始化 Modal 
	$('#modalTitle').html("编辑");
	$('.content #brId').val(Id);
	//$('.content #name').attr('readonly','readonly');
	$('.content #customerCompany').val( $("#"+trId+" td")[1].innerHTML );
	$('.content #brDate').val( $("#"+trId+" td")[2].innerHTML );
	$('.content #amount').val( $("#"+trId+" td")[3].innerHTML );
	$('.content #remark').val( $("#"+trId+" td")[4].innerHTML );
}

//$(document).ready(function(){
//	
//	$('.ui.form')
//	  .form({
//		customerCompany: {
//	      identifier  : 'customerCompany',
//	      rules: [
//	        {
//	          type   : 'empty',
//	          prompt : 'Please enter your name'
//	        }
//	      ]
//	    },
//	    brDate: {
//	      identifier  : 'brDate',
//	      rules: [
//	        {
//	          type   : 'empty',
//	          prompt : 'Please select a gender'
//	        }
//	      ]
//	    },
//	    amount: {
//	      identifier : 'amount',
//	      rules: [
//	        {
//	          type   : 'empty',
//	          prompt : 'Please enter a username'
//	        }
//	      ]
//	    }
//	  });
//});


function addAndupdate(){
	console.log(contextPath);
	var title = flagTitle;
	if(title=="新增"){
		document.getElementById('modalForm').action= contextPath + '/billReceivable/save';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/billReceivable/save',
			data: $("#modalForm").serialize(),
			async:true,
			dataType: 'text',
			success: function(result) {
				console.log(result);
				if(result=='true'){
					$.messager.confirm('提示', '添加成功，刷新页面查看', function(r){
						if(r){
							console.log('ok');
							window.location.href=contextPath + '/billReceivable';
						}
					});
				}else{
					$.messager.alert("提示", "添加失败");
				}
				
			},
			error:function(result) {
				$.messager.alert("提示", "添加失败");
			}
		});
		$('#modal').modal('hide'); 
		
	}else if(title=="编辑"){
		document.getElementById('modalForm').action= contextPath + '/billReceivable/update';
		$.ajax({
			cache: true,
			type:"POST",
			url :contextPath + '/billReceivable/update',
			data: $("#modalForm").serialize(),
			async:true,
			dataType: 'text',
			success: function(result) {
				if(result=='true'){
					$.messager.confirm('提示', '修改成功，刷新页面查看', function(r){
						if(r){
							console.log('ok');
							window.location.href=contextPath + '/billReceivable';
						}
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
	//document.getElementById('modalForm').submit();
	$('#modal').modal('hide'); 
}

function deleteInfo(brId){
	$.messager.confirm('提示', '确认是否删除', function(r){
		if(r) {
			// 异步执行删除
			$.ajax({
				cache: true,
				type:"POST",
				url :contextPath + '/billReceivable/delete',
				data: {brId:brId},
				async:true,
				dataType: 'text',
				success: function(result) {
					if(result=='true'){
						$.messager.alert("提示", "删除成功");
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

