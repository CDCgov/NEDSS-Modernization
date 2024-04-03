import { ReactNode } from 'react';
import classnames from 'classnames';
import React from 'react';
import { LinkButton } from './LinkButton';

describe('<LinkButton />', () => {
    it('renders correct href in link that is visible', () => {
        const onChange = () => {};
        cy.mount(
            <LinkButton target="_self" href="/nbs/page-builder/api/v1/pages/create">
                Create new page
            </LinkButton>
        );
        cy.contains('a', 'Create new page').should('have.attr', 'href', '/nbs/page-builder/api/v1/pages/cre');
    });

    it('renders link that is visible', () => {
        const onChange = () => {};
        cy.mount(
            <LinkButton target="_self" href="/nbs/page-builder/api/v1/pages/create">
                Create new page
            </LinkButton>
        );
        cy.contains('a').should('be.visible');
    });
});
