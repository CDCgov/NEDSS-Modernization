import { render } from '@testing-library/react';
import { LaboratoryReportSearchCriteria } from './LaboratoryReportSearchCriteria'; // Update this import path

jest.mock('./GeneralFields', () => ({
    GeneralFields: () => <div data-testid="general-fields">General Fields</div>
}));

jest.mock('./CriteriaFields', () => ({
    CriteriaFields: () => <div data-testid="criteria-fields">Criteria Fields</div>
}));

describe('LaboratoryReportSearchCriteria', () => {
    it('renders the General search section expanded by default', () => {
        const { container } = render(<LaboratoryReportSearchCriteria />);

        const detailsElements = container.querySelectorAll('details');
        const generalAccordion = detailsElements[0];

        expect(generalAccordion).toHaveTextContent('General search');
        expect(generalAccordion).toHaveAttribute('open');
    });

    it('renders the Lab report criteria section collapsed by default', () => {
        const { container } = render(<LaboratoryReportSearchCriteria />);

        const detailsElements = container.querySelectorAll('details');
        const criteriaAccordion = detailsElements[1];

        expect(criteriaAccordion).toHaveTextContent('Lab report criteria');
        expect(criteriaAccordion).not.toHaveAttribute('open');
    });
});
