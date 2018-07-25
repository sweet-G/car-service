<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-库存管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>
    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                配件入库
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <!-- Default box -->
            <div class="box">
                <div class="box-body">
                    <form action="" method="post" id="addForm">
                        <div class="form-group">
                            <label class=" control-label">配件编号:</label>
                            <input type="text" name="partsNo" class="form-control" placeholder="请输入配件编号">
                        </div>
                        <div class="form-group">
                            <label>配件名称:</label>
                            <input type="text" name="partsName" class="form-control" placeholder="请输入配件名称">
                        </div>

                        <div class="form-group">
                            <label>入库数量:</label>
                            <input type="text" name="inventory" class="form-control" placeholder="请输入入库数量">
                        </div>
                        <div class="form-group">
                            <label>进价:</label>
                            <input type="text" name="inPrice" class="form-control" placeholder="请输入进价">
                        </div>
                        <div class="form-group">
                            <label>售价:</label>
                            <input type="text" name="salePrice" class="form-control" placeholder="请输入售价">
                        </div>
                        <div class="form-group">
                            <label>类型:</label>
                            <select name="type" class="form-control">
                                <option>请选择类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}" >${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>产地:</label>
                            <input type="text" name="address" class="form-control" placeholder="请输入产地">
                        </div>

                    </form>
                    <button class="btn btn-primary pull-left" id="saveBtn">保存</button>
                    <button class="btn btn-default pull-left" style="margin-left: 20px" id="cancel">取消</button>
                </div>
                <!-- /.box-body -->

            </div>
            <!-- /.box -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- 底部 -->
    <%@ include file="../include/footer.jsp" %>

</div>

<%@ include file="../include/js.jsp" %>
<script>
    $(function(){
        $("#saveBtn").click(function() {
            $("#addForm").submit();
        })

        $("#cancel").click(function () {
            window.location.href = "/parts";
        })

        $("#addForm").validate({
            errorElement: 'span',
            errorClass: 'text-danger',
            errorPlacement: function(error, element) {
                error.appendTo(element.parent());
            },
            rules:{
                partsNo:{
                    required: true,
                    remote:"/parts/check/partsNo"
                },
                partsName:{
                    required: true
                },
                inventory:{
                    required: true
                },
                inPrice:{
                    required: true
                },
                salePrice:{
                    required: true
                },
                typeId:{
                    required: true
                },
                address:{
                    required: true
                }
            },
            messages:{
                partsNo:{
                    required: "请输入配件编号",
                    remote:"配件编号重复"
                },
                partsName:{
                    required: "请输入配件名称"
                },
                inventory:{
                    required: "请输入入库数量"
                },
                inPrice:{
                    required: "请输入进价"
                },
                salePrice:{
                    required: "请输入售价"
                },
                typeId:{
                    required: "请输入配件类型"
                },
                address:{
                    required: "请输入配件产地"
                }
            },
            submitHandler:function(){
                $.ajax({
                    url: '/parts/new',
                    type:'post',
                    data:'$("#addForm").serialize()',
                    beforeSend:function(){
                        $("#saveBtn").attr("disabled","disabled").text("保存中..");
            },
                    success:function(json){
                        if(json == "true"){
                            window.location.href = "/parts";
                        }
                    },
                    error:function(){
                        layer.alert("系统繁忙");
                    },
                    complete:function(){
                        $("#saveBtn").removeAttr("disabled").text("保存");
                    }
                })
            }
        })

    })
</script>
</body>
</html>

