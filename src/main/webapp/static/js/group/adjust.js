
$(document).ready(function() {
	//初始化accountAdd Modal --by accountAdd
	$('#groupStart').click(function(){	
    	console.log('start');   	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked');
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共启用'+kwdCount+'个单元，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				groupId: list[i].groupId,
        				groupName:list[i].groupName,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/group/start");
        		$("#form3").submit();
    		});
    	}
    	
    	return;	
	});
    $('#groupPause').click(function(){
    	console.log('pause');
    	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked'); 	
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共暂停'+kwdCount+'个单元，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				groupId: list[i].groupId,
        				groupName:list[i].groupName,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/group/pause");
        		$("#form3").submit();
    		});
    	}
   	
    	return;		
		
	});
    $('#groupDel').click(function(){
	
    });
    $('#groupPrice').click(function(){
    	
    	console.log('price1');
    	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked');
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		
    		var data = [];
    		var kwdCount = list.length;
    		
    		for(var i = 0; i < list.length; i++){
    			data[i] = {
    				account:list[i].account,
    				groupId: list[i].groupId,
    				groupName:list[i].groupName,
    				maxPrice: list[i].maxPrice
    			};
    		}
    		
    		var jsonData = $.toJsonString(data);
    		$("#jsonData").val(jsonData);
    		$("#kwdCount").val(kwdCount);
    		$("#form2").attr("action", "/sos/group/price");
    		$("#form2").submit();
    	}
    	
    	return;
    	
       });
   
    function keywordprice(){
    console.log('price2');
   	 var checkNum = $('#dg').datagrid('getChecked').length;
   	 $.post('/sos/group/price',{'checkNum':checkNum},function(data){
   		 console.log(data);
   	 });
    };   
    
});