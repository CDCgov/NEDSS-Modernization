import { Grid, Radio } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { Controller } from 'react-hook-form';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { formatInterfaceString } from 'utils/util';

export interface InputEthnicityFields {
    ethnicity: string;
    // TODO races
}
export default function EthnicityFields({ id, title, control }: { id?: string; title?: string; control?: any }) {
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) =>
                                Object.values(searchCriteria.ethnicities).map((ethnicity, key) => (
                                    <Controller
                                        key={key}
                                        control={control}
                                        name="ethnicity"
                                        render={({ field: { onChange } }) => (
                                            <Radio
                                                onChange={onChange}
                                                value={ethnicity.id.code}
                                                id={ethnicity.id.code}
                                                name={'ethnicity'}
                                                label={formatInterfaceString(ethnicity.codeDescTxt)}
                                            />
                                        )}
                                    />
                                ))
                            }
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
