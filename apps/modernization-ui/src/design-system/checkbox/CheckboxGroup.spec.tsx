import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { CheckboxGroup } from './CheckboxGroup';

const options = [
    { value: 'value1', label: 'label1', name: 'name1' },
    { value: 'value2', label: 'label2', name: 'name2' }
];

describe('CheckboxGroup', () => {
    it('should render with options', () => {
        const { getByLabelText } = render(<CheckboxGroup name="test" label="checkbox group label" options={options} />);

        const checkbox1 = getByLabelText('name1');
        expect(checkbox1).toBeInTheDocument();
        expect(checkbox1).not.toBeChecked();
        expect(checkbox1).not.toBeDisabled();

        const checkbox2 = getByLabelText('name2');
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

        const one = getByLabelText('One Name');
        expect(one).toBeChecked();

        const two = getByLabelText('Two Name');
        expect(two).not.toBeChecked();

        const three = getByLabelText('Three Name');
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

        const one = getByLabelText('One Name');
        expect(one).not.toBeChecked();

        const two = getByLabelText('Two Name');
        expect(two).not.toBeChecked();

        const user = userEvent.setup();
        await user.click(one);

        expect(one).toBeChecked();
        expect(two).not.toBeChecked();

        await user.click(two);

        expect(one).toBeChecked();
        expect(two).toBeChecked();
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

        const one = getByLabelText('name1');
        const two = getByLabelText('name2');

        const user = userEvent.setup();
        await user.click(one);

        expect(onChange).toHaveBeenCalledWith(expect.arrayContaining([expect.objectContaining({ value: 'value1' })]));

        await user.click(two);

        expect(onChange).toHaveBeenCalledWith(
            expect.arrayContaining([
                expect.objectContaining({ value: 'value1' }),
                expect.objectContaining({ value: 'value2' })
            ])
        );
    });

    it('should disable checkboxes', () => {
        const { getByLabelText } = render(
            <CheckboxGroup name="test" label="Testing CheckboxGroup" options={options} disabled />
        );

        const checkbox1 = getByLabelText('name1');
        expect(checkbox1).toBeDisabled();

        const checkbox2 = getByLabelText('name2');
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
        expect(group).toBeInTheDocument();
    });
});
