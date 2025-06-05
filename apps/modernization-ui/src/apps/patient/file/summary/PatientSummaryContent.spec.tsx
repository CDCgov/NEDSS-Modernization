import React from 'react';
import { render } from '@testing-library/react';
import { PatientSummaryContent } from './PatientSummaryContent';
import { PatientDemographicsSummary } from 'generated';

describe('PatientSummaryContent', () => {
    const baseSummary: PatientDemographicsSummary = {
        address: { address: '123 Main St' },
        phone: { number: '555-1234' },
        email: 'test@example.com',
        identifications: [{ value: 'ID123' }],
        races: ['Race1', 'Race2'],
        ethnicity: 'Ethnicity1'
    };

    it('renders all summary fields with data', () => {
        const { getByText } = render(<PatientSummaryContent summary={baseSummary} />);
        expect(getByText('Patient summary')).toBeInTheDocument();
        expect(getByText('ADDRESS')).toBeInTheDocument();
        expect(getByText('123 Main St')).toBeInTheDocument();
        expect(getByText('PHONE')).toBeInTheDocument();
        expect(getByText('555-1234')).toBeInTheDocument();
        expect(getByText('EMAIL')).toBeInTheDocument();
        expect(getByText('test@example.com')).toBeInTheDocument();
        expect(getByText('IDENTIFICATION')).toBeInTheDocument();
        expect(getByText('ID123')).toBeInTheDocument();
        expect(getByText('RACE')).toBeInTheDocument();
        expect(getByText('Race1, Race2')).toBeInTheDocument();
        expect(getByText('ETHNICITY')).toBeInTheDocument();
        expect(getByText('Ethnicity1')).toBeInTheDocument();
    });

    it('renders dashes for missing identifications', () => {
        const { container } = render(<PatientSummaryContent summary={{ ...baseSummary, identifications: [] }} />);
        expect(container.querySelector('.groupID .itemContent')).toHaveTextContent('---');
    });

    it('renders only available races', () => {
        const { getByText } = render(<PatientSummaryContent summary={{ ...baseSummary, races: ['Race1'] }} />);
        expect(getByText('Race1')).toBeInTheDocument();
    });

    it('renders only available ethnicity', () => {
        const { getByText } = render(<PatientSummaryContent summary={{ ...baseSummary, ethnicity: 'Ethnicity1' }} />);
        expect(getByText('Ethnicity1')).toBeInTheDocument();
    });
});
