import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormAccordion } from './FormAccordion'; // Update this import path
import { UseFormReturn } from 'react-hook-form';
import { LabReportFilterEntry } from './labReportFormTypes';

jest.mock('./GeneralFields', () => ({
    GeneralFields: () => <div data-testid="general-fields">General Fields</div>
}));

jest.mock('./CriteriaFields', () => ({
    CriteriaFields: () => <div data-testid="criteria-fields">Criteria Fields</div>
}));

describe('FormAccordion', () => {
    const mockForm = {} as UseFormReturn<LabReportFilterEntry>;

    it('renders the accordion with two sections', () => {
        const { getByText } = render(<FormAccordion form={mockForm} />);

        expect(getByText('General search')).toBeInTheDocument();
        expect(getByText('Lab report criteria')).toBeInTheDocument();
    });

    it('renders the GeneralFields component in the first section', () => {
        const { getByTestId } = render(<FormAccordion form={mockForm} />);

        expect(getByTestId('general-fields')).toBeInTheDocument();
    });

    it('renders the CriteriaFields component in the second section', () => {
        const { getByTestId } = render(<FormAccordion form={mockForm} />);

        expect(getByTestId('criteria-fields')).toBeInTheDocument();
    });

    it('expands the General search section by default', () => {
        const { getByText } = render(<FormAccordion form={mockForm} />);

        const generalSection = getByText('General search').closest('button');
        expect(generalSection).toHaveAttribute('aria-expanded', 'true');
    });

    it('collapses the Lab report criteria section by default', () => {
        const { getByText } = render(<FormAccordion form={mockForm} />);

        const criteriaSection = getByText('Lab report criteria').closest('button');
        expect(criteriaSection).toHaveAttribute('aria-expanded', 'false');
    });

    it('allows multiple sections to be expanded', async () => {
        const { getByText } = render(<FormAccordion form={mockForm} />);

        const generalSection = getByText('General search').closest('button');
        const criteriaSection = getByText('Lab report criteria').closest('button');

        expect(generalSection).toHaveAttribute('aria-expanded', 'true');
        expect(criteriaSection).toHaveAttribute('aria-expanded', 'false');

        await userEvent.click(criteriaSection!);

        expect(generalSection).toHaveAttribute('aria-expanded', 'true');
        expect(criteriaSection).toHaveAttribute('aria-expanded', 'true');
    });

    it('applies the correct CSS classes and headingLevel', () => {
        const { getAllByRole } = render(<FormAccordion form={mockForm} />);

        const accordionItems = getAllByRole('heading', { level: 3 });
        expect(accordionItems).toHaveLength(2);

        accordionItems.forEach((item) => {
            expect(item.closest('.accordian-item')).toBeInTheDocument();
        });
    });
});
