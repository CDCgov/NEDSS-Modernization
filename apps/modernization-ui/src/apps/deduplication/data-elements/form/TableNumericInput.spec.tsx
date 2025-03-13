import { render, screen, fireEvent } from '@testing-library/react';
import { TableNumericInput } from './TableNumericInput';
import '@testing-library/jest-dom';

describe('TableNumericInput', () => {
    it('should render the input with a label', () => {
        render(
            <TableNumericInput
                name="testInput"
                label="Test Label"
                value=""
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const input = screen.getByTestId('testInput');
        const label = screen.getByText('Test Label');

        expect(input).toBeInTheDocument();
        expect(label).toBeInTheDocument();
    });

    it('should render the input without a label if not provided', () => {
        render(
            <TableNumericInput
                name="testInput"
                value=""
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const input = screen.getByTestId('testInput');
        const label = screen.queryByText('Test Label');

        expect(input).toBeInTheDocument();
        expect(label).not.toBeInTheDocument();
    });

    it('should update the value when the input changes', () => {
        const onChangeMock = jest.fn();
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                onChange={onChangeMock}
                onBlur={() => {}}
            />
        );

        const input = screen.getByTestId('testInput');
        fireEvent.change(input, { target: { value: '10' } });

        expect(onChangeMock).toHaveBeenCalledWith(expect.objectContaining({ target: { value: '10' } }));
    });

    it('should display an error message when error is passed', () => {
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                error="This is an error"
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const errorTooltip = screen.getByText('This is an error');
        expect(errorTooltip).toBeInTheDocument();

        const input = screen.getByTestId('testInput');
        expect(input).toHaveClass('errorBorder');
    });

    it('should not display error message when no error is passed', () => {
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const errorTooltip = screen.queryByText('This is an error');
        expect(errorTooltip).not.toBeInTheDocument();

        const input = screen.getByTestId('testInput');
        expect(input).not.toHaveClass('errorBorder');
    });

    it('should be disabled when disabled prop is true', () => {
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                disabled
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const input = screen.getByTestId('testInput');
        expect(input).toBeDisabled();
    });

    it('should call onBlur when the input loses focus', () => {
        const onBlurMock = jest.fn();
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                onChange={() => {}}
                onBlur={onBlurMock}
            />
        );

        const input = screen.getByTestId('testInput');
        fireEvent.blur(input);

        expect(onBlurMock).toHaveBeenCalled();
    });

    it('should respect min, max, and step values', () => {
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                min={0}
                max={10}
                step={1}
                onChange={() => {}}
                onBlur={() => {}}
            />
        );

        const input = screen.getByTestId('testInput');
        expect(input).toHaveAttribute('min', '0');
        expect(input).toHaveAttribute('max', '10');
        expect(input).toHaveAttribute('step', '1');
    });
});
