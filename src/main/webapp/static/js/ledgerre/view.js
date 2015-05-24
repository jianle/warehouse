

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

var url;
var flagTitle="编辑";
var curDate = myformatter(new Date());
$(document).ready(function() {
    //初始化 Modal --by 
    $('#addButton').click(function(){
        //$('#modal').modal('show');
        $('#dlg').dialog('open').dialog('setTitle','新增');
        url = contextPath + '/invoice/save';
        $("#invId2").remove();
        $('#invId').numberbox('enable','true');
        $('#number').numberbox('enable','true');
        if(flagTitle=="编辑"){
            $('#modalForm').form('clear');
            $('#brId1').val($('#brId').val())
            $('#invId').numberbox('enable','true');
            $('#number').numberbox('enable','true');
            $('#number').numberbox('setValue', 1);
            $('#valoremTax').numberbox('setValue', 0.0);
            $('#amount').numberbox('setValue', 0.0);
            $('#amountTax').numberbox('setValue', 0.0);
            $('#rateTax').combobox('setValue', 3);
            $('#remark').textbox('setValue', '');
            $('#invDate').datebox('setValue', curDate);
            $('#incDate').datebox('setValue', curDate);
            $('#verification').numberbox('setValue', 0);
            $('#isDeleted').combobox('setValue', 0);
        }
        flagTitle="新增";
    });
    
    //自动生成金额
    $('#valoremTax').numberbox({
        "onChange":function(n,o){
            var _rateTax = $('#rateTax').numberbox('getValue')/100;
            var _amount = n / (_rateTax + 1);
            var _amountTax = _amount*_rateTax;
            $('#amount').numberbox('setValue', _amount);
            $('#amountTax').numberbox('setValue', _amountTax);
        }
    });
    
    $('#rateTax').combobox({
        "onChange":function(n,o){
            var _rateTax = n/100;
            var _valoremTax = $('#valoremTax').numberbox('getValue');
            var _amount = _valoremTax / (_rateTax + 1);
            var _amountTax = _amount*_rateTax;
            $('#amount').numberbox('setValue', _amount);
            $('#amountTax').numberbox('setValue', _amountTax);
        }
    });
    
});




function edit(Id,conId){
    url = contextPath + '/ledgerReceivable/update';
    $('#dlg').dialog('open').dialog('setTitle','编辑');
    var trId = Id+"_tr";
    flagTitle="编辑";
    var tdDate = $("#"+trId+" td");
    //初始化 Modal 
    $('#lrId').val(Id);
    //$('.content #name').attr('readonly','readonly');
    $('#conId').combobox('setValue', conId);
    $('#amount').numberbox('setValue', tdDate[1].innerHTML);
    $('#payDate').datebox('setValue', tdDate[2].innerHTML);
    $('#verification').numberbox('setValue', tdDate[3].innerHTML);
    $('#remark').textbox('setValue', tdDate[4].innerHTML);
}


function save(){
    $('#modalForm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            console.log(result.indexOf("true"));
            var hasTrue = result.indexOf("true");
            if (hasTrue>=0){
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
                    console.log('ok');
                    window.location.href=contextPath + '/ledgerReceivable';
                });
            } else {
                $.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
        }
    }); 
    $('#dlg').dialog('close');
}


function deleteInfo(lrId){
    $.messager.confirm('提示', '确认是否删除', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/ledgerReceivable/delete',
                data: {lrId:lrId},
                async:true,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    if(result.value){
                        $.messager.show({
                            title: '提示信息',
                            msg: '删除成功'
                        });
                        $("#"+lrId+"_tr").remove()
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


