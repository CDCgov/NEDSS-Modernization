import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { IDForm } from './IdForm';
import { IdentificationType } from '../../../../generated/graphql/schema';
import { formatInterfaceString } from '../../../../utils/util';

describe('IDForm component tests', () => {
    it('should render 2 Labels for ID Type and ID Number', () => {
        const { result } = renderHook(() => useForm());
        const { container, getByLabelText } = render(<IDForm control={result.current.control}/>);
        expect(container.querySelectorAll('.usa-label')[0].textContent).toBe('ID type');
        expect(getByLabelText('ID number')).toBeTruthy();
    });

    it('should render IdentificationType dropdown with correct options', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<IDForm control={result.current.control}/>);
        const options = container.querySelectorAll('div select')[0].childNodes;
        Object.values(IdentificationType).forEach((value, idx) => {
            expect(formatInterfaceString(value)).toBe(options[idx+1].textContent);
        });
    });
});
