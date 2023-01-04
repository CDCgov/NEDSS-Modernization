import { Control, FieldValues } from 'react-hook-form';
import { InvestigationFilter, LabReportFilter } from '../../../../generated/graphql/schema';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    filter?: InvestigationFilter | LabReportFilter;
};

export const LabSearchCriteria = ({ control }: GeneralSearchProps) => {
    return (
        <>
            <SelectControl control={control} name="resultedTest" label="Resulted Test:" options={[]} />

            <SelectControl control={control} name="codedResult" label="Coded Result/Organism:" options={[]} />
        </>
    );
};
