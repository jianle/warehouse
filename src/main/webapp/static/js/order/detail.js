
$(document).ready(function(){
	var sNameSuggest = $("#oId").bsSuggest({
        url: contextPath + '/order/detail/getAlloId?oId=',
        idField: "oId",
        keyField: "oId",
        listAlign: "auto"
    }).on('onDataRequestSuccess', function (e, result) {
    	//console.log('onDataRequestSuccess: ', result);
    }).on('onSetSelectValue', function (e, keyword) {
    	//console.log('onSetSelectValue: ', keyword);
        console.log(keyword.id);
        $('.modal-body #oId').val(keyword.id);
        document.getElementById("formoId").submit();
    }).on('onUnsetSelectValue', function (e) {
    	//console.log("onUnsetSelectValue");
    });
});


