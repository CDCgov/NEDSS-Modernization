import { act, render } from '@testing-library/react';
import { LabReportFilter } from 'generated/graphql/schema';
import { useForm } from 'react-hook-form';
import { LabReportCriteriaFields } from './LabReportCriteria';
import userEvent from '@testing-library/user-event';

const LabCriteriaFieldsWithForm = () => {
    const labReportForm = useForm<LabReportFilter>({ defaultValues: {} });
    return (
        <LabReportCriteriaFields
            form={labReportForm}
            resultedTestOptions={[{ label: 'resulted test option', value: 'resulted1' }]}
            onResultedTestSearch={() => {
                return [{ label: 'resulted test option', value: 'resulted1' }];
            }}
            codedResultOptions={[{ label: 'coded result option', value: 'coded1' }]}
            onCodedResultSearch={() => {}}
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

describe('InvestigationGeneralFields component', () => {
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
});
