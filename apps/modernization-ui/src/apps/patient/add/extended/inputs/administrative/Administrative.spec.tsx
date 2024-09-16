import { render, fireEvent } from '@testing-library/react';
import { Administrative } from './Administrative';

const onChange = jest.fn();
const isDirty = jest.fn();

describe('Administrative', () => {
    beforeEach(() => {
        onChange.mockClear();
        isDirty.mockClear();
    });

    it('should render the component with correct title', () => {
        const { getByText } = render(<Administrative onChange={onChange} />);
        expect(getByText('Administrative')).toBeInTheDocument();
    });

    it('should render all input fields', () => {
        const { getByLabelText } = render(<Administrative onChange={onChange} />);

        expect(getByLabelText('General comments')).toBeInTheDocument();
    });

    it('should use default values for input fields', () => {
        const { getByLabelText } = render(<Administrative onChange={onChange} />);

        expect(getByLabelText('General comments')).toHaveValue('');
    });

    it('should call isDirty when input values change', () => {
        const { getByLabelText } = render(<Administrative onChange={onChange} />);

        fireEvent.change(getByLabelText('General comments'), { target: { value: 'S' } });
        expect(isDirty).toHaveBeenCalledWith(true);
    });
});
