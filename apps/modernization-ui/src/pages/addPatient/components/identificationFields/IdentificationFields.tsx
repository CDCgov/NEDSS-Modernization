import { Button, Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { Controller } from 'react-hook-form';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { formatInterfaceString } from '../../../../utils/util';
import { Input } from '../../../../components/FormInputs/Input';

export const IdentificationFields = ({
    id,
    title,
    control,
    fields,
    append
}: {
    id: string;
    title: string;
    control: any;
    fields: any;
    append: any;
}) => {
    const handleAddAnotherId = () => {
        append({ identificationType: null, assigningAuthority: null, identificationNumber: null });
    };

    return (
        <FormCard id={id} title={title}>
            {fields.map((item: any, index: number) => (
                <Grid col={12} className="padding-x-3" key={item.id}>
                    <Grid row>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => (
                                <Grid col={6}>
                                    <Controller
                                        control={control}
                                        name={`identification[${index}].identificationType`}
                                        render={({ field: { onChange } }) => (
                                            <SelectInput
                                                options={Object.values(searchCriteria.identificationTypes).map(
                                                    (type) => {
                                                        return {
                                                            name: formatInterfaceString(type.codeDescTxt),
                                                            value: type.id.code
                                                        };
                                                    }
                                                )}
                                                onChange={onChange}
                                                htmlFor={`identification[${index}].identificationType`}
                                                label="ID type"
                                            />
                                        )}
                                    />
                                </Grid>
                            )}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                    <Grid row>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`identification[${index}].assigningAuthority`}
                                render={({ field: { onChange } }) => (
                                    <SelectInput
                                        options={[
                                            { name: 'CA', value: 'CA' },
                                            { name: 'LA', value: 'LA' },
                                            { name: 'AL', value: 'AL' }
                                        ]}
                                        onChange={onChange}
                                        htmlFor={`identification[${index}].assigningAuthority`}
                                        label="Assigning authority "
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row>
                        <Grid col={6}>
                            <Controller
                                control={control}
                                name={`identification[${index}].identificationNumber`}
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        defaultValue={value}
                                        onChange={onChange}
                                        type="text"
                                        label="ID value"
                                        htmlFor={`identification[${index}].identificationNumber`}
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
