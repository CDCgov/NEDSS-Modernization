import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { MultiSelect } from './MultiSelect';
import { axe } from 'jest-axe';

describe('MultiSelect', () => {
    const options = [
        { name: 'Option One', value: '1', label: 'Option One' },
        { name: 'Option Two', value: '2', label: 'Option Two' },
        { name: 'Option Three', value: '3', label: 'Option Three' },
    ];

    it('should display options when clicked', async () => {
        const { getByText, getByRole } = render(
            <MultiSelect id="test-multi-select" name="test-multi-select" label="Test Multi Select" options={options} />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.click(component);

        expect(getByText('Select all')).toBeInTheDocument();

        options.forEach((option) => {
            expect(getByText(option.label)).toBeInTheDocument();
        });
    });

    it('should allow selecting multiple options', async () => {
        const onChange = vi.fn();
        const { getByText, getByRole, queryByText, container } = render(
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

        // menu should still be open
        await user.click(getByText('Option Two'));

        expect(onChange).toHaveBeenCalledWith([expect.objectContaining({ value: '2', label: 'Option Two' })]);

        expect(await axe(container)).toHaveNoViolations();

        // Close menu
        await user.keyboard('{Escape}');
        expect(queryByText('Option Three')).toBeNull();
    });

    it('should allow selecting all', async () => {
        const onChange = vi.fn();
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

        await user.click(component).then(() => user.click(getByText('Select all')));

        expect(onChange).toHaveBeenCalledWith([
            expect.objectContaining({ value: '1', label: 'Option One' }),
            expect.objectContaining({ value: '2', label: 'Option Two' }),
            expect.objectContaining({ value: '3', label: 'Option Three' }),
        ]);
    });

    it('should allow de-selecting all', async () => {
        const onChange = vi.fn();
        const { getByText, getByRole } = render(
            <MultiSelect
                id="test-multi-select"
                name="test-multi-select"
                label="Test Multi Select"
                options={options}
                value={options}
                onChange={onChange}
            />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.click(component).then(() => user.click(getByText('Deselect all')));

        expect(onChange).toHaveBeenCalledWith([]);
    });

    it('should allow selecting search results', async () => {
        const onChange = vi.fn();
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

        await user.type(component, 'one');

        await user.click(component).then(() => user.click(getByText('Select search results')));

        expect(onChange).toHaveBeenCalledWith([expect.objectContaining({ value: '1', label: 'Option One' })]);
    });

    it('should allow de-selecting search results', async () => {
        const onChange = vi.fn();
        const { getByText, getByRole } = render(
            <MultiSelect
                id="test-multi-select"
                name="test-multi-select"
                label="Test Multi Select"
                options={options}
                value={options}
                onChange={onChange}
            />
        );

        const component = getByRole('combobox');

        const user = userEvent.setup();

        await user.type(component, 'one');

        await user.click(component).then(() => user.click(getByText('Deselect search results')));

        expect(onChange).toHaveBeenCalledWith([
            expect.objectContaining({ value: '2', label: 'Option Two' }),
            expect.objectContaining({ value: '3', label: 'Option Three' }),
        ]);
    });
});
