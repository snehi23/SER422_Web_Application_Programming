var mongoose = require('mongoose');
var bookSchema = new mongoose.Schema({
  isbn : String,
  publisher: String,
  title: String,
  publish_year: Number,
  author: Array
  },  {
  collection: 'books'
});
mongoose.model('Book', bookSchema);
