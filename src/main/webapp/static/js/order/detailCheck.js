document.onkeydown = function() {
    if (event.keyCode == 13) {
        var scode = $('#checkCode').numberbox('getValue');
        var checkeds = $('#'+ scode +'_checked').html();
        checkeds = parseInt(checkeds) + 1;
        $('#'+ scode +'_checked').html(checkeds);
    }
}
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