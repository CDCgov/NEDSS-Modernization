import { render } from '@testing-library/react';
import GeneralInformation from './generalInformation';
import { useForm } from 'react-hook-form';
import { renderHook } from '@testing-library/react-hooks';

describe('General information component tests', () => {
    it('Should renders General information component', async () => {
        const { result } = renderHook(() => useForm());
        const { getByTestId } = render(<GeneralInformation control={result.current.control} />);
        // expect(getByTestId('required-text').innerHTML).toBe(`All fields marked with`);
    });

    it('Should call labels', async () => {
        // const { getByTestId } = render(<GeneralInformation />);
        // expect(getByTestId('date-lable').innerHTML).toBe(`Information as of Date`);
        // expect(getByTestId('comment-lable').innerHTML).toBe(`Comments`);
    });
});
