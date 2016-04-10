var http = require('http');
var fs = require('fs');
var querystring = require('querystring');
var url = require('url');

var allRecords = [];
var allParams = '';
var data = '';

http.createServer(function (req, res) {

if(req.method == 'GET') {
	if(req.url == '/')
		showIndexPage(res);

  if(url.parse(req.url)['pathname'] == '/coders')
    displayRecords(req, res);

}

if(req.method == 'POST') {
	if(req.url == '/post_coder')
			storeRecord(req, res);
}

}).listen(8081);

function showIndexPage(res) {

	fs.readFile('index1.html', function(err, data) {

    var status = ['SUCCESS','FAILURE']

    if(err)
        data = '<html><body>'+status[1]+'!!!</br></body></html></br>';
    else {
      if(allRecords.length > 0)
        data = '<html><body>'+status[0]+'!!!</br>' + allRecords.length + ' USER ADDED</br>'+'</body></html></br>' + data;
    }
      res.writeHead(200, {
			'Content-Type': 'text/html',
			'Content-Length': data.length
		});
		res.write(data);
		res.end();
	});
}

function storeRecord(req, res) {
	req.on('data', function(params) {
		allParams += params;
	});

	req.on('end', function() {
		data = querystring.parse(allParams);
    allParams = '';

		if(!data['fname'] == '' && !data['lname'] == '')
			allRecords.push(data);

    data = '';
	});
  res.writeHead(301,
  {Location: '/'}
  );
  res.end();
}

function displayRecords(req, res) {
	// Parse GET Query
	var query = url.parse(req.url, true).query;

	var filteredRecords = filterRecords(query);

	// GET Query no-cache settings
  res.writeHead(200, {
  'Content-Type': 'text/html',
	'Cache-Control': 'no-cache, no-store, must-revalidate',
	'Pragma': 'no-cache'
  });
	var html = '';

	// Detect user-agent from headers
	var userAgent = req.headers['user-agent'];

	if(userAgent.indexOf("Chrome") > -1)
		html += '<HTML><HEAD><TITLE>VIEW CODER DETAILS</TITLE></HEAD><BODY bgcolor=pink>';
	else
	 	html += '<HTML><HEAD><TITLE>VIEW CODER DETAILS</TITLE></HEAD><BODY>';

		for(var i in filteredRecords)
			html += (Number(i)+1) + '. '+filteredRecords[i]['fname'] + ' '+filteredRecords[i]['lname'] + ' '+filteredRecords[i]['progLanguages'].filter(Boolean) + ' '+filteredRecords[i]['daysOfWeek'].filter(Boolean)+ ' '+filteredRecords[i]['hairColor']+'</br>';
		html +=	'</BODY></HTML>';

	res.write(html);
  res.end();
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
					if(allRecords[i]['progLanguages'].indexOf(query['progLanguages'][j]) > -1) {
							isExist = true;
							break;
					}
			}

			if(!isExist)
				continue;

			isExist  = false;

			for(var j in query['daysOfWeek']) {
					if(allRecords[i]['daysOfWeek'].indexOf(query['daysOfWeek'][j]) > -1) {
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
