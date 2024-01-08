import { BrowserRouter } from 'react-router-dom';
import { LabReportResults } from './LabReportResults';
import { render } from '@testing-library/react';
import { LabReport } from 'generated/graphql/schema';

const labReports: [LabReport] = [
    {
        id: '10056325',
        observationUid: 10056325,
        lastChange: '2023-08-16T20:39:34.003Z',
        classCd: 'OBS',
        moodCd: 'EVN',
        observationLastChgTime: '2023-08-16T20:39:34.003Z',
        cdDescTxt: 'No Information Given',
        recordStatusCd: 'LOG_DEL',
        programAreaCd: 'STD',
        jurisdictionCd: 130006,
        jurisdictionCodeDescTxt: null,
        pregnantIndCd: null,
        localId: 'OBS10001008GA01',
        activityToTime: null,
        effectiveFromTime: null,
        rptToStateTime: '2023-07-27T00:00:00Z',
        addTime: '2023-07-27T17:04:00.023Z',
        electronicInd: 'N',
        versionCtrlNbr: 2,
        addUserId: 10054282,
        lastChgUserId: 10054282,
        personParticipations: [
            {
                actUid: 10056325,
                localId: 'PSN10063000GA01',
                typeCd: 'PATSBJ',
                entityId: 10056319,
                subjectClassCd: 'PSN',
                participationRecordStatus: 'ACTIVE',
                typeDescTxt: 'Patient subject',
                participationLastChangeTime: '2023-07-27T17:03:59.820Z',
                firstName: 'John',
                lastName: 'Doe',
                birthTime: '1990-01-01T00:00:00Z',
                currSexCd: 'M',
                personCd: 'PAT',
                personParentUid: 10000001,
                personRecordStatus: 'ACTIVE',
                personLastChangeTime: null,
                shortId: 63000
            }
        ],
        organizationParticipations: [
            {
                actUid: 10056325,
                typeCd: 'AUT',
                entityId: 10003001,
                subjectClassCd: 'ORG',
                typeDescTxt: 'Author',
                participationRecordStatus: null,
                participationLastChangeTime: null,
                name: 'Piedmont Hospital',
                organizationLastChangeTime: null
            }
        ],
        observations: [
            {
                cd: 'T-50130',
                cdDescTxt: 'Acid-Fast Stain',
                domainCd: 'Result',
                statusCd: null,
                altCd: '11545-1',
                altDescTxt: 'MICROSCOPIC OBSERVATION',
                altCdSystemCd: 'LN',
                displayName: 'abnormal',
                ovcCode: 'ABN',
                ovcAltCode: 'R-42037',
                ovcAltDescTxt: 'SNOMED',
                ovcAltCdSystemCd: 'SNM'
            }
        ],
        associatedInvestigations: null
    }
];

describe('LabReportResults component tests', () => {
    it('should render lab report details', () => {
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults data={labReports} totalResults={1} handlePagination={() => {}} currentPage={0} />
            </BrowserRouter>
        );
        expect(getByText('Doe, John')).toBeInTheDocument();
        expect(getByText('Acid-Fast Stain = abnormal')).toBeInTheDocument();
        expect(getByText('OBS10001008GA01')).toBeInTheDocument();
        expect(getByText('1/1/1990')).toBeInTheDocument();
        expect(getByText('(33 years)')).toBeInTheDocument();
        expect(getByText('63000')).toBeInTheDocument();
        expect(getByText('7/27/2023')).toBeInTheDocument();
        expect(getByText('Piedmont Hospital')).toBeInTheDocument();
        expect(getByText('Lab Report').closest('a')).toHaveAttribute('href', '#');
    });
});
