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
        const { getByRole } = render(<LaboratoryReportSearchCriteria />);

        const generalButton = getByRole('button', { name: 'General search' });
        const generalPanel = getByRole('region', { name: 'General search' });

        expect(generalButton).toHaveAttribute('aria-expanded', 'true');
        expect(generalPanel).not.toHaveAttribute('hidden');
    });

    it('renders the Lab report criteria section collapsed by default', () => {
        const { getByRole } = render(<LaboratoryReportSearchCriteria />);

        const criteriaButton = getByRole('button', { name: 'Lab report criteria' });

        expect(criteriaButton).toHaveAttribute('aria-expanded', 'false');
    });
});
