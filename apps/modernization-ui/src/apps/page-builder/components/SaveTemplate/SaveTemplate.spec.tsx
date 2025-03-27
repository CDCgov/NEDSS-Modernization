import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router';
import { SaveTemplates } from './SaveTemplate';

describe('When SaveTemplates component loads', () => {
    it('Save button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <SaveTemplates />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});
