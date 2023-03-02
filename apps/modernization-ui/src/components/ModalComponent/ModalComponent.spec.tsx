// eslint-disable-next-line no-redeclare
import { render, screen } from '@testing-library/react';
import { ModalComponent } from './ModalComponent';

describe('Modal component', () => {
    it('Should render modal component', () => {
        render(<ModalComponent modalHeading="test heading" modalBody={<div>Hello world</div>} />);
        expect(screen.getByText('test heading')).toBeInTheDocument();
        expect(screen.getByText('Hello world')).toBeInTheDocument();
    });
});
