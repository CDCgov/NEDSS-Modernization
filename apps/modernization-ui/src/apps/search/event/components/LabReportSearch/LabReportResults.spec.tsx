import { BrowserRouter } from 'react-router-dom';
import { LabReportResults } from './LabReportResults';
import { render } from '@testing-library/react';
import { LabReport } from 'generated/graphql/schema';

const labReports: [LabReport] = [
    {
        id: '10056325',
        jurisdictionCd: 130006,
        jurisdictionCodeDescTxt: null,
        localId: 'OBS10001008GA01',
        addTime: '2023-07-27T17:04:00.023Z',
        personParticipations: [
            {
                typeCd: 'PATSBJ',
                participationRecordStatus: 'ACTIVE',
                typeDescTxt: 'Patient subject',
                firstName: 'John',
                lastName: 'Doe',
                birthTime: '1990-01-01T00:00:00Z',
                currSexCd: 'M',
                personCd: 'PAT',
                personParentUid: 10000001,
                shortId: 63000
            }
        ],
        organizationParticipations: [
            {
                typeCd: 'AUT',
                name: 'Piedmont Hospital'
            }
        ],
        observations: [
            {
                cdDescTxt: 'Acid-Fast Stain',
                altCd: '11545-1',
                displayName: 'abnormal'
            }
        ],
        associatedInvestigations: []
    }
];

describe('LabReportResults component tests', () => {
    it('should render lab report details', () => {
        const { getByText } = render(
            <BrowserRouter>
                <LabReportResults data={labReports} totalResults={1} handlePagination={() => {}} currentPage={0} />
            </BrowserRouter>
        );
        const timeDiff = Date.now() - new Date(labReports[0].personParticipations![0]?.birthTime).getTime();
        const age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
        expect(getByText('Doe, John')).toBeInTheDocument();
        expect(getByText('Acid-Fast Stain = abnormal')).toBeInTheDocument();
        expect(getByText('OBS10001008GA01')).toBeInTheDocument();
        expect(getByText('01/01/1990')).toBeInTheDocument();
        expect(getByText(`(${age} years)`)).toBeInTheDocument();
        expect(getByText('63000')).toBeInTheDocument();
        expect(getByText('07/27/2023')).toBeInTheDocument();
        expect(getByText('Piedmont Hospital')).toBeInTheDocument();
        expect(getByText('Lab Report').closest('a')).toHaveAttribute('href', '#');
    });
});
