import { Grid } from '@trussworks/react-uswds';
import { PersonFilter } from 'generated/graphql/schema';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';

type IDFormProps = {
    control: UseFormReturn<PersonFilter>;
};
export const IDForm = ({ control: form }: IDFormProps) => {
    const identificationType = useWatch({ control: form.control, name: 'identification.identificationType' });
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="identification.identificationType"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    options={Object.values(searchCriteria.identificationTypes).map((type) => {
                                        return {
                                            name: type.codeDescTxt,
                                            value: type.id.code
                                        };
                                    })}
                                    onChange={onChange}
                                    htmlFor={'identificationType'}
                                    label="ID type"
                                />
                            )}
                        />
                    </Grid>
                )}
            </SearchCriteriaContext.Consumer>
            <Grid col={12}>
                {identificationType ? (
                    <>
                        <Controller
                            control={form.control}
                            name="identification.identificationNumber"
                            rules={{ required: true }}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="ID number"
                                    required
                                    htmlFor="identificationNumber"
                                    id="identificationNumber"
                                />
                            )}
                        />
                    </>
                ) : null}
            </Grid>
        </>
    );
};
