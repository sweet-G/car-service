<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/static/plugins/font-awesome/css/font-awesome.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="/static/dist/css/AdminLTE.min.css">

    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="/static/dist/css/skins/_all-skins.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        body{
            background-color:#ADD7F0;
        }

        .profile{
            margin-bottom: 15px
        }
    </style>
</head>
<body class="hold-transition">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>个人资料</b></a>
    </div>
    <!-- /.login-logo -->

    <div class="login-box-body" style="font-size: 20px;font-family: 华文新魏;padding-bottom: 0px">
        <div class="profile" >
            <img src="/static/img/th.jpg" style="width: 100px;height: 100px;margin-left: 110px">
        </div>
        <div class="profile">
            <i class="fa fa-user-o"></i> 姓名：<span>${employee.employeeName}</span>
        </div>
        <div class="profile">
            <i class="fa fa-volume-control-phone"></i> 电话：<span>${employee.employeeTel}</span>
        </div>
        <div class="profile">
            <i class="fa fa-meh-o"></i> 角色：<span>
            <c:forEach items="${roleList}" var="role">
            ${role.roleName}、
            </c:forEach> /span>
        </div>
        <div>
            <i class="fa fa-file-text-o"></i> 座右铭：<span>世界很大，努力努力，加油加油，看好你呦</span>
        </div>
        <div class="box-footer">
            <a href="/home"><i class="fa fa-angle-double-left"></i></a>
            <a style="margin-left: 260px" href="/profile/${employee.id}/edit"><i class="fa fa-pencil-square-o"></i></a>
        </div>
    </div>
    <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 2.2.3 -->
<script src="/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>

<script>
    $(function(){
        $("#btn").click(function () {
            $("#loginForm").submit();
        })
    })
</script>

</body>
</html>