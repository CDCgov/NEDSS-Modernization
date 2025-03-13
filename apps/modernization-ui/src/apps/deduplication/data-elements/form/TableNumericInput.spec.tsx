import { render, screen, fireEvent } from '@testing-library/react';
import { TableNumericInput } from './TableNumericInput';
import '@testing-library/jest-dom';

describe('TableNumericInput', () => {
    it('should display an error message when error is passed', () => {
        render(
            <TableNumericInput
                name="testInput"
                value="5"
                error="This is an error"
                onChange={() => {}}
                onBlur={() => {}}
                data-testid="testInput"
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
                data-testid="testInput"
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
                data-testid="testInput"
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
                data-testid="testInput"
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
                data-testid="testInput"
            />
        );

        const input = screen.getByTestId('testInput');
        expect(input).toHaveAttribute('min', '0');
        expect(input).toHaveAttribute('max', '10');
        expect(input).toHaveAttribute('step', '1');
    });
});
