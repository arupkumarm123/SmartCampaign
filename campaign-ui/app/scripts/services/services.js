'use strict';

var services = angular.module('campaign.services',
    ['ngResource' ,'ng']);

services.factory('Campaign' , ['$resource' , function($resource) {
    return $resource('http://localhost:port/campaign/:id' , {id:'@id',port: ':5500' , 'update':{ method: 'PUT'}})
}]);

services.factory('CampaignLoader' , ['Campaign','$q' , function(Campaign , $q) {

      return function() {

        var delay = $q.defer();
        Campaign.get(function(campaign) {
          delay.resolve(campaign);
        }, function() {
            delay.reject("unable to fetch campaing");
        });

        return delay.promise;
      }
}]);
services.factory('MultiCampaigns' , [ 'Campaign' , '$q', function(Campaign, $q) {
    return function() {
       var delay = $q.defer();
       Campaign.query(function(campaigns) {
        delay.resolve(campaigns);
      }, function() {
         delay.reject("unable to fetch campaigns");
       });
      return delay.promise;
  };
}]);

services.factory('CampaignService' , ['Campaign' , '$q' , function(Campaign , $q ) {

      function getCampaigns() {
        var delay = $q.defer();
        Campaign.query(function(campaigns ) {

          delay.resolve(campaigns);
        }, function() {
          delay.reject("unable to fethc campaings");
        });
        return delay.promise;
      }

      function updateCampaign(id , campaign) {
        var delay = $q.defer();
        Campaign.save({'id': id} , campaign , function(savedCampaign){
          delay.resolve(savedCampaign);
        } , function() {
           delay.reject("uanable to save campaign");
        });

        return delay.promise;
      }

      return {
        getCampaigns : getCampaigns,
        updateCampaign : updateCampaign
      }

}]);
