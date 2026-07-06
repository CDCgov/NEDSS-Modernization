import { render, screen } from '@testing-library/react';
import { VerticalField } from './VerticalField.tsx';

describe('VeritcalField', () => {
    it('should display the label', async () => {
        render(
            <VerticalField label="title goes here" htmlFor="1">
                Value goes here
            </VerticalField>
        );

        expect(await screen.findByText('title goes here')).toBeVisible();
    });

    it('should not display the label when required but no label provided', async () => {
        render(
            <VerticalField label="" htmlFor="1" required>
                Value goes here
            </VerticalField>
        );

        expect(await screen.queryByText('title goes here')).toBeNull();
    });
});
