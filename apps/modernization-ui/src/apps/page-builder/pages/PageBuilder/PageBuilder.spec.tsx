import { BrowserRouter } from 'react-router-dom';
import { PageBuilder } from './PageBuilder';
import { render } from '@testing-library/react';

describe('when rendered', () => {
    it('should display side nav', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageBuilder nav>Child</PageBuilder>
            </BrowserRouter>
        );

        const sideNav = container.getElementsByClassName('side-nav')[0];
        expect(sideNav).toBeInTheDocument();
    });

    it('should not side nav', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageBuilder>Child</PageBuilder>
            </BrowserRouter>
        );

        const sideNav = container.getElementsByClassName('page-builder-side-nav')[0];
        expect(sideNav).toBeUndefined();
    });
});
