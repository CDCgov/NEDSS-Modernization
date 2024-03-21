import { render, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { PatientResults } from './PatientResults';

// Mock data for testing
const mockData = [
    {
        patient: 10000001,
        birthday: '1990-01-01',
        age: 34,
        gender: 'Male',
        status: 'ACTIVE',
        shortId: 63000,
        legalName: {
            first: 'John',
            middle: 'J',
            last: 'Doe',
            suffix: null
        },
        names: [
            {
                first: 'John',
                middle: 'J',
                last: 'Doe',
                suffix: null
            }
        ],
        identification: [
            {
                type: 'Account number',
                value: '3453453533'
            }
        ],
        addresses: [
            {
                use: 'Home',
                address: '123 Main St.',
                address2: null,
                city: 'Atlanta',
                state: 'GA',
                zipcode: '30024'
            }
        ],
        phones: ['232-322-2222', '456-232-3222'],
        emails: ['fdsfs@dsds.com']
    }
];
const totalResults = 50;
const currentPage = 1;

const mockHandlePagination = jest.fn();

describe('PatientResults', () => {
    it('renders patient results and handles pagination', () => {
        const { getByText, queryByText } = render(
            <MemoryRouter>
                <PatientResults
                    data={mockData}
                    totalResults={totalResults}
                    handlePagination={mockHandlePagination}
                    currentPage={currentPage}
                />
            </MemoryRouter>
        );

        expect(getByText('John J Doe')).toBeInTheDocument();

        expect(getByText('Next')).toBeInTheDocument();

        const nextPageButton = getByText('Next');
        fireEvent.click(nextPageButton);

        const prevButton = queryByText('Previous');
        if (prevButton) {
            fireEvent.click(prevButton);
            expect(mockHandlePagination).toHaveBeenCalledWith(currentPage - 1);
        }

        const pageNumberButton = getByText('2');
        fireEvent.click(pageNumberButton);

        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage + 1);
    });

    it('navigates to patient profile on result click', () => {
        const { getByText } = render(
            <MemoryRouter>
                <PatientResults
                    data={mockData}
                    totalResults={totalResults}
                    handlePagination={mockHandlePagination}
                    currentPage={currentPage}
                />
            </MemoryRouter>
        );

        const firstPatientResult = getByText('John J Doe');
        fireEvent.click(firstPatientResult);
    });
});
