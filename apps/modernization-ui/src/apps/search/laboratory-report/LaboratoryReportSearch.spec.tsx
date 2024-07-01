import { render } from '@testing-library/react';
import { LaboratoryReportSearch } from './LaboratoryReportSearch';
import { useForm } from 'react-hook-form';
import { initialEntry } from './initiaEntry';
import { Props } from '../layout/SearchLayout';

jest.mock('react-hook-form', () => ({
    useForm: jest.fn()
}));

jest.mock('apps/search/layout', () => ({
    SearchLayout: ({ criteria, resultsAsList, resultsAsTable }: Props) => (
        <div>
            <div data-testid="criteria">{criteria()}</div>
            <div data-testid="results-list">{resultsAsList()}</div>
            <div data-testid="results-table">{resultsAsTable()}</div>
        </div>
    )
}));

jest.mock('./FormAccordion', () => ({
    FormAccordion: () => <div data-testid="form-accordion">Form Accordion</div>
}));

jest.mock('./initiaEntry', () => ({
    initialEntry: jest.fn().mockReturnValue({})
}));

describe('LaboratoryReportSearch', () => {
    beforeEach(() => {
        (useForm as jest.Mock).mockReturnValue({
            control: {},
            handleSubmit: jest.fn(),
            reset: jest.fn()
        });
    });

    it('renders SearchLayout with correct props', () => {
        const { getByTestId } = render(<LaboratoryReportSearch />);

        expect(getByTestId('criteria')).toBeInTheDocument();
        expect(getByTestId('results-list')).toBeInTheDocument();
        expect(getByTestId('results-table')).toBeInTheDocument();
    });

    it('renders FormAccordion in criteria section', () => {
        const { getByTestId } = render(<LaboratoryReportSearch />);

        expect(getByTestId('form-accordion')).toBeInTheDocument();
    });

    it('renders result list placeholder', () => {
        const { getByText } = render(<LaboratoryReportSearch />);

        expect(getByText('result list')).toBeInTheDocument();
    });

    it('renders result table placeholder', () => {
        const { getByText } = render(<LaboratoryReportSearch />);

        expect(getByText('result table')).toBeInTheDocument();
    });

    it('initializes form with correct default values', () => {
        render(<LaboratoryReportSearch />);

        expect(useForm).toHaveBeenCalledWith({
            defaultValues: undefined,
            mode: 'onBlur'
        });
        expect(initialEntry).toHaveBeenCalled();
    });
});
