// simple socket server
var net = require('net');
var total = 0;
net.createServer(function (sock) {
    console.log("Incoming connection accepted");
    sock.on('data', function (data) {
      console.log("Received from client: " +data);
      var d = JSON.parse(data);

      switch (d.command) {
        case 'a':
                total += Number(d.val);
          break;
        case 'm':
                total -= Number(d.val);
          break;
        case 's':
                total = Number(d.val);
          break;
        case 'q':
                process.exit(0);
          break;
        default:
                total = "Invalid request specification"

      }
        sock.write(total.toString(), function() {
        	console.log("Finished response to client");
        })
    }).on('error', function (e) {
	console.log("Some kind of server error");
    });
}).listen(3000);
