var mongoose = require('mongoose'),
    Book = mongoose.model("Book")

exports.createBook = function(req, res, next) {

    var data =  req.body;
    console.log(req.body.author);
    if(req.body.author == null || req.body.author == ['']) {
      res.status(403);
      res.json({
          type: false,
          data: "Author information required."
      });
    }
    else {
      var bookModel = new Book(data);
      bookModel.save(function(err, book) {
        if (err) {
          res.status(500);
          res.json({
            type: false,
            data: "Error occured: " + err
          });
        }
        else {
          res.json({
            type: true,
            data: book
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
              type: false,
              data: "Error occured: " + err
          });
      } else {
          res.json({
              type: true,
              data: book
          });
      }
  });
}

exports.retrieveBookByTitle = function(req, res, next) {
  var bookTitle = req.params.title;
  Book.find({"title": bookTitle}, function(err, book) {
      if (err) {
          res.status(500);
          res.json({
              type: false,
              data: "Error occured: " + err
          });
      } else {
          res.json({
              type: true,
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
              type: false,
              data: "Error occured: " + err
          });
      } else {
          res.json({
              type: true,
              data: book
          });
      }
  });
}

exports.updateBook = function(req, res, next) {
      var data =  req.body;
      var bookModel = new Book(data);
      bookModel.save(function(err, book) {
        if (err) {
          res.status(500);
          res.json({
            type: false,
            data: "Error occured: " + err
          });
        }
        else {
          res.json({
            type: true,
            data: book
          });
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
                type: false,
                data: "Error occured: " + err
            });
        } else {
            res.json({
                type: true,
                data: book
            });
        }
    });
  });
}
