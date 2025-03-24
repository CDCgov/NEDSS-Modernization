import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MultiSelect } from './MultiSelect';

describe('MultiSelect', () => {
    const options = [
        { name: 'Option One', value: '1', label: 'Option One' },
        { name: 'Option Two', value: '2', label: 'Option Two' },
        { name: 'Option Three', value: '3', label: 'Option Three' }
    ];

    it('should display options when clicked', async () => {
        const { getByText, getByRole } = render(
            <MultiSelect id="test-multi-select" name="test-multi-select" label="Test Multi Select" options={options} />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.click(component);

        options.forEach((option) => {
            expect(getByText(option.label)).toBeInTheDocument();
        });
    });

    it('should allow selecting multiple options', async () => {
        const onChange = jest.fn();
        const { getByText, getByRole } = render(
            <MultiSelect
                id="test-multi-select"
                name="test-multi-select"
                label="Test Multi Select"
                options={options}
                onChange={onChange}
            />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.click(component).then(() => user.click(getByText('Option One')));

        expect(onChange).toHaveBeenCalledWith([expect.objectContaining({ value: '1', label: 'Option One' })]);
    });
});
