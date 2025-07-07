import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { PatientSearchResultListItem } from './PatientSearchResultListItem';
import { PatientSearchResult } from 'generated/graphql/schema';

const mockNow = jest.fn();

jest.mock('design-system/date/clock', () => ({
    now: () => mockNow()
}));

describe('PatientSearchResultListItem', () => {
    beforeEach(() => {
        mockNow.mockReturnValue(new Date('2025-01-25T00:00:00'));
    });

    it('should render the patient name', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            legalName: {
                first: 'Legal',
                last: 'Name'
            },
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );

        expect(getByText('Name, Legal')).toBeInTheDocument();
    });

    it('should render the date of birth', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            birthday: '1995-07-05',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('07/05/1995 (29 years)', { trim: true })).toBeInTheDocument();
    });

    it('should render the sex', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            gender: 'gender-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };
        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('gender-value')).toBeInTheDocument();
    });

    it('should render the patient ID', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('653')).toBeInTheDocument();
    });

    it('should render the address', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [
                {
                    address: '123 Fake St',
                    address2: null,
                    city: 'Faketown',
                    state: 'FS',
                    use: 'HOME',
                    zipcode: '12345'
                }
            ],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );

        expect(getByText(/123 Fake St/)).toBeInTheDocument();
        expect(getByText(/Faketown, FS 12345/)).toBeInTheDocument();
    });

    it('should render the phone number', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: ['phone-number-value'],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: [
                {
                    number: 'phone-number-value',
                    type: '',
                    use: ''
                }
            ]
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('phone-number-value')).toBeInTheDocument();
    });

    it('should render the email address', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: ['email-address-value'],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('email-address-value')).toBeInTheDocument();
    });

    it('should render the other names', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [{ first: 'Jane', last: 'Doe' }],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );

        expect(getByText('Doe, Jane')).toBeInTheDocument();
    });

    it('should render no data in all places when there is no data', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { queryAllByText, getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );

        expect(getByText('No Data')).toBeInTheDocument();
        expect(queryAllByText('---')).toHaveLength(7);
    });

    it('should render each identification', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [
                { type: 'identification-one-type', value: 'identification-one-value' },
                { type: 'identification-two-type', value: 'identification-two-value' }
            ],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('identification-one-type')).toBeInTheDocument();
        expect(getByText('identification-one-value')).toBeInTheDocument();

        expect(getByText('identification-two-type')).toBeInTheDocument();
        expect(getByText('identification-two-value')).toBeInTheDocument();
    });

    it('should render "ID Types" label when there are no identifications', () => {
        const patient: PatientSearchResult = {
            patient: 829,
            shortId: 653,
            status: 'status-value',
            addresses: [],
            phones: [],
            emails: [],
            names: [],
            identification: [],
            detailedPhones: []
        };

        const { getByText } = render(
            <MemoryRouter>
                <PatientSearchResultListItem result={patient} />
            </MemoryRouter>
        );
        expect(getByText('ID Types')).toBeInTheDocument();
    });
});
