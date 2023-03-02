const fs = require('fs');
const path = require('path');

module.exports.createPatient = fs.readFileSync(path.join(__dirname, 'createPatient.gql'), 'utf8');
module.exports.updatePatient = fs.readFileSync(path.join(__dirname, 'updatePatient.gql'), 'utf8');
