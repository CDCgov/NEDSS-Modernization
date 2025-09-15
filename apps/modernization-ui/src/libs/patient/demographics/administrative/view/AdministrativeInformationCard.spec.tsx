import { render, screen } from '@testing-library/react';
import { AdministrativeInformationCard } from './AdministrativeInformationCard';

describe('when displaying administrative comments for a patient', () => {
    it('should display title correctly', () => {
        const data = { comment: 'random comments' };
        render(<AdministrativeInformationCard data={data} collapsible id={'test'} title={'Administrative'} />);

        expect(screen.getByText('Administrative')).toBeInTheDocument();
    });

    it('should display the data correctly', () => {
        const data = { asOf: '2025-01-25T00:00:00', comment: 'random comments' };
        render(<AdministrativeInformationCard data={data} collapsible id={'test'} title={'test'} />);

        expect(screen.getByText('random comments')).toBeInTheDocument();
        expect(screen.getByText('As of 01/25/2025')).toBeInTheDocument();
    });

    it('should display No comments available when no comments are present', () => {
        const data = { comment: '' };
        render(<AdministrativeInformationCard data={data} collapsible id={'test'} title={'test'} />);

        expect(screen.getByText('No comments available.')).toBeInTheDocument();
    });
});
