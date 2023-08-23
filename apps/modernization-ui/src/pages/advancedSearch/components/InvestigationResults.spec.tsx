import { Investigation } from 'generated/graphql/schema';
import { InvestigationResults } from './InvestigationResults';
import { getByText, render } from '@testing-library/react';
import { BrowserRouter, Router } from 'react-router-dom';

const investigations: [Investigation] = [
    {
        id: '10056296',
        recordStatus: 'OPEN',
        lastChangeTime: '2023-07-21T15:21:03.770Z',
        publicHealthCaseUid: 10056296,
        caseClassCd: null,
        outbreakName: null,
        caseTypeCd: 'I',
        cdDescTxt: '2019 Novel Coronavirus',
        progAreaCd: 'GCD',
        jurisdictionCd: 130006,
        jurisdictionCodeDescTxt: 'Clayton County',
        pregnantIndCd: null,
        localId: 'CAS10003001GA99',
        rptFormCmpltTime: null,
        activityToTime: null,
        activityFromTime: '2023-07-21T00:00:00Z',
        addTime: '2023-07-21T15:21:03.770Z',
        publicHealthCaseLastChgTime: '2023-07-21T15:21:03.770Z',
        addUserId: 10054282,
        lastChangeUserId: 10054282,
        currProcessStateCd: null,
        investigationStatusCd: 'O',
        moodCd: 'EVN',
        notificationLocalId: null,
        notificationAddTime: null,
        notificationRecordStatusCd: null,
        notificationLastChgTime: null,
        personParticipations: [
            {
                actUid: 10056296,
                localId: 'PSN10063000GA01',
                typeCd: 'SubjOfPHC',
                entityId: 10056290,
                subjectClassCd: 'PSN',
                participationRecordStatus: 'ACTIVE',
                typeDescTxt: null,
                participationLastChangeTime: '2023-07-21T15:21:03.653Z',
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
        organizationParticipations: null
    }
];

describe('InvestigationResults component tests', () => {
    it('should render a grid with investigation details', () => {
        const { getByText } = render(
            <BrowserRouter>
                <InvestigationResults
                    data={investigations}
                    totalResults={1}
                    handlePagination={() => {}}
                    currentPage={0}
                />
            </BrowserRouter>
        );
        expect(getByText('Doe, John')).toBeInTheDocument();
        expect(getByText('2019 Novel Coronavirus')).toBeInTheDocument();
        expect(getByText('CAS10003001GA99')).toBeInTheDocument();
        expect(getByText('1/1/1990')).toBeInTheDocument();
        expect(getByText('(33 years)')).toBeInTheDocument();
        expect(getByText('63000')).toBeInTheDocument();
        expect(getByText('7/21/2023')).toBeInTheDocument();
        expect(getByText('OPEN')).toBeInTheDocument();
        expect(getByText('Clayton County')).toBeInTheDocument();
    });
});
