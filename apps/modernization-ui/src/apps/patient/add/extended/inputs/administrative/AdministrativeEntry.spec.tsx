import { render, fireEvent } from '@testing-library/react';
import { AdministrativeEntry } from './AdministrativeEntry';

const onChange = jest.fn();
const isDirty = jest.fn();

describe('AdministrativeEntry', () => {
    beforeEach(() => {
        onChange.mockClear();
        isDirty.mockClear();
    });

    it('should render the component with correct title', () => {
        const { getByText } = render(<AdministrativeEntry onChange={onChange} isDirty={isDirty} />);
        expect(getByText('Administrative')).toBeInTheDocument();
    });

    it('should render all input fields', () => {
        const { getByLabelText } = render(<AdministrativeEntry onChange={onChange} isDirty={isDirty} />);

        expect(getByLabelText('General comments')).toBeInTheDocument();
    });

    it('should use default values for input fields', () => {
        const { getByLabelText } = render(<AdministrativeEntry onChange={onChange} isDirty={isDirty} />);

        expect(getByLabelText('General comments')).toHaveValue('');
    });

    it('should call isDirty when input values change', () => {
        const { getByLabelText } = render(<AdministrativeEntry onChange={onChange} isDirty={isDirty} />);

        fireEvent.change(getByLabelText('General comments'), { target: { value: 'S' } });
        expect(isDirty).toHaveBeenCalledWith(true);
    });
});
