import { render } from '@testing-library/react';
import { axe } from 'vitest-axe';
import { TextInputField } from './TextInputField';

describe('when entering text values for a field', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<TextInputField id={'testing-input'} label={'Numeric Input test'} />);

        expect(await axe(container)).toHaveNoViolations();
    });
});
