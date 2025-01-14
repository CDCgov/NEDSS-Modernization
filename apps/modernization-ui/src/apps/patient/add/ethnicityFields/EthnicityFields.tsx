import { Controller, useFormContext } from 'react-hook-form';
import { Radio } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { CodedValue } from 'coded';

type CodedValues = {
    ethnicGroups: CodedValue[];
};

type Props = { id: string; title: string; coded: CodedValues };

export default function EthnicityFields({ id, title, coded }: Props) {
    const { control } = useFormContext();

    return (
        <FormCard id={id} title={title}>
            <div className="fields">
                {coded.ethnicGroups.map((ethnicity, key) => (
                    <Controller
                        key={key}
                        control={control}
                        name="ethnicity"
                        render={({ field: { onChange, name, value } }) => (
                            <Radio
                                onChange={onChange}
                                checked={value === ethnicity.value}
                                defaultValue={ethnicity.value}
                                id={ethnicity.value}
                                name={name}
                                label={ethnicity.name}
                            />
                        )}
                    />
                ))}
            </div>
        </FormCard>
    );
}
