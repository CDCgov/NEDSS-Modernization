
const fs = require('fs');
const path = require('path');

module.exports.findPatientById = fs.readFileSync(path.join(__dirname, 'findPatientById.gql'), 'utf8');
module.exports.findAllPatients = fs.readFileSync(path.join(__dirname, 'findAllPatients.gql'), 'utf8');
module.exports.findPatientsByFilter = fs.readFileSync(path.join(__dirname, 'findPatientsByFilter.gql'), 'utf8');
module.exports.findPatientsByOrganizationFilter = fs.readFileSync(path.join(__dirname, 'findPatientsByOrganizationFilter.gql'), 'utf8');
module.exports.findOrganizationById = fs.readFileSync(path.join(__dirname, 'findOrganizationById.gql'), 'utf8');
module.exports.findAllOrganizations = fs.readFileSync(path.join(__dirname, 'findAllOrganizations.gql'), 'utf8');
module.exports.findOrganizationsByFilter = fs.readFileSync(path.join(__dirname, 'findOrganizationsByFilter.gql'), 'utf8');
module.exports.findPlaceById = fs.readFileSync(path.join(__dirname, 'findPlaceById.gql'), 'utf8');
module.exports.findAllPlaces = fs.readFileSync(path.join(__dirname, 'findAllPlaces.gql'), 'utf8');
module.exports.findPlacesByFilter = fs.readFileSync(path.join(__dirname, 'findPlacesByFilter.gql'), 'utf8');
module.exports.findAllCountryCodes = fs.readFileSync(path.join(__dirname, 'findAllCountryCodes.gql'), 'utf8');
module.exports.findAllStateCodes = fs.readFileSync(path.join(__dirname, 'findAllStateCodes.gql'), 'utf8');
module.exports.findAllUsers = fs.readFileSync(path.join(__dirname, 'findAllUsers.gql'), 'utf8');
module.exports.findAllJurisdictions = fs.readFileSync(path.join(__dirname, 'findAllJurisdictions.gql'), 'utf8');
module.exports.findAllProgramAreas = fs.readFileSync(path.join(__dirname, 'findAllProgramAreas.gql'), 'utf8');
module.exports.findAllConditionCodes = fs.readFileSync(path.join(__dirname, 'findAllConditionCodes.gql'), 'utf8');
module.exports.findAllOutbreaks = fs.readFileSync(path.join(__dirname, 'findAllOutbreaks.gql'), 'utf8');
module.exports.findLocalLabTest = fs.readFileSync(path.join(__dirname, 'findLocalLabTest.gql'), 'utf8');
module.exports.findLoincLabTest = fs.readFileSync(path.join(__dirname, 'findLoincLabTest.gql'), 'utf8');
module.exports.findLocalCodedResults = fs.readFileSync(path.join(__dirname, 'findLocalCodedResults.gql'), 'utf8');
module.exports.findSnomedCodedResults = fs.readFileSync(path.join(__dirname, 'findSnomedCodedResults.gql'), 'utf8');
module.exports.findInvestigationsByFilter = fs.readFileSync(path.join(__dirname, 'findInvestigationsByFilter.gql'), 'utf8');
module.exports.findLabReportsByFilter = fs.readFileSync(path.join(__dirname, 'findLabReportsByFilter.gql'), 'utf8');
<<<<<<< HEAD
module.exports.findDocumentsRequiringReviewForPatient = fs.readFileSync(path.join(__dirname, 'findDocumentsRequiringReviewForPatient.gql'), 'utf8');
module.exports.findOpenInvestigationsForPatient = fs.readFileSync(path.join(__dirname, 'findOpenInvestigationsForPatient.gql'), 'utf8');
=======
>>>>>>> f0987278 (adding progress for tables in demographics and pop up message for delete funcionality)
