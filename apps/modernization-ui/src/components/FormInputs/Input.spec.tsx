import { Input } from './Input';
import { render } from '@testing-library/react';

describe('Input component tests', () => {
    describe('when there is an error', () => {
        it('should render an input and display the error', () => {
            const onChange = () => {};
            const { getByLabelText, getByRole } = render(
                <Input
                    id="test-input-id"
                    name="test-input-name"
                    label="Test Input Label"
                    className="test-input-class-name"
                    htmlFor="test-input-id"
                    type="text"
                    onChange={onChange}
                    defaultValue="test-input-defaultValue"
                    error="invalid input"
                />
            );
            expect(getByLabelText('Test Input Label')).toBeTruthy();
            expect(getByRole('alert')).toHaveTextContent('invalid input');
        });
    });

    describe('when there is no error', () => {
        it('should render an input and display no errors', () => {
            const onChange = () => {};
            const { getByLabelText, queryByRole } = render(
                <Input
                    id="test-input-id"
                    name="test-input-name"
                    label="Test Input Label"
                    className="test-input-class-name"
                    htmlFor="test-input-id"
                    type="text"
                    onChange={onChange}
                    defaultValue="test-input-defaultValue"
                />
            );
            expect(getByLabelText('Test Input Label')).toBeTruthy();
            expect(queryByRole('alert')).not.toBeInTheDocument();
        });
    });

    describe('when required is true', () => {
        it('should set aria-required="true" when required is true', () => {
            const onChange = () => {};
            const { getByLabelText } = render(
                <Input
                    id="test-input-id"
                    name="test-input-name"
                    label="Test Input Label"
                    type="text"
                    onChange={onChange}
                    defaultValue="test-input-defaultValue"
                    required
                />
            );
            const input = getByLabelText('Test Input Label');
            expect(input).toHaveAttribute('aria-required', 'true');
        });

        it('should set required attribute when required is true', () => {
            const onChange = () => {};
            const { getByLabelText } = render(
                <Input
                    id="test"
                    name="test"
                    label="Test Input Label"
                    type="text"
                    onChange={onChange}
                    required
                />
            );
            const input = getByLabelText('Test Input Label');
            expect(input).toHaveAttribute('required');
        });

        it('should set required and aria-required on masked input', () => {
            const onChange = () => {};
            const { getByLabelText } = render(
                <Input
                    id="test"
                    name="test"
                    label="Test Input Label"
                    type="text"
                    onChange={onChange}
                    mask="99/99/9999"
                    required
                />
            );
            const input = getByLabelText('Test Input Label');
            expect(input).toHaveAttribute('required');
            expect(input).toHaveAttribute('aria-required', 'true');
        });

        it('should set required and aria-required on textarea', () => {
            const onChange = () => {};
            const { getByLabelText } = render(
                <Input
                    id="test"
                    name="test"
                    label="Test Input Label"
                    type="text"
                    onChange={onChange}
                    multiline
                    required
                />
            );
            const textarea = getByLabelText('Test Input Label');
            expect(textarea).toHaveAttribute('required');
            expect(textarea).toHaveAttribute('aria-required', 'true');
        });
    });
});
