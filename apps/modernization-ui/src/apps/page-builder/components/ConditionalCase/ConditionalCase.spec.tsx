import { render } from '@testing-library/react';
import { ConditionalCase } from './ConditionalCase';
import { AlertProvider } from '../../../../alert';

describe('General information component tests', () => {
    it('should display condition form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <ConditionalCase />
            </AlertProvider>
        );
        expect(getByTestId('header-title').innerHTML).toContain('Jurisdictional Questions');
    });
});
