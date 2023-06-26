import { Controller } from 'react-hook-form';
import { Grid, Radio } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { CodedValue } from 'coded';

type CodedValues = {
    ethnicGroups: CodedValue[];
};

type Props = { id: string; title: string; control: any; coded: CodedValues };

export default function EthnicityFields({ id, title, control, coded }: Props) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        {coded.ethnicGroups.map((ethnicity, key) => (
                            <Controller
                                key={key}
                                control={control}
                                name="ethnicity"
                                render={({ field: { onChange } }) => (
                                    <Radio
                                        onChange={onChange}
                                        value={ethnicity.value}
                                        id={ethnicity.value}
                                        name={'ethnicity'}
                                        label={ethnicity.name}
                                    />
                                )}
                            />
                        ))}
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
