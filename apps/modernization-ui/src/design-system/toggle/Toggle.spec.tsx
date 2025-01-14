import { render } from '@testing-library/react';
import { Toggle } from './Toggle';
import userEvent from '@testing-library/user-event';

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
    it('should emit onChange event when label clicked', () => {
        const onChange = jest.fn();

        const { getByText } = render(<Toggle name={'test'} label={'test'} value={false} onChange={onChange} />);

        const label = getByText('test');

        userEvent.click(label);

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
