import { Grid } from '@trussworks/react-uswds';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { IdentificationType } from '../../generated/graphql/schema';
import { formatInterfaceString } from '../../utils';

export const IDForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="identificationType"
                    render={({ field: { onChange } }) => (
                        <SelectInput
                            options={Object.values(IdentificationType).map((type) => {
                                return {
                                    name: formatInterfaceString(type),
                                    value: type
                                };
                            })}
                            onChange={onChange}
                            htmlFor={'identificationType'}
                            label="ID type"
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="identificationNumber"
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
