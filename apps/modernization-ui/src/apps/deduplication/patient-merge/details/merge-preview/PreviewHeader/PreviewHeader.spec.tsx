import { render, screen } from '@testing-library/react';
import { PreviewHeader } from './PreviewHeader';

describe('PreviewHeader', () => {
    it('renders the patient full name as a heading', () => {
        render(<PreviewHeader />);
        const heading = screen.getByRole('heading');
        expect(heading).toBeInTheDocument();
        expect(heading.textContent).not.toEqual('---'); // should not be fallback
    });

    it('displays sex, DOB, and patient ID', () => {
        render(<PreviewHeader />);

        expect(screen.getByText(/male/i)).toBeInTheDocument(); // sex
        expect(screen.getByText(/12\/04\/1975/i)).toBeInTheDocument(); // dob
        expect(screen.getByText(/Patient ID: 98765/i)).toBeInTheDocument(); // patientId
    });

    it('does not crash with missing deceasedOn', () => {
        render(<PreviewHeader />);
        expect(screen.queryByText(/deceased/i)).not.toBeInTheDocument();
    });

    it('does not render actions section when null is passed', () => {
        const { container } = render(<PreviewHeader />);
        const actionsSection = container.querySelector('.actions');
        expect(actionsSection?.childNodes.length).toBe(0);
    });
});
