import { render } from '@testing-library/react';
import { AlertProvider } from '../../../../alert';
import { ConditionalCase } from './ConditionalCase';

describe('General information component tests', () => {
    it('should display condition form', () => {
        const { getByTestId } = render(
            <AlertProvider>
                <ConditionalCase />
            </AlertProvider>
        );
        expect(getByTestId('header-title-condition-case').innerHTML).toContain('Jurisdictional Questions');
    });
});
