var restify = require('restify'),
    mongoose = require('mongoose'),
    db = require('./db'),
    author = require('./models/authors'),
    book = require('./models/books');

var author_controller = require('./controllers/author');
var book_controller = require('./controllers/book');

var server = restify.createServer();

server.use(restify.fullResponse())
      .use(restify.bodyParser())

server.post("/authors", author_controller.createAuthor);
server.post("/books", book_controller.createBook);

server.listen(8081);
