import { Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { Race } from '../../../../generated/graphql/schema';
import { formatInterfaceString } from '../../../../utils/util';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';

export const EthnicityForm = ({ control }: any) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="ethnicity"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'ethnicity'}
                                    label="Ethnicity"
                                    options={Object.values(searchCriteria.ethnicities).map((ethnicity) => {
                                        return {
                                            name: ethnicity.codeDescTxt,
                                            value: ethnicity.id.code
                                        };
                                    })}
                                />
                            )}
                        />
                    </Grid>
                )}
            </SearchCriteriaContext.Consumer>
            <Grid col={12}>
                <Controller
                    control={control}
                    name="race"
                    render={({ field: { onChange, value } }) => (
                        <SelectInput
                            defaultValue={value}
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
