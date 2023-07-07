import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { EthnicityForm } from './EthnicityForm';
import { Race, Ethnicity } from '../../../../generated/graphql/schema';
import { formatInterfaceString } from '../../../../utils/util';
import { SearchCriteria, SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';

describe('EthnicityForm component tests', () => {
    it('should render 2 dropdown Labels Ethnicity and Race', () => {
        const { result } = renderHook(() => useForm());
        const { container } = render(<EthnicityForm control={result.current.control} />);
        expect(container.querySelectorAll('.usa-label')[0].textContent).toBe('Ethnicity');
        expect(container.querySelectorAll('.usa-label')[1].textContent).toBe('Race');
    });

    it('should render Ethnicity dropdown with correct options', () => {
        const { result } = renderHook(() => useForm());
        const ethnicities: Ethnicity[] = [];
        ethnicities.push({ codeDescTxt: 'Not Hispanic or Latino', id: { code: '2186-5' } });
        ethnicities.push({ codeDescTxt: 'Hispanic or Latino', id: { code: '2135-2' } });
        const searchCriteria: SearchCriteria = {
            ethnicities,
            programAreas: [],
            conditions: [],
            jurisdictions: [],
            userResults: [],
            outbreaks: [],
            races: [],
            identificationTypes: [],
            states: [],
            counties: [],
            countries: [],
            authorities: []
        };
        const { container } = render(
            <SearchCriteriaContext.Provider value={{ searchCriteria }}>
                <EthnicityForm control={result.current.control} />
            </SearchCriteriaContext.Provider>
        );
        const options = container.querySelectorAll('div select')[0].childNodes;
        Object.values(ethnicities).forEach((value, idx) => {
            expect(formatInterfaceString(value.codeDescTxt)).toBe(options[idx + 1].textContent);
        });
    });

    it('should render Race dropdown with correct options', () => {
        const { result } = renderHook(() => useForm());
        const races: Race[] = [];
        races.push({ codeDescTxt: 'Asian', id: { code: '2028-9' } });
        races.push({ codeDescTxt: 'White', id: { code: '2106-3' } });
        const searchCriteria: SearchCriteria = {
            races,
            ethnicities: [],
            programAreas: [],
            conditions: [],
            jurisdictions: [],
            userResults: [],
            outbreaks: [],
            identificationTypes: [],
            states: [],
            counties: [],
            countries: [],
            authorities: []
        };
        const { container } = render(
            <SearchCriteriaContext.Provider value={{ searchCriteria }}>
                <EthnicityForm control={result.current.control} />
            </SearchCriteriaContext.Provider>
        );
        const options = container.querySelectorAll('div select')[1].childNodes;
        Object.values(races).forEach((value, idx) => {
            expect(formatInterfaceString(value.codeDescTxt)).toBe(options[idx + 1].textContent);
        });
    });
});
