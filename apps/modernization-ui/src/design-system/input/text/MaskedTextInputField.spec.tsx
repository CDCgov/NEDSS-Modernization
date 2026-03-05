import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { MaskedTextInputField } from './MaskedTextInputField';

describe('MaskedTextInput', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <MaskedTextInputField id="testing-masked" label="Masked Input Field" mask="__" onChange={() => {}} />
        );

        expect(await axe(container)).toHaveNoViolations();
    });
});
