
//实现翻页提交
function formPaginationSumbmit(currentPage, type) {
	$('#currentPage').val(currentPage);
	document.formPagination.submit();
}

//监听SELECT 修改提交
$(function(){
	var selected = document.getElementById('isDisable1');
	selected.onchange = function(){ 
		document.getElementById('supplierSearchId').submit();
	}
});

