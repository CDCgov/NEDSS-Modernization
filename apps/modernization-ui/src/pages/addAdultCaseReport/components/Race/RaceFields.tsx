import { Checkbox, Fieldset, Grid, Button } from '@trussworks/react-uswds';
import { Controller, useFieldArray, useFormContext } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { CodedValue } from 'coded';

type CodedValues = {
    raceCategories: CodedValue[];
};

type Props = { id: string; title: string; coded: CodedValues };

export default function RaceFields({ id, title, coded }: Props) {

    const { control } = useFormContext();

    const { fields, append } = useFieldArray({
        control,
        name: 'identification'
    });
    let raceCategories = [{value: 4, name:'American Indian or Alaska Native'}, {value: 1, name:'Black or African American'}, {value: 2, name:'White'}, {value: 3, name:'Asian'}, {value: 5, name:'Native Hawaiian or Other Pacific Islander'}, {value: 6, name:'Other'}, {value: 7, name:'Refused to Answer'}, {value: 8, name:'Not Asked'}, {value: 9, name:'Unknown'} ];

    const handleAddAnotherId = () => {
        append({ type: null, authority: null, value: null });
    };
    // debugger

    const tempArr: any = [];
    return (
        <FormCard title={title} id={id}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        <Fieldset>
                            <Controller
                                control={control}
                                name="race"
                                render={({ field: { onChange, value } }) => {
                                    return (
                                        <>
                                            {raceCategories.map((race, index) => (
                                                <Checkbox
                                                    key={index}
                                                    onChange={(e) => {
                                                        if (!value || value?.length === 0) {
                                                            if (e.target.checked) {
                                                                tempArr.push(e.target.value);
                                                                onChange(tempArr);
                                                            } else {
                                                                const index = tempArr.indexOf(e.target.value);
                                                                if (index > -1) {
                                                                    tempArr.splice(index, 1);
                                                                }
                                                                onChange(tempArr);
                                                            }
                                                        } else {
                                                            if (e.target.checked) {
                                                                value.push(e.target.value);
                                                                onChange(value);
                                                            } else {
                                                                const index = value.indexOf(e.target.value);
                                                                if (index > -1) {
                                                                    value.splice(index, 1);
                                                                }
                                                                onChange(value);
                                                            }
                                                        }
                                                    }}
                                                    defaultChecked={
                                                        value?.find((it: any) => it === race.value) || false
                                                    }
                                                    value={value?.find((it: any) => it === race.value) || race.value}
                                                    id={race.value}
                                                    name={'race'}
                                                    label={race.name}
                                                />
                                            ))}
                                        </>
                                    );
                                }}
                            />
                        </Fieldset>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
