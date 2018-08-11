<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <section class="sidebar">
        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>
            <!-- 保养服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-share-alt"></i> <span>汽车服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/order/undone/list"><i class="fa fa-circle-o"></i>订单查询</a></li>
                    <li><a href="/order/new"><i class="fa fa-circle-o"></i>新建订单</a></li>
                </ul>
            </li>
            <!-- 维修服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-wrench"></i> <span>维修服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/fix/list"><i class="fa fa-circle-o"></i>维修任务领取</a></li>
                </ul>
            </li>
            <!-- 质检服务 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-quora"></i> <span>质检服务</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/check/list"><i class="fa fa-circle-o"></i>质检任务领取</a></li>
                </ul>
            </li>
            <!-- 库存管理 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-home"></i> <span>库存管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="/parts"><i class="fa fa-circle-o"></i>配件管理</a></li>
                    <li><a href="/type"><i class="fa fa-circle-o"></i>类型管理</a></li>
                    <li><a href="/parts/new"><i class="fa fa-circle-o"></i>配件入库</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>入库查询</a></li>
                    <li><a href="/parts/out"><i class="fa fa-circle-o"></i>出库查询</a></li>
                </ul>
            </li>
            <!-- 结算管理 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-jpy"></i> <span>结算管理</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>订单结算</a></li>
                    <li><a href="#"><i class="fa fa-circle-o"></i>优惠券</a></li>
                </ul>
            </li>
            <!-- 统计报表 -->
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-pie-chart"></i> <span>统计报表</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#"><i class="fa fa-circle-o"></i>收入统计</a></li>
                </ul>
            </li>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>

<!-- =============================================== -->