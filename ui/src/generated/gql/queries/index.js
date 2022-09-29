
const fs = require('fs');
const path = require('path');

module.exports.findPatientById = fs.readFileSync(path.join(__dirname, 'findPatientById.gql'), 'utf8');
module.exports.findAllPatients = fs.readFileSync(path.join(__dirname, 'findAllPatients.gql'), 'utf8');
module.exports.findPatientsByFilter = fs.readFileSync(path.join(__dirname, 'findPatientsByFilter.gql'), 'utf8');
