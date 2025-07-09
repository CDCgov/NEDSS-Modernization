import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { MaskedTextInputField } from './MaskedTextInputField';

describe('MaskedTextInput', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <MaskedTextInputField id="test" label="Masked Input Field" mask="__" onChange={() => {}} />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should set required attribute when required is true', () => {
        const { getByRole } = render(
            <MaskedTextInputField id="test" label="Masked Input Field" mask="__" onChange={() => {}} required />
        );
        const input = getByRole('textbox', { name: 'Masked Input Field' });
        expect(input).toHaveAttribute('required');
    });

    it('should set aria-required attribute when required is true', () => {
        const { getByRole } = render(
            <MaskedTextInputField id="test" label="Masked Input Field" mask="__" onChange={() => {}} required />
        );
        const input = getByRole('textbox', { name: 'Masked Input Field' });
        expect(input).toHaveAttribute('aria-required', 'true');
    });
});
