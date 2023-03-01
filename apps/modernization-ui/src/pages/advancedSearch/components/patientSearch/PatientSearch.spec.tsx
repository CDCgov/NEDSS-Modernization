import { render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import { useForm } from 'react-hook-form';
import { PersonFilter } from '../../../../generated/graphql/schema';
import { PatientSearch } from './PatientSearch';

describe('PatientSearch component tests', () => {
    it('should render 5 accordions each with a specific form', () => {
        const { result } = renderHook(() => useForm());
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        let sampleData;
        const { container, getByLabelText } = render(
            <PatientSearch 
                handleSubmission={sampleSearchFunction}
                data={sampleData}
                clearAll={sampleClearFunction}
            />
        );
        const accordionH4Elements = container.querySelectorAll('h4.accordian-item');
        expect(accordionH4Elements.length).toBe(5);
        expect(accordionH4Elements[0].textContent).toBe('Basic info');
        expect(accordionH4Elements[1].textContent).toBe('Address');
        expect(accordionH4Elements[2].textContent).toBe('Contact');
        expect(accordionH4Elements[3].textContent).toBe('ID');
        expect(accordionH4Elements[4].textContent).toBe('Race / Ethnicity');
    });

    it('should have the basic info accorgion expanded by default and rest all closed', () => {
        const { result } = renderHook(() => useForm());
        const sampleSearchFunction = (data: PersonFilter) => {};
        const sampleClearFunction = () => {};
        let sampleData;
        const { container, getByLabelText } = render(
            <PatientSearch 
                handleSubmission={sampleSearchFunction}
                data={sampleData}
                clearAll={sampleClearFunction}
            />
        );
        const accordionButtonElements = container.querySelectorAll('h4.accordian-item button');
        expect(accordionButtonElements[0].getAttribute('aria-expanded')).toBe("true");
        expect(accordionButtonElements[1].getAttribute('aria-expanded')).toBe("false");
        expect(accordionButtonElements[2].getAttribute('aria-expanded')).toBe("false");
        expect(accordionButtonElements[3].getAttribute('aria-expanded')).toBe("false");
        expect(accordionButtonElements[4].getAttribute('aria-expanded')).toBe("false");
    });
});