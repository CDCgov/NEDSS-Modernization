const fs = require('fs');
const path = require('path');

module.exports.findPatientsByFilter = fs.readFileSync(path.join(__dirname, 'findPatientsByFilter.gql'), 'utf8');
module.exports.findInvestigationsByFilter = fs.readFileSync(path.join(__dirname, 'findInvestigationsByFilter.gql'), 'utf8');
module.exports.findAllJurisdictions = fs.readFileSync(path.join(__dirname, 'findAllJurisdictions.gql'), 'utf8');
module.exports.findLabReportsByFilter = fs.readFileSync(path.join(__dirname, 'findLabReportsByFilter.gql'), 'utf8');
module.exports.findAllProgramAreas = fs.readFileSync(path.join(__dirname, 'findAllProgramAreas.gql'), 'utf8');
