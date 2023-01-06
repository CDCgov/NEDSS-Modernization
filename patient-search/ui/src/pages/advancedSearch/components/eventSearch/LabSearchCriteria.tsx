import { Control, Controller, FieldValues } from 'react-hook-form';
import { InvestigationFilter, LabReportFilter } from '../../../../generated/graphql/schema';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';
import { ComboBox, Label } from '@trussworks/react-uswds';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    filter?: InvestigationFilter | LabReportFilter;
    resultChanges?: (string: any) => void;
    resultsTestOptions?: { value: string; label: string }[];
};

export const LabSearchCriteria = ({ control, resultChanges, resultsTestOptions }: GeneralSearchProps) => {
    return (
        <>
            <Label htmlFor={'resultedTest'}>Resulted Test:</Label>
            <Controller
                control={control}
                name={'resultedTest'}
                render={({ field: { onChange, value } }) => (
                    <ComboBox
                        id="resultedTest"
                        name="resultedTest"
                        options={resultsTestOptions || []}
                        defaultValue={value}
                        onChange={(e) => {
                            onChange(e);
                        }}
                        inputProps={{
                            onChange: (e) => {
                                resultChanges?.(e);
                            }
                        }}
                    />
                )}
            />
            {/* <SelectControl control={control} name="resultedTest" label="Resulted Test:" options={[]} /> */}

            <SelectControl control={control} name="codedResult" label="Coded Result/Organism:" options={[]} />
        </>
    );
};
