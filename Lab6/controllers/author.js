var mongoose = require('mongoose'),
    Author = mongoose.model("Author"),
    Book = mongoose.model("Book")

exports.createAuthor = function(req, res, next) {
    var authorModel = new Author(req.body);
    authorModel.save(function(err, author) {
      if (err) {
        res.status(500);
        res.json({
          message: "Internal Server Error"
        });
      }
      else {
        res.json({
          message: "Author Created",
          data: author.id
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
              message: "Internal Server Error"
          });
      } else {
          res.json({
              data: author
          });
      }
  });
}

exports.deleteAuthor = function(req, res, next) {
  var authorID = req.params.id;
  isDelete = true;

  Book.find({}, function(err, books) {
      for (var i = 0; i < books.length; i++) {
        var bookauthorlist = books[i].author;
        var authorids = bookauthorlist[0].split(',');
        if (authorids.indexOf(authorID) > -1) {
            isDelete = false;
            break;
        }
      }

          if (isDelete) {
          Author.remove({"id": authorID}, function(err, author) {
            if (err) {
              res.status(500);
              res.json({
                message: "Internal Server Error"
              });
              } else {
                res.json({
                  message: "Author Deleted"
              });
            }
          });
        } else {

              res.json({
                  message: "Can not delete. Book contains Author"
              });

        }
  });
}

exports.updateAuthor = function(req, res, next) {
    var authorModel = new Author(req.body);

    Author.find({"id": authorModel.id}, function(err, author) {

        if (err) {
            res.status(500);
            res.json({
                message: "Internal Server Error"
            });
        } else {

          if(author == []) {
            authorModel.save(function(err, author) {
              if (err) {
                res.status(500);
                res.json({
                  message: "Internal Server Error"
                });
              }
              else {
                res.json({
                  message: "Author Created",
                  data: author.id
                });
              }
            });
          } else {

            author[0].fname = authorModel.fname;
            author[0].lname = authorModel.lname;

            author[0].save(function(err, author) {
              if (err) {
                res.status(500);
                res.json({
                  message: "Internal Server Error"
                });
              }
              else {
                res.json({
                  message: "Author Updated",
                  data: author.id
                });
              }
            });
          }
        }

    });

}
