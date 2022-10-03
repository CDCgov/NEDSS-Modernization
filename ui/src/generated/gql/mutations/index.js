
const fs = require('fs');
const path = require('path');

module.exports.createPatient = fs.readFileSync(path.join(__dirname, 'createPatient.gql'), 'utf8');
module.exports.deletePatient = fs.readFileSync(path.join(__dirname, 'deletePatient.gql'), 'utf8');
