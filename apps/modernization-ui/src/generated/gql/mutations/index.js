const fs = require('fs');
const path = require('path');

module.exports.updatePatientGeneralInfo = fs.readFileSync(path.join(__dirname, 'updatePatientGeneralInfo.gql'), 'utf8');
module.exports.updatePatientBirthAndGender = fs.readFileSync(path.join(__dirname, 'updatePatientBirthAndGender.gql'), 'utf8');
module.exports.updatePatientMortality = fs.readFileSync(path.join(__dirname, 'updatePatientMortality.gql'), 'utf8');
module.exports.updateEthnicity = fs.readFileSync(path.join(__dirname, 'updateEthnicity.gql'), 'utf8');
module.exports.addPatientRace = fs.readFileSync(path.join(__dirname, 'addPatientRace.gql'), 'utf8');
module.exports.updatePatientRace = fs.readFileSync(path.join(__dirname, 'updatePatientRace.gql'), 'utf8');
module.exports.deletePatientRace = fs.readFileSync(path.join(__dirname, 'deletePatientRace.gql'), 'utf8');
module.exports.addPatientName = fs.readFileSync(path.join(__dirname, 'addPatientName.gql'), 'utf8');
module.exports.updatePatientName = fs.readFileSync(path.join(__dirname, 'updatePatientName.gql'), 'utf8');
module.exports.deletePatientName = fs.readFileSync(path.join(__dirname, 'deletePatientName.gql'), 'utf8');
