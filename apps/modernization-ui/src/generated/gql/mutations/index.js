const fs = require('fs');
const path = require('path');

module.exports.createPatient = fs.readFileSync(path.join(__dirname, 'createPatient.gql'), 'utf8');
module.exports.updatePatientGeneralInfo = fs.readFileSync(path.join(__dirname, 'updatePatientGeneralInfo.gql'), 'utf8');
module.exports.updatePatientSexBirth = fs.readFileSync(path.join(__dirname, 'updatePatientSexBirth.gql'), 'utf8');
module.exports.updateMortality = fs.readFileSync(path.join(__dirname, 'updateMortality.gql'), 'utf8');
module.exports.deletePatient = fs.readFileSync(path.join(__dirname, 'deletePatient.gql'), 'utf8');
