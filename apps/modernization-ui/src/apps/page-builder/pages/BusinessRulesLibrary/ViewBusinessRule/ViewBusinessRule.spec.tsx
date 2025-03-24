import { screen, render } from '@testing-library/react';
import { ViewBusinessRule } from './ViewBusinessRule';
import { BrowserRouter } from 'react-router';
import { Rule } from 'apps/page-builder/generated';
import { PageProvider } from 'page';

describe('when ViewBusinessRule rendered', () => {
    it('should display heading', () => {
        const { container } = render(
            <BrowserRouter>
                <PageProvider>
                    <ViewBusinessRule />
                </PageProvider>
            </BrowserRouter>
        );
        const heading = container.querySelector('h2');
        expect(heading).toHaveTextContent('View business rule');
    });

    it('should display table headers', async () => {
        const { findAllByRole } = render(
            <BrowserRouter>
                <PageProvider>
                    <ViewBusinessRule />
                </PageProvider>
            </BrowserRouter>
        );

        const tableHeads = await findAllByRole('columnheader');
        expect(tableHeads).toHaveLength(2);
    });

    it('should rule fields', async () => {
        const { findAllByRole } = render(
            <BrowserRouter>
                <PageProvider>
                    <ViewBusinessRule />
                </PageProvider>
            </BrowserRouter>
        );

        const tableData = await findAllByRole('cell');
        expect(tableData).toHaveLength(14);
    });
});
