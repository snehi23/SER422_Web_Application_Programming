// calcclient.js to go with calcserver.js
var sock = require('net').Socket();
sock.on('data', function(data) {

	if(typeof JSON.parse(data) == 'string')
		console.log('Response: ' + JSON.parse(data));
	else {
		var list = {};
		list = JSON.parse(data);

		if(typeof list == 'object') {
			console.log('All Records:');
			for(var i in list)
				console.log('ClientID : '+i+' Total : '+list[i]);
		}	else
			console.log('Response: ' + list);
		}

	sock.destroy(); // kill client after server's response
});
sock.on('close', function() {
	console.log('Connection closed');
});
// now make a request
sock.connect(3000);
sock.write(JSON.stringify({clientID:process.argv[2], command:process.argv[3], val:process.argv[4]}));
sock.end();
