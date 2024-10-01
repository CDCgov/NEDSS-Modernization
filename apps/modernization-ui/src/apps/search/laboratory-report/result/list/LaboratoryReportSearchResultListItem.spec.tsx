import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { LaboratoryReportSearchResultListItem } from './LaboratoryReportSearchResultListItem';
import { LabReport } from 'generated/graphql/schema';

describe('LaboratoryReportSearchResultListItem', () => {
    it('should render the legal name', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    shortId: 123,
                    personCd: 'PAT',
                    firstName: 'First',
                    lastName: 'Last',
                    typeCd: 'PATSBJ'
                }
            ],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );

        expect(getByText('First Last')).toBeInTheDocument();
    });

    it('should render the date of birth', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    shortId: 123,
                    personCd: 'PAT',
                    birthTime: '1995-05-07',
                    currSexCd: 'M',
                    typeCd: 'PATSBJ'
                }
            ],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('05/07/1995')).toBeInTheDocument();
    });

    it('should render the sex', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    shortId: 123,
                    personCd: 'PAT',
                    birthTime: '1995-05-07',
                    currSexCd: 'M',
                    typeCd: 'PATSBJ'
                }
            ],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('Male')).toBeInTheDocument();
        expect(getByText('Current sex')).toBeInTheDocument();
    });

    it('should render the patient ID', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [
                {
                    shortId: 1051,
                    personCd: 'PAT',
                    firstName: 'First',
                    lastName: 'Last',
                    typeCd: 'PATSBJ'
                }
            ],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('1051')).toBeInTheDocument();
    });

    it('should render the document type', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('Lab report')).toBeInTheDocument();
    });

    it('should render the date received', () => {
        const result: LabReport = {
            addTime: '2021-09-23',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('09/23/2021')).toBeInTheDocument();
    });

    it('should render the description', () => {
        const result: LabReport = {
            addTime: '2021-09-17',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [{ cdDescTxt: 'test description', altCd: 'alt-cd-vlalue', displayName: 'test display' }],
            organizationParticipations: [],
            personParticipations: [],
            tests: [
                {
                    name: 'some resulted test',
                    high: '20',
                    low: '10'
                }
            ],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('some resulted test:')).toBeInTheDocument();
        expect(getByText('(10 - 20)')).toBeInTheDocument();
    });

    it('should render the jurisdiction', () => {
        const resolver = jest.fn();

        const result: LabReport = {
            addTime: '2021-09-17',
            associatedInvestigations: [],
            id: '100234',
            jurisdictionCd: 1013,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [],
            tests: [],
            relevance: 5
        };

        render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={resolver} />
            </MemoryRouter>
        );

        expect(resolver).toHaveBeenCalledWith('1013');
    });

    it('should render the associated to', () => {
        const result: LabReport = {
            addTime: '2015-09-22',
            associatedInvestigations: [
                { cdDescTxt: 'associated-investigation-description', localId: 'associated-investigation-local' }
            ],
            id: '100234',
            jurisdictionCd: 567,
            localId: '999',
            observations: [],
            organizationParticipations: [],
            personParticipations: [],
            tests: [],
            relevance: 5
        };

        const { getByText } = render(
            <MemoryRouter>
                <LaboratoryReportSearchResultListItem result={result} jurisdictionResolver={jest.fn()} />
            </MemoryRouter>
        );
        expect(getByText('Associated to')).toBeInTheDocument();

        const value = getByText(/associated-investigation-local/);

        expect(value).toHaveTextContent(/associated-investigation-description/);
    });
});
