import { Checkbox, Fieldset, Grid } from '@trussworks/react-uswds';
import { Controller, useFormContext } from 'react-hook-form';
import FormCard from 'components/FormCard/FormCard';
import { CodedValue } from 'coded';

type CodedValues = {
    raceCategories: CodedValue[];
};

type Props = { id: string; title: string; coded: CodedValues };

export default function RaceFields({ id, title, coded }: Props) {
    const { control } = useFormContext();

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
                                            {coded.raceCategories.map((race, index) => (
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
