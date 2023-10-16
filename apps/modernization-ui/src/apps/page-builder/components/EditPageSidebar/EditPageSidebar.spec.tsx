import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageSidebar } from './EditPageSidebar';

const modal = { current: { modalId: 'modalid', modalIsOpen: false, toggleModal: jest.fn() }, isShowing: false };

describe('EditPageSidebar', () => {
    it('should render successfully', () => {
        const { baseElement } = render(<EditPageSidebar modalRef={modal} />);
        expect(baseElement).toBeTruthy();
    });

    it('should have an item with the text "Add section"', () => {
        const { getByText } = render(<EditPageSidebar modalRef={modal} />);
        expect(getByText('Add section')).toBeTruthy();
    });

    describe('when add section button is clicked', () => {
        it('should open the modal', () => {
            const { getByText } = render(<EditPageSidebar modalRef={modal} />);
            const addSectionButton = getByText('Add section');
            fireEvent.click(addSectionButton);

            const modalHeading = screen.findAllByText('Manage Sections');
            expect(modalHeading).toBeTruthy();
        });
    });
});
