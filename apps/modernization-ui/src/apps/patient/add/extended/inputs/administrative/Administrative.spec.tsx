import { render } from '@testing-library/react';
import { Administrative } from './Administrative';
import { FormProvider, useForm } from 'react-hook-form';

const mockOnChange = jest.fn();

const TestWrapper = ({ children }: { children: React.ReactNode }) => {
    const methods = useForm();
    return <FormProvider {...methods}>{children}</FormProvider>;
};

describe('Administrative', () => {
    beforeEach(() => {
        mockOnChange.mockClear();
    });

    it('should render the component with correct title', () => {
        const { getByText } = render(
            <TestWrapper>
                <Administrative />
            </TestWrapper>
        );
        expect(getByText('Administrative')).toBeInTheDocument();
    });

    it('should render all input fields', () => {
        const { getByLabelText } = render(
            <TestWrapper>
                <Administrative />
            </TestWrapper>
        );

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('General comments')).toBeInTheDocument();
    });
});
