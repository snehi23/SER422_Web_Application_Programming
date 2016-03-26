// simple socket server
var net = require('net');
var total = '';
var totals = new Map();


net.createServer(function (sock) {
    console.log("Incoming connection accepted");
    sock.on('data', function (data) {
      console.log("Received from client: " +data);
      var d = JSON.parse(data);

      if(d.val == undefined || isNaN(d.val))
          d.command = '';

      switch (d.command) {
        case 'a':
                if(totals.get(d.clientID) == undefined)
                    totals.set(d.clientID,Number(d.val));
                else
                    totals.set(d.clientID,Number(totals.get(d.clientID))+Number(d.val));
                total = totals.get(d.clientID);
          break;
        case 'm':
                if(totals.get(d.clientID) == undefined)
                    totals.set(d.clientID,Number(d.val));
                else
                    totals.set(d.clientID,Number(totals.get(d.clientID))-Number(d.val));
                total = totals.get(d.clientID);
          break;
        case 's':
                totals.set(d.clientID,Number(d.val));
                total = totals.get(d.clientID);
          break;
        case 'q':
                process.exit(0);
          break;
        default:
                total = "Invalid request specification USAGE: node calcclient.js <client> <cmd> <val>";

      }
        sock.write(total.toString(), function() {
        	console.log("Finished response to client");
        })
    }).on('error', function (e) {
	console.log("Some kind of server error");
    });
}).listen(3000);
