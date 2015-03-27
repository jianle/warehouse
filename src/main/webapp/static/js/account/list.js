
//添加删除搜索条件
function addRow() {
    console.log('addRow');
    $row = $("#flagtbody tr:eq(0)");
    rowContent = '<tr>'
    	        + $row.html()
    	        +'</tr>' ;
    
    console.log(rowContent);
    
    $('#flagtbody').append(rowContent);
}

function delRow() {
//移除对应
	console.log('delRow');

	if($("#flagtbody").find("tr").length >1){
		$("#flagtbody tr:last").remove();
	}
	 	
}