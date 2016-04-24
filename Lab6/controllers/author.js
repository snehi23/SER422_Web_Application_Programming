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
            })
        } else {
            res.json({
                type: true,
                data: author
            })
        }
    })
}
