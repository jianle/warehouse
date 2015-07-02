
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}



// 定义默认当前日期
var curDate = new Date();
var url;
function add(){
    $('#dlg').dialog('open').dialog('setTitle','新增订单');
    $('#fm').form('clear');
    $('#documentDate').datebox('setValue', myformatter(curDate));
    $('#saleDate').datebox('setValue', myformatter(curDate));
    $('#userName').textbox('setValue', curuserName);
    url = contextPath + '/order/save';
}

function save(){
    
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        dataType:'json',
        success: function(data){
            var result = eval('('+data+')');
            if (result){
                $('#dlg').dialog('close');       // close the dialog
                $('#dg').datagrid('reload');     // reload the user data
                $.messager.confirm('提示', '操作成功，是否刷新页面查看', function(r){
                    console.log('ok');
                    window.location.href=contextPath + '/order';
                });
            } else {
                $.messager.show({
                    title: '提示信息',
                    msg: '操作失败'
                });
            }
        }
    });
         
}

function edit(Id, name){
    var _formLoadData;
    
    $.getJSON(contextPath+"/order/getOrderInfo", {o_id:Id}, function(data, status){
        _formLoadData = data;
        console.log(data);
        $('#fm').form('load',data);
        $('#customerName').combobox("setValue", name);
        url = contextPath + '/order/update';
        $('#dlg').dialog('open').dialog('setTitle','编辑');
    });
    
}

function destroy(Id){
    $.messager.confirm('提示','确认是否要删除?',function(r){
        if (r){
            $.post(contextPath+'/order/delete',{oId:Id},function(data){
                var result = eval('('+data+')');
                if (result){
                    $.messager.show({    // show error message
                        title: '提示',
                        msg: '删除成功，请刷新查看！'
                    });
                    //$('#dg').datagrid('reload');    // reload the user data
                } else {
                    $.messager.show({    // show error message
                        title: '提示',
                        msg: '删除失败'
                    });
                }
            },'json');
        }
    });
}

var statusType= new Array();
var cssType= new Array();
statusType[0]="新&nbsp;&nbsp;&nbsp增";
cssType[0]="mini-label-primary";

statusType[1]="已配货";
cssType[1]="mini-label-warning";

statusType[2]="已验货";
cssType[2]="mini-label-info";

statusType[3]="已出库";
cssType[3]="mini-label-danger";

statusType[4]="已完成";
cssType[4]="mini-label-success";

function formatterStatus(val,row,index){
    return '<font color="red" class="mini-label '+ cssType[val] +'">' + statusType[val] + '</font>';
}




