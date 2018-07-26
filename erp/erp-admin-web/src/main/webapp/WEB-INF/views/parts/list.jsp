<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-配件管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                配件管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline pull-left">
                        <input type="text" name="partsName" value="${param.partsName}" placeholder="配件名称" class="form-control">
                        <input type="text" name="partsInventory" value="${param.partsInventory}" placeholder="库存量" class="form-control">
                        <select class="form-control" name="partsType" id="partsType">
                            <option value="">请选择配件类型</option>
                            <c:forEach items="${typeList}" var="type">
                                <option value="${type.id}" ${param.partsType == type.id ? 'selected': ''} >${type.typeName}</option>
                            </c:forEach>

                        </select>
                        <button class="btn btn-default">搜索</button>
                    </form>
                </div>
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>配件编码</th>
                            <th>名称</th>
                            <th>库存</th>
                            <th>进价</th>
                            <th>售价</th>
                            <th>类型</th>
                            <th>产地</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="parts">
                            <tr>
                                <td>${parts.partsNo}</td>
                                <td>${parts.partsName}</td>
                                <td>${parts.inventory}</td>
                                <td>${parts.inPrice}</td>
                                <td>${parts.salePrice}</td>
                                <td>${parts.type.typeName}</td>
                                <td>${parts.address}</td>
                                <td><a href="/parts/${parts.id}/edit">更新</a>
                                    <a href="javascript:;" class="del"partsNo="${parts.partsNo}" ref="${parts.id}">删除</a> </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp" %>


</div>
<!-- ./wrapper -->

<%@ include file="../include/js.jsp" %>
<script>
    $(function(){
        var message = "${message}";
        if(message) {
            layer.msg(message);
        }

        $(".del").click(function(){
            var id = $(this).attr("ref");
            var partsNo = $(this).attr("partsNo");
            layer.msg("delete..."+ id);
            layer.prompt({title:'请输入要删除的配件编号',formType: 3},function(text,index){
                if(text.toUpperCase() == partsNo.toUpperCase()){
                    layer.close(index);
                    window.location.href = "/parts/" + id + "/del";
                }else{
                    layer.close(index);
                    layer.msg("配件编码错误，删除已取消");
                }
            });

        })


        $("#pagination").twbsPagination({
            totalPages : ${page.pages},
            visiblePages : 5,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?p={{number}}&partsType=${param.partsType}&partsInventory=${param.partsInventory}&partsName=" + encodeURIComponent("${param.partsName}")
        });

        var locale = {
            "format": 'YYYY-MM-DD',
            "separator": " - ",//
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };

        var startDate = "";
        var endDate = "";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }


        $('#time').daterangepicker({
            autoUpdateInput:false,
            "locale": locale,
            "opens":"right",
            "timePicker":false
        },function(start,end) {
            $("#startTime").val(start.format('YYYY-MM-DD'));
            $("#endTime").val(end.format('YYYY-MM-DD'));

            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });
    })
</script>
</body>
</html>
