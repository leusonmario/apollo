/**service module 定义*/
var appService = angular.module('app.service', ['ngResource']);

/**utils*/
var appUtil = angular.module('app.util', ['toastr']);

/** directive */
var directive_module = angular.module('apollo.directive', ['app.service', 'app.util', 'toastr']);

/** page module 定义*/
// 首页
var index_module = angular.module('index', ['toastr', 'app.service', 'app.util', 'angular-loading-bar']);
//项目主页
var application_module = angular.module('application', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);
//创建项目页面
var create_app_module = angular.module('create_app', ['apollo.directive', 'toastr', 'app.service', 'app.util', 'angular-loading-bar']);
//配置同步页面
var sync_item_module = angular.module('sync_item', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);
//namespace
var namespace_module = angular.module('namespace', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);
//server config
var server_config_module = angular.module('server_config', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);
//role
var role_module = angular.module('role', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);
//cluster
var cluster_module = angular.module('cluster', ['app.service', 'apollo.directive', 'app.util', 'toastr', 'angular-loading-bar']);





