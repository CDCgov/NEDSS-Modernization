import { screen, render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { RepeatingBlock } from './RepeatingBlock';
import { ReactNode } from 'react';

type TestType = {
    firstInput: string;
    secondInput: string;
    thirdInput?: number;
    others: [];
};

const onChange = jest.fn();
const isDirty = jest.fn();

const defaultValues: Partial<TestType> = {
    firstInput: 'first value',
    secondInput: 'second value'
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

type Props = {
    values?: TestType[];
    errors?: ReactNode[];
    defaults?: Partial<TestType>;
};

const Fixture = ({ values = [], errors, defaults }: Props) => (
    <RepeatingBlock<TestType>
        id="testing"
        title={'Test title'}
        defaultValues={{ ...defaultValues, ...defaults }}
        columns={columns}
        values={values}
        onChange={onChange}
        isDirty={isDirty}
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
        const { getByLabelText } = render(<Fixture />);
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

        const button = getByRole('button');
        expect(button).toBeInTheDocument();
        expect(button).toHaveTextContent('Add test');
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
        const { getByRole, getByLabelText } = render(<Fixture />);

        await awaitRender();

        const button = getByRole('button');
        expect(button).toBeInTheDocument();
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
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [
                { firstInput: 'first input value', secondInput: 'second input value' }
            ]);
        });
    });

    it('should display submitted data in table', async () => {
        const { getByRole, getAllByRole } = render(<Fixture />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first value');
        expect(columns[1]).toHaveTextContent('second value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should reset after adding data', async () => {
        const { getByRole, getByLabelText, queryByText } = render(<Fixture defaults={{ firstInput: undefined }} />);

        await awaitRender();
        // try to add with empty required field
        userEvent.click(getByRole('button'));

        // ensure validation message appears
        await waitFor(() => {
            expect(queryByText('First input is required.')).toBeInTheDocument();
        });

        // enter data and submit
        userEvent.type(getByLabelText('First Input'), 'typed value');
        userEvent.click(getByRole('button'));

        // expect value to be added
        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'typed value', secondInput: 'second value' }]);
        });

        // verify validation message is no longer visible
        await waitFor(() => {
            expect(queryByText('First input is required.')).not.toBeInTheDocument();
        });

        // immediately click add button again
        userEvent.click(getByRole('button'));

        // verify validation text is shown
        await waitFor(() => {
            expect(queryByText('First input is required.')).toBeInTheDocument();
        });
    });

    it('should display icons in last column of table', async () => {
        const { getByRole, getAllByRole } = render(<Fixture />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

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

    it('should render view when view icon clicked', async () => {
        const { getByRole, getAllByRole, getByText } = render(<Fixture />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

        const iconContainer = getAllByRole('cell')[2].children[0].children[0];
        expect(iconContainer.children).toHaveLength(3);

        // View icon
        userEvent.click(iconContainer.children[0].children[0]);

        expect(getByText('Render view first value: first value')).toBeInTheDocument();
        expect(getByText('Render view second value: second value')).toBeInTheDocument();
    });

    it('should delete row when delete icon clicked', async () => {
        const { getByLabelText } = render(
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

        const remove = getByLabelText('Delete');
        userEvent.click(remove);

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith([]);
        });
    });

    it('should allow edit of row', async () => {
        const { getByRole, getAllByRole, getByLabelText, debug } = render(
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

        const edit = getByLabelText('Edit');

        userEvent.click(edit);

        const button = getByRole('button', { name: 'Update test title' });
        const input1 = getByLabelText('First Input');

        userEvent.type(input1, '-changed');
        userEvent.tab();

        userEvent.click(button);

        await waitFor(() => {
            // change event fires, form resets to default
            expect(onChange).toHaveBeenCalledWith(
                expect.arrayContaining([
                    expect.objectContaining({ firstInput: 'first-value-changed', secondInput: 'second-value' })
                ])
            );
            expect(getByLabelText('First Input')).toHaveValue('first value');
            expect(getByLabelText('Second Input')).toHaveValue('second value');
        });

        const iconContainer = getAllByRole('cell')[2].children[0].children[0];

        await waitFor(() => {
            // view clicked, input values set to entry value
            userEvent.click(iconContainer.children[1].children[0]);
            expect(getByLabelText('First Input')).toHaveValue('first-value-changed');
            expect(getByLabelText('Second Input')).toHaveValue('second-value');
        });

        // table display updated
        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first-value-changed');
        expect(columns[1]).toHaveTextContent('second-value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should display errors passed to component', async () => {
        const { getByText } = render(<Fixture errors={['First error', 'Second error']} />);
        await awaitRender();

        expect(getByText('First error')).toBeInTheDocument();
        expect(getByText('Second error')).toBeInTheDocument();
    });
});
