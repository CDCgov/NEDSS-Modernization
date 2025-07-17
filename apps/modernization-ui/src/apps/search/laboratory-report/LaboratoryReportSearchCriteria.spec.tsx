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

        const sections = getAllByRole('region');

        const general = sections[0];

        expect(general).toHaveTextContent('General search');
        expect(general).toHaveAttribute('open');
    });

    it('renders the Lab report criteria section collapsed by default', () => {
        const { getAllByRole } = render(<LaboratoryReportSearchCriteria />);

        const sections = getAllByRole('region');

        const criteria = sections[1];

        expect(criteria).toHaveTextContent('Lab report criteria');
        expect(criteria).not.toHaveAttribute('open');
    });
});
