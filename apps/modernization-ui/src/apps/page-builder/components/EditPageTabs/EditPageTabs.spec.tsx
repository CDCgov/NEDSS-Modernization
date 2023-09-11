import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageTabs } from './EditPageTabs';

const props = {
    tabs: [{ name: 'first tab' }, { name: 'second tab' }],
    active: 0,
    setActive: jest.fn(),
    onAddSuccess: jest.fn()
};

describe('EditPageTabs', () => {
    it('should render the EditPageTabs component', () => {
        render(<EditPageTabs {...props} />);
        expect(screen.getByText('first tab')).toBeInTheDocument();
        expect(screen.getByText('second tab')).toBeInTheDocument();
    });

    it('it should render component with the active tab', () => {
        render(<EditPageTabs {...props} />);
        const firstTab = screen.getByText('first tab').parentElement;
        const secondTab = screen.getByText('second tab').parentElement;
        expect(firstTab).toHaveClass('active');
        expect(secondTab).not.toHaveClass('active');
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
            const secondTab = screen.getByText('second tab');
            fireEvent.click(secondTab);

            expect(props.setActive).toHaveBeenCalledWith(1);
        });
    });
});
