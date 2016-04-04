var http = require('http');
var fs = require('fs');
var querystring = require('querystring');

var allRecords = [];
var allParams = '';
var data = '';

http.createServer(function (req, res) {

if(req.method == 'GET') {
	if(req.url == '/')
		showIndexPage(res);
}

if(req.method == 'POST') {
	if(req.url == '/post_coder') {
		storeRecord(req, res);
	}
}

}).listen(8080);

function showIndexPage(res) {

	fs.readFile('index.html', function(err, data) {

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
	});
}

function storeRecord(req, res) {
	req.on('data', function(params) {
		allParams += params;
	});

	req.on('end', function() {
		data = querystring.parse(allParams);
    allParams = '';
		allRecords.push(data);
    data = '';
	});
  res.writeHead(301,
  {Location: '/'}
  );
  res.end();
}
