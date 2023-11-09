import { fireEvent, render } from '@testing-library/react';
import { NavigationEntry } from './NavigationEntry';
import { BrowserRouter } from 'react-router-dom';
import { SideNavigation } from './SideNavigation';

describe('SideNavigation', () => {
    it('should render a title', async () => {
        const { findByText } = render(<SideNavigation title="test title" active={1} entries={[]} />);
        const entry = await findByText('test title');
        expect(entry).toBeInTheDocument();
    });

    it('should render nav entry', async () => {
        const { findByText } = render(
            <SideNavigation
                title="test title"
                active={1}
                entries={[<NavigationEntry key={1} label="test label" href="/some-href" />]}
            />
        );
        const entry = await findByText('test label');
        expect(entry).toBeInTheDocument();
        expect(entry).toHaveAttribute('href', '/some-href');
        expect(entry.parentElement).toHaveClass('active');
    });

    it('should set original active', async () => {
        const { findByText } = render(
            <SideNavigation
                title="test title"
                active={1}
                entries={[
                    <NavigationEntry key={1} label="test label" href="/some-href" />,
                    <NavigationEntry key={2} label="another label" href="/some-other-href" />
                ]}
            />
        );
        const active = await findByText('test label');
        expect(active.parentElement).toHaveClass('active');
        const other = await findByText('another label');
        expect(other.parentElement).not.toHaveClass('active');
    });

    it('should update active on click', async () => {
        const { findByText } = render(
            <SideNavigation
                title="test title"
                active={1}
                entries={[
                    <NavigationEntry key={1} label="test label" href="/some-href" />,
                    <NavigationEntry key={2} label="another label" href="/some-other-href" />
                ]}
            />
        );
        const first = await findByText('test label');
        expect(first.parentElement).toHaveClass('active');
        const second = await findByText('another label');
        expect(second.parentElement).not.toHaveClass('active');

        fireEvent.click(second);

        expect(first.parentElement).not.toHaveClass('active');
        expect(second.parentElement).toHaveClass('active');
    });
});
