var mongoose = require('mongoose');
var authorSchema = new mongoose.Schema({
  id : {
    type: Number,
    unique: true
  },
  fname: String,
  lname: String
  },  {
  collection: 'authors'
});
mongoose.model('Author', authorSchema);
