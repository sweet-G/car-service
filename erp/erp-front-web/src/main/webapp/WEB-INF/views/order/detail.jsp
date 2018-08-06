<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-订单详情</title>

    <style>
        .td_title {
            font-weight: bold;
        }
    </style>
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper" id="app">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="order"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <div class="box-header with-border">
                <h3 class="box-title">保养维修单</h3>
                <div class="box-tools">
                    <a href="/order/undone/list" class="btn btn-primary btn-sm"><i class="fa fa-arrow-left"></i> 返回列表</a>
                    <button class="btn bg-maroon btn-sm" id="printBtn"><i class="fa fa-print"></i> 打印</button>
                    <c:if test="${order.state == '1'}">
                        <a href="/order/${order.id}/edit" class="btn bg-purple btn-sm"><i class="fa fa-pencil"></i> 修改订单</a>
                        <button class="btn bg-orange btn-sm" id="transBtn"><i class="fa fa-recycle"></i> 订单下发</button>
                        <button class="btn btn-danger btn-sm" id="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                    </c:if>
                </div>
            </div>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">订单信息</h3>
                </div>

                <div class="bo-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">订单号:</td>
                            <td>${order.id}</td>
                            <td class="td_title">订单日期:</td>
                            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"></fmt:formatDate> </td>

                        </tr>
                        <tr>
                            <td class="td_title">订单金额:</td>
                            <td>${order.fee}</td>
                            <td class="td_title">订单状态:</td>
                            <td>${order.stateName}</td>
                        </tr>

                    </table>
                </div>
            </div>


            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户车辆信息</h3>
                </div>
                <div class="box-body">
                    <table class="table">
                        <tr>
                            <td class="td_title">车牌号:</td>
                            <td>${order.car.licenceNo}</td>
                            <td class="td_title">客户姓名:</td>
                            <td>${order.customer.userName}</td>
                            <td class="td_title">身份证号:</td>
                            <td>${order.customer.idCard}</td>
                        </tr>
                        <tr>
                            <td class="td_title">车主电话:</td>
                            <td id="tel">${order.customer.tel}</td>
                            <td class="td_title">车型:</td>
                            <td id="carType">${order.car.carType}</td>
                            <td class="td_title">车辆识别码:</td>
                            <td id="carNo">${order.car.carNo}</td>

                        </tr>
                    </table>

                </div>
            </div>
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">项目选择</h3>
                </div>

                <div class="box-body">
                    <table class="table table-bordered " style="border-width: 2px;" id="infoForm">
                        <thead>
                        <tr>
                            <th>项目代码</th>
                            <th>项目名称</th>
                            <th>工时费用</th>
                        </tr>
                        </thead>
                        <tbody id="addTr">
                        <tr>
                            <td>${serviceType.serviceNo}</td>
                            <td>${serviceType.serviceName}</td>
                            <td>${serviceType.serviceHour * 50}</td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：${serviceType.serviceHour * 50} 元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">选择配件</h3>
                </div>

                <div class="box-body">

                    <table class="table table-bordered " style="border-width: 2px;">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>名称</th>
                            <th>单价</th>
                            <th>数量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${partsList}" var="parts">
                            <tr>
                                <td>${parts.partsNo}</td>
                                <td>${parts.partsName}</td>
                                <td>${parts.salePrice}</td>
                                <td>${parts.num}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4" class="td_title">小计 ：${order.fee - serviceType.serviceHour * 50} 元</td>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

            <div class="box">
            <div class="box-header">
                <h3 class="box-title" style="font-size: 15px;color: #0c0c0c">总计：${order.fee} 元</h3>
            </div>
            </div>
            <h4 class="pull-right visible-print-block" style="margin-right: 150px;">客户签字：</h4>

        </section>
        <!-- /.content -->

    </div>
    <!-- /.content-wrapper -->


    <%@ include file="../include/footer.jsp" %>

</div>
<!-- ./wrapper -->
<%@ include file="../include/js.jsp" %>
<script>
    $(function() {

        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        var orderId = ${order.id};
        //删除客户
        $("#delBtn").click(function(){
            layer.confirm("确定要删除吗?",function (index) {
                layer.close(index);
                window.location.href = "/order/"+ orderId +"/del";
            });
        });

        //打印
        $("#printBtn").click(function(){
            window.print();
        })

        // 订单下发
        $("#transBtn").click(function(){
            layer.confirm("确定要下发订单么？如此将不能修改和删除订单！",function (index) {
                layer.close(index);
                $.get("/order/"+ orderId +"/trans").done(function(res){
                    if(res.state == "success") {
                        layer.msg("订单已生成并下发！等待维修中...",{time: 2000, icon:1},function(){
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message);
                    }
                }).error(function(){
                    layer.msg("系统异常")
                })
            });
        })

    })
</script>
</body>
</html>