'use strict';
var mongoose = require('mongoose');

module.exports = {
  createAuthor: createAuthor
};



function createAuthor(req, res) {

  console.log(req.swagger.params);

  // mongoose.model('Author').findOneAndUpdate(query, newRecord, {upsert: true},function(err, author) {
  //   if(err)
  //     next(err);
  //
  //     res.json({
  //         message: 'success',
  //         data: author.id
  //     })
  // });

}
