var mongoose = require('mongoose');
var authorSchema = new mongoose.Schema({
  id : Number,
  fname: String,
  lname: String
  },  {
  collection: 'authors'
});
mongoose.model('Author', authorSchema);
