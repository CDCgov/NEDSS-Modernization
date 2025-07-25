import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { LabReport } from 'generated/graphql/schema';
import { LaboratoryReportSearchResultsTable } from './LaboratoryReportSearchResultsTable';
import { Selectable } from 'options';
import { Column } from 'design-system/table';

vi.mock('design-system/table/preferences', () => ({
    useColumnPreferences: () => ({ apply: (columns: Column<LabReport>[]) => columns })
}));

const Wrapper = ({
    results,
    jurisdictionResolver = jest.fn()
}: {
    results: LabReport[];
    jurisdictionResolver?: (value: string) => Selectable | undefined;
}) => {
    return (
        <MemoryRouter>
            <LaboratoryReportSearchResultsTable results={results} jurisdictionResolver={jurisdictionResolver} />
        </MemoryRouter>
    );
};

describe('When a Laboratory Report search result is viewed in a table', () => {
    it('should display column headers', () => {
        const { getAllByRole } = render(<Wrapper results={[]} />);

        const headers = getAllByRole('columnheader');

        expect(headers[0]).toHaveTextContent('Legal name');
        expect(headers[1]).toHaveTextContent('Date of birth');
        expect(headers[2]).toHaveTextContent('Current sex');
        expect(headers[3]).toHaveTextContent('Patient ID');
        expect(headers[4]).toHaveTextContent('Document type');
        expect(headers[5]).toHaveTextContent('Date received');
        expect(headers[6]).toHaveTextContent('Description');
        expect(headers[7]).toHaveTextContent('Reporting facility');
        expect(headers[8]).toHaveTextContent('Ordering provider');
        expect(headers[9]).toHaveTextContent('Jurisdiction');
        expect(headers[10]).toHaveTextContent('Associated with');
        expect(headers[11]).toHaveTextContent('Local ID');
    });

    it('should display column content', () => {
        const result: LabReport[] = [
            {
                __typename: 'LabReport',
                addTime: '2015-09-22',
                associatedInvestigations: [
                    {
                        __typename: 'AssociatedInvestigation',
                        cdDescTxt: 'Bacterial Vaginosis',
                        localId: 'CAS10001001GA01'
                    },
                    {
                        __typename: 'AssociatedInvestigation',
                        cdDescTxt: 'Bacterial Vaginosis',
                        localId: 'CAS10001001GA01'
                    }
                ],
                id: '10000013',
                jurisdictionCd: 130006,
                localId: 'CAS10000000GA01',
                observations: [
                    {
                        __typename: 'Observation',
                        cdDescTxt: 'No Information Given',
                        statusCd: null,
                        altCd: null,
                        displayName: null
                    },
                    {
                        __typename: 'Observation',
                        cdDescTxt: '11-Desoxycortisol',
                        statusCd: null,
                        altCd: '1657-6',
                        displayName: 'abnormal'
                    }
                ],
                organizationParticipations: [
                    {
                        __typename: 'LabReportOrganizationParticipation',
                        typeCd: 'AUT',
                        name: 'Emory University Hospital'
                    }
                ],
                personParticipations: [
                    {
                        __typename: 'LabReportPersonParticipation',
                        birthTime: '1990-01-01',
                        currSexCd: 'M',
                        typeCd: 'PATSBJ',
                        firstName: 'Surma',
                        lastName: 'Singh',
                        personCd: 'PAT',
                        personParentUid: 10000001,
                        shortId: 63000
                    },
                    {
                        __typename: 'LabReportPersonParticipation',
                        birthTime: '1990-01-01',
                        currSexCd: 'M',
                        typeCd: 'ORD',
                        firstName: 'John',
                        lastName: 'Henry',
                        personCd: 'PRV',
                        personParentUid: 10000001,
                        shortId: 63000
                    }
                ],
                tests: [
                    {
                        name: '11-Desoxycortisol',
                        coded: 'abnormal'
                    }
                ],
                relevance: 1
            }
        ];

        const jurisdictionResolver = jest.fn();

        jurisdictionResolver.mockReturnValue({ name: 'Gwinnett County' });

        const { getAllByRole } = render(<Wrapper results={result} jurisdictionResolver={jurisdictionResolver} />);
        const columns = getAllByRole('cell');
        expect(columns[0]).toHaveTextContent('Surma Singh');
        expect(columns[1]).toHaveTextContent('01/01/1990');
        expect(columns[2]).toHaveTextContent('Male');
        expect(columns[3]).toHaveTextContent('63000');
        expect(columns[4]).toHaveTextContent('Lab report');
        expect(columns[5]).toHaveTextContent('09/22/2015');
        expect(columns[6]).toHaveTextContent('11-Desoxycortisol:');
        expect(columns[7]).toHaveTextContent('Emory University Hospital');
        expect(columns[8]).toHaveTextContent('John Henry');
        expect(columns[9]).toHaveTextContent('Gwinnett County');
        expect(columns[10]).toHaveTextContent('Bacterial Vaginosis');
        expect(columns[11]).toHaveTextContent('CAS10000000GA01');

        expect(jurisdictionResolver).toHaveBeenCalledWith('130006');
    });
});
