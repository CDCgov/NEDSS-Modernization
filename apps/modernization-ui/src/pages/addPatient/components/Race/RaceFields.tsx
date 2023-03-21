import { Checkbox, Fieldset, Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { formatInterfaceString } from '../../../../utils/util';

export interface InputEthnicityFields {
    ethnicity: string;
    // TODO races
}
export default function RaceFields({ title, id }: { id?: string; title?: string }) {
    return (
        <FormCard title={title} id={id}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        <Fieldset>
                            <SearchCriteriaContext.Consumer>
                                {({ searchCriteria }) =>
                                    Object.values(searchCriteria.races).map((race, index) => (
                                        <Checkbox
                                            key={index}
                                            id={race.id.code}
                                            name={race.id.code}
                                            label={formatInterfaceString(race.codeDescTxt)}
                                        />
                                    ))
                                }
                            </SearchCriteriaContext.Consumer>
                        </Fieldset>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
