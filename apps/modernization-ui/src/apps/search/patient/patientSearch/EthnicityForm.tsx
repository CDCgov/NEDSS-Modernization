import { Grid } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Controller } from 'react-hook-form';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';

export const EthnicityForm = ({ control }: any) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <Grid col={12}>
                            <Controller
                                control={control}
                                name="ethnicity"
                                render={({ field: { onChange, value, name } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        label="Ethnicity"
                                        htmlFor={name}
                                        id={name}
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
                        <Grid col={12}>
                            <Controller
                                control={control}
                                name="race"
                                render={({ field: { onChange, value, name } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        label="Race"
                                        htmlFor={name}
                                        id={name}
                                        options={Object.values(searchCriteria.races).map((race) => {
                                            return {
                                                name: race.codeDescTxt,
                                                value: race.id.code
                                            };
                                        })}
                                    />
                                )}
                            />
                        </Grid>
                    </>
                )}
            </SearchCriteriaContext.Consumer>
        </>
    );
};
