import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { IDForm } from './IdForm';
import { IdentificationType, PersonFilter } from 'generated/graphql/schema';
import { SearchCriteria, SearchCriteriaContext } from 'providers/SearchCriteriaContext';

describe('IDForm component tests', () => {
    it('should render 1 Label for ID Type', () => {
        const { result } = renderHook(() => useForm<PersonFilter>());
        const { container } = render(<IDForm control={result.current} />);
        expect(container.querySelectorAll('.usa-label')[0].textContent).toBe('ID type');
        expect(container.querySelector('#identificationNumber')).not.toBeTruthy();
    });

    it('should render 2 Labels when ID Type is selected', () => {
        const { result } = renderHook(() =>
            useForm<PersonFilter>({ defaultValues: { identification: { identificationType: 'someType' } } })
        );
        const { container, getByLabelText } = render(<IDForm control={result.current} />);
        expect(container.querySelectorAll('.usa-label')[0].textContent).toBe('ID type');
        expect(getByLabelText('ID number')).toBeTruthy();
    });

    it('should render IdentificationType dropdown with correct options', () => {
        const { result } = renderHook(() => useForm<PersonFilter>());
        const identificationTypes: IdentificationType[] = [];
        identificationTypes.push({ codeDescTxt: "Driver's license number", id: { code: 'DL' } });
        identificationTypes.push({ codeDescTxt: 'Social Security', id: { code: 'SS' } });
        const searchCriteria: SearchCriteria = {
            identificationTypes,
            races: [],
            ethnicities: [],
            programAreas: [],
            conditions: [],
            jurisdictions: [],
            userResults: [],
            outbreaks: [],
            states: []
        };

        const { container } = render(
            <SearchCriteriaContext.Provider value={{ searchCriteria }}>
                <IDForm control={result.current} />
            </SearchCriteriaContext.Provider>
        );
        const options = container.querySelectorAll('div select')[0].childNodes;
        Object.values(identificationTypes).forEach((value, idx) => {
            expect(value.codeDescTxt).toBe(options[idx + 1].textContent);
        });
    });
});
