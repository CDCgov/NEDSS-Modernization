import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { OperatorSelect, OperatorSelectProps } from './OperatorSelect';
import { textOperators, defaultTextOperator, textAlphaOperators } from 'options/operator';

describe('OperatorSelect', () => {
    const mockOnChange = jest.fn();

    const defaultProps: OperatorSelectProps = {
        id: 'operator-select',
        value: null,
        showLabel: false,
        sizing: 'compact',
        onChange: mockOnChange
    };

    it('renders without crashing', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox');
        expect(selectElement).toBeInTheDocument();
    });

    it('displays the operator label when showLabel is true', () => {
        const { getByLabelText } = render(<OperatorSelect {...defaultProps} showLabel={true} />);
        const labelElement = getByLabelText('Operator');
        expect(labelElement).toBeInTheDocument();
    });

    it('does not display the label when showLabel is false', () => {
        const { queryByLabelText } = render(<OperatorSelect {...defaultProps} showLabel={false} />);
        const labelElement = queryByLabelText('Operator');
        expect(labelElement).not.toBeInTheDocument();
    });

    it('calls onChange when an option is selected', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox');
        fireEvent.change(selectElement, { target: { value: textOperators[0].value } });
        expect(mockOnChange).toHaveBeenCalledWith(textOperators[0]);
    });

    it('displays the EQUAL operator by default', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox') as HTMLSelectElement;
        expect(selectElement.value).toBe(defaultTextOperator.value);
    });

    it('displays the correct initial value when specified', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} value={textOperators[1]} />);
        const selectElement = getByRole('combobox') as HTMLSelectElement;
        expect(selectElement.value).toBe(textOperators[1].value);
    });

    it('renders all of the operator options when mode is not specified', () => {
        const { getAllByRole } = render(<OperatorSelect {...defaultProps} />);
        const options = getAllByRole('option');
        // all options + the placeholder
        expect(options.length).toBe(textOperators.length + 1);
    });

    it('renders only the basic operator options when mode is alpha', () => {
        const { getAllByRole } = render(<OperatorSelect {...defaultProps} mode="alpha" />);
        const options = getAllByRole('option');
        // basic options + the placeholder
        expect(options.length).toBe(textAlphaOperators.length + 1);
    });
});