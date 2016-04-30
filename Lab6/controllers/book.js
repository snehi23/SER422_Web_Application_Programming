var mongoose = require('mongoose'),
    Book = mongoose.model("Book")

exports.createBook = function(req, res, next) {

    var data =  req.body;
    console.log(req.body.author);
    if(req.body.author == null || req.body.author == ['']) {
      res.status(403);
      res.json({
          message: "Author information required."
      });
    }
    else {
      var bookModel = new Book(data);
      bookModel.save(function(err, book) {
        if (err) {
          res.status(500);
          res.json({
                message: "Internal Server Error"
          });
        }
        else {
          res.json({
            message: "Book Created",
            data: book.id
          });
        }
      });
  }
}

exports.retrieveBookByISBN = function(req, res, next) {
  var bookISBN = req.params.isbn;
  Book.find({"isbn": bookISBN}, function(err, book) {
      if (err) {
          res.status(500);
          res.json({
                message: "Internal Server Error"
          });
      } else {
          res.json({
              data: book
          });
      }
  });
}

exports.retrieveBookByTitle = function(req, res, next) {
  var bookTitle = req.params.title;
  Book.find({"title": new RegExp(bookTitle)}, function(err, book) {
      if (err) {
          res.status(500);
          res.json({
              message: "Internal Server Error"
          });
      } else {
          res.json({
              data: book
          });
      }
  });
}

exports.deleteBook = function(req, res, next) {
  var bookISBN = req.params.isbn;
  Book.remove({"isbn": bookISBN}, function(err, book) {
      if (err) {
          res.status(500);
          res.json({
              message: "Internal Server Error"
          });
      } else {
          res.json({
              message: "Book Deleted"
          });
      }
  });
}

exports.updateBook = function(req, res, next) {
    var bookModel = new Book(req.body);
    Book.find({"isbn": bookModel.isbn}, function(err, book) {

        if (err) {
            res.status(500);
            res.json({
                message: "Internal Server Error"
            });
        } else {

          if(book == []) {
            bookModel.save(function(err, book) {
              if (err) {
                res.status(500);
                res.json({
                  message: "Internal Server Error"
                });
              }
              else {
                res.json({
                  message: "Book Updated",
                  data: book.isbn
                });
              }
            });
          } else {

            book[0].publisher = bookModel.publisher;
            book[0].title = bookModel.title;
            book[0].publish_year = bookModel.publish_year;

            book[0].save(function(err, book) {
              if (err) {
                res.status(500);
                res.json({
                  message: "Internal Server Error"
                });
              }
              else {
                res.json({
                  message: "Book Created",
                  data: book.isbn
                });
              }
            });
          }
        }

    });

}

exports.addAuthorToBook = function(req, res, next) {
  var bookISBN = req.params.isbn;
  var query = {"isbn": bookISBN};
  var newAuthorID = req.body.author;

  Book.find({"isbn":bookISBN}, function(err, book) {
    var authorArray = book[0]["author"];
    authorArray.push(newAuthorID);
    var update = {"author": authorArray};
    var options = {};

    Book.findOneAndUpdate(query, update, options, function(err, book) {
        if (err) {
            res.status(500);
            res.json({
                  message: "Internal Server Error"
            });
        } else {
            res.json({
                message: "Book Updated",
                data: book
            });
        }
    });
  });
}
