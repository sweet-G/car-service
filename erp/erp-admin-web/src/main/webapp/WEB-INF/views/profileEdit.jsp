
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 个人设置 - 修改</title>
    <%@include file="include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="include/header.jsp"%>

    <!-- =============================================== -->

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                个人设置
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改资料</h3>
                    <div class="box-tools">
                        <a href="/home" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="" method="post" id="editForm">
                        <div class="form-group">
                            <label>姓名</label>
                            <input disabled type="text" name="employeeName" value="${employee.employeeName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>手机号</label>
                            <input type="text" name="employeeTel" value="${employee.employeeTel}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>新密码(原密码为手机号)</label>
                            <input type="password" name="password" id="password" class="form-control">
                         </div>
                        <div class="form-group">
                            <label>确认新密码</label>
                            <input type="password" name="newPassword" id="newPassword" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>角色</label>
                            <c:forEach items="${roleList}" var="role">
                            <input disabled type="text" name="role" value="${role.roleName}" class="form-control">
                            </c:forEach>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="editBtn">保存</button>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<%@include file="include/js.jsp"%>
<script>
    $(function () {

        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $("#editBtn").click(function () {
            $("#editForm").submit();
        });

        $("#newPassword").blur(function () {
            var password = $("#password").val();
            var newPassword = $(this).val();
            if(password != newPassword){
                layer.msg("两次密码不一致，请重新输入");
            }
        });

    })
</script>
</body>
</html>
