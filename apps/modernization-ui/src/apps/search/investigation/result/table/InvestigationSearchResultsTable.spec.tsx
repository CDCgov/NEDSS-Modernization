import { render } from '@testing-library/react';
import { Investigation } from 'generated/graphql/schema';
import { MemoryRouter } from 'react-router-dom';
import { Column } from 'design-system/table';
import { InvestigationSearchResultsTable } from './InvestigationSearchResultsTable';
import { SelectableResolver } from 'options';

jest.mock('design-system/table/preferences', () => ({
    useColumnPreferences: () => ({ apply: (columns: Column<Investigation>[]) => columns })
}));

describe('When InvestigationSearchResultsTable renders', () => {
    const Wrapper = ({
        results,
        notificationStatusResolver = jest.fn()
    }: {
        results: Investigation[];
        notificationStatusResolver?: SelectableResolver;
    }) => {
        return (
            <MemoryRouter>
                <InvestigationSearchResultsTable
                    results={results}
                    notificationStatusResolver={notificationStatusResolver}
                />
            </MemoryRouter>
        );
    };

    it('should display column headers', () => {
        const { getAllByRole } = render(<Wrapper results={[]} />);

        const headers = getAllByRole('columnheader');

        expect(headers[0]).toHaveTextContent('Legal name');
        expect(headers[1]).toHaveTextContent('Date of birth');

        expect(headers[2]).toHaveTextContent('Current sex');
        expect(headers[3]).toHaveTextContent('Patient ID');
        expect(headers[4]).toHaveTextContent('Condition');
        expect(headers[5]).toHaveTextContent('Start date');
        expect(headers[6]).toHaveTextContent('Jurisdiction');
        expect(headers[7]).toHaveTextContent('Investigator');
        expect(headers[8]).toHaveTextContent('Investigation ID');
        expect(headers[9]).toHaveTextContent('Status');
        expect(headers[10]).toHaveTextContent('Notification');
    });

    it('should display the patient legal name', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                personParticipations: [
                    {
                        personCd: 'PAT',
                        typeCd: 'SubjOfPHC',
                        firstName: 'legal-first-name',
                        lastName: 'legal-last-name'
                    }
                ]
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[0]).toHaveTextContent('legal-first-name legal-last-name');
    });

    it('should display the patient date of birth', () => {
        const results: Investigation[] = [
            {
                relevance: 1,

                personParticipations: [
                    {
                        personCd: 'PAT',
                        typeCd: 'SubjOfPHC',
                        birthTime: '1976-12-01'
                    }
                ]
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[1]).toHaveTextContent('12/01/1976');
    });

    it('should display the patient gender', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                personParticipations: [
                    {
                        currSexCd: 'M',
                        typeCd: 'SubjOfPHC',
                        personCd: 'PAT'
                    }
                ]
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[2]).toHaveTextContent('Male');
    });

    it('should display the patient ID', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                personParticipations: [
                    {
                        shortId: 677,
                        personCd: 'PAT',
                        typeCd: 'SubjOfPHC'
                    }
                ]
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[3]).toHaveTextContent('677');
    });

    it('should display the condition under investigation', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                cdDescTxt: 'investigated-condition',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[4]).toHaveTextContent('investigated-condition');
    });

    it('should display the start date', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                startedOn: '2015-09-22',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[5]).toHaveTextContent('09/22/2015');
    });

    it('should display the Jurisdiction', () => {
        const results: Investigation[] = [
            {
                relevance: 1069,
                jurisdictionCodeDescTxt: 'jurisdication name',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[6]).toHaveTextContent('jurisdication name');
    });

    it('should display the Investigator', () => {
        const results: Investigation[] = [
            {
                relevance: 63.1,
                personParticipations: [
                    {
                        typeCd: 'InvestgrOfPHC',
                        personCd: 'PRV',
                        firstName: 'investigator-first-name',
                        lastName: 'investigator-last-name'
                    }
                ]
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[7]).toHaveTextContent('investigator-first-name investigator-last-name');
    });

    it('should display the Investigation ID', () => {
        const results: Investigation[] = [
            {
                relevance: 1,
                localId: 'CAS10000000GA01',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[8]).toHaveTextContent('CAS10000000GA01');
    });

    it('should display the open Investigation status', () => {
        const results: Investigation[] = [
            {
                relevance: 63.1,
                investigationStatusCd: 'O',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[9]).toHaveTextContent('open');
    });

    it('should display the closed Investigation status', () => {
        const results: Investigation[] = [
            {
                relevance: 63.1,
                investigationStatusCd: 'C',
                personParticipations: []
            }
        ];

        const { getAllByRole } = render(<Wrapper results={results} />);

        const columns = getAllByRole('cell');

        expect(columns[9]).toHaveTextContent('closed');
    });

    it('should display Notification status using resolver', () => {
        const notificationStatusResolver = jest.fn();

        const results: Investigation[] = [
            {
                __typename: 'Investigation',
                relevance: 1,
                id: '10000013',
                cdDescTxt: 'Pertussis',
                jurisdictionCodeDescTxt: 'Clayton County',
                localId: 'CAS10000000GA01',
                addTime: '2015-09-22',
                investigationStatusCd: 'O',
                notificationRecordStatusCd: 'notification-status',
                personParticipations: []
            }
        ];

        render(<Wrapper results={results} notificationStatusResolver={notificationStatusResolver} />);

        expect(notificationStatusResolver).toHaveBeenCalledWith('notification-status');
    });
});
