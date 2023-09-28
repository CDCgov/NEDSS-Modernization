import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageTabs } from './EditPageTabs';
import { PageTab } from 'apps/page-builder/generated';

const props = {
    tabs: [{ name: 'first tab' }, { name: 'second tab' }] as PageTab[],
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
        const firstTab = screen.getAllByText('first tab');
        const secondTab = screen.getAllByText('second tab');

        expect(firstTab[0].parentElement).toHaveClass('active');
        expect(secondTab[0].parentElement).not.toHaveClass('active');
    });

    it('should have the Manage Tabs button', () => {
        render(<EditPageTabs {...props} />);
        const manageButton = screen.getByTestId('openManageTabs');

        expect(manageButton).toBeInTheDocument();
        expect(manageButton).toHaveTextContent('Manage Tabs');
    });

    describe('when a tab is clicked', () => {
        it('is set to active', () => {
            render(<EditPageTabs {...props} />);
            const secondTab = screen.getAllByText('second tab');
            fireEvent.click(secondTab[0]);

            expect(props.setActive).toHaveBeenCalledWith(1);
        });
    });
});
