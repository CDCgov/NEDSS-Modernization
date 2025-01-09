import { render } from '@testing-library/react';
import '@testing-library/jest-dom';
import { AddPatientHeaderContent } from './AddPatientHeaderContent';

describe('AddPatientHeaderContent', () => {
    it('should renders the title correctly', () => {
        const title = 'Test Title';
        const { getByRole } = render(<AddPatientHeaderContent title={title}>Test Children</AddPatientHeaderContent>);

        expect(getByRole('heading', { level: 1 })).toHaveTextContent(title);
    });

    it('should renders children correctly', () => {
        const childrenText = () => 'Test Children';
        const { getByText } = render(
            <AddPatientHeaderContent title="Test Title">{childrenText()}</AddPatientHeaderContent>
        );

        expect(getByText(childrenText())).toBeInTheDocument();
    });

    it('should applies the correct header class', () => {
        const { getByRole } = render(
            <AddPatientHeaderContent title="Test Title">Test Children</AddPatientHeaderContent>
        );

        const headerElement = getByRole('banner');
        expect(headerElement).toHaveClass('header');
    });
});
