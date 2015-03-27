
$(document).ready(function() {
	//初始化accountAdd Modal --by accountAdd
	$('#keywordStart').click(function(){	
    	console.log('start');   	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked');
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共启用'+kwdCount+'个关键词，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				keywordId: list[i].keywordId,
        				keywordName:list[i].keyword,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/keyword/start");
        		$("#form3").submit();
    		});
    	}
    	
    	return;	
	});
    $('#keywordPause').click(function(){
    	console.log('pause');
    	
    	//alert($.toJsonString($('#dg').datagrid('getChecked')));
    	
    	var list = $('#dg').datagrid('getChecked'); 	
    	
    	if(list.length == 0){
    		alert("请选择");
    		return;
    	}else{
    		var kwdCount = list.length;
    		var textValue = '本次一共暂停'+kwdCount+'个关键词，处理中请不要做其他操作，确定立刻执行？确认后请等待窗口关闭后再进行其他操作呦~';    
    	    $('#tips').textbox('setValue',textValue);
    		$('#dlg').dialog('open');
    		$('#keywordButton').click(function(){
    			var data = [];
    			
    			console.log("*****");
    	  		
        		for(var i = 0; i < list.length; i++){
        			data[i] = {
        				account:list[i].account,
        				keywordId: list[i].keywordId,
        				keywordName:list[i].keyword,
        				pause:list[i].pause
        			};
        		}
        		
        		var jsonData = $.toJsonString(data);
        		$("#jsonData1").val(jsonData);
        		$("#kwdCount1").val(kwdCount);
        		$("#form3").attr("action", "/sos/keyword/pause");
        		$("#form3").submit();
    		});
    	}
   	
    	return;		
		
	});
    $('#keywordDel').click(function(){
	
    });
    $('#keywordPrice').click(function(){
    	
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
    				keywordId: list[i].keywordId,
    				keywordName:list[i].keyword,
    				price: list[i].price
    			};
    		}
    		
    		var jsonData = $.toJsonString(data);
    		$("#jsonData").val(jsonData);
    		$("#kwdCount").val(kwdCount);
    		$("#form2").attr("action", "/sos/keyword/price");
    		$("#form2").submit();
    	}
    	
    	return;
    	
       });
   
    function keywordprice(){
    console.log('price2');
   	 var checkNum = $('#dg').datagrid('getChecked').length;
   	 $.post('/sos/keyword/price',{'checkNum':checkNum},function(data){
   		 console.log(data);
   	 });
    };   
    
});