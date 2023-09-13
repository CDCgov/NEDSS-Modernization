import { render } from '@testing-library/react';
import { LabReportFilter } from 'generated/graphql/schema';
import { useForm } from 'react-hook-form';
import { LabReportCriteriaFields } from './LabReportCriteria';

const LabCriteriaFieldsWithForm = () => {
    const labReportForm = useForm<LabReportFilter>({ defaultValues: {} });
    return (
        <LabReportCriteriaFields
            form={labReportForm}
            codedResultOptions={[{ label: 'coded result option', value: 'coded1' }]}
            resultedTestOptions={[{ label: 'resulted test option', value: 'resulted1' }]}
            resultedTestSearch={() => {}}
            codedResultSearch={() => {}}
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
            resultedTestSearch={() => {}}
            codedResultSearch={() => {}}
        />
    );
};

describe('InvestigationGeneralFields component', () => {
    it('should contain default selections', () => {
        const { getAllByTestId, getByText } = render(<LabCriteriaFieldsWithForm />);
        const selects = getAllByTestId('combo-box-select');

        // Resulted test
        getByText('Resulted test');
        expect(selects[0].getElementsByTagName('option')[0]).toHaveTextContent('resulted test option');
        expect(selects[0]).toHaveValue(undefined);

        // Coded result/organism
        getByText('Coded result/organism');
        expect(selects[1].getElementsByTagName('option')[0]).toHaveTextContent('coded result option');
        expect(selects[1]).toHaveValue(undefined);
    });

    it('should show form values', () => {
        const { getAllByTestId } = render(<LabCriteriaFieldsWithDefaultsSet />);
        const selects = getAllByTestId('combo-box-select');

        // Resulted test
        expect(selects[0]).toHaveValue('resulted1');

        // Coded result/organism
        expect(selects[1]).toHaveValue('coded1');
    });
});
