document.onkeydown = function() {
    if (event.keyCode == 13) {
        var scode = $('#checkCode').textbox('getValue');
        var checkeds = $('#'+ scode +'_checked').val();
        if(checkeds == ""){
            checkeds = 0;
        }
        
        checkeds = parseInt(checkeds) + 1;
        $('#'+ scode +'_checked').val(checkeds);
        $('#checkCode').textbox('setValue','');
        
        var _flag = $('#'+ scode +'_tr td')[1].innerHTML;
        var flag = parseInt(_flag);
        var checkBoxId = scode + '_checkbox';
        
        if(flag==checkeds){
            $('#'+checkBoxId).prop('checked',true);
        } else {
            $('#'+checkBoxId).prop('checked',false);
        }
    }
}

$(document).ready(function(){
    $("#checkTable input[type='text']").change(function() {
        var flagId = this.id;
        var checkBoxId = flagId.replace('checked','checkbox');
        
        if($('#'+checkBoxId).is(':checked')) {
            $('#'+checkBoxId).prop('checked',false);
            return;
        }
        
        var _flag = $('#'+ flagId).val();
        var flag = parseInt(_flag);
        
        var trId = flagId.replace('checked','tr');
        var _flag1 = $('#'+ trId +' td')[1].innerHTML;
        var flag1 = parseInt(_flag1);
        
        
        if(flag==flag1){
            $('#'+checkBoxId).prop('checked',true);
        } else {
            $('#'+checkBoxId).prop('checked',false);
        }
    });
});


var scode;
function editChecheds(scode1){
    scode = scode1;
    $('#editChecheds').dialog('open').dialog('setTitle','编辑');
    var checkeds = $('#'+ scode +'_checked').html();
    $('#tmpCheckeds').numberbox('setValue', checkeds);
    
}

function editSubmit(){
    var checkeds = $('#tmpCheckeds').numberbox('getValue');
    $('#'+ scode +'_checked').html(checkeds);
    $('#editChecheds').dialog('close');
}

var checkeds = new Array();

function confirmChecked(oId){
    var _size = $("input[name = okchecked]:checkbox").size();
    var __size = 0;
    $("input[name = okchecked]:checkbox").each(function(){
        if ($(this).is(":checked")) {
            __size++;
        }
    });
    //console.log(_size + "-----" + __size);
    if(_size == __size) {
        // 异步执行删除
        $.ajax({
            cache: true,
            type:"POST",
            url :contextPath + '/order/updateStatus',
            data: {status:2,oId:oId},
            async:true,
            dataType: 'json',
            success: function(result) {
                console.log(result);
                if(result.value){
                    $.messager.confirm('提示', '验货成功，是否刷新页面查看', function(r){
                        window.location.reload();
                    });
                   //$("#"+invId+"_tr").remove()
                }else{
                    $.messager.alert("提示", "验货失败");
                }
                
            },
            error:function(result) {
                $.messager.alert("提示", "验货失败");
            }
        });
    } else {
        $.messager.alert("提示", "数量合不上，请仔细检查");
    }
}