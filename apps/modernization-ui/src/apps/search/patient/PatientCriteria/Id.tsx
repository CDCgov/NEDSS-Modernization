import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { useConceptOptions } from 'options/concepts';
import { Input } from 'components/FormInputs/Input';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';

export const Id = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const identificationType = useWatch({ control: control, name: 'identificationType' });

    return (
        <SearchCriteria sizing={sizing}>
            <Controller
                control={control}
                name="identificationType"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="ID type"
                        id={name}
                        sizing={sizing}
                        orientation={orientation}
                        options={useConceptOptions('EI_TYPE_PAT', { lazy: false }).options}
                    />
                )}
            />
            {identificationType && (
                <Controller
                    control={control}
                    name="identification"
                    rules={{
                        required: { value: true, message: 'ID number is required' }
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <Input
                            sizing={sizing}
                            orientation={orientation}
                            type="text"
                            defaultValue={value}
                            onBlur={onBlur}
                            onChange={onChange}
                            name={name}
                            label="ID number"
                            required
                            error={error?.message}
                        />
                    )}
                />
            )}
        </SearchCriteria>
    );
};
