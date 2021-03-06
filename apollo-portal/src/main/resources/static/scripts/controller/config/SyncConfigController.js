sync_item_module.controller("SyncItemController",
                              ['$scope', '$location', '$window', 'toastr', 'AppService', 'AppUtil', 'ConfigService',
                               function ($scope, $location, $window, toastr, AppService, AppUtil, ConfigService) {

                                   var params = AppUtil.parseParams($location.$$url);
                                   $scope.pageContext = {
                                       appId: params.appid,
                                       env: params.env,
                                       clusterName: params.clusterName,
                                       namespaceName: params.namespaceName
                                   };
                                   

                                   
                                   ////// load items //////
                                   ConfigService.find_items($scope.pageContext.appId, $scope.pageContext.env,
                                                            $scope.pageContext.clusterName, $scope.pageContext.namespaceName).then(function (result) {

                                       $scope.sourceItems = [];
                                       result.forEach(function (item) {
                                           if (item.key){
                                               item.checked = false;
                                               $scope.sourceItems.push(item);
                                           }
                                       })
                                       
                                   }, function (result) {
                                       toastr.error(AppUtil.errorMsg(result), "加载配置出错");
                                   });

                                   var itemAllSelected = false;
                                   $scope.toggleItemsCheckedStatus = function () {
                                       itemAllSelected = !itemAllSelected;
                                       $scope.sourceItems.forEach(function (item) {
                                           item.checked = itemAllSelected;
                                       })       
                                   };

                                   $scope.diff = function () {
                                       $scope.hasDiff = false;
                                       ConfigService.diff($scope.pageContext.namespaceName, parseSyncSourceData()).then(function (result) {

                                           $scope.diffs = result;
                                           result.forEach(function (diff) {
                                               if (!$scope.hasDiff) {
                                                   $scope.hasDiff = diff.diffs.createItems.length + diff.diffs.updateItems.length > 0;
                                               }
                                           });
                                           $scope.syncItemNextStep(1);
                                       }, function (result) {
                                           toastr.error(AppUtil.errorMsg(result));
                                       });
                                   };
                                   
                                   $scope.syncItems = function () {
                                    ConfigService.sync_items($scope.pageContext.appId, $scope.pageContext.namespaceName, parseSyncSourceData()).then(function (result) {
                                        $scope.syncItemStep += 1;
                                        $scope.syncSuccess = true;
                                    }, function (result) {
                                        $scope.syncSuccess = false;
                                        toastr.error(AppUtil.errorMsg(result));
                                    });    
                                   };

                                   var selectedClusters = [];
                                   $scope.collectSelectedClusters = function (data) {
                                       selectedClusters = data;
                                   };
                                   
                                   function parseSyncSourceData() {
                                       var sourceData = {
                                           syncToNamespaces: [],
                                           syncItems: []
                                       };
                                       var namespaceName = $scope.pageContext.namespaceName;
                                       selectedClusters.forEach(function (cluster) {
                                           if (cluster.checked){
                                               cluster.clusterName = cluster.name;
                                               cluster.namespaceName = namespaceName;
                                               sourceData.syncToNamespaces.push(cluster);
                                           }
                                       });
                                       $scope.sourceItems.forEach(function (item) {
                                           if (item.checked) {
                                               sourceData.syncItems.push(item);
                                           }
                                       });
                                       return sourceData;
                                   }

                                   ////// flow control ///////

                                   $scope.syncItemStep = 1;
                                   $scope.syncItemNextStep = function (offset) {
                                       $scope.syncItemStep += offset;
                                   };

                                   $scope.backToAppHomePage = function () {
                                       $window.location.href = '/config.html?#appid=' + $scope.pageContext.appId;
                                   };

                                   $scope.switchSelect = function (o) {
                                       o.checked = !o.checked;
                                   }
                               }]);

