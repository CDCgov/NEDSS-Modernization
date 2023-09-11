import { render, screen } from '@testing-library/react';
import { EditPageTabs } from './EditPageTabs';

const props = {
    tabs: [{ name: 'first tab' }],
    active: 1,
    setActive: jest.fn(),
    onAddSuccess: jest.fn()
};

describe('EditPageTabs', () => {
    it('should render the EditPageTabs component', () => {
        render(<EditPageTabs {...props} />);
        expect(screen.getByText('first tab')).toBeInTheDocument();
    });
});
