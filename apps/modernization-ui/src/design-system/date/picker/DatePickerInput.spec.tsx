import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { DatePickerInput } from './DatePickerInput';

describe('when picking a date value for data entry', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<DatePickerInput id={'testing-date-picker'} label={'Date picker input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });
});
