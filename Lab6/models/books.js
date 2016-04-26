var mongoose = require('mongoose');
var bookSchema = new mongoose.Schema({
  isbn : {
    type: String,
    required: true,
    unique: true
    },
  publisher: String,
  title: String,
  publish_year: Number,
  author:{
    type: Array,
    required: true
  }
  },  {
  collection: 'books'
});
module.exports.Book = mongoose.model('Book', bookSchema);
