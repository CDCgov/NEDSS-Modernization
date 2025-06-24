import { render, screen } from '@testing-library/react';
import { AdministrativeInformationCard } from './AdministrativeInformationCard';
import { AdministrativeInformation } from './AdministrativeInformation';
import { internalizeDate, today } from 'date';

describe('when displaying administrative comments for a patient', () => {
    it('should display title correctly', () => {
        const data: AdministrativeInformation = { asOf: today(), comment: 'random comments' };
        render(<AdministrativeInformationCard data={data} collapsible />);

        expect(screen.getByText('Administrative comments')).toBeInTheDocument();
    });

    it('should display the data correctly', () => {
        const date = today();
        const data: AdministrativeInformation = { asOf: today(), comment: 'random comments' };
        render(<AdministrativeInformationCard data={data} collapsible />);

        expect(screen.getByText('random comments')).toBeInTheDocument();
        expect(screen.getByText(`As of ${internalizeDate(date)}`)).toBeInTheDocument();
    });
});
