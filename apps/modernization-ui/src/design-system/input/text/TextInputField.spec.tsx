import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { TextInputField } from './TextInputField';

describe('when entering text values for a field', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<TextInputField id={'testing-input'} label={'Numeric Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should set required attribute when required is true', () => {
        const { getByRole } = render(<TextInputField id={'testing-input'} label={'Test Input test'} required />);
        const input = getByRole('textbox', { name: 'Test Input test' });
        expect(input).toHaveAttribute('required');
    });

    it('should set aria-required attribute when required is true', () => {
        const { getByRole } = render(<TextInputField id={'testing-input'} label={'Test Input test'} required />);
        const input = getByRole('textbox', { name: 'Test Input test' });
        expect(input).toHaveAttribute('aria-required', 'true');
    });
});
