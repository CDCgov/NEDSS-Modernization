import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageTabs } from './EditPageTabs';
import { PagesTab } from 'apps/page-builder/generated';

const props = {
    tabs: [{ name: 'first tab', visible: true }, { name: 'second tab', visible: true }] as PagesTab[],
    active: 0,
    setActive: jest.fn(),
    onAddSuccess: jest.fn()
};

describe('EditPageTabs', () => {
    it('should render', () => {
        render(<EditPageTabs {...props} />);
        expect(screen.getAllByText('first tab')[0]).toBeInTheDocument();
        expect(screen.getAllByText('second tab')[0]).toBeInTheDocument();
    });

    it('it should render component with the active tab', () => {
        render(<EditPageTabs {...props} />);
        const tabs = screen.getByTestId('edit-page-tabs');
        expect(tabs.firstChild).toHaveClass('active');
    });

    it('should have the Manage tabs button', () => {
        render(<EditPageTabs {...props} />);
        const manageButton = screen.getByTestId('openManageTabs');

        expect(manageButton).toBeInTheDocument();
        expect(manageButton).toHaveTextContent('Manage tabs');
    });

    describe('when a tab is clicked', () => {
        it('is set to active', () => {
            render(<EditPageTabs {...props} />);
            const secondTab = screen.getAllByText('second tab');
            fireEvent.click(secondTab[0]);

            expect(secondTab[0].parentElement).not.toHaveClass('active');
        });
    });
});
