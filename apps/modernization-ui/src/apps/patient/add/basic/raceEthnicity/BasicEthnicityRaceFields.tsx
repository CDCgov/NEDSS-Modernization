import { Controller, useFormContext } from 'react-hook-form';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { CheckboxGroup } from 'design-system/checkbox';
import { useRaceCategoryOptions, MULTI_RACE } from 'options/race';
import { usePatientEthnicityCodedValues } from 'apps/patient/profile/ethnicity';
import { BasicEthnicityRace } from 'apps/patient/add/basic/entry';
import { Selectable } from 'options';

const categoryFilter = (race: Selectable) => race.value !== MULTI_RACE.value;

export const BasicRaceEthnicityFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const codedEthnicity = usePatientEthnicityCodedValues();
    const categories = useRaceCategoryOptions({ filter: categoryFilter });
    const { control } = useFormContext<{ ethnicityRace: BasicEthnicityRace }>();

    return (
        <section>
            <Controller
                control={control}
                name="ethnicityRace.ethnicity"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Ethnicity"
                        sizing={sizing}
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
            {categories.length && (
                <Controller
                    control={control}
                    name="ethnicityRace.races"
                    render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                        <CheckboxGroup
                            label="Race"
                            sizing={sizing}
                            orientation={orientation}
                            onChange={onChange}
                            onBlur={onBlur}
                            name={name}
                            value={value}
                            error={error?.message}
                            options={categories}
                        />
                    )}
                />
            )}
        </section>
    );
};
