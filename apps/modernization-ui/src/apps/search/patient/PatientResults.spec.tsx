import { render, fireEvent } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import { PatientResults } from './PatientResults';

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

        expect(getByText('John J Doe')).toBeInTheDocument();

        const pageNumberButton = getByText('1');
        fireEvent.click(pageNumberButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage);
    });

    it('renders patient results and handles next button pagination', () => {
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

        expect(getByText('Next')).toBeInTheDocument();

        const nextPageButton = getByText('Next');
        fireEvent.click(nextPageButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage + 1);
    });

    it('renders patient results and handles previous button pagination', () => {
        const currentPage = 2;
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

        expect(getByText('Previous')).toBeInTheDocument();
        const prevButton = getByText('Previous');
        fireEvent.click(prevButton);
        expect(mockHandlePagination).toHaveBeenCalledWith(currentPage - 1);
    });

    it('navigates to patient profile on result click', async () => {
        const { getByText } = render(
            <MemoryRouter initialEntries={['/advanced-search/person']}>
                <Routes>
                    <Route
                        path="/advanced-search/person"
                        element={
                            <PatientResults
                                data={mockData}
                                totalResults={totalResults}
                                handlePagination={mockHandlePagination}
                                currentPage={currentPage}
                            />
                        }
                    />
                    <Route path="/patient-profile/:id" element={<div>Patient Profile Page</div>} />
                </Routes>
            </MemoryRouter>
        );

        const firstPatientResult = getByText('John J Doe');
        fireEvent.click(firstPatientResult);
        expect(getByText('Patient Profile Page')).toBeInTheDocument();
    });
});
