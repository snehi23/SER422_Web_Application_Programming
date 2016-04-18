var express = require('express');
var bodyParser = require('body-parser');
var ejs = require('ejs');
var url = require('url');
var cookieParser = require('cookie-parser');
var session = require('express-session');
var app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cookieParser());
app.use(session({secret: '1234567890QWERTY'}));
app.set('view engine','ejs');
app.set('views', '.');

var allRecords = [];
var allLang = ["C","JAVA","PYTHON","JAVA SCRIPT","OPA"];
var allDays = ["MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"];
var allHairColors = ["brunette","blonde","black","other"];

app.get('/', function (req, res) {

  var uname = req.cookies.uname;

  if(uname) {
    var records = [];
    records = findMyPreferences(uname);
    res.render('index', {uname: uname, records: records});
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
  records = findMyPreferences(fname);
  res.render('index', {uname: fname, records: records});

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

  records = findMyPreferences(uname);
  res.render('index', {uname: uname, records: records});
});

app.post('/post_coder', function (req, res) {
  storeRecord(req, res);
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

  var entryExist = false;

  if(!fname == '' && !lname == '') {

    allRecords.forEach(function(record) {

      if(fname == record['fname'] && lname == record['lname']) {
          entryExist = true;
          record['progLanguages'] = progLanguages;
          record['daysOfWeek'] = daysOfWeek;
          record['hairColor'] = hairColor;
      }

    });

    if(!entryExist)
    allRecords.push({fname: fname ,lname: lname, progLanguages: progLanguages, daysOfWeek: daysOfWeek, hairColor: hairColor});

  }

  req.session.destroy();

  var uname = req.cookies.uname;
  var records = [];

  records = findMyPreferences(uname);
  res.render('index', {uname: uname, records: records});
}

function findMyPreferences(uname) {

  var myQuery;
  var preferenceList = [];
  var recordCountMap = [];
  var count = 0;

  allRecords.forEach(function(record) {
     if(record['fname'] == uname)
        myQuery = record;
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

    return records.slice(0,3);

  } else {
    return [];
  }


}
