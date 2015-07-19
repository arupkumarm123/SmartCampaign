var express = require("express"),
  app     = express(),
  port    = parseInt(process.env.PORT, 10) || 8080;

app.configure(function(){
  app.use(express.methodOverride());
  app.use(express.bodyParser());
  app.use(express.static(__dirname + '/app'));
  app.use(app.router);
});

var recipes_map = {
  "1": {

    "campaign_id": "1",

    "campaign_name": "JbongOnline",

    "campaign_desc": "50% off sale on premium brand",

    "start_date": "12-Dec-2015",

    "end_date": "12-Dec-2015",

    "campaign_description": "Big launch of premium brands on Jabong",

    "country": "India",

    "region": "Bangalore"
  },

  "2": {

    "campaign_id": "2",

    "campaign_name": "JabongKids",

    "campaign_desc": "50% off sale on Jabong kids ware brand",

    "start_date": "12-Dec-2015",

    "end_date": "14-Dec-2015",

    "campaign_description": "Big discount on premium brands kids brand",

    "country": "India",

    "region": "Bangalore"
  },
  "3": {

    "campaign_id": "3",

    "campaign_name": "JabongHouseHold",

    "campaign_desc": "50% off sale on Kitchen  brand",

    "start_date": "12-Sep-2015",

    "end_date": "19-Sep-2015",

    "campaign_description": "All kitchen items ",

    "country": "India",

    "region": "Kolkata"
  }

};

var next_id = 4;

app.get('/campaign', function(req, res) {
  var recipes = [];

  for (var key in recipes_map) {
    recipes.push(recipes_map[key]);
  }

  // Simulate delay in server
  setTimeout(function() {
    res.send(recipes);
  }, 500);
});

app.get('/campaign/:id', function(req, res) {
  console.log('Requesting recipe with id', req.params.id);
  res.send(recipes_map[req.params.id]);
});

app.post('/campaign/', function(req, res) {
  var recipe = {};
  recipe.id = next_id++;
  recipe.title = req.body.title;
  recipe.description = req.body.description;
  recipe.ingredients = req.body.ingredients;
  recipe.instructions = req.body.instructions;

  recipes_map[recipe.id] = recipe;

  res.send(recipe);
});

app.post('/campaign/:id', function(req, res) {
  var recipe = {};
  recipe.id = req.params.id;
  recipe.title = req.body.title;
  recipe.description = req.body.description;
  recipe.ingredients = req.body.ingredients;
  recipe.instructions = req.body.instructions;

  recipes_map[recipe.id] = recipe;

  res.send(recipe);
});

app.listen(port);
console.log('Now serving the app at http://localhost:' + port + '/');
