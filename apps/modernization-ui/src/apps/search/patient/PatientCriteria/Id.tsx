import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { SingleSelect } from 'design-system/select';
import { useConceptOptions } from 'options/concepts';
import { Input } from 'components/FormInputs/Input';
import { SearchCriteria } from 'apps/search/criteria';
import { PatientCriteriaEntry } from 'apps/search/patient/criteria';

export const Id = () => {
    const { control } = useFormContext<PatientCriteriaEntry, Partial<PatientCriteriaEntry>>();
    const identificationType = useWatch({ control: control, name: 'identificationType' });

    return (
        <SearchCriteria>
            <Controller
                control={control}
                name="identificationType"
                render={({ field: { onChange, value, name } }) => (
                    <SingleSelect
                        value={value}
                        onChange={onChange}
                        name={name}
                        label="ID"
                        id={name}
                        sizing="compact"
                        options={useConceptOptions('EI_TYPE_PAT', { lazy: false }).options}
                    />
                )}
            />
            {identificationType && (
                <Controller
                    control={control}
                    name="identification"
                    rules={{
                        required: { value: true, message: 'Id number is required' }
                    }}
                    render={({ field: { onBlur, onChange, value, name } }) => (
                        <Input
                            sizing="compact"
                            type="text"
                            defaultValue={value}
                            onBlur={onBlur}
                            onChange={onChange}
                            name={name}
                            label="Id number"
                            required
                        />
                    )}
                />
            )}
        </SearchCriteria>
    );
};
