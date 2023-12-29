import { render } from '@testing-library/react';
import { InvestigationFilter } from 'generated/graphql/schema';
import { useForm } from 'react-hook-form';
import { InvestigationAccordion } from './InvestigationAccordion';

const InvestigationAccordionWithForm = () => {
    const investigationForm = useForm<InvestigationFilter>({ defaultValues: {} });
    return <InvestigationAccordion form={investigationForm} />;
};

describe('InvestigationAccordion component tests', () => {
    it('should contain general search and investigation sections', () => {
        const { getByTestId } = render(<InvestigationAccordionWithForm />);

        // Accordion is created
        const accordion = getByTestId('accordion');

        // general section exists
        const generalSection = getByTestId('accordionItem_investigation-general-section');
        expect(accordion).toContainElement(generalSection);

        // criteria section exists
        const criteriaSection = getByTestId('accordionItem_investigation-criteria-section');
        expect(accordion).toContainElement(criteriaSection);
    });
});
