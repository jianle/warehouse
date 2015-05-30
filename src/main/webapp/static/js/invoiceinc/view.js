


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
    
    $('#selinvType').change(function(){
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
        url = contextPath + '/invoiceIncome/save';
        $("#invId2").remove();
        $('#invId').numberbox('enable','true');
        $('#number').numberbox('enable','true');
        if(flagTitle=="编辑"){
            $('#modalForm').form('clear');
            $('#invId').numberbox('enable','true');
            $('#number').numberbox('enable','true');
            $('#number').numberbox('setValue', 1);
            $('#valoremTax').numberbox('setValue', 0.0);
            $('#amount').numberbox('setValue', 0.0);
            $('#amountTax').numberbox('setValue', 0.0);
            $('#rateTax').combobox('setValue', 3);
            $('#invType').combobox('setValue', 0);
            $('#remark').textbox('setValue', '');
            $('#invDate').datebox('setValue', curDate);
            $('#rateRebate').numberbox('setValue', 0.0);
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


function edit(Id,proId,conId,invType,isDeleted){
    url = contextPath + '/invoiceIncome/update';
    
    $.getJSON(contextPath+"/invoiceIncome/get", {invId:Id}, function(data, status){
        console.log(data);
        $('#modalForm').form('load',data);
        url = contextPath + '/invoiceIncome/update';
        $('#dlg').dialog('open').dialog('setTitle','编辑');
        var trId = Id+"_tr";
        var tdDate = $("#"+trId+" td");
        flagTitle="编辑";
        $("#modalForm").append('<input type="hidden" id="invId2" value="" name="invId" />')
        //初始化 Modal 
        $('#invId').numberbox('disable','true');
        $('#number').numberbox('disable','true');
        $('#invId2').val(Id);
    });
    
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
                $('#dlg').dialog('close');
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
                    console.log('ok');
                    window.location.reload();
                });
            } else {
                $.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
            $('#dlg').dialog('close');
        }
    }); 
    $('#dlg').dialog('close');
}


function deleteInfo(invId){
    $.messager.confirm('提示', '确认是否删除', function(r){
        if(r) {
            // 异步执行删除
            $.ajax({
                cache: true,
                type:"POST",
                url :contextPath + '/invoiceIncome/delete',
                data: {invId:invId},
                async:true,
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    if(result.value){
                        $.messager.show({
                            title: '提示信息',
                            msg: '删除成功'
                        });
                        $("#"+invId+"_tr").remove()
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

