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
    'campaign.directives',
    'ngWebsocket'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/list.html',
        controller: 'ListController',
        resolve: {
          campaigns: ['CampaignService' , function(CampaignService) {
            return CampaignService.getCampaigns();
          }]
        }
      });
      //.when('/about', {
      //  templateUrl: 'views/about.html',
      //  co:1ntroller: 'AboutCtrl'
      //})
      //.otherwise({
      //  redirectTo: '/'
      //});
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
      CampaignService.updateCampaign(evt.targetScope.row.entity.id, evt.targetScope.row.entity).then(function(data){
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
