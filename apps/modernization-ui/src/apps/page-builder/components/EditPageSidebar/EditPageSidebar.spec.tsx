import { fireEvent, render, screen } from '@testing-library/react';
import { EditPageSidebar } from './EditPageSidebar';

const modal1 = { current: null, isShowing: false };
const modal2 = { current: null, isShowing: false };
describe('EditPageSidebar', () => {
    it('should render successfully', () => {
        const { baseElement } = render(<EditPageSidebar addSectionModalRef={modal1} reorderModalRef={modal2} />);
        expect(baseElement).toBeTruthy();
    });

    it('should have an item with the text "Add section"', () => {
        const { getByText } = render(<EditPageSidebar addSectionModalRef={modal1} reorderModalRef={modal2} />);
        expect(getByText('Add section')).toBeTruthy();
    });

    describe('when add section button is clicked', () => {
        it('should open the modal', () => {
            const { getByText } = render(<EditPageSidebar addSectionModalRef={modal1} reorderModalRef={modal2} />);
            const addSectionButton = getByText('Add section');
            fireEvent.click(addSectionButton);

            const modalHeading = screen.findAllByText('Manage Sections');
            expect(modalHeading).toBeTruthy();
        });
    });
});
