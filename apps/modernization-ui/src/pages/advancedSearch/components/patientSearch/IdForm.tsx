import { Grid } from '@trussworks/react-uswds';
import { Input } from '../../../../components/FormInputs/Input';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Control, Controller } from 'react-hook-form';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { PersonFilter } from 'generated/graphql/schema';

type IDFormProps = {
    control: Control<PersonFilter>;
};
export const IDForm = ({ control }: IDFormProps) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <Grid col={12}>
                        <Controller
                            control={control}
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
                <Controller
                    control={control}
                    name="identification.identificationNumber"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            defaultValue={value}
                            type="text"
                            label="ID number"
                            htmlFor="identificationNumber"
                            id="identificationNumber"
                        />
                    )}
                />
            </Grid>
        </>
    );
};
