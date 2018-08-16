<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-订单列表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>

    <%@ include file="../include/sider.jsp" %>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                出库单查询
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box no-border">
                <div class="box-body">
                    <form class="form-inline">
                        <input type="text" name="orderId" placeholder="订单号" value="${param.orderId}" class="form-control">
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
                            <th>订单号</th>
                            <th>配件编码</th>
                            <th>配件名称</th>
                            <th>总数量</th>
                            <th>出库数量</th>
                            <th>余量</th>
                            <th>取件员</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="partsStream">
                            <tr>
                                <td>${partsStream.orderId}</td>
                                <td>${partsStream.parts.partsNo}</td>
                                <td>${partsStream.parts.partsName}</td>
                                <td>${partsStream.parts.inventory + partsStream.num}</td>
                                <td>${partsStream.num}</td>
                                <td>${partsStream.parts.inventory}</td>
                                <td>${partsStream.employee.employeeName}</td>
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
            href:"/parts/out?p={{number}}&orderId=${param.orderId}"
        });

    })
</script>
</body>
</html>
