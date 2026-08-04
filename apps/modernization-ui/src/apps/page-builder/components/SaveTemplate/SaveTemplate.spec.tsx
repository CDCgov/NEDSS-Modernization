import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router';
import { createRef } from 'react';

import { SaveTemplates } from './SaveTemplate';

describe('When SaveTemplates component loads', () => {
    it('Save button should be disabled', () => {
        const mockModalRef = createRef<any>();

        const { container } = render(
            <BrowserRouter>
                <SaveTemplates modalRef={mockModalRef} />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn).toBeDisabled();
    });
});
