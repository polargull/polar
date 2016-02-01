function initPortal () {
    $('#pp').portal({
        border:false,
        fit:true
    });
    add();
}
function add() {
    for(var i=0; i<3; i++) {
        var p = $('<div/>').appendTo('body');
        p.panel({
            title:'Title'+i,
            content:'<div style="padding:5px;">Content'+(i+1)+'</div>',
            height:100,
            closable:true,
            collapsible:true
        });
        $('#pp').portal('add', {
            panel:p,
            columnIndex:i
        });
    }
    $('#pp').portal('resize');
}
function remove() {
    $('#pp').portal('remove',$('#pgrid'));
    $('#pp').portal('resize');
}