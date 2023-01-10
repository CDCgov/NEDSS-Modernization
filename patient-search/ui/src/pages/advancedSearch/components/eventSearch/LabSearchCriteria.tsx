import { Control, Controller, FieldValues } from 'react-hook-form';
import { InvestigationFilter, LabReportFilter } from '../../../../generated/graphql/schema';
import { ComboBox, Label } from '@trussworks/react-uswds';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    filter?: InvestigationFilter | LabReportFilter;
    resultChanges?: (string: any) => void;
    resultsTestOptions?: { value: string; label: string }[];
    codedResultsChange?: (string: any) => void;
    codedResults?: { value: string; label: string }[];
};

export const LabSearchCriteria = ({
    control,
    resultChanges,
    resultsTestOptions,
    codedResults,
    codedResultsChange
}: GeneralSearchProps) => {
    return (
        <>
            <Label htmlFor={'resultedTest'}>Resulted Test:</Label>
            <Controller
                control={control}
                name={'resultedTest'}
                render={({ field: { onChange, value } }) => (
                    <ComboBox
                        key={value}
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

            <Label htmlFor={'codedResult'}>Coded Result/Organism:</Label>
            <Controller
                control={control}
                name={'codedResult'}
                render={({ field: { onChange, value } }) => (
                    <ComboBox
                        key={value}
                        id="codedResult"
                        name="codedResult"
                        options={codedResults || []}
                        defaultValue={value}
                        onChange={(e) => {
                            onChange(e);
                        }}
                        inputProps={{
                            onChange: (e) => {
                                codedResultsChange?.(e);
                            }
                        }}
                    />
                )}
            />
        </>
    );
};
