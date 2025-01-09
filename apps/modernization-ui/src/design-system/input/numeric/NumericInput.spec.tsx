import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { NumericInput } from './NumericInput';

describe('when entering numeric values for a field', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<NumericInput id={'testing-input'} label={'Numeric Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });
});
