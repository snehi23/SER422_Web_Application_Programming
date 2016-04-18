var mongoose = require('mongoose');
var developerSchema = new mongoose.Schema({
  fname: String,
  lname: String,
  progLanguages: Array,
  daysOfWeek: Array,
  hairColor: String
  },  {
  collection: 'developerRecords'
});
mongoose.model('Developer', developerSchema);
