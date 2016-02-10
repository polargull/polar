<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout" data-options="fit:true">
    <table id="dg_product" class="easyui-datagrid" style="width:700px;height:250px" fit="true" fitColumns="true" singleSelect="true" toolbar="#tb"
        url="productList.do"
        rownumbers="true" pagination="true">
        <thead>
            <tr>
                <th field="id" width="80">ID</th>
                <th field="name" width="120">名称</th>
                <th field="category" width="120" formatter="categoryformate">类型</th>
                <th field="num" width="80">数量</th>
                <th field="price" width="80">原价格</th>
                <th field="productStyle" width="80" formatter="styleformate">款式</th>
                <th field="desc" width="200">描述</th>
                <th field="state" width="60" align="center" formatter="stateformate">状态</th>
            </tr>
        </thead>
    </table>
</div>
<div id="tb" style="padding:5px;height:auto">    
    <div style="margin-bottom:5px">    
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>    
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>    
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>    
        <a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>    
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>    
    </div>    
    <div>
        促销时间: <input id="saleStart" class="easyui-datebox" style="width:80px">    
        到: <input id="saleEnd" class="easyui-datebox" style="width:80px">    
        款式:
        <input id="pStyle" class="easyui-combobox" style="width:100px" data-options="
                valueField: 'label',
                textField: 'value',
                data: [{
                    label: '全部',
                    value: '全部',
                    selected:true
                },{
                    label: '有',
                    value: '有'
                },{
                    label: '无',
                    value: '无'
                }]" />
        <input class="easyui-textbox" id="pName" data-options="prompt:'商品名称'" style="width:100px"/>
        <a href="#" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
</div>
<script type="text/javascript">
    $('#dg_product').datagrid({
        loadFilter: function(data){
            FastJson.format(data);//重点！！
            return data;
        }
    });
    $.fn.datebox.defaults.formatter = function(date) {
        var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        var d = date.getDate();  
        return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);  
    };
    $.fn.datebox.defaults.parser = function(s) {
        return parseTime (s);
    };
    function stateformate (value,rowData,rowIndex) {
        if (value == 1)
            return '<font style=\'color:green\'>上架</font>';
        if (value == 2)
            return '<font style=\'color:red\'>下架</font>';  
    }
    function styleformate (value,rowData,rowIndex) {
        if (value == null)
            return '单款';
        return '多款';
    }
    function categoryformate (value,rowData,rowIndex) {
        if (value != null) 
            return value.desc;
    }
    function parseTime (s) {
        if (s) {  
            var a = s.split('-');  
            var d = new Date(parseInt(a[0]), parseInt(a[1]) - 1, parseInt(a[2]));  
            return d;  
        } else {  
            return new Date();  
        }
    }
    $('#searchBtn').click(function(event) {
        var saleStart = $('#saleStart').datebox('getValue');
        var saleEnd = $('#saleEnd').datebox('getValue');
        var pStyle = $('#pStyle').combobox('getValue');
        var pName = $('#pName').textbox('getValue');
        try{
            validate(saleStart,saleEnd);
            var param = buildParam (saleStart,saleEnd,pStyle,pName);
            postGrid(param);
        } catch(e){
            alert(e.message);
        }
    });

    function postGrid (param) {        
        $('#dg_product').datagrid('load',{
            param: param
        });
    }
    function buildParam (saleStart,saleEnd,pStyle,pName) {
        var param = '';
        if (saleStart!=''&&saleEnd!='') {
            param+='saleTimeRrang:'+parseTime(saleStart).getTime()/1000+'-'+parseTime(saleEnd).getTime()/1000+';';
        }
        if (pStyle!='') {
            param+='style:'+pStyle+';';
        }
        if (pName!='') {
            param+='name:'+pName+';';
        }
        return  param;
    }
    function validate (saleStart,saleEnd) {
        if (saleStart==''&&saleEnd=='')
            return;
        if (saleStart!=''&&saleEnd=='')
            throw new Error("结束时间不能为空!");
        if (saleStart==''&&saleEnd!='')
            throw new Error("开始时间不能为空!");
        if (parseTime(saleStart).getTime()>=parseTime(saleEnd).getTime())
            throw new Error("时间范围错误!");
    }
</script>