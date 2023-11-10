import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { SaveTemplates } from './SaveTemplate';
import { AlertProvider } from 'alert';

describe('When SaveTemplates component loads', () => {
    it('Save button should be disabled', () => {
        const { container } = render(
            <AlertProvider>
                <BrowserRouter>
                    <SaveTemplates />
                </BrowserRouter>
            </AlertProvider>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});
