
$(document).ready(function() {
	//初始化accountAdd Modal --by accountAdd
	$('#campaignStart').click(function(){	
    	console.log('start');   	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked');
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共启用'+kwdCount+'个计划，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				campaignId: list[i].campaignId,
        				campaignName:list[i].campaignName,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/campaign/start");
        		$("#form3").submit();
    		});
    	}
    	
    	return;	
	});
    $('#campaignPause').click(function(){
    	console.log('pause');
    	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked'); 	
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共暂停'+kwdCount+'个计划，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				campaignId: list[i].campaignId,
        				campaignName:list[i].campaignName,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/campaign/pause");
        		$("#form3").submit();
    		});
    	}
   	
    	return;		
		
	});
    $('#campaignDel').click(function(){
	
    });
});