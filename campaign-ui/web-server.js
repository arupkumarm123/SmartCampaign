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
  '1': {
    "id": "1",
    "title": "Arup first campaign is it reloading",
    "description": "This is for launching a new product category on flipkart",
    "targeting": [
      {
        "amount": "1",
        "amountUnits": "packet",
        "ingredientName": "Chips Ahoy"
      }
    ],
    "instructions": "1.Please generate a email and sms for the targeting"
  },
  '2': {
    id: 2,
    'title': 'Arup Second campaign',
    'description': 'Description 2',
    'instructions': 'Instruction 2',
    targeting: [
      {amount: 13, amountUnits: 'pounds', ingredientName: 'Awesomeness'}
    ]
  }
};
var next_id = 3;

app.get('/recipes', function(req, res) {
  var recipes = [];

  for (var key in recipes_map) {
    recipes.push(recipes_map[key]);
  }

  // Simulate delay in server
  setTimeout(function() {
    res.send(recipes);
  }, 500);
});

app.get('/recipes/:id', function(req, res) {
  console.log('Requesting recipe with id', req.params.id);
  res.send(recipes_map[req.params.id]);
});

app.post('/recipes', function(req, res) {
  var recipe = {};
  recipe.id = next_id++;
  recipe.title = req.body.title;
  recipe.description = req.body.description;
  recipe.ingredients = req.body.ingredients;
  recipe.instructions = req.body.instructions;

  recipes_map[recipe.id] = recipe;

  res.send(recipe);
});

app.post('/recipes/:id', function(req, res) {
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
