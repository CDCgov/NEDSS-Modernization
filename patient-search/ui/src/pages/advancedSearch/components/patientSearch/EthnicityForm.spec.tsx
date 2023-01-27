import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { EthnicityForm } from './EthnicityForm';
import { Ethnicity, Race } from '../../../../generated/graphql/schema';
import { formatInterfaceString } from '../../../../utils/util';

describe('EthnicityForm component tests', () => {
    it('should render 2 dropdown Labels Ethnicity and Race', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<EthnicityForm control={result.current.control}/>);
        expect(container.querySelectorAll('.usa-label')[0].textContent).toBe('Ethnicity');
        expect(container.querySelectorAll('.usa-label')[1].textContent).toBe('Race');
    });

    it('should render Ethnicity dropdown with correct options', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<EthnicityForm control={result.current.control}/>);
        const options = container.querySelectorAll('div select')[0].childNodes;
        Object.values(Ethnicity).forEach((value, idx) => {
            expect(formatInterfaceString(value)).toBe(options[idx+1].textContent);
        });
    });

    it('should render Race dropdown with correct options', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<EthnicityForm control={result.current.control}/>);
        const options = container.querySelectorAll('div select')[1].childNodes;
        Object.values(Race).forEach((value, idx) => {
            expect(formatInterfaceString(value)).toBe(options[idx+1].textContent);
        });
    });
});