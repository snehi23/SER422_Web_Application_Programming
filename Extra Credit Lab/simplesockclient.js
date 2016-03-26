// simplesockclient to go with simplesockserver.js
var sock = require('net').Socket();
sock.on('data', function(data) {
	console.log('Response: ' + data);
	sock.destroy(); // kill client after server's response
});
sock.on('close', function() {
	console.log('Connection closed');
});
// now make a request
sock.connect(3000);
sock.write(JSON.stringify({command:process.argv[2], val:process.argv[3]}));
sock.end();
