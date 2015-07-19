'use strict';

var app = angular.module('campaign',
                         ['campaign.directives',
                          'campaign.services']);

//app.config(['$routeProvider', function($routeProvider) {
//    $routeProvider.
//      when('/', {
//        controller: 'ListController',
//        resolve: {
//          campaigns: ['MultiCampaigns', function(MultiCampaigns) {
//            return MultiCampaigns();
//          }]
//        },
//        templateUrl:'/views/list.html'
//      });
//}]);

//app.controller('ListController', ['$scope', 'campaigns',
//    function($scope, campaigns) {
//      $scope.campaigns = campaigns;
//      $scope.gridOptions = {
//        data: campaigns
//      };
//    }]);
