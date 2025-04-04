import { render, fireEvent } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { TextCriteriaField, TextCriteriaFieldProps } from './TextCriteriaField';
import { TextCriteria } from 'options/operator';

const renderComponent = (props: Partial<TextCriteriaFieldProps> = {}) => {
    const combinedProps: TextCriteriaFieldProps = {
        id: 'test-operator-input',
        label: 'test-operator-input',
        onChange: jest.fn(),
        ...props
    };
    return render(<TextCriteriaField {...combinedProps} />);
};

describe('TextCriteriaField', () => {
    it('renders without crashing with default props', () => {
        const { getByLabelText } = renderComponent();
        expect(getByLabelText('test-operator-input')).toBeInTheDocument();
    });

    it('displays error message when error prop is provided', () => {
        const { getByText } = renderComponent({ error: 'Test error message' });

        expect(getByText('Test error message')).toBeInTheDocument();
    });

    it('renders with value and default operator', () => {
        const { getByLabelText, getByRole } = renderComponent({ value: 'hello' });

        const input = getByLabelText('test-operator-input');
        expect(input).toHaveValue('hello');

        const operatorSelect = getByRole('combobox');
        expect(operatorSelect).toHaveValue('startsWith');
    });

    it('renders with non-default initial value and operator', () => {
        const initialValue: TextCriteria = { equals: 'initial value' };
        const { getByLabelText, getByRole } = renderComponent({ value: initialValue });

        const input = getByLabelText('test-operator-input');
        expect(input).toHaveValue('initial value');

        const operatorSelect = getByRole('combobox');
        expect(operatorSelect).toHaveValue('equals');
    });

    it('renders with initial value and non-default operator', () => {
        const initialValue: TextCriteria = { not: 'hello' };
        const { getByLabelText, getByRole } = renderComponent({ value: initialValue });

        const input = getByLabelText('test-operator-input');
        expect(input).toHaveValue('hello');

        const operatorSelect = getByRole('combobox');
        expect(operatorSelect).toHaveValue('not');
    });

    it('renders with containers operator when text value starts with %', () => {
        const { getByLabelText, getByRole } = renderComponent({ value: '%hello' });

        const input = getByLabelText('test-operator-input');
        expect(input).toHaveValue('hello');

        const operatorSelect = getByRole('combobox');
        expect(operatorSelect).toHaveValue('contains');
    });

    it('renders with default values when null value specified', () => {
        const { getByLabelText, getByRole } = renderComponent({ value: null });

        const input = getByLabelText('test-operator-input');
        expect(input).toHaveValue('');

        const operatorSelect = getByRole('combobox');
        expect(operatorSelect).toHaveValue('startsWith');
    });

    it('fires onChange event with input text changed', async () => {
        const user = userEvent.setup();

        const handleChange = jest.fn();
        const initialValue: TextCriteria = { equals: 'hello' };
        const { getByLabelText } = renderComponent({ value: initialValue, onChange: handleChange });

        const input = getByLabelText('test-operator-input');

        await user.type(input, 'world');

        expect(handleChange).toHaveBeenCalledWith({ equals: 'hellow' });
    });

    it('fires onChange event with selection changed', async () => {
        const user = userEvent.setup();

        const handleChange = jest.fn();
        const initialValue: TextCriteria = { equals: 'hello' };
        const { getByRole } = renderComponent({ value: initialValue, onChange: handleChange });

        const operatorSelect = getByRole('combobox');
        await user.selectOptions(operatorSelect, 'not');
        expect(handleChange).toHaveBeenCalledWith({ not: 'hello' });
    });
});
