import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { ToggleField } from './ToggleField';

describe('when entering text values for a field', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<ToggleField id={'testing-input'} label={'Toggle Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should set aria-required="true" when required is true', () => {
        const { getByRole } = render(<ToggleField id={'testing-input'} label={'Toggle Input test'} required />);
        const input = getByRole('checkbox', { name: 'Toggle Input test' });
        expect(input).toHaveAttribute('aria-required', 'true');
    });

    it('should set required attribute when required is true', () => {
        const { getByRole } = render(<ToggleField id="test" label="Toggle Input test" required />);
        const input = getByRole('checkbox', { name: 'Toggle Input test' });
        expect(input).toHaveAttribute('required');
    });
});
