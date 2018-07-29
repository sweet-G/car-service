<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 修改角色</title>
    <%@include file="../../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/treegrid/css/jquery.treegrid.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="/static/plugins/iCheck/square/blue.css">
</head>
<body class="hold-transition skin-purple sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">

    <%@include file="../../include/header.jsp"%>

    <!-- =============================================== -->

    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_roles"/>
    </jsp:include>

    <!-- =============================================== -->

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                角色管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改角色</h3>
                    <div class="box-tools">
                        <a href="/manage/role" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form method="post" id="editForm">
                        <div class="form-group">
                            <label>角色名称</label>
                            <input type="text" name="roleName" value="${role.roleName}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>角色代号</label>
                            <input type="text" name="roleCode" value="${role.roleCode}" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限配置</label>
                            <table class="table tree">

                                <tbody>
                                <c:forEach items="${permissionBooleanMap}" var="entry">
                                    <tr class="treegrid-${entry.key.id}
                                <c:if test="${entry.key.pid != 0}">
                                    treegrid-parent-${entry.key.pid}
                                </c:if>">
                                        <td>
                                            <input type="checkbox" name="permissionId"  value="${entry.key.id}" ${entry.value ? 'checked' : ''}>
                                        </td>
                                        <td>${entry.key.permissionName}</td>
                                        <td>${entry.key.permissionCode}</td>
                                        <td>${entry.key.url}</td>
                                        <td>${entry.key.permissionType}</td>
                                        <td>
                                            <a class="btn btn-primary btn-xs" href="/manage/permission/${permission.id}/edit" title="编辑"><i class="fa fa-pencil"></i></a>
                                            <a class="btn btn-danger btn-xs delLink" rel="${permission.id}" href="javascript:;" title="删除"><i class="fa fa-trash"></i></a>
                                        </td>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>

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

<%@include file="../../include/js.jsp"%>
<script src="/static/plugins/treegrid/js/jquery.treegrid.min.js"></script>
<script src="/static/plugins/treegrid/js/jquery.treegrid.bootstrap3.js"></script>
<!-- iCheck -->
<script src="/static/plugins/iCheck/icheck.min.js"></script>
<script>
    $(function () {

        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
        $('.tree').treegrid();
        $('input[type=checkbox]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });

        var message = "${message}";
        if(message){
            layer.msg(message);
        }

        $(".delLink").click(function() {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除改权限么？",function() {
                // window.location.href = "/manage/permission/" + id + "/del";
                $.get("/manage/permission/" + id + "/del").done(function(res){
                    if(res.state == "success") {
                        layer.msg("删除成功", {icon:2, time:2000},function () {
                            history.go(0);
                        });
                    } else {
                        layer.msg(res.message, {icon:2, time:2000});
                    }
                }).error(function() {
                    layer.msg("系统异常")
                })
            })
        })

    })
</script>
</body>
</html>
