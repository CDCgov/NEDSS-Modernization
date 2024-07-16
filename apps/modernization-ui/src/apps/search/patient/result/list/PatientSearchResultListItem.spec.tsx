import { render } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { PatientSearchResultListItem } from './PatientSearchResultListItem';
import { PatientSearchResult } from 'generated/graphql/schema';

const expectedResult: PatientSearchResult = {
    legalName: {
        first: 'John',
        last: 'Doe',
        middle: null,
        suffix: null
    },
    birthday: '1990-01-01',
    gender: 'Male',
    age: 34,
    patient: 12345678,
    shortId: 12345,
    addresses: [{ use: '', address: '123 Fake St', city: 'Faketown', state: 'FS', zipcode: '12345' }],
    phones: ['555-555-5555'],
    emails: ['john.doe@example.com'],
    names: [{ first: 'Jane', last: 'Doe' }],
    status: '',
    identification: [
        { type: 'Social Security', value: '123-45-6789' },
        { type: "Driver's license number", value: '123' }
    ]
};

describe('PatientSearchResultListItem', () => {
    describe('Legal Name', () => {
        it('should render the legal name', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            const legalName = 'Doe, John';

            expect(getByText(legalName)).toBeInTheDocument();
        });
    });

    describe('Date of birth', () => {
        it('should render the date of birth', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.birthday)).toBeInTheDocument();
        });
        it('should render "No Data" when no date of birth is available', () => {
            const result = { ...expectedResult };
            delete result.birthday;
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });
    describe('Sex', () => {
        it('should render the sex', () => {
            expectedResult.gender = 'Male';
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.gender)).toBeInTheDocument();
        });

        it('should render "No Data" when no sex is available', () => {
            const result = { ...expectedResult };
            delete result.gender;
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });

    describe('Patient ID', () => {
        it('should render the patient ID', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.shortId.toString())).toBeInTheDocument();
        });
    });

    describe('Address', () => {
        it('should render the address', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            const address = '123 Fake St';
            const city = 'Faketown, FS, 12345';

            expect(getByText(address)).toBeInTheDocument();
            expect(getByText(city)).toBeInTheDocument();
        });
    });

    describe('Phone', () => {
        it('should render the phone number', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.phones[0])).toBeInTheDocument();
        });

        it('should render "No Data" when no phone number is available', () => {
            const result = { ...expectedResult };
            result.phones = [];
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });
    describe('Email', () => {
        it('should render the email address', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.emails[0])).toBeInTheDocument();
        });

        it('should render "No Data" when no email address is available', () => {
            const result = { ...expectedResult };
            result.emails = [];
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });

    describe('Other Names', () => {
        it('should render the other names', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );

            const name = 'Jane Doe';

            expect(getByText(name)).toBeInTheDocument();
        });

        it('should render "No Data" when no other names are available', () => {
            const result = { ...expectedResult };
            result.names = [];
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });

    describe('Drivers license number', () => {
        it('should render the drivers license number', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.identification[1].value)).toBeInTheDocument();
        });

        it('should render "No Data" when no drivers license number is available', () => {
            const result = { ...expectedResult };
            result.identification = result.identification.filter((i) => i.type !== "Driver's license number");
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });

    describe('Social Security number', () => {
        it('should render the social security number', () => {
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={expectedResult} />
                </MemoryRouter>
            );
            expect(getByText(expectedResult.identification[0].value)).toBeInTheDocument();
        });

        it('should render "No Data" when no social security number is available', () => {
            const result = { ...expectedResult };
            result.identification = result.identification.filter((i) => i.type !== 'Social Security');
            const { getByText } = render(
                <MemoryRouter>
                    <PatientSearchResultListItem result={result} />
                </MemoryRouter>
            );
            expect(getByText('No Data')).toBeInTheDocument();
        });
    });
});
