// simple socket server
var net = require('net');
var total = '';
var totals = {};

net.createServer(function (sock) {
    console.log("Incoming connection accepted");
    sock.on('data', function (data) {
      console.log("Received from client: " +data);
      var d = JSON.parse(data);

      if(d.val == undefined || isNaN(d.val))
          d.command = '';

      switch (d.command) {
        case 'a':
                if(totals[d.clientID] == undefined)
                    totals[d.clientID] = Number(d.val);
                else
                    totals[d.clientID] = Number(d.val) + Number(totals[d.clientID]);
                total = Number(totals[d.clientID]);
          break;
        case 'm':
                if(totals[d.clientID] == undefined)
                    totals[d.clientID] = Number(d.val);
                else
                    totals[d.clientID] = Number(d.val) - Number(totals[d.clientID]);
                total = Number(totals[d.clientID]);
          break;
        case 's':
                totals[d.clientID] = Number(d.val);
                total = Number(totals[d.clientID]);
          break;
        case 'q':
                sock.write(JSON.stringify(totals), function() {
                  console.log("Finished response to client");
                })
                process.exit(0);
          break;
        default:
                total = 'Invalid request specification [USAGE: node calcclient.js <client> <cmd> <val>]';

      }
      console.log(JSON.stringify(total));
        sock.write(JSON.stringify(total), function() {
        	console.log("Finished response to client");
        })
    }).on('error', function (e) {
	console.log("Some kind of server error");
    });
}).listen(3000);
