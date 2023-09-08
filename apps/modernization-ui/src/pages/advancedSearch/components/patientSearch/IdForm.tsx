import { Grid } from '@trussworks/react-uswds';
import { Input } from '../../../../components/FormInputs/Input';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';

export const IDForm = ({ control }: any) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="identificationType"
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
                    name="identificationNumber"
                    render={({ field: { onChange, value } }) => (
                        <Input
                            onChange={onChange}
                            value={value}
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
