function initTabs () {
    $('#tt').tabs({
        border:false,
        onSelect:function(title){
            updateTab(title);
        }
    })
}
function updateTab (title) {
    if (title == '首页') {
        return;
    }
    var current_tab = $('#tt').tabs('getSelected');    
    $('#tt').tabs('update',{
         tab:current_tab,
         options : {  
              // content : 'Introduction to language',  
              href:nav(title)
          //或者 href : '';  
         }  
    }); 
}
function nav (title) {
  if (title == '用户管理') {
      return 'user_manager.html';
  } else if (title == '商品管理') {
      return 'product_manager.jsp';
  } else if (title == '订单管理') {
    return 'order_manager.html';
  }
}
function addTab(title) {        
    $('#tt').tabs('add',{
        title:title,
        content:'Tab Body',
        closable:true,
        tools:[{
            iconCls:'icon-mini-refresh',
            handler:function(){
              convertTitleToDataGrid(title).datagrid('reload');
            }
        }]
    });
}
function convertTitleToDataGrid (title) {
  if (title == '订单管理') 
    return $('#dg_order');
  if (title == '商品管理')
    return $('#db_product');
  if (title == '用户管理')
    return $('#db_user');
}