import { render, screen } from '@testing-library/react';
import { DetailValue, DetailView } from './DetailView';
import { axe } from 'jest-axe';

describe('DetailView', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <DetailView>
                <DetailValue label="First name">Hari</DetailValue>
                <DetailValue label="Last name">Puttar</DetailValue>
                <DetailValue label="Age">11</DetailValue>
                <DetailValue label="Email" />
            </DetailView>
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display a labeled value', () => {
        render(
            <DetailView>
                <DetailValue label="Label">Value</DetailValue>
            </DetailView>
        );

        expect(screen.getByRole('definition', { name: 'Label' })).toHaveTextContent('Value');
    });

    it('should display the no data placeholder when no value is given', () => {
        render(
            <DetailView>
                <DetailValue label="Label" />
            </DetailView>
        );

        expect(screen.getByRole('definition', { name: 'Label' })).toHaveTextContent('---');
    });
});
