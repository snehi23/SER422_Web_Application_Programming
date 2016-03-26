// simple socket server
var net = require('net');
var sum = 0;
net.createServer(function (sock) {
    console.log("Incoming connection accepted");
    sock.on('data', function (data) {
      console.log("Received from client: " +data);
      var d = JSON.parse(data);
        sum += Number(d.val);
        sock.write(sum.toString(), function() {
        	console.log("Finished response to client");
        })
    }).on('error', function (e) {
	console.log("Some kind of server error");
    });
}).listen(3000);
