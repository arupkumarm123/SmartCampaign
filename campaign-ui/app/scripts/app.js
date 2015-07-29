'use strict'

var app = angular
  .module('campaignApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngGrid',
    //'campaign',
    'campaign.services',
    'ui.router',
    'campaign.directives'
  ])
  .config(function ($stateProvider,$routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/list.html',
        controller: 'ListController',
        resolve: {
          campaigns: ['CampaignService' , function(CampaignService) {
            return CampaignService.getCampaigns();
          }]
        }
      }).when('#/campaign' , {
        templateUrl: 'views/List.html',
        controller: 'ListController',
        resolve: {
          campaigns: ['CampaignService' , function(CampaignService) {
            return CampaignService.getCampaigns();
          }]
        }
      });
    $stateProvider.state('/' ,{
      url:"/dashboard",
      views : {
        "dashboard.view"  : {
          template:'<div class="col-xs-10" ng-view>middle panel</div>'
        }
      }
    }).state('login', {
        url:"/login",
        views : {
          "login.view" : {
           templateUrl: 'views/login.html'
          }
        },
      // You can pair a controller to your template. There *must* be a template to pair with.
      controller: ['$scope', 'utils',
        function (  $scope,  utils) {
          var password = $scope.credentials.password;

          $scope.login = function () {
            console.log(password);
            $state.go('dasboard');
          };
        }]

      })

  });


app.controller('ListController', ['$scope', 'CampaignService','campaigns',
  function($scope, CampaignService ,  campaigns) {
    $scope.campaigns =  campaigns;
    $scope.$on('ngGridEventEndCellEdit', function(evt){
        var payload = {
          'ID': evt.targetScope.row.entity.ID,
          'First_Name': evt.targetScope.row.entity.First_Name,
          'Last_Name': evt.targetScope.row.entity.Last_Name,
        };
      CampaignService.updateCampaign(evt.targetScope.row.entity.id,
        evt.targetScope.row.entity).then(function(data){
          getCampaigns();
        });
    });
    $scope.gridOptions = {
      data:"campaigns",
      columnDefs: [
        {'field':'description', 'displayName':'Description' , 'enableCellEdit' : 'true'},
        {'field':'name', 'displayName':'Name' , 'enableCellEdit':'true'},
        {'field':'startdata' , 'displayName': 'Start Date' }
      ]
    };
  }]);
