import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Toggle } from './Toggle';

describe('Toggle testing', () => {
    it('should render checked', () => {
        const { getByRole } = render(<Toggle name={'test'} label={'test'} value={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });

    it('should render unchecked', () => {
        const { getByRole } = render(<Toggle name={'test'} label={'test'} value={false} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeChecked();
    });
    it('should emit onChange event when label clicked', async () => {
        const user = userEvent.setup();

        const onChange = jest.fn();

        const { getByText } = render(<Toggle name={'test'} label={'test'} value={false} onChange={onChange} />);

        const label = getByText('test');

        await user.click(label);

        expect(onChange).toHaveBeenCalledWith(true);
    });
    it('should render unchecked', () => {
        const { getByRole } = render(<Toggle name={'test'} label={'test'} value={false} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).not.toBeChecked();
    });

    it('should render checked', () => {
        const { getByRole } = render(<Toggle name={'test'} label={'test'} value={true} />);

        const checkbox = getByRole('checkbox');
        expect(checkbox).toBeChecked();
    });
});
