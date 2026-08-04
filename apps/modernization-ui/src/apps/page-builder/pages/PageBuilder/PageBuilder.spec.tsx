import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router';

import { PageBuilder } from './PageBuilder';

describe('when rendered', () => {
    it('should display side nav', async () => {
        const { container } = render(
            <BrowserRouter>
                <PageBuilder nav={true}>Child</PageBuilder>
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
