var invIds = new Array();
$(document).ready(function(){
    $('#checkVerification').click(
     function(){
    	invIds = new Array()
        var lrId = $('#lrId').val();
        $("input[name = invId]:checkbox").each(function(){
            if ($(this).is(":checked")) {
                var invId = $(this).attr("value");
                invIds.push(invId);
            }
        });
        if(invIds.length == 1){
            var invId = invIds[0];
            var tdData = $('#'+invId+'_tr td');
            $('#invIdVer').val(invId);
            var _amount = tdData[2].innerHTML;
            var _verificationedVer = tdData[6].innerHTML;
            $('#amountVer').numberbox('disable','true');
            $('#verificationedVer').numberbox('disable','true');
            
            $('#amountVer').numberbox('setValue',_amount);
            $('#verificationedVer').numberbox('setValue',_verificationedVer);
            $('#verificationVer').numberbox('setValue',_amount - _verificationedVer);
            $('#dlgVer').dialog('open').dialog('setTitle','提示');
            
        }else if(invIds.length>1) {
        	$('#dlgVerAll').dialog('open').dialog('setTitle','提示');
        }else {
            if(invIds.length==0) $.messager.alert('提示', '请至少勾选一个核销发票');
            else $.messager.alert('提示', '可销金额不够，请重新选择');
        }
        console.log(invIds);
    });
});

//check all
function checkAllMonitor(){
    var checkAll = $('#checkAll')[0].checked;
    if(checkAll) {
        $("input[name = invId]:checkbox").prop("checked", true);
    }else {
        $("input[name = invId]:checkbox").prop("checked", false);
    }
}

//异步执行多个核销记录
function verAllSubmit(){
    $('#dlgVerAll').dialog('close');
    // 异步执行核销
    $.ajax({
        cache: true,
        type:"POST",
        url :contextPath + '/ledgerReceivable/verification/confirm',
        data: {invId:JSON.stringify(''+ invIds),incDate:$('#incDateAll').datebox('getValue')},
        async:true,
        dataType: 'json',
        success: function(result) {
            console.log(result);
            if(result.value){
                $.messager.confirm('提示', '核销成功，是否刷新页面查看', function(r){
                    window.location.reload();
                });
                
            }else{
                $.messager.alert("提示", "核销失败");
            }
            
        },
        error:function(result) {
            $.messager.alert("提示", "核销失败");
        }
    });
}


// 异步执行单个核销记录
function verSubmit(){
    $('#dlgVer').dialog('close');
    $.ajax({
        cache: true,
        type:"POST",
        url :contextPath + '/ledgerReceivable/verification/confirmone',
        data: $("#modalFormVer").serialize(),
        async:true,
        dataType: 'json',
        success: function(result) {
            console.log(result);
            if(result.value){
                $.messager.confirm('提示', '核销成功，是否刷新页面查看', function(r){
                    window.location.reload();
                });
                
            }else{
                $.messager.alert("提示", "核销失败");
            }
            
        },
        error:function(result) {
            $.messager.alert("提示", "核销失败");
        }
    });
}
