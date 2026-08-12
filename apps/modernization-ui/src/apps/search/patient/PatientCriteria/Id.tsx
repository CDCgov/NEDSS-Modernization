import { Controller, useFormContext, useWatch } from 'react-hook-form';

import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';
import { EntryFieldsProps } from 'design-system/entry';
import { TextInputField } from 'design-system/input/text/TextInputField';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';

export const Id = ({ sizing, orientation }: EntryFieldsProps) => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const identificationType = useWatch({ control, name: 'identificationType' });

    const conceptOptions = useConceptOptions('EI_TYPE_PAT', { lazy: false }).options;

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
                        options={conceptOptions}
                    />
                )}
            />
            {identificationType && (
                <Controller
                    control={control}
                    name="identification"
                    rules={{
                        required: { value: true, message: 'ID number is required' },
                    }}
                    render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                        <TextInputField
                            id={name}
                            sizing={sizing}
                            orientation={orientation}
                            type="text"
                            value={value}
                            onBlur={onBlur}
                            onChange={onChange}
                            name={name}
                            label="ID number"
                            required={true}
                            error={error?.message}
                        />
                    )}
                />
            )}
        </SearchCriteria>
    );
};
