import { Controller, useFormContext } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { BasicEthnicityRace } from '../entry';
import { SingleSelect } from 'design-system/select';
import { usePatientEthnicityCodedValues } from 'apps/patient/profile/ethnicity';
import { CheckboxGroup } from 'design-system/checkbox';
import { useRaceCodedValues } from 'coded/race';

type BasicRaceEthnicityFieldsProps = EntryFieldsProps;

export const BasicRaceEthnicityFields = ({ orientation = 'horizontal' }: BasicRaceEthnicityFieldsProps) => {
    const codedEthnicity = usePatientEthnicityCodedValues();
    const codedRaces = useRaceCodedValues();
    const { control } = useFormContext<{ ethnicityRace: BasicEthnicityRace }>();

    return (
        <section>
            <Controller
                control={control}
                name="ethnicityRace.ethnicity"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Ethnicity"
                        sizing="compact"
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        value={value}
                        options={codedEthnicity.ethnicGroups}
                    />
                )}
            />
            {codedRaces.length && (
                <Controller
                    control={control}
                    name="ethnicityRace.races"
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <CheckboxGroup
                            label="Race"
                            orientation={orientation}
                            onChange={onChange}
                            onBlur={onBlur}
                            name={name}
                            value={value}
                            error={error?.message}
                            className="basic-race-ethnicity"
                            options={codedRaces}
                            sizing="compact"
                        />
                    )}
                />
            )}
        </section>
    );
};
