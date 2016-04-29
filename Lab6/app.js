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
server.get("/authors/:id", author_controller.retrieveAuthor);
server.get("/books/isbn/:isbn", book_controller.retrieveBookByISBN);
server.get("/books/title/:title", book_controller.retrieveBookByTitle);
server.del("/authors/:id", author_controller.deleteAuthor);
server.del("/books/:isbn", book_controller.deleteBook);
server.patch("/books/:isbn", book_controller.addAuthorToBook);
//TODO
//server.put("/authors", author_controller.updateAuthor);
//server.put("/books", book_controller.updateBook);

server.listen(8081);
