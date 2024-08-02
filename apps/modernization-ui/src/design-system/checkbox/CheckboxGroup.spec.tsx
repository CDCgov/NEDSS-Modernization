import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CheckboxGroup } from './CheckboxGroup';
import { act } from 'react-dom/test-utils';

const options = [
    { value: 'value1', label: 'label1', name: 'name1' },
    { value: 'value2', label: 'label2', name: 'name2' }
];

describe('CheckboxGroup', () => {
    it('should render with options', () => {
        const { getByLabelText } = render(<CheckboxGroup name="test" label="checkbox group label" options={options} />);

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).toBeInTheDocument();
        expect(checkbox1).not.toBeChecked();
        expect(checkbox1).not.toBeDisabled();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).toBeInTheDocument();
        expect(checkbox2).not.toBeChecked();
        expect(checkbox1).not.toBeDisabled();
    });

    it('should render with label', () => {
        const { getByText } = render(<CheckboxGroup name="test" label="checkbox group label" options={options} />);
        const label = getByText('checkbox group label');
        expect(label).toBeInTheDocument();
    });

    it('should render options with values selected', () => {
        const { getByLabelText } = render(
            <CheckboxGroup
                name="test"
                label="Testing CheckboxGroup"
                options={[
                    { name: 'One Name', label: 'One Label', value: 'ONE' },
                    { name: 'Two Name', label: 'Two Label', value: 'TWO' },
                    { name: 'Three Name', label: 'Three Label', value: 'Three' }
                ]}
                value={[
                    { name: 'Three Name', label: 'Three Label', value: 'Three' },
                    { name: 'One Name', label: 'One Label', value: 'ONE' }
                ]}
            />
        );

        const one = getByLabelText('One Label');
        expect(one).toBeChecked();

        const two = getByLabelText('Two Label');
        expect(two).not.toBeChecked();

        const three = getByLabelText('Three Label');
        expect(three).toBeChecked();
    });

    it('should check option when clicked', async () => {
        const { getByLabelText } = render(
            <CheckboxGroup
                name="test"
                label="Testing CheckboxGroup"
                options={[
                    { name: 'One Name', label: 'One Label', value: 'ONE' },
                    { name: 'Two Name', label: 'Two Label', value: 'TWO' },
                    { name: 'Three Name', label: 'Three Label', value: 'Three' }
                ]}
            />
        );

        const one = getByLabelText('One Label') as HTMLInputElement;
        const two = getByLabelText('Two Label') as HTMLInputElement;

        expect(one.checked).toBe(false);
        expect(two.checked).toBe(false);

        await act(async () => {
            one.checked = true;
            one.dispatchEvent(new Event('change', { bubbles: true }));
        });

        await waitFor(() => {
            expect(one.checked).toBe(true);
            expect(two.checked).toBe(false);
        });

        await act(async () => {
            two.checked = true;
            two.dispatchEvent(new Event('change', { bubbles: true }));
        });

        await waitFor(() => {
            expect(one.checked).toBe(true);
            expect(two.checked).toBe(true);
        });
    });

    it('should emit onChange when clicked', async () => {
        const onChange = jest.fn();

        const { getByLabelText } = render(
            <CheckboxGroup
                name="test"
                label="Testing CheckboxGroup"
                options={[
                    { value: 'value1', label: 'label1', name: 'name1' },
                    { value: 'value2', label: 'label2', name: 'name2' }
                ]}
                onChange={onChange}
            />
        );

        const one = getByLabelText('label1');
        const two = getByLabelText('label2');

        await act(async () => {
            userEvent.click(one);
        });

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith([{ value: 'value1', label: 'label1', name: 'name1' }]);
        });

        await act(async () => {
            userEvent.click(two);
        });

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith([{ value: 'value2', label: 'label2', name: 'name2' }]);
        });

        expect(onChange).toHaveBeenCalledTimes(2);
    });

    it('should disable checkboxes', () => {
        const { getByLabelText } = render(
            <CheckboxGroup name="test" label="Testing CheckboxGroup" options={options} disabled />
        );

        const checkbox1 = getByLabelText('label1');
        expect(checkbox1).toBeDisabled();

        const checkbox2 = getByLabelText('label2');
        expect(checkbox2).toBeDisabled();
    });

    it('should render with specified className', () => {
        const { getByRole } = render(
            <CheckboxGroup
                name="test"
                label="Testing CheckboxGroup"
                className="customClass"
                options={options}
                disabled
            />
        );

        const group = getByRole('group');
        expect(group).toHaveClass('customClass');
    });
});
