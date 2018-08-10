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
                    <c:if test="${empty fixOrderList}">
                        <h4>暂无任务</h4>
                    </c:if>


                    <c:forEach items="${fixOrderList}" var="fixOrder">
                        <div class="panel panel-info">
                            <!-- Default panel contents -->
                            <div class="panel-heading">
                                <a href="/fix/${fixOrder.orderId}/detail">订单号：${fixOrder.orderId}</a> - ${fixOrder.carType} - ${fixOrder.orderType}
                                <c:if test="${fixOrder.state == '2'}">
                                    <button rel="${fixOrder.orderId}" class="btn btn-success btn-sm pull-right receiveBtn">任务领取</button>
                                </c:if>

                            </div>
                            <!-- List group -->
                            <ul class="list-group">
                                <c:forEach items="${fixOrder.partsList}" var="parts">
                                    <li class="list-group-item">${parts.partsName} * ${parts.partsNum}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>
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

        $(".receiveBtn").click(function() {
            var orderId = $(this).attr("rel");
            layer.confirm("确定接收该任务么？", function(){
                // 改成ajax方式：error：当前员工已有正在进行的维修任务
                $.get("/fix/" + orderId + "/receive").done(function (res) {
                    if(res.state = "success"){
                        layer.msg("任务领取成功",{time:2000,icon:1},function () {
                            window.location.href = "/fix/" + orderId + "/detail";
                        });
                    }else{
                        layer.msg(res.message);
                    }
                }).error(function () {
                    layer.msg("系统异常")
                })
            })
        })

        $("#pagination").twbsPagination({
            totalPages : ${page.pages},
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"/fix/list?p={{number}}"
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
