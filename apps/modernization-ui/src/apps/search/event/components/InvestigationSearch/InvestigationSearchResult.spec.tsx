import { render } from '@testing-library/react';
import { Investigation } from 'generated/graphql/schema';
import { BrowserRouter } from 'react-router-dom';
import { InvestigationSearchResult } from './InvestigationSearchResult';

describe('when an investigation is found', () => {
    const investigation: Investigation = {
        id: '10056296',

        cdDescTxt: '2019 Novel Coronavirus',

        jurisdictionCodeDescTxt: 'Clayton County',

        localId: 'CAS10003001GA99',

        addTime: '2023-07-21T15:21:03.770Z',

        investigationStatusCd: 'O',

        notificationRecordStatusCd: null,

        personParticipations: [
            {
                typeCd: 'SubjOfPHC',
                firstName: 'John',
                lastName: 'Doe',
                birthTime: '1990-01-01T00:00:00Z',
                currSexCd: 'M',
                personCd: 'PAT',
                personParentUid: 10000001,
                shortId: 63000
            }
        ]
    };

    it('should display the investigation search result', () => {
        const { getByText } = render(
            <BrowserRouter>
                <InvestigationSearchResult investigation={investigation} />
            </BrowserRouter>
        );

        const timeDiff = Date.now() - new Date('1990-01-01T00:00:00Z').getTime();
        const age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
        expect(getByText('Doe, John')).toBeInTheDocument();
        expect(getByText('2019 Novel Coronavirus')).toBeInTheDocument();
        expect(getByText('CAS10003001GA99')).toBeInTheDocument();
        expect(getByText('01/01/1990')).toBeInTheDocument();
        expect(getByText(`(${age} years)`)).toBeInTheDocument();
        expect(getByText('63000')).toBeInTheDocument();
        expect(getByText('07/21/2023')).toBeInTheDocument();
        expect(getByText('OPEN')).toBeInTheDocument();
        expect(getByText('Clayton County')).toBeInTheDocument();
        expect(getByText('2019 Novel Coronavirus').closest('a')).toHaveAttribute('href', '#');
    });
});
