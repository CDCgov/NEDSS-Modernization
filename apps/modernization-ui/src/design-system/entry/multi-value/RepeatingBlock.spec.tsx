import { screen, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { RepeatingBlock, RepeatingBlockProps } from './RepeatingBlock';

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

const awaitRender = async () => {
    expect(await screen.findByText('Test title')).toBeInTheDocument();
};

describe('RepeatingBlock', () => {
    it('should display provided title', async () => {
        const { getByRole } = render(<Fixture />);
        // wait on render to prevent act warning
        await awaitRender();

        const heading = getByRole('heading');
        expect(heading).toHaveTextContent('Test');
    });

    it('should display provided form', async () => {
        const { getByLabelText } = render(<Fixture />);
        await awaitRender();

        expect(getByLabelText('First Input')).toBeInTheDocument();
        expect(getByLabelText('Second Input')).toBeInTheDocument();
    });

    it('should display default values', async () => {
        const { getByLabelText } = render(
            <Fixture
                defaultValues={{
                    firstInput: 'first value',
                    secondInput: 'second value'
                }}
            />
        );
        await awaitRender();

        const firstInput = getByLabelText('First Input');
        expect(firstInput).toBeInTheDocument();
        expect(firstInput).toHaveValue('first value');

        const secondInput = getByLabelText('Second Input');
        expect(secondInput).toBeInTheDocument();
        expect(secondInput).toHaveValue('second value');
    });

    it('should display add button', async () => {
        const { getByRole } = render(<Fixture />);
        await awaitRender();

        const button = getByRole('button', { name: 'Add test title' });
        expect(button).toBeInTheDocument();
    });

    it('should display add button with correct size', async () => {
        const { getByRole } = render(<Fixture sizing="small" />);
        await awaitRender();

        const button = getByRole('button', { name: 'Add test title' });
        expect(button).toBeInTheDocument();
        expect(button).toHaveClass('small');
    });

    it('should display specified columns', async () => {
        const { getByRole } = render(
            <Fixture values={[{ firstInput: 'first-input-value', secondInput: 'second-input-value', others: [] }]} />
        );

        await waitFor(() => {
            expect(getByRole('table')).toBeInTheDocument();
        });

        expect(getByRole('columnheader', { name: 'First column name' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Second column name' })).toBeInTheDocument();
    });

    it('should trigger on change when data is submitted', async () => {
        const onChange = jest.fn();

        const { getByRole, getByLabelText } = render(<Fixture onChange={onChange} />);

        await awaitRender();

        const add = getByRole('button', { name: 'Add test title' });

        const input1 = getByLabelText('First Input');
        expect(input1).toBeInTheDocument();
        const input2 = getByLabelText('Second Input');
        expect(input2).toBeInTheDocument();

        userEvent.clear(input1);
        userEvent.type(input1, 'first input value');
        expect(input1).toHaveValue('first input value');
        userEvent.clear(input2);
        userEvent.type(input2, 'second input value');
        expect(input2).toHaveValue('second input value');
        userEvent.click(add);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [
                { firstInput: 'first input value', secondInput: 'second input value' }
            ]);
        });
    });

    it('should not display clear button when adding and no changes have been made.', async () => {
        const { queryByRole } = render(<Fixture />);

        await awaitRender();

        expect(queryByRole('button', { name: 'Clear' })).not.toBeInTheDocument();
    });

    it('should display clear button when adding and changes have been made.', async () => {
        const { getByRole, getByLabelText } = render(<Fixture />);

        await awaitRender();

        const input1 = getByLabelText('First Input');

        userEvent.type(input1, '-change');

        expect(getByRole('button', { name: 'Clear' })).toBeInTheDocument();
    });

    it('should reset values to default state when Clear is clicked.', async () => {
        const { getByRole, getByLabelText } = render(<Fixture />);

        await awaitRender();

        const input1 = getByLabelText('First Input');

        userEvent.type(input1, '-change');

        const clear = getByRole('button', { name: 'Clear' });

        userEvent.click(clear);

        userEvent.type(input1, 'first input value');
    });

    it('should display submitted data in table', async () => {
        const onChange = jest.fn();

        const { getByRole, getAllByRole, getByLabelText } = render(<Fixture onChange={onChange} />);

        await awaitRender();

        userEvent.type(getByLabelText('First Input'), 'first value');
        userEvent.type(getByLabelText('Second Input'), 'second value');
        userEvent.tab();

        const add = getByRole('button', { name: 'Add test title' });
        userEvent.click(add);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first value');
        expect(columns[1]).toHaveTextContent('second value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should reset after adding data', async () => {
        const onChange = jest.fn();

        const { getByRole, getByLabelText, queryByText } = render(<Fixture onChange={onChange} />);

        await awaitRender();

        const add = getByRole('button', { name: 'Add test title' });

        // try to add with empty required field
        userEvent.click(add);

        // ensure validation message appears
        await waitFor(() => {
            expect(queryByText('First input is required.')).toBeInTheDocument();
        });

        // enter data and submit
        userEvent.type(getByLabelText('First Input'), 'typed value');
        userEvent.click(add);

        // expect value to be added
        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'typed value', secondInput: undefined }]);
        });

        // verify validation message is no longer visible
        await waitFor(() => {
            expect(queryByText('First input is required.')).not.toBeInTheDocument();
        });

        // immediately click add button again
        userEvent.click(add);

        // verify validation text is shown
        await waitFor(() => {
            expect(queryByText('First input is required.')).toBeInTheDocument();
        });
    });

    it('should display icons in last column of table', async () => {
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

        await awaitRender();

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
        await awaitRender();

        const icons = container.querySelectorAll('.actions svg');
        expect(icons).toHaveLength(3);
        icons.forEach((icon) => {
            expect(icon).toHaveClass('small');
        });
    });

    it('should render view when view icon clicked', async () => {
        const { getByLabelText, getByText } = render(
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

        await awaitRender();

        const view = getByLabelText('View');
        userEvent.click(view);

        expect(getByText('Render view first value: first-value')).toBeInTheDocument();
        expect(getByText('Render view second value: second-value')).toBeInTheDocument();
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

        await awaitRender();

        const remove = getByLabelText('Delete');
        userEvent.click(remove);

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith([]);
        });
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

        await awaitRender();

        const edit = getByLabelText('Edit');

        userEvent.click(edit);

        const update = getByRole('button', { name: 'Update test title' });
        const input1 = getByLabelText('First Input');

        userEvent.type(input1, '-changed');
        userEvent.tab();

        userEvent.click(update);

        await waitFor(() => {
            // change event fires, form resets to default
            expect(onChange).toHaveBeenCalledWith(
                expect.arrayContaining([
                    expect.objectContaining({ firstInput: 'first-value-changed', secondInput: 'second-value' })
                ])
            );
        });

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

        await awaitRender();

        const edit = getByLabelText('Edit');

        userEvent.click(edit);

        const input1 = getByLabelText('First Input');

        userEvent.type(input1, '-changed');
        userEvent.tab();

        const cancel = getByRole('button', { name: 'Cancel' });
        userEvent.click(cancel);

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith(
                expect.arrayContaining([
                    expect.objectContaining({ firstInput: 'first-value', secondInput: 'second-value' })
                ])
            );
        });

        // table display updated
        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first-value');
        expect(columns[1]).toHaveTextContent('second-value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should display errors passed to component ', async () => {
        const { getByText } = render(<Fixture errors={['First error', 'Second error']} />);
        await awaitRender();

        expect(getByText('First error')).toBeInTheDocument();
        expect(getByText('Second error')).toBeInTheDocument();
    });

    it('should display form errors', async () => {
        const { getByRole, queryByText } = render(<Fixture />);
        await awaitRender();

        const add = getByRole('button', { name: 'Add test title' });
        userEvent.click(add);

        await waitFor(() => {
            expect(queryByText('First input is required.')).toBeInTheDocument();
        });
    });

    it('should call isDirty with true when form input applied', () => {
        const isDirty = jest.fn();
        const { getByLabelText } = render(<Fixture isDirty={isDirty} />);
        const input1 = getByLabelText('First Input');
        userEvent.type(input1, 'first value');

        expect(isDirty).toHaveBeenCalledWith(true);
    });

    it('should call isDirty with false when form input cleared', () => {
        const isDirty = jest.fn();
        const { getByLabelText, getByRole } = render(<Fixture isDirty={isDirty} />);
        const input1 = getByLabelText('First Input');
        userEvent.type(input1, 'first value');

        const clear = getByRole('button', { name: 'Clear' });
        userEvent.click(clear);

        expect(isDirty).toHaveBeenCalledWith(false);
    });

    it('should call isValid with false when there are form errors', async () => {
        const isValid = jest.fn();
        const { getByRole, getByText } = render(<Fixture isValid={isValid} />);
        await awaitRender();

        const add = getByRole('button', { name: 'Add test title' });
        userEvent.click(add);

        await waitFor(() => {
            expect(getByText('First input is required.')).toBeInTheDocument();
            expect(isValid).toHaveBeenCalledWith(false);
        });
    });
});
