import { render, screen } from '@testing-library/react';
import { SystemManagementInfoCard } from './SystemManagementInfoCard';
import { MemoryRouter } from 'react-router';
import React from 'react';

const links = [
    { text: 'Page Builder', href: '/page/builder' },
    { text: 'Static Page Editor', href: '/editor' }
];

const Fixture = (props: React.ComponentProps<typeof SystemManagementInfoCard>) => {
    return (
        <MemoryRouter>
            <SystemManagementInfoCard {...props} />
        </MemoryRouter>
    );
};

describe('SystemManagementInfoCard', () => {
    test('renders title and links', () => {
        render(<Fixture id="page" title="Page" filter="" links={links} />);
        expect(screen.getByText('Page')).toBeInTheDocument();
        expect(screen.getByText('Page Builder')).toBeInTheDocument();
        expect(screen.getByText('Static Page Editor')).toBeInTheDocument();
    });

    test('filters links by text', () => {
        render(<Fixture id="page" title="Page" filter="builder" links={links} />);
        expect(screen.getByText('Page Builder')).toBeInTheDocument();
        expect(screen.queryByText('Static Page Editor')).not.toBeInTheDocument();
    });

    test('renders nothing if no links match', () => {
        const { container } = render(
            <Fixture id="page" title="Page" filter="zzz" links={links} />
        );
        expect(container).toBeEmptyDOMElement();
    });
});
