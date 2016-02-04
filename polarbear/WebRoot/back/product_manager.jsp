<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table id="dg_product" class="easyui-datagrid" style="width:700px;height:250px" fit="true" fitColumns="true"
        url="productList.do"
        rownumbers="true" pagination="true">
    <thead>
        <tr>
            <th field="id" width="80">ID</th>
            <th field="name" width="120">名称</th>
            <th field="num" width="80" align="right">数量</th>
            <th field="price" width="80" align="right">原价格</th>
            <th field="desc" width="200">描述</th>
            <th field="state" width="60" align="center">状态</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
    $('#dg_product').datagrid({
    toolbar: [{
        iconCls: 'icon-edit',
        handler: function(){alert('edit')}
    },'-',{
        iconCls: 'icon-help',
        handler: function(){alert('help')}
    }]
});
</script>
