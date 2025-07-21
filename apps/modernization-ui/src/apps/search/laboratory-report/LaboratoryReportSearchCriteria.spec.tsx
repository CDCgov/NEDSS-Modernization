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
        const { getAllByRole } = render(<LaboratoryReportSearchCriteria />);

        const buttons = getAllByRole('button');
        const regions = getAllByRole('region');

        const generalButton = buttons.find((button) => button.textContent?.includes('General search'));
        const generalPanel = regions[0];

        expect(generalButton).toHaveAttribute('aria-expanded', 'true');
        expect(generalPanel).not.toHaveAttribute('hidden');
    });

    it('renders the Lab report criteria section collapsed by default', () => {
        const { getAllByRole } = render(<LaboratoryReportSearchCriteria />);

        const buttons = getAllByRole('button');
        const criteriaButton = buttons.find((button) => button.textContent?.includes('Lab report criteria'));

        expect(criteriaButton).toHaveAttribute('aria-expanded', 'false');
    });
});
