import { render } from '@testing-library/react';
import { axe } from 'jest-axe';

import { NumericInput, NumericRangeInput } from './NumericInput';
import { EMPTY_NUMBER_BETWEEN_CRITERIA } from './range/NumberRangeField';

describe('when entering numeric values for a field', () => {
    it('should render with no accessibility violations on single input', async () => {
        const { container } = render(
            <NumericInput value={null} onChange={vi.fn()} id="testing-input" label="Numeric Input test" />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render with no accessibility violations on range input', async () => {
        const { container } = render(
            <NumericRangeInput
                id="testing-date-range-accessibility"
                label="Numeric Range Input test"
                onChange={vi.fn()}
                value={EMPTY_NUMBER_BETWEEN_CRITERIA}
            />
        );

        expect(await axe(container)).toHaveNoViolations();
    });
});
