

//监听SELECT 修改提交
$(function(){
	var selected = document.getElementById('isDisable1');
	selected.onchange = function(){ 
		document.getElementById('supplierSearchId').submit();
	}
});

