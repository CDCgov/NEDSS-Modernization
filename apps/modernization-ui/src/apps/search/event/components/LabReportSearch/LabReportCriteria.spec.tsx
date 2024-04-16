import { render, fireEvent } from '@testing-library/react';
import { LabReportFilter } from 'generated/graphql/schema';
import { useForm } from 'react-hook-form';
import { LabReportCriteriaFields } from './LabReportCriteria';
import { useFindDistinctResultedTestLazyQuery } from 'generated/graphql/schema';

interface LabCriteriaFieldsWithFormWrapperProps {
    onResultedTestSearch?: jest.Mock<any, any>;
    onCodedResultSearch?: jest.Mock<any, any>;
}

jest.mock('generated/graphql/schema', () => ({
    ...jest.requireActual('generated/graphql/schema'),
    useFindDistinctResultedTestLazyQuery: jest.fn()
}));

const LabCriteriaFieldsWithForm = ({
    onResultedTestSearch,
    onCodedResultSearch
}: LabCriteriaFieldsWithFormWrapperProps) => {
    const labReportForm = useForm<LabReportFilter>({ defaultValues: {} });
    return (
        <LabReportCriteriaFields
            form={labReportForm}
            resultedTestOptions={[{ label: 'resulted test option', value: 'resulted1' }]}
            onResultedTestSearch={(e: string) => onResultedTestSearch?.(e)}
            codedResultOptions={[{ label: 'coded result option', value: 'coded1' }]}
            onCodedResultSearch={(e: string) => onCodedResultSearch?.(e)}
        />
    );
};

const LabCriteriaFieldsWithDefaultsSet = () => {
    const labReportForm = useForm<LabReportFilter>({
        defaultValues: { codedResult: 'coded1', resultedTest: 'resulted1' }
    });
    return (
        <LabReportCriteriaFields
            form={labReportForm}
            codedResultOptions={[{ label: 'coded result option', value: 'coded1' }]}
            resultedTestOptions={[{ label: 'resulted test option', value: 'resulted1' }]}
            onResultedTestSearch={() => {
                return [{ label: 'resulted test option', value: 'resulted1' }];
            }}
            onCodedResultSearch={() => {}}
        />
    );
};

describe('LabReportCriteria component', () => {
    it('should contain selections for resulted test', async () => {
        const { getByTestId } = render(<LabCriteriaFieldsWithForm />);
        const resultedTestInput = getByTestId('resultedTest');
        expect(resultedTestInput).toBeInTheDocument();
        const codedResultInput = getByTestId('codedResult');
        expect(codedResultInput).toBeInTheDocument();
    });

    it('should contain default input for resulted test', async () => {
        const { getByTestId } = render(<LabCriteriaFieldsWithDefaultsSet />);
        const resultedTestInput = getByTestId('resultedTest');
        expect(resultedTestInput).toHaveValue('resulted1');
    });

    it('should call onResultedTestSearch on input change', async () => {
        const onResultedTestSearch = jest.fn();
        const { getByTestId } = render(<LabCriteriaFieldsWithForm onResultedTestSearch={onResultedTestSearch} />);
        const resultedTestInput = getByTestId('resultedTest');
        fireEvent.change(resultedTestInput, { target: { value: 'test' } });
        expect(onResultedTestSearch).toHaveBeenCalledWith('test');
    });

    it('should render LabReportCriteria component', async () => {
        const { getByText } = render(<LabCriteriaFieldsWithForm />);
        const criteriaLabel = getByText('Resulted test');
        expect(criteriaLabel).toBeInTheDocument();
    });

    it('should call onCodedResultSearch when input value changes', () => {
        const onCodedResultSearchMock = jest.fn();
        const { getByTestId } = render(<LabCriteriaFieldsWithForm onCodedResultSearch={onCodedResultSearchMock} />);
        const codedResultInput = getByTestId('codedResult');
        fireEvent.change(codedResultInput, { target: { value: 'test' } });
        expect(onCodedResultSearchMock).toHaveBeenCalledWith('test');
    });

    it('should call getLocalResultedTests when input value changes', async () => {
        const mockGetLocalResultedTests = jest.fn();
        const onResultedTestSearch = jest.fn();
        const mockQueryResult = {
            data: null,
            loading: false,
            error: null,
            called: true
        };
        (useFindDistinctResultedTestLazyQuery as jest.Mock).mockReturnValue([
            mockGetLocalResultedTests,
            mockQueryResult
        ]);

        const { getByTestId } = render(<LabCriteriaFieldsWithForm onResultedTestSearch={onResultedTestSearch} />);
        const resultedTestInput = getByTestId('resultedTest');
        fireEvent.change(resultedTestInput, { target: { value: 'test' } });
        expect(onResultedTestSearch).toHaveBeenCalledWith('test');
    });
});
