// simplesockclient to go with simplesockserver.js
var sock = require('net').Socket();
sock.on('data', function(data) {

	var list = JSON.parse(data);

	if(Array.isArray(list)) {
		console.log('All Records:');
		for(var i in list)
			console.log('ClientID : '+i+' Total : '+list[i]);
	}	else
		console.log('Response: ' + list);

	sock.destroy(); // kill client after server's response
});
sock.on('close', function() {
	console.log('Connection closed');
});
// now make a request
sock.connect(3000);
sock.write(JSON.stringify({clientID:process.argv[2], command:process.argv[3], val:process.argv[4]}));
sock.end();
