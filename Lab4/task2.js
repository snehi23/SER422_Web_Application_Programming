var express = require('express');
var bodyParser = require('body-parser');
var ejs = require('ejs');
var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.set('view engine','ejs');
app.set('views', '.');

var allRecords = [];

app.get('/', function (req, res) {
  res.locals.status = null;
  res.locals.count = null;
  res.render('index2');
});

app.post('/post_coder', function (req, res) {
  storeRecord(req, res);
});

app.get('/coders', function (req, res) {
  res.render('displayRecords', {records:allRecords});
});

app.listen(8081);

function storeRecord(req, res) {

  var status = ['SUCCESS','FAILURE']

  if(!req.body['fname'] == '' && !req.body['lname'] == '')
	   allRecords.push(req.body);

  res.render('index2', {status:status[0],count:allRecords.length+' USER ADDED'});

  res.end();
}
