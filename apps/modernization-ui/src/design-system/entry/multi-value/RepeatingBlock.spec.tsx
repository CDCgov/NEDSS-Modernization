import { act, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Input } from 'components/FormInputs/Input';
import { Control, Controller } from 'react-hook-form';
import { RepeatingBlock } from './RepeatingBlock';

type TestType = {
    firstInput: string;
    secondInput: string;
};

const onChange = jest.fn();
const isDirty = jest.fn();

const defaultValues: TestType = {
    firstInput: 'first value',
    secondInput: 'second value'
};

const renderForm = (control: Control<TestType>) => {
    return (
        <section>
            <Controller
                name="firstInput"
                control={control}
                render={({ field: { onBlur, onChange, value, name } }) => (
                    <Input
                        flexBox
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="First Input"
                        id={name}
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

const renderView = (entry: TestType) => {
    return (
        <section>
            <div>
                <label>Render view first value: {entry.firstInput}</label>
            </div>
            <div>
                <label>Render view second value: {entry.secondInput}</label>
            </div>
        </section>
    );
};
type Props = {
    errors?: string[];
};
const UnderTest = ({ errors }: Props) => {
    return (
        <RepeatingBlock<TestType>
            title={'Test title'}
            defaultValues={defaultValues}
            columns={[
                {
                    id: 'first',
                    name: 'First',
                    render: (entry: TestType) => <>{entry.firstInput}</>
                },
                {
                    id: 'second',
                    name: 'Second',
                    render: (entry: TestType) => <>{entry.secondInput}</>
                }
            ]}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            viewRenderer={renderView}
            errors={errors}
        />
    );
};

const awaitRender = async () => {
    expect(await screen.findByText('Test title')).toBeInTheDocument();
};

describe('MultiValueEntry', () => {
    it('should display provided title', async () => {
        const { getByRole } = render(<UnderTest />);
        // wait on render to prevent act warning
        await awaitRender();

        const heading = getByRole('heading');
        expect(heading).toHaveTextContent('Test');
    });

    it('should display provided form', async () => {
        const { getByLabelText } = render(<UnderTest />);
        await awaitRender();

        expect(getByLabelText('First Input')).toBeInTheDocument();
        expect(getByLabelText('Second Input')).toBeInTheDocument();
    });

    it('should display default values', async () => {
        const { getByLabelText } = render(<UnderTest />);
        await awaitRender();

        const firstInput = getByLabelText('First Input');
        expect(firstInput).toBeInTheDocument();
        expect(firstInput).toHaveValue('first value');

        const secondInput = getByLabelText('Second Input');
        expect(secondInput).toBeInTheDocument();
        expect(secondInput).toHaveValue('second value');
    });

    it('should display add button', async () => {
        const { getByRole } = render(<UnderTest />);
        await awaitRender();

        const button = getByRole('button');
        expect(button).toBeInTheDocument();
        expect(button).toHaveTextContent('Add test');
    });

    it('should display specified columns', async () => {
        const getAllByRole = await waitFor(async () => {
            const { getAllByRole } = render(<UnderTest />);
            return getAllByRole;
        });

        act(() => {
            const inputs = getAllByRole('textbox');
            userEvent.type(inputs[0], 'test');
            userEvent.type(inputs[1], 'tedt22');
            const button = getAllByRole('button');
            userEvent.click(button[0]);
        });

        await waitFor(() => {
            const table = getAllByRole('table');
            expect(table[0]).toBeInTheDocument();
        });
    });

    it('should trigger on change when data is submitted', async () => {
        const { getByRole, getByLabelText } = render(<UnderTest />);

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
            expect(onChange).toBeCalledTimes(2);
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [
                { firstInput: 'first input value', secondInput: 'second input value' }
            ]);
        });
    });

    it('should display submitted data in table', async () => {
        const { getByRole, getAllByRole } = render(<UnderTest />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toBeCalledTimes(2);
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first value');
        expect(columns[1]).toHaveTextContent('second value');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should display icons in last column of table', async () => {
        const { getByRole, getAllByRole } = render(<UnderTest />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toBeCalledTimes(2);
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
        const { getByRole, getAllByRole, getByText } = render(<UnderTest />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toBeCalledTimes(2);
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
        const { getByRole, getAllByRole } = render(<UnderTest />);

        await awaitRender();

        const button = getByRole('button');
        userEvent.click(button);

        await waitFor(() => {
            expect(onChange).toBeCalledTimes(2);
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [{ firstInput: 'first value', secondInput: 'second value' }]);
        });

        const iconContainer = getAllByRole('cell')[2].children[0].children[0];

        await waitFor(() => {
            userEvent.click(iconContainer.children[2].children[0]);
            expect(onChange).toBeCalledTimes(3);
            expect(onChange).toHaveBeenNthCalledWith(3, []);
        });
    });

    it('should allow edit of row', async () => {
        const { getByRole, getAllByRole, getByLabelText } = render(<UnderTest />);

        await awaitRender();

        const button = getByRole('button');
        const input1 = getByLabelText('First Input');
        const input2 = getByLabelText('Second Input');

        userEvent.clear(input1);
        userEvent.type(input1, 'first changed');
        userEvent.clear(input2);
        userEvent.type(input2, 'second changed');
        userEvent.click(button);

        await waitFor(() => {
            // change event fires, form resets to default
            expect(onChange).toBeCalledTimes(2);
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [
                { firstInput: 'first changed', secondInput: 'second changed' }
            ]);
            expect(getByLabelText('First Input')).toHaveValue('first value');
            expect(getByLabelText('Second Input')).toHaveValue('second value');
        });

        const iconContainer = getAllByRole('cell')[2].children[0].children[0];

        await waitFor(() => {
            // view clicked, input values set to entry value
            userEvent.click(iconContainer.children[1].children[0]);
            expect(getByLabelText('First Input')).toHaveValue('first changed');
            expect(getByLabelText('Second Input')).toHaveValue('second changed');
        });

        userEvent.clear(input1);
        userEvent.type(input1, 'first changed again');
        userEvent.clear(input2);
        userEvent.type(input2, 'second changed again');
        userEvent.click(button);

        await waitFor(() => {
            // change event fires, form resets to default
            expect(onChange).toBeCalledTimes(3);
            expect(onChange).toHaveBeenNthCalledWith(1, []);
            expect(onChange).toHaveBeenNthCalledWith(2, [
                { firstInput: 'first changed', secondInput: 'second changed' }
            ]);
            expect(onChange).toHaveBeenNthCalledWith(3, [
                { firstInput: 'first changed again', secondInput: 'second changed again' }
            ]);
            expect(getByLabelText('First Input')).toHaveValue('first value');
            expect(getByLabelText('Second Input')).toHaveValue('second value');
        });

        // table display updated
        const columns = getAllByRole('cell');
        expect(columns).toHaveLength(3);
        expect(columns[0]).toHaveTextContent('first changed again');
        expect(columns[1]).toHaveTextContent('second changed again');
        expect(columns[2]).toHaveTextContent('');
    });

    it('should display errors passed to component', async () => {
        const { getByText } = render(<UnderTest errors={['First error', 'Second error']} />);
        await awaitRender();

        expect(getByText('First error')).toBeInTheDocument();
        expect(getByText('Second error')).toBeInTheDocument();
    });
});
