<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <%@ include file="../include/sider.jsp" %>
    <!-- =============================================== -->
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                维修部任务领取
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <form class="form-inline" style="margin-bottom: 20px">
                        <input type="text" name="orderId" placeholder="订单号" value="${param.orderId}"class="form-control">
                        <%-- <input type="hidden" name="startTime" id="startTime"value="${param.startTime}">
                        <input type="hidden" name="endTime" id="endTime"value="${param.endTime}">
                        <input type="text" class="form-control" id="time" placeholder="下单日期选择">--%>
                        <button class="btn btn-default">搜索</button>
                    </form>

                    <div class="panel panel-info">
                        <!-- Default panel contents -->
                        <c:forEach items="${page.list}" var="order">
                            <div class="panel-heading">订单号：${order.id} - ${order.car.carType} - ${order.serviceTypeId} <button class="btn btn-success btn-sm pull-right">任务领取</button> </div>
                            <c:forEach items="${partsList}" var="parts">
                                <ul class="list-group">
                                    <li class="list-group-item">${parts.partsName} * ${parts.num}</li>
                                </ul>
                            </c:forEach>
                        </c:forEach>
                        <!-- List group -->
                    </div>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
            </div>
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
        $("#pagination").twbsPagination({
            totalPages : ${page.pages},
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"/fix/list?p={{number}}&orderId=${param.orderId}"
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
