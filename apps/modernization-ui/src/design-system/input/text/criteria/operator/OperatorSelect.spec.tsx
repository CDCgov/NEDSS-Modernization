import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { OperatorSelect, OperatorSelectProps } from './OperatorSelect';
import { textOperators, defaultTextOperator, textAlphaOperators } from 'options/operator';

describe('OperatorSelect', () => {
    const mockOnChange = jest.fn();

    const defaultProps: OperatorSelectProps = {
        id: 'operator-select',
        value: null,
        onChange: mockOnChange
    };

    it('renders without crashing', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox');
        expect(selectElement).toBeInTheDocument();
    });

    it('calls onChange when an option is selected', async () => {
        const user = userEvent.setup();

        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox');

        await userEvent.selectOptions(selectElement, textOperators[0].value);

        expect(mockOnChange).toHaveBeenCalledWith(textOperators[0]);
    });

    it('displays the EQUAL operator by default', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} />);
        const selectElement = getByRole('combobox');
        expect(selectElement).toHaveValue(defaultTextOperator.value);
    });

    it('displays the correct initial value when specified', () => {
        const { getByRole } = render(<OperatorSelect {...defaultProps} value={textOperators[1]} />);
        const selectElement = getByRole('combobox');
        expect(selectElement).toHaveValue(textOperators[1].value);
    });

    it('renders all of the operator options when mode is not specified', () => {
        const { getAllByRole } = render(<OperatorSelect {...defaultProps} />);
        const options = getAllByRole('option');
        // all options
        expect(options.length).toBe(textOperators.length);
    });

    it('renders only the basic operator options when mode is alpha', () => {
        const { getAllByRole } = render(<OperatorSelect {...defaultProps} mode="alpha" />);
        const options = getAllByRole('option');
        // alpha options
        expect(options.length).toBe(textAlphaOperators.length);
    });
});
