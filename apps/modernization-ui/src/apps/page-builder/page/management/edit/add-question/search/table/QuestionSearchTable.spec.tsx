import { render } from '@testing-library/react';

import { PaginationProvider } from 'pagination';
import { MemoryRouter } from 'react-router';
import { QuestionSearchTable } from './QuestionSearchTable';

describe('question search table tests', () => {
    it('should render No Data when empty', () => {
        const { getByText } = render(
            <MemoryRouter>
                <PaginationProvider>
                    <QuestionSearchTable questions={[]} onCreateNew={jest.fn()} />
                </PaginationProvider>
            </MemoryRouter>
        );
        expect(getByText('No Data')).toBeInTheDocument();
    });

    it('should render headers when empty', () => {
        const { getAllByRole } = render(
            <MemoryRouter>
                <PaginationProvider>
                    <QuestionSearchTable questions={[]} onCreateNew={jest.fn()} />
                </PaginationProvider>
            </MemoryRouter>
        );

        const headers = getAllByRole('columnheader');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Unique ID');
        expect(headers[3]).toHaveTextContent('Label');
        expect(headers[4]).toHaveTextContent('Subgroup');
    });

    it('should display proper values for a question', async () => {
        const { findAllByRole } = render(
            <MemoryRouter>
                <PaginationProvider>
                    <QuestionSearchTable
                        questions={[
                            {
                                id: 1,
                                uniqueId: 'unique id',
                                label: 'label',
                                subgroupName: 'subgroup name',
                                type: 'PHIN',
                                status: 'Active'
                            }
                        ]}
                        onCreateNew={jest.fn()}
                    />
                </PaginationProvider>
            </MemoryRouter>
        );

        const data = await findAllByRole('cell');
        expect(data[1]).toHaveTextContent('PHIN');
        expect(data[2]).toHaveTextContent('unique id');
        expect(data[3]).toHaveTextContent('label');
        expect(data[4]).toHaveTextContent('subgroup name');
    });
});
