import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { ContactForm } from './ContactForm';

describe('ContactForm component tests', () => {
    it('should render a grid with 2 inputs 1 for phone number and other for email', () => {
        const { result } = renderHook(() => useForm());
        const { getByLabelText } = render(<ContactForm control={result.current.control}/>);
        expect(getByLabelText('Phone number')).toBeTruthy();
        expect(getByLabelText('Email')).toBeTruthy();
    });
});