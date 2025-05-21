import { render, screen } from '@testing-library/react';
import { PatientSummaryCard } from './PatientSummaryCard';

describe('PatientSummaryCard', () => {
    const baseProps = {
        firstName: 'Luigi',
        lastName: 'Mario',
        dob: '01/01/1980',
        age: 45,
        gender: 'Male',
        patientId: '12345'
    };

    it('renders all patient details with separators by default', () => {
        render(<PatientSummaryCard {...baseProps} />);
        expect(screen.getByText('Mario, Luigi')).toBeInTheDocument();
        expect(screen.getByText('Male')).toBeInTheDocument();
        expect(screen.getByText('01/01/1980 (45 years)')).toBeInTheDocument();
        expect(screen.getByText('Patient ID: 12345')).toBeInTheDocument();

        const separators = screen.getAllByText('|');
        expect(separators.length).toBe(3);
    });

    it('does not render separators when separator prop is false', () => {
        render(<PatientSummaryCard {...baseProps} separator={false} />);
        expect(screen.queryByText('|')).not.toBeInTheDocument();
    });

    it('handles missing gender gracefully', () => {
        const { gender, ...partialProps } = baseProps;
        render(<PatientSummaryCard {...partialProps} />);
        expect(screen.queryByText('Male')).not.toBeInTheDocument();
    });

    it('works when age is a string', () => {
        render(<PatientSummaryCard {...baseProps} age="45" />);
        expect(screen.getByText('01/01/1980 (45 years)')).toBeInTheDocument();
    });
});
