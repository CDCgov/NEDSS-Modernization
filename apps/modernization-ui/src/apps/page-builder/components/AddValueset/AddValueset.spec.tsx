import { render } from '@testing-library/react';
import { AddValueset } from './AddValueset';
import { AlertProvider } from 'alert';

describe('General information component tests', () => {
    it('should display Add Valueset form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <AddValueset />
            </AlertProvider>
        );
        expect(getByTestId('error-text').innerHTML).toBe('Value set code Not Valid');
    });
});
