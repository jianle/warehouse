$(document).ready(function() {
	var sNameSuggest = $("#oId").bsSuggest({
		url : contextPath + '/order/detail/getAlloId?oId=',
		idField : "oId",
		keyField : "oId",
		listAlign : "auto"
	}).on('onDataRequestSuccess', function(e, result) {
		// console.log('onDataRequestSuccess: ', result);
	}).on('onSetSelectValue', function(e, keyword) {
		// console.log('onSetSelectValue: ', keyword);
		console.log(keyword.id);
		$('.modal-body #oId').val(keyword.id);
		document.getElementById("formoId").submit();
	}).on('onUnsetSelectValue', function(e) {
		// console.log("onUnsetSelectValue");
	});
});

var editIndex = undefined;
var currentIndex = 0;
function endEditing() {

	if (editIndex == undefined) {
		return true
	}
	if ($('#dg').datagrid('validateRow', editIndex)) {

		console.log('editIndex:' + editIndex);
		var ed = $('#dg').datagrid('getEditor', {
			index : editIndex,
			field : 'gId'
		});
		var gName = $(ed.target).combobox('getText');
		$('#dg').datagrid('getRows')[editIndex]['gName'] = gName;

		var ed2 = $('#dg').datagrid('getEditor', {
			index : editIndex,
			field : 'sId'
		});
		var sName = $(ed2.target).combobox('getText');
		$('#dg').datagrid('getRows')[editIndex]['sName'] = sName;

		$('#dg').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function synchCombobox(sId) {
	var comboboxData;
	var url = contextPath + '/goods/getname?sId=' + sId;
	var ed = $('#dg').datagrid('getEditor', {
		index : editIndex,
		field : 'gId'
	});
	$.ajax({
		url : url,
		dataType : 'json',
		type : 'POST',
		success : function(data) {
			comboboxData = data;
			$(ed.target).combobox('loadData', comboboxData);
		}
	});
}
function onClickRow(index) {
	currentIndex = index;
	if (editIndex != index) {
		if (endEditing()) {
			$('#dg').datagrid('selectRow', index).datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#dg').datagrid('selectRow', editIndex);
		}
	}
}
function append() {
	if (endEditing()) {
		$('#dg').datagrid('appendRow', {
			status : '修改'
		});
		editIndex = $('#dg').datagrid('getRows').length - 1;
		var row = $('#dg').datagrid('getRows')[currentIndex];
		currentIndex = editIndex;
		if (row.status == '修改' && currentIndex>=1 &&row.gId) {
			console.log(row);
			row.oId = $('#oId').val();
			$.ajax({
				cache : true,
				type : "POST",
				url : contextPath + '/order/detail/save',
				data : row,
				async : true,
				dataType : 'json',
				success : function(result) {
					if (result) {
						$.messager.show({
							timeout:350,
							title : '提示信息',
							msg : '操作成功'
						});
					} else {
						$.messager.show({
							title : '提示信息',
							msg : '操作失败'
						});
					}
				},
				error : function(result) {
					$.messager.show({
						title : '提示信息',
						msg : '操作失败'
					});
				}
			});
		}
		$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',
				editIndex);
	}
}
function removeit() {
	if (editIndex == undefined) {
		return
	}
	var row = $('#dg').datagrid('getSelected');
	row.oId = $('#oId').val();
	console.log(JSON.stringify(row));
	if(row.odId){
		$.ajax({
			cache : true,
			type : "POST",
			url : contextPath + '/order/detail/delete',
			data : {
				odId : row.odId
			},
			async : true,
			dataType : 'json',
			success : function(result) {
				if (result) {
					$.messager.show({
						timeout:350,
						title : '提示信息',
						msg : '操作成功'
					});
				} else {
					$.messager.show({
						title : '提示信息',
						msg : '操作失败'
					});
				}
			},
			error : function(result) {
				$.messager.show({
					title : '提示信息',
					msg : '操作失败'
				});
			}
		});
	}
	
	$('#dg').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept() {
	if (endEditing()) {
		var row = $('#dg').datagrid('getSelected');
		row.oId = $('#oId').val();
		row.status='保存';
		console.log(JSON.stringify(row));
		$.ajax({
			cache : true,
			type : "POST",
			url : contextPath + '/order/detail/save',
			data : row,
			async : true,
			dataType : 'json',
			success : function(result) {
				if (result) {
					$.messager.show({
						timeout:350,
						title : '提示信息',
						msg : '操作成功'
					});
				} else {
					$.messager.show({
						title : '提示信息',
						msg : '操作失败'
					});
				}
			},
			error : function(result) {
				$.messager.show({
					title : '提示信息',
					msg : '操作失败'
				});
			}
		});
		$('#dg').datagrid('acceptChanges');
	}
}
function reject() {
	$('#dg').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges() {
	var rows = $('#dg').datagrid('getChanges');
	alert(rows.length + ' rows are changed!');
}