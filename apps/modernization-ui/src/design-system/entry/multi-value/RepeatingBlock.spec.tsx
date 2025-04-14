import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { RepeatingBlock, RepeatingBlockProps } from './RepeatingBlock';
import { axe } from 'jest-axe';

type TestType = {
    firstInput: string;
    secondInput: string;
    thirdInput?: number;
    others: [];
};

const UnderTestForm = () => {
    const { control } = useFormContext<TestType>();
    return (
        <section>
            <Controller
                name="firstInput"
                control={control}
                rules={{ required: { value: true, message: 'First input is required.' } }}
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <Input
                        flexBox
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="First Input"
                        id={name}
                        required
                    />
                )}
            />
            <Controller
                name="secondInput"
                control={control}
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <Input
                        flexBox
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Second Input"
                        id={name}
                    />
                )}
            />
        </section>
    );
};

const UnderTestView = ({ entry }: { entry: TestType }) => (
    <section>
        <div>
            <label>Render view first value: {entry.firstInput}</label>
        </div>
        <div>
            <label>Render view second value: {entry.secondInput}</label>
        </div>
    </section>
);

const columns = [
    {
        id: 'first',
        name: 'First column name',
        render: (entry: TestType) => entry.firstInput
    },
    {
        id: 'second',
        name: 'Second column name',
        render: (entry: TestType) => entry.secondInput
    }
];

const Fixture = ({
    values = [],
    errors,
    defaultValues,
    sizing,
    onChange = jest.fn(),
    isDirty = jest.fn(),
    isValid
}: Partial<RepeatingBlockProps<TestType>>) => (
    <RepeatingBlock<TestType>
        id="testing"
        title={'Test title'}
        defaultValues={defaultValues}
        columns={columns}
        values={values}
        onChange={onChange}
        isDirty={isDirty}
        sizing={sizing}
        isValid={isValid}
        formRenderer={() => <UnderTestForm />}
        viewRenderer={(entry) => <UnderTestView entry={entry} />}
        errors={errors}
    />
);

describe('RepeatingBlock', () => {
    it('should display provided title', () => {
        const { getByRole } = render(<Fixture />);

        const heading = getByRole('heading');
        expect(heading).toHaveTextContent('Test');
    });

    it('should display provided form', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('First Input')).toBeInTheDocument();
        expect(getByLabelText('Second Input')).toBeInTheDocument();
    });

    it('should display default values', () => {
        const { getByLabelText } = render(
            <Fixture
                defaultValues={{
                    firstInput: 'first value',
                    secondInput: 'second value'
                }}
            />
        );

        const firstInput = getByLabelText('First Input');
        expect(firstInput).toBeInTheDocument();
        expect(firstInput).toHaveValue('first value');

        const secondInput = getByLabelText('Second Input');
        expect(secondInput).toBeInTheDocument();
        expect(secondInput).toHaveValue('second value');
    });

    it('should display add button', () => {
        const { getByRole } = render(<Fixture />);

        const button = getByRole('button', { name: 'Add test title' });
        expect(button).toBeInTheDocument();
        expect(button).toHaveAttribute('aria-description');
    });

    it('should display add button with correct size', () => {
        const { getByRole } = render(<Fixture sizing="small" />);

        const button = getByRole('button', { name: 'Add test title' });
        expect(button).toBeInTheDocument();
        expect(button).toHaveClass('small');
    });

    it('should display specified columns', () => {
        const { getByRole } = render(
            <Fixture values={[{ firstInput: 'first-input-value', secondInput: 'second-input-value', others: [] }]} />
        );

        expect(getByRole('table')).toBeInTheDocument();

        expect(getByRole('columnheader', { name: 'First column name' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Second column name' })).toBeInTheDocument();
    });

    it('should trigger on change when data is submitted', async () => {
        const onChange = jest.fn();

        const { getByRole, getByLabelText } = render(<Fixture onChange={onChange} />);

        const add = getByRole('button', { name: 'Add test title' });

        const input1 = getByLabelText('First Input');
        expect(input1).toBeInTheDocument();
        const input2 = getByLabelText('Second Input');
        expect(input2).toBeInTheDocument();

        const user = userEvent.setup();

        await user
            .type(input1, 'first input value')
            .then(() => user.type(input2, 'second input value'))
            .then(() => user.click(add));

        expect(onChange).toHaveBeenNthCalledWith(1, []);
        expect(onChange).toHaveBeenNthCalledWith(2, [
            { firstInput: 'first input value', secondInput: 'second input value' }
        ]);
    });

    it('should not display clear button when adding and no changes have been made.', () => {
        const { queryByRole } = render(<Fixture />);

        expect(queryByRole('button', { name: 'Clear' })).not.toBeInTheDocument();
    });

    it('should display clear button when adding and changes have been made.', async () => {
        const { getByRole, getByLabelText } = render(<Fixture />);

        const input1 = getByLabelText('First Input');

        const user = userEvent.setup();

        await user.type(input1, '-change');

        const button = getByRole('button', { name: 'Clear' });

        expect(button).toBeInTheDocument();
        expect(button).toHaveAttribute('aria-description');
    });

    it('should reset values to default state when Clear is clicked.', async () => {
        const { getByRole, getByLabelText } = render(
            <Fixture
                defaultValues={{
                    firstInput: 'first value'
                }}
            />
        );

        const input1 = getByLabelText('First Input');

        const user = userEvent.setup();

        await user.type(input1, '-change');

        const clear = getByRole('button', { name: 'Clear' });
        await user.click(clear);

        expect(input1).toHaveValue('first value');
    });

    it('should display submitted data in table', async () => {
        const onChange = jest.fn();

        const { getByRole, getAllByRole, getByLabelText } = render(<Fixture onChange={onChange} />);

        const add = getByRole('button', { name: 'Add test title' });

        const user = userEvent.setup();

        await user
            .type(getByLabelText('First Input'), 'first value')
            .then(() => user.type(getByLabelText('Second Input'), 'second value'))
            .then(() => user.click(add));

        expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);

        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first value');
        expect(columns[1]).toHaveTextContent('second value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should reset after adding data', async () => {
        const onChange = jest.fn();

        const { getByRole, getByLabelText, queryByText } = render(<Fixture onChange={onChange} />);

        const add = getByRole('button', { name: 'Add test title' });

        // try to add with empty required field
        const user = userEvent.setup();

        await user.click(add);

        // ensure validation message appears
        expect(queryByText('First input is required.')).toBeInTheDocument();

        // enter data and submit
        await user.type(getByLabelText('First Input'), 'typed value').then(() => user.click(add));

        // expect value to be added

        expect(onChange).toHaveBeenNthCalledWith(1, []);
        expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'typed value', secondInput: undefined }]);

        // verify validation message is no longer visible

        expect(queryByText('First input is required.')).not.toBeInTheDocument();

        // immediately click add button again
        await user.click(add);

        // verify validation text is shown
        expect(queryByText('First input is required.')).toBeInTheDocument();
    });

    it('should display icons in last column of table', () => {
        const { getAllByRole } = render(
            <Fixture
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const iconContainer = getAllByRole('cell')[2].children[0].children[0];
        expect(iconContainer.children).toHaveLength(3);

        // View icon
        expect(iconContainer.children[0]).toHaveAttribute('aria-label', 'View');
        expect(iconContainer.children[0]).toHaveAttribute('data-tooltip-position', 'top');

        // Edit icon
        expect(iconContainer.children[1]).toHaveAttribute('aria-label', 'Edit');
        expect(iconContainer.children[1]).toHaveAttribute('data-tooltip-position', 'top');

        // Delete icon
        expect(iconContainer.children[2]).toHaveAttribute('aria-label', 'Delete');
        expect(iconContainer.children[2]).toHaveAttribute('data-tooltip-position', 'top');
    });

    it('should render icons with correct sizing', async () => {
        const { container } = render(
            <Fixture
                sizing="small"
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const icons = container.querySelectorAll('.actions svg');
        expect(icons).toHaveLength(3);
        icons.forEach((icon) => {
            expect(icon).toHaveClass('small');
        });
    });

    it('should render view when view icon clicked', async () => {
        const { getByLabelText, getByText, getAllByRole } = render(
            <Fixture
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const view = getByLabelText('View');
        const user = userEvent.setup();

        await user.click(view);

        expect(getByText('Render view first value: first-value')).toBeInTheDocument();
        expect(getByText('Render view second value: second-value')).toBeInTheDocument();

        const buttons = getAllByRole('button');
        expect(buttons).toHaveLength(1);
        expect(buttons[0]).toHaveTextContent('Add test title');
        expect(buttons[0].innerHTML).toContain('svg');
        expect(buttons[0]).toHaveAttribute('aria-description');
    });

    it('should render edit when edit icon clicked', async () => {
        const { getByLabelText, getAllByRole } = render(
            <Fixture
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const editIcon = getByLabelText('Edit');
        const user = userEvent.setup();

        await user.click(editIcon);

        expect(getByLabelText('First Input')).toBeInTheDocument();
        expect(getByLabelText('Second Input')).toBeInTheDocument();

        const buttons = getAllByRole('button');
        expect(buttons).toHaveLength(2);
        expect(buttons[0]).toHaveTextContent('Update test title');
        expect(buttons[0]).toHaveAttribute('aria-description');
        expect(buttons[0].innerHTML).not.toContain('svg');
        expect(buttons[1]).toHaveTextContent('Cancel');
        expect(buttons[1]).toHaveAttribute('aria-description');
    });

    it('should delete row when delete icon clicked', async () => {
        const onChange = jest.fn();
        const { getByLabelText } = render(
            <Fixture
                onChange={onChange}
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const remove = getByLabelText('Delete');
        const user = userEvent.setup();

        await user.click(remove);

        expect(onChange).toHaveBeenCalledWith([]);
    });

    it('should allow edit of row', async () => {
        const onChange = jest.fn();

        const { getByRole, getAllByRole, getByLabelText } = render(
            <Fixture
                onChange={onChange}
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const edit = getByLabelText('Edit');

        const user = userEvent.setup();

        await user.click(edit);

        const update = getByRole('button', { name: 'Update test title' });
        const input1 = getByLabelText('First Input');

        await user.type(input1, '-changed{tab}').then(() => user.click(update));

        // change event fires, form resets to default
        expect(onChange).toHaveBeenCalledWith(
            expect.arrayContaining([
                expect.objectContaining({ firstInput: 'first-value-changed', secondInput: 'second-value' })
            ])
        );

        // table display updated
        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first-value-changed');
        expect(columns[1]).toHaveTextContent('second-value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should allow cancelling update of row being edited', async () => {
        const onChange = jest.fn();

        const { getByRole, getAllByRole, getByLabelText } = render(
            <Fixture
                onChange={onChange}
                values={[
                    {
                        firstInput: 'first-value',
                        secondInput: 'second-value',
                        others: []
                    }
                ]}
            />
        );

        const edit = getByLabelText('Edit');

        const user = userEvent.setup();

        await user.click(edit);

        const input1 = getByLabelText('First Input');

        await user.type(input1, '-changed{tab}');

        const cancel = getByRole('button', { name: 'Cancel' });
        await user.click(cancel);

        expect(onChange).toHaveBeenCalledWith(
            expect.arrayContaining([
                expect.objectContaining({ firstInput: 'first-value', secondInput: 'second-value' })
            ])
        );

        // table display updated
        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first-value');
        expect(columns[1]).toHaveTextContent('second-value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should display errors passed to component ', async () => {
        const { getByText } = render(<Fixture errors={['First error', 'Second error']} />);

        expect(getByText('First error')).toBeInTheDocument();
        expect(getByText('Second error')).toBeInTheDocument();
    });

    it('should display form errors', async () => {
        const { getByRole, queryByText } = render(<Fixture />);

        const add = getByRole('button', { name: 'Add test title' });

        const user = userEvent.setup();

        await user.click(add);

        expect(queryByText('First input is required.')).toBeInTheDocument();
    });

    it('should call isDirty with true when form input applied', async () => {
        const isDirty = jest.fn();
        const { getByLabelText } = render(<Fixture isDirty={isDirty} />);
        const input1 = getByLabelText('First Input');
        const user = userEvent.setup();

        await user.type(input1, 'first value');

        expect(isDirty).toHaveBeenCalledWith(true);
    });

    it('should call isDirty with false when form input cleared', async () => {
        const isDirty = jest.fn();
        const { getByLabelText, getByRole } = render(<Fixture isDirty={isDirty} />);
        const input1 = getByLabelText('First Input');
        const user = userEvent.setup();

        await user.type(input1, 'first value');

        const clear = getByRole('button', { name: 'Clear' });

        await user.click(clear);

        expect(isDirty).toHaveBeenCalledWith(false);
    });

    it('should call isValid with false when there are form errors', async () => {
        const isValid = jest.fn();
        const { getByRole, getByText } = render(<Fixture isValid={isValid} />);

        const add = getByRole('button', { name: 'Add test title' });
        const user = userEvent.setup();

        await user.click(add);

        expect(getByText('First input is required.')).toBeInTheDocument();
        expect(isValid).toHaveBeenCalledWith(false);
    });

    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture />);

        expect(await axe(container)).toHaveNoViolations();
    });
});
