import { render } from '@testing-library/react';

import { PageProvider } from 'page';
import { MemoryRouter } from 'react-router-dom';
import { QuestionSearchTable } from './QuestionSearchTable';

describe('question search table tests', () => {
    it('should render No Data when empty', () => {
        const { getByText } = render(
            <MemoryRouter>
                <PageProvider>
                    <QuestionSearchTable questions={[]} onCreateNew={jest.fn()} />
                </PageProvider>
            </MemoryRouter>
        );
        expect(getByText('No Data')).toBeInTheDocument();
    });

    it('should render headers when empty', () => {
        const { getAllByRole } = render(
            <MemoryRouter>
                <PageProvider>
                    <QuestionSearchTable questions={[]} onCreateNew={jest.fn()} />
                </PageProvider>
            </MemoryRouter>
        );

        const headers = getAllByRole('columnheader');
        expect(headers[1]).toHaveTextContent('Status');
        expect(headers[2]).toHaveTextContent('Type');
        expect(headers[3]).toHaveTextContent('Unique ID');
        expect(headers[4]).toHaveTextContent('Label');
        expect(headers[5]).toHaveTextContent('Subgroup');
    });

    it('should display proper values for a question', async () => {
        const { findAllByRole } = render(
            <MemoryRouter>
                <PageProvider>
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
                </PageProvider>
            </MemoryRouter>
        );

        const data = await findAllByRole('cell');
        expect(data[1]).toHaveTextContent('Active');
        expect(data[2]).toHaveTextContent('PHIN');
        expect(data[3]).toHaveTextContent('unique id');
        expect(data[4]).toHaveTextContent('label');
        expect(data[5]).toHaveTextContent('subgroup name');
    });
});
