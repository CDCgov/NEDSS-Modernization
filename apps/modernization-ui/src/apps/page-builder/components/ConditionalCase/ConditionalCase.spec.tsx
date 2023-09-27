import { render } from '@testing-library/react';
import { AlertProvider } from '../../../../alert';
import { ConditionalCase } from './ConditionalCase';
import { BrowserRouter } from 'react-router-dom';

describe('General information component tests', () => {
    it('should display condition form', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <ConditionalCase />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByTestId('header-title-condition-case').innerHTML).toContain('Jurisdictional Questions');
    });
});
