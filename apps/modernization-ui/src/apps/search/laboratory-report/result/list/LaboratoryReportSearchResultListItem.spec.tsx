import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { LaboratoryReportSearchResultListItem } from './LaboratoryReportSearchResultListItem';
import { LabReport } from 'generated/graphql/schema';

const expectedResult: LabReport = {
    addTime: new Date('09-09-2021').toISOString(),
    associatedInvestigations: [
        { cdDescTxt: 'associated-investigation-description', localId: 'associated-investigation-local' }
    ],
    id: '100234',
    jurisdictionCd: 567,
    localId: '999',
    observations: [{ cdDescTxt: 'test description', altCd: 'test description', displayName: 'test display' }],
    organizationParticipations: [{ name: 'test name', typeCd: 'TST' }],
    personParticipations: [
        {
            shortId: 123,
            personCd: '123',
            birthTime: '05-05-1995',
            currSexCd: 'Male',
            firstName: 'Jon',
            lastName: 'Doe',
            typeCd: 'PATSBJ'
        }
    ],
    relevance: 5
};

describe('LaboratoryReportSearchResultListItem', () => {
    describe('Legal Name', () => {
        it('should render the legal name', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            const legalName = 'Jon Doe';

            expect(getByText(legalName)).toBeInTheDocument();
        });
    });

    describe('Date of birth', () => {
        it('should render the date of birth', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.personParticipations[0].birthTime)).toBeInTheDocument();
        });
    });

    describe('Sex', () => {
        it('should render the sex', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.personParticipations[0].currSexCd ?? '')).toBeInTheDocument();
        });
    });

    describe('Patient ID', () => {
        it('should render the patient ID', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.personParticipations[0].shortId?.toString() ?? '')).toBeInTheDocument();
        });
    });

    describe('Document Type', () => {
        it('should render the document type', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText('Lab report')).toBeInTheDocument();
        });
    });

    describe('Date received', () => {
        it('should render the date received', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText('09/09/2021')).toBeInTheDocument();
        });
    });

    describe('Description', () => {
        it('should render the description', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText('test description = test display')).toBeInTheDocument();
        });
    });

    describe('Jurisdiction', () => {
        it('should render the jurisdiction', () => {
            const resolver = jest.fn();

            render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={resolver} />
                </MemoryRouter>
            );

            expect(resolver).toHaveBeenCalledWith('567');
        });
    });

    describe('Associated to', () => {
        it('should render the associated to', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <LaboratoryReportSearchResultListItem result={expectedResult} jurisdictionResolver={jest.fn()} />
                </MemoryRouter>
            );
            expect(getByText('Associated to')).toBeInTheDocument();

            const value = getByText(/associated-investigation-local/);

            expect(value).toHaveTextContent(/associated-investigation-description/);
        });
    });
});
