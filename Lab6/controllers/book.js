var mongoose = require('mongoose'),
    Book = mongoose.model("Book")

exports.createBook = function(req, res, next) {

    var data =  req.body;

    if(req.body.author == [''])
      data.author = null;

    var bookModel = new Book(data);

    bookModel.save(function(err, book) {
        if (err) {
            res.status(500);
            res.json({
                type: false,
                data: "Error occured: " + err
            })
        } else {
            res.json({
                type: true,
                data: book
            })
        }
    })
}

exports.retrieveBook = function(req, res, next) {
  var bookISBN = req.params.isbn;
  Book.find({"isbn": bookISBN}, function(err, book) {
      if (err) {
          res.status(500);
          res.json({
              type: false,
              data: "Error occured: " + err
          })
      } else {
          res.json({
              type: true,
              data: book
          })
      }
  });
}
