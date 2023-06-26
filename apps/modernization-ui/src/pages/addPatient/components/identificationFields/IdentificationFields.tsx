import { Controller } from 'react-hook-form';
import { Button, Grid } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { CodedValue } from 'coded';

type CodedValues = {
    identificationTypes: CodedValue[];
    assigningAuthorities: CodedValue[];
};

type Props = { id: string; title: string; control: any; fields: any; append: any; coded: CodedValues };

export const IdentificationFields = ({ id, title, control, fields, append, coded }: Props) => {
    const handleAddAnotherId = () => {
        append({ type: null, authority: null, value: null });
    };

    return (
        <FormCard id={id} title={title}>
            {fields.map((item: any, index: number) => (
                <Grid col={12} className="padding-x-3 padding-bottom-2" key={item.id}>
                    <Grid row>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`identification[${index}].type`}
                                render={({ field: { onChange } }) => {
                                    return (
                                        <SelectInput
                                            defaultValue={control._formValues['identification']?.[index]?.type || ''}
                                            options={coded.identificationTypes}
                                            onChange={onChange}
                                            htmlFor={`identification[${index}].type`}
                                            label="ID type"
                                        />
                                    );
                                }}
                            />
                        </Grid>
                    </Grid>
                    <Grid row>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`identification[${index}].authority`}
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        onChange={onChange}
                                        defaultValue={value}
                                        name={`identification[${index}].authority`}
                                        htmlFor={`identification[${index}].authority`}
                                        label="Assigning authority"
                                        options={coded.assigningAuthorities}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`identification[${index}].value`}
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        defaultValue={value}
                                        onChange={onChange}
                                        type="text"
                                        label="ID value"
                                        htmlFor={`identification[${index}].value`}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                </Grid>
            ))}
            <Grid col={12} className="padding-x-3 padding-bottom-3 padding-top-0">
                <Button
                    type={'button'}
                    onClick={handleAddAnotherId}
                    className="text-bold"
                    unstyled
                    style={{ margin: '0', padding: '0' }}>
                    + Add another ID
                </Button>
            </Grid>
        </FormCard>
    );
};
