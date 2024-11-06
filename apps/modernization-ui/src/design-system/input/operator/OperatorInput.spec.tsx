import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { OperatorInput, OperatorInputProps } from './OperatorInput';
import { TextCriteria } from 'options/operator';

const renderComponent = (props: Partial<OperatorInputProps> = {}) => {
    const combinedProps: OperatorInputProps = {
        id: 'test-operator-input',
        label: 'test-operator-input',
        onChange: jest.fn(),
        ...props
    };
    return render(<OperatorInput {...combinedProps} />);
};

describe('OperatorInput', () => {
    it('renders without crashing with default props', () => {
        const { getByLabelText } = renderComponent();
        expect(getByLabelText('test-operator-input')).toBeInTheDocument();
    });

    it('calls onChange with correct value when input changes', () => {
        const handleChange = jest.fn();
        const { getByLabelText } = renderComponent({ onChange: handleChange });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        fireEvent.change(input, { target: { value: 'new value' } });

        expect(handleChange).toHaveBeenCalledWith({ equals: 'new value' });
    });

    it('calls onChange with correct value when operator changes', () => {
        const handleChange = jest.fn();
        const { getByRole } = renderComponent({ value: 'foo', onChange: handleChange });

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        fireEvent.change(operatorSelect, { target: { value: 'not' } });

        expect(handleChange).toHaveBeenCalledWith({ not: 'foo' });
    });

    it('displays error message when error prop is provided', () => {
        const { getByText } = renderComponent({ error: 'Test error message' });

        expect(getByText('Test error message')).toBeInTheDocument();
    });

    it('renders with equals initial value and operator', () => {
        const initialValue: TextCriteria = { equals: 'initial value' };
        const { getByLabelText, getByRole } = renderComponent({ value: initialValue });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        expect(input.value).toBe('initial value');

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        expect(operatorSelect.value).toBe('equals');
    });

    it('renders with initial value and non-default operator', () => {
        const initialValue: TextCriteria = { startsWith: 'hello' };
        const { getByLabelText, getByRole } = renderComponent({ value: initialValue });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        expect(input.value).toBe('hello');

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        expect(operatorSelect.value).toBe('startsWith');
    });

    it('renders with containers operator when text value starts with %', () => {
        const { getByLabelText, getByRole } = renderComponent({ value: '%hello' });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        expect(input.value).toBe('hello');

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        expect(operatorSelect.value).toBe('contains');
    });

    it('renders with default values when null value specified', () => {
        const { getByLabelText, getByRole } = renderComponent({ value: null });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        expect(input.value).toBe('');

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        expect(operatorSelect.value).toBe('equals');
    });

    it('fires onChange event with input text changed', () => {
        const handleChange = jest.fn();
        const initialValue: TextCriteria = { startsWith: 'hello' };
        const { getByLabelText } = renderComponent({ value: initialValue, onChange: handleChange });

        const input = getByLabelText('test-operator-input') as HTMLInputElement;
        fireEvent.change(input, { target: { value: 'world' } });
        expect(handleChange).toHaveBeenCalledWith({ startsWith: 'world' });
    });

    it('fires onChange event with selection changed', () => {
        const handleChange = jest.fn();
        const initialValue: TextCriteria = { startsWith: 'hello' };
        const { getByRole } = renderComponent({ value: initialValue, onChange: handleChange });

        const operatorSelect = getByRole('combobox') as HTMLSelectElement;
        fireEvent.change(operatorSelect, { target: { value: 'not' } });
        expect(handleChange).toHaveBeenCalledWith({ not: 'hello' });
    });
});
