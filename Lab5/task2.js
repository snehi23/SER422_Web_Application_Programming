var express = require('express'),
    bodyParser = require('body-parser'),
    ejs = require('ejs'),
    url = require('url'),
    cookieParser = require('cookie-parser'),
    session = require('express-session'),
    mongoose = require('mongoose'),
    mongodb = require('./model/mongodb'),
    developer = require('./model/developers');

var app = express();
var router = express.Router();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cookieParser());
app.use(session({secret: '1234567890QWERTY'}));
app.set('view engine','ejs');
app.set('views', '.');

var allLang = ["C","JAVA","PYTHON","JAVA SCRIPT","OPA"];
var allDays = ["MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"];
var allHairColors = ["brunette","blonde","black","other"];

app.get('/', function (req, res) {

  var uname = req.cookies.uname;

  if(uname) {
    var records = [];
    showPreferences(res, uname);
  }
  else
    res.render('login');

});

app.get('/login', function (req, res) {
  res.render('login');
});

app.post('/login', function (req, res) {

  var fname = req.body['fname'];
  var lname = req.body['lname'];
  var records = [];

  res.cookie('uname', fname);
  showPreferences(res, fname);

});

app.get('/multiform1', function (req, res) {
  var fname = req.session.fname;

  res.render('multiform1', {fname: fname});
});

app.post('/multiform1', function (req, res) {
  var fname = req.session.fname;

  res.render('multiform1', {fname: fname});
});

app.post('/multiform2', function(req, res) {
  if(req.body['fname'])
    req.session.fname = req.body['fname'];
  var lname = req.session.lname;

  res.render('multiform2', {lname: lname});

});

app.post('/multiform3', function(req, res) {
  if(req.body['lname'])
    req.session.lname = req.body['lname'];
  var selectedLang = req.session.selectedLang;

  res.render('multiform3', {allLang: allLang, selectedLang: selectedLang});
});

app.post('/multiform4', function(req, res) {
  if(req.body['progLanguages'])
    req.session.selectedLang = req.body['progLanguages'];
  var selectedDays= req.session.selectedDays;

  res.render('multiform4', {allDays: allDays, selectedDays: selectedDays});
});

app.post('/multiform5', function(req, res) {
  if(req.body['daysOfWeek'])
    req.session.selectedDays = req.body['daysOfWeek'];
  var selectedHairColors= req.session.selectedHairColors;

  res.render('multiform5', {allHairColors: allHairColors, selectedHairColors: selectedHairColors});
});

app.post('/multiform6', function(req, res) {
  if(req.body['hairColor'])
    req.session.selectedHairColors = req.body['hairColor'];

  // collect session values
  var fname = req.session.fname;
  var lname = req.session.lname;
  var selectedLang = req.session.selectedLang;
  var selectedDays= req.session.selectedDays;
  var selectedHairColors= req.session.selectedHairColors;

  res.render('multiform6', {fname: fname ,lname: lname, selectedLang: selectedLang, selectedDays: selectedDays, selectedHairColors: selectedHairColors});
});

app.post('/remove', function (req, res) {

  req.session.destroy();

  var uname = req.cookies.uname;
  var records = [];

  showPreferences(res, uname);
});

app.post('/post_coder', function (req, res) {
  storeRecord(req, res);
});

app.get('/search', function (req, res) {
  res.render('search');
});

app.get('/coders', function (req, res) {
  displayRecords(req, res);
});

router.get('/firstname/:name', function(req, res) {
  filterRecordsByFirstName(res, req.params.name);
});

router.get('/lastname/:name', function(req, res) {
  filterRecordsByLastName(res, req.params.name);
});

app.use('/get_coder', router);

// ERROR HANDLER CODE
app.post('/', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.post('/login', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/multiform1', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/multiform2', function (req, res, next) {
  err.status = 405;
  next(err);
});
app.get('/multiform3', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/multiform4', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/multiform5', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/multiform6', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/remove', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.get('/post_coder', function (req, res, next) {
  err.status = 405;
  next(err);
});

app.post('/coders', function (req, res, next) {
  err.status = 405;
  next(err);
});

router.post('/firstname/:name', function(req, res, next) {
  err.status = 405;
  next(err);
});

router.post('/lastname/:name', function(req, res, next) {
  err.status = 405;
  next(err);
});

app.get('*', function(req, res, next) {
  err.status = 404;
  next(err);
});

app.post('*', function(req, res, next) {
  err.status = 404;
  next(err);
});

router.get('*', function(req, res, next) {
  err.status = 405;
  next(err);
});

router.post('*', function(req, res, next) {
  err.status = 405;
  next(err);
});

app.use(function(err, req, res, next) {

  var errorCode = err.status || 500;
  var errorMessage = '';

  switch(errorCode) {
    case 400:
      errorMessage = 'This is Bad Request';
      break;
    case 404:
      errorMessage = 'Resource Not Found';
      break;
    case 405:
      errorMessage = 'Method not Allowed';
      break;
    case 500:
      errorMessage = 'Internal Server Error';
  }

  res.status(errorCode);

     res.render('error', {
         code : errorCode,
         message: errorMessage
     });
});

app.listen(8081);

function storeRecord(req, res) {

  // collect session values
  var fname = req.session.fname;
  var lname = req.session.lname;
  var progLanguages = req.session.selectedLang;
  var daysOfWeek= req.session.selectedDays;
  var hairColor= req.session.selectedHairColors;

  var query = {fname: fname, lname: lname};

  var newRecord = {
    fname: fname,
    lname: lname,
    progLanguages: progLanguages,
    daysOfWeek: daysOfWeek,
    hairColor: hairColor
  };

  mongoose.model('Developer').findOneAndUpdate(query, newRecord, {upsert: true},function(err, record) {
    if(err)
      next(err);
  });

  req.session.destroy();

  var uname = req.cookies.uname;
  var records = [];

  showPreferences(res, uname);
}

function showPreferences(res, uname) {

    var filterRecordsByPreferences = function(err, allRecords) {

    var myQuery;
    var preferenceList = [];
    var recordCountMap = [];
    var count = 0;

    allRecords.forEach(function(record){
      if(record['fname'] == uname) {
        myQuery = record;
      }
    });

    if(myQuery) {
      allRecords.forEach(function(record) {
        count = 0;
        if(Array.isArray(myQuery['progLanguages'])) {
          myQuery['progLanguages'].forEach(function(lang){
              if(record['progLanguages'].indexOf(lang) > -1)
                count += 1;
          });
        } else {
          if(record['progLanguages'].indexOf(myQuery['progLanguages']) > -1)
            count += 1;
        }

        if(Array.isArray(myQuery['daysOfWeek'])) {
          myQuery['daysOfWeek'].forEach(function(day){
              if(record['daysOfWeek'].indexOf(day) > -1)
                count += 1;
          });
        } else {
          if(record['daysOfWeek'].indexOf(myQuery['daysOfWeek']) > -1)
            count += 1;
        }

        if(myQuery['hairColor'] == record['hairColor'])
          count += 1;

        recordCountMap.push({record: record, count: count})

      });

      recordCountMap.sort(function (rd1, rd2) {
        return rd2.count - rd1.count;
      });

      var records = recordCountMap.map(function (entry) {
        return entry.record;
      });

      res.render('index', {uname: uname, records: records.slice(0,3)});

    } else {
      var records = [];
      res.render('index', {uname: uname, records: records});
    }
  }

  mongoose.model('Developer').find({}, filterRecordsByPreferences);

}

function displayRecords(req, res) {

  var recordList = function(err, allRecords) {
    var query = url.parse(req.url, true).query;
    var color = '';

    var filteredRecords = filterRecords(allRecords, query);

    // Detect user-agent from headers
    var userAgent = req.headers['user-agent'];
    if(userAgent.indexOf("Chrome") > -1)
      color = 'pink';
    else
      color = '';

  res.status(200);
  res.set({'Cache-Control': 'no-cache'});
  res.render('display', {records:filteredRecords, color:color});

  }
  mongoose.model('Developer').find({}, recordList);

}

function filterRecords(allRecords, query) {

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

function filterRecordsByFirstName(res, firstname) {

  var recordList = function(err, filteredRecords) {

      res.status(200);
      res.set({'Cache-Control': 'no-cache'});
      res.render('display', {records:filteredRecords, color:''});

  }
  mongoose.model('Developer').find({fname: new RegExp(firstname)}, recordList);
}

function filterRecordsByLastName(res, lastname) {

  var recordList = function(err, filteredRecords) {

      res.status(200);
      res.set({'Cache-Control': 'no-cache'});
      res.render('display', {records:filteredRecords, color:''});

  }
  mongoose.model('Developer').find({lname: new RegExp(lastname)}, recordList);
}
