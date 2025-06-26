import { render, screen } from '@testing-library/react';
import { SystemManagementInfoCard } from './SystemManagementInfoCard';

const links = [
    { text: 'Page Builder', href: '/page/builder' },
    { text: 'Static Page Editor', href: '/editor' }
];
describe('SystemManagementInfoCard', () => {
    test('renders title and links', () => {
        render(<SystemManagementInfoCard id="page" title="Page" filter="" links={links}/>);
        expect(screen.getByText('Page')).toBeInTheDocument();
        expect(screen.getByText('Page Builder')).toBeInTheDocument();
        expect(screen.getByText('Static Page Editor')).toBeInTheDocument();
    });

    test('filters links by text', () => {
        render(<SystemManagementInfoCard id="page" title="Page" filter="builder" links={links}/>);
        expect(screen.getByText('Page Builder')).toBeInTheDocument();
        expect(screen.queryByText('Static Page Editor')).not.toBeInTheDocument();
    });

    test('renders nothing if no links match', () => {
        const {container} = render(
            <SystemManagementInfoCard id="page" title="Page" filter="zzz" links={links}/>
        );
        expect(container).toBeEmptyDOMElement();
    });
});
