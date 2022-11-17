import { Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { Ethnicity, Race } from '../../generated/graphql/schema';
import { formatInterfaceString } from '../../utils';

export const EthnicityForm = ({ control }: any) => {
    return (
        <>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="ethnicity"
                    render={({ field: { onChange } }) => (
                        <SelectInput
                            onChange={onChange}
                            htmlFor={'ethnicity'}
                            label="Ethnicity"
                            options={Object.values(Ethnicity).map((ethnicity) => {
                                return {
                                    name: formatInterfaceString(ethnicity),
                                    value: ethnicity
                                };
                            })}
                        />
                    )}
                />
            </Grid>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="race"
                    render={({ field: { onChange } }) => (
                        <SelectInput
                            onChange={onChange}
                            htmlFor={'race'}
                            label="Race"
                            options={Object.values(Race).map((race) => {
                                return {
                                    name: formatInterfaceString(race),
                                    value: race
                                };
                            })}
                        />
                    )}
                />
            </Grid>
        </>
    );
};
