const fs = require('fs');
const path = require('path');

module.exports.findPatientsByFilter = fs.readFileSync(path.join(__dirname, 'findPatientsByFilter.gql'), 'utf8');
module.exports.findInvestigationsByFilter = fs.readFileSync(path.join(__dirname, 'findInvestigationsByFilter.gql'), 'utf8');
module.exports.findAllJurisdictions = fs.readFileSync(path.join(__dirname, 'findAllJurisdictions.gql'), 'utf8');
module.exports.findLabReportsByFilter = fs.readFileSync(path.join(__dirname, 'findLabReportsByFilter.gql'), 'utf8');
module.exports.findContactsNamedByPatient = fs.readFileSync(path.join(__dirname, 'findContactsNamedByPatient.gql'), 'utf8');
module.exports.findPatientNamedByContact = fs.readFileSync(path.join(__dirname, 'findPatientNamedByContact.gql'), 'utf8');
module.exports.findDocumentsForPatient = fs.readFileSync(path.join(__dirname, 'findDocumentsForPatient.gql'), 'utf8');
module.exports.findInvestigationsForPatient = fs.readFileSync(path.join(__dirname, 'findInvestigationsForPatient.gql'), 'utf8');
module.exports.findMorbidityReportsForPatient = fs.readFileSync(path.join(__dirname, 'findMorbidityReportsForPatient.gql'), 'utf8');
module.exports.findPatientProfile = fs.readFileSync(path.join(__dirname, 'findPatientProfile.gql'), 'utf8');
module.exports.findTreatmentsForPatient = fs.readFileSync(path.join(__dirname, 'findTreatmentsForPatient.gql'), 'utf8');
module.exports.findVaccinationsForPatient = fs.readFileSync(path.join(__dirname, 'findVaccinationsForPatient.gql'), 'utf8');
module.exports.findAllProgramAreas = fs.readFileSync(path.join(__dirname, 'findAllProgramAreas.gql'), 'utf8');
