export const caseReportLinks = [
    {
        text: 'Manage trigger codes for case reporting',
        href: '/nbs/TriggerCodes.do?method=manageLoad&initLoad=true'
    },
    { text: 'Manage laboratories', href: '/nbs/Laboratories.do?method=searchLab' },
    { group: 'Manage lab results' },
    { text: 'Manage local results', href: '/nbs/ExistingLocallyDefinedLabResults.do?method=searchLabLoad' },
    { text: 'Manage SNOMEDs', href: '/nbs/SnomedCode.do?method=manageSnomedCodeLoad' },
    {
        text: 'Manage link between lab results and SNOMED',
        href: '/nbs/ExistingResultsSnomedLink.do?method=searchResultSnomedLinkLoad'
    },
    {
        text: 'Manage link between SNOMED and condition',
        href: '/nbs/SnomedtoConditionLink.do?method=searchSnomedtoCondLinkLoad'
    },
    { group: 'Manage lab tests' },
    { text: 'Manage local lab tests', href: '/nbs/LDLabTests.do?method=searchLabLoad' },
    { text: 'Manage LOINCs', href: '/nbs/ManageLoincs.do?method=manageLoincs' },
    { text: 'Manage link between lab tests and LOINC', href: '/nbs/LabTestLoincLink.do?method=searchLoincLoad' },
    {
        text: 'Manage link between LOINC and condition',
        href: '/nbs/LoinctoConditionLink.do?method=searchLoinctoCondLinkLoad'
    }
];
