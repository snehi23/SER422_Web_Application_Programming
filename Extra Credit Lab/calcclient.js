var sock = require('net').Socket();
sock.on('data', function(data) {

	if(typeof JSON.parse(data) == 'string')
		console.log('Response: ' + JSON.parse(data));
	else {
			var list = {};
			list = JSON.parse(data);

			if(typeof list == 'object') {
				if(list.length > 0) {
					console.log('All Records:');
				for(var i in list)
					console.log('ClientID : '+i+' Total : '+list[i]);
				} else {
					console.log('No records found!');
				}	
			}	else
				console.log('Response: ' + list);
		}

	sock.destroy();
});
sock.on('close', function() {
	console.log('Connection closed');
});
sock.connect(3000);
sock.write(JSON.stringify({clientID:process.argv[2], command:process.argv[3], val:process.argv[4]}));
sock.end();
