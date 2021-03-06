var express = require('express');
var bodyParser = require('body-parser');
var ejs = require('ejs');
var url = require('url');
var app = express();
var router = express.Router();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.set('view engine','ejs');
app.set('views', '.');

var allRecords = [];

app.get('/', function (req, res) {
  res.locals.status = null;
  res.locals.count = null;
  res.set({'Cache-Control': 'no-cache'});
  res.render('index2');
});

app.post('/post_coder', function (req, res) {
  storeRecord(req, res);
});

app.get('/coders', function (req, res) {
  displayRecords(req, res);
});

app.get('/error', function (req, res) {
  res.set({'Cache-Control': 'no-cache'});
  res.status(400);
  res.send("This is a bad request.");
});

router.get('/firstname/:name', function(req, res) {

  var filteredRecords = filterRecordsByFirstName(req.params.name);

  res.status(200);
  res.set({'Cache-Control': 'no-cache'});
  res.render('displayRecords', {records:filteredRecords,color:''});
});

router.get('/lastname/:name', function(req, res) {

  var filteredRecords = filterRecordsByLastName(req.params.name);

  res.status(200);
  res.set({'Cache-Control': 'no-cache'});
  res.render('displayRecords', {records:filteredRecords,color:''});
});

app.use('/get_coder', router);

app.listen(8081);

function storeRecord(req, res) {

  var status = ['SUCCESS!!!','FAILURE!!!']

  if(!req.body['fname'] == '' && !req.body['lname'] == '')
	   allRecords.push(req.body);

  res.render('index2', {status:status[0],count:allRecords.length+' USER ADDED'});

  res.end();
}

function displayRecords(req, res) {

  var query = url.parse(req.url, true).query;
  var color = '';

  var filteredRecords = filterRecords(query);

  // Detect user-agent from headers
  var userAgent = req.headers['user-agent'];
  if(userAgent.indexOf("Chrome") > -1)
    color = 'pink';
  else
    color = '';

  res.status(200);

  res.set({'Cache-Control': 'no-cache'});

  res.render('displayRecords', {records:filteredRecords,color:color});

}

function filterRecords(query) {

	var filteredRecords = [];

	if(Object.keys(query).length == 0 || (query['fname'] == '' && query['lname'] == '' && query['progLanguages'] == '' && query['daysOfWeek'] == '' && query['hairColor'] == '')) {
		return allRecords;
	}
	else {

		for(var i in allRecords) {

			var isExist = false;

			if(query['fname'] == '' || !allRecords[i]['fname'].includes(query['fname']))
				continue;
			if(query['lname'] == '' || !allRecords[i]['lname'].includes(query['lname']))
				continue;

        for(var j in query['progLanguages']) {
  					if(allRecords[i]['progLanguages'].indexOf(query['progLanguages'].filter(Boolean)[j]) > -1) {
  							isExist = true;
  							break;
  					}
  			}

			if(!isExist)
				continue;

			isExist  = false;

      for(var j in query['daysOfWeek']) {
					if(allRecords[i]['daysOfWeek'].indexOf(query['daysOfWeek'].filter(Boolean)[j]) > -1) {
							isExist = true;
							break;
					}
			}

			if(!isExist)
				continue;

			if(query['hairColor'] == '' || !(allRecords[i]['hairColor'] == query['hairColor']))
				continue;

			filteredRecords.push(allRecords[i]);
		}
	}
	return filteredRecords;
}

function filterRecordsByFirstName(firstname) {
  var filteredRecords = [];

  for(var i in allRecords)
    	if(allRecords[i]['fname'].includes(firstname))
        filteredRecords.push(allRecords[i]);

  return filteredRecords;
}

function filterRecordsByLastName(lastname) {
  var filteredRecords = [];

  for(var i in allRecords)
    	if(allRecords[i]['lname'].includes(lastname))
        filteredRecords.push(allRecords[i]);

  return filteredRecords;
}
