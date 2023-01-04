
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
module.exports.findInvestigationsByFilter = fs.readFileSync(path.join(__dirname, 'findInvestigationsByFilter.gql'), 'utf8');
module.exports.findLabReportsByFilter = fs.readFileSync(path.join(__dirname, 'findLabReportsByFilter.gql'), 'utf8');
