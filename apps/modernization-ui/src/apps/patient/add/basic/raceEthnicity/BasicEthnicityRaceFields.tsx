import { BasicEthnicityRace } from 'apps/patient/add/basic/entry';
import { useEthnicityCodedValues } from 'apps/patient/data/ethnicity/useEthnicityCodedValues';
import { CheckboxGroup } from 'design-system/checkbox';
import { EntryFieldsProps } from 'design-system/entry';
import { SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { MULTI_RACE, useRaceCategoryOptions } from 'options/race';
import { Controller, useFormContext } from 'react-hook-form';

const categoryFilter = (race: Selectable) => race.value !== MULTI_RACE.value;

export const BasicRaceEthnicityFields = ({ orientation = 'horizontal', sizing = 'medium' }: EntryFieldsProps) => {
    const categories = useRaceCategoryOptions({ filter: categoryFilter });
    const { control } = useFormContext<{ ethnicityRace: BasicEthnicityRace }>();

    const { ethnicGroups } = useEthnicityCodedValues();

    return (
        <>
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
                        options={ethnicGroups}
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
        </>
    );
};
