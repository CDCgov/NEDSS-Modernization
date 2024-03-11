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
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        htmlFor={'ethnicity'}
                                        label="Ethnicity"
                                        id="ethnicity"
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
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        defaultValue={value}
                                        onChange={onChange}
                                        htmlFor={'race'}
                                        label="Race"
                                        id="race"
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
