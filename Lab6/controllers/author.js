var mongoose = require('mongoose'),
    Author = mongoose.model("Author")

exports.createAuthor = function(req, res, next) {
    var authorModel = new Author(req.body);
    authorModel.save(function(err, author) {
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
          data: author
        });
      }
    });
}

exports.retrieveAuthor = function(req, res, next) {
  var authorID = req.params.id;
  Author.find({"id": authorID}, function(err, author) {
      if (err) {
          res.status(500);
          res.json({
              type: false,
              data: "Error occured: " + err
          });
      } else {
          res.json({
              type: true,
              data: author
          });
      }
  });
}

exports.deleteAuthor = function(req, res, next) {
  var authorID = req.params.id;
  Book.find({"author": authorID}, function(err, book) {
      if (err) {
          if (isDelete) {
          Author.remove({"id": authorID}, function(err, author) {
            if (err) {
              res.status(500);
              res.json({
                type: false,
                data: "Error occured: " + err
              });
              } else {
                res.json({
                  type: true,
                  data: author
              });
            }
        });
      }
    }
  });
}

exports.updateAuthor = function(req, res, next) {
    var authorModel = new Author(req.body);
    authorModel.save(function(err, author) {
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
          data: author
        });
      }
    });
}
