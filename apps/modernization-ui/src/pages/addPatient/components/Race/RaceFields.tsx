import { Checkbox, Fieldset, Grid } from '@trussworks/react-uswds';
import FormCard from '../../../../components/FormCard/FormCard';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { formatInterfaceString } from '../../../../utils/util';
import { Controller } from 'react-hook-form';

export interface InputEthnicityFields {
    ethnicity: string;
    // TODO races
}
export default function EthnicityFields({ id, title, control }: { id?: string; title?: string; control?: any }) {
    const tempArr: any = [];
    return (
        <FormCard title={title} id={id}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12}>
                        <Fieldset>
                            <Controller
                                control={control}
                                name="race"
                                render={({ field: { onChange, value } }) => {
                                    return (
                                        <SearchCriteriaContext.Consumer>
                                            {({ searchCriteria }) =>
                                                Object.values(searchCriteria.races).map((race, index) => (
                                                    <Checkbox
                                                        key={index}
                                                        onChange={(e) => {
                                                            if (!value || value?.length === 0) {
                                                                if (e.target.checked) {
                                                                    tempArr.push(e.target.value);
                                                                    onChange(tempArr);
                                                                } else {
                                                                    const index = tempArr.indexOf(e.target.value);
                                                                    if (index > -1) {
                                                                        tempArr.splice(index, 1);
                                                                    }
                                                                    onChange(tempArr);
                                                                }
                                                            } else {
                                                                if (e.target.checked) {
                                                                    value.push(e.target.value);
                                                                    onChange(value);
                                                                } else {
                                                                    const index = value.indexOf(e.target.value);
                                                                    if (index > -1) {
                                                                        value.splice(index, 1);
                                                                    }
                                                                    onChange(value);
                                                                }
                                                            }
                                                        }}
                                                        defaultChecked={
                                                            value?.find((it: any) => it === race.id.code) || false
                                                        }
                                                        checked={value?.find((it: any) => it === race.id.code) || false}
                                                        value={
                                                            value?.find((it: any) => it === race.id.code) ||
                                                            race.id.code
                                                        }
                                                        id={race.id.code}
                                                        name={'race'}
                                                        label={formatInterfaceString(race.codeDescTxt)}
                                                    />
                                                ))
                                            }
                                        </SearchCriteriaContext.Consumer>
                                    );
                                }}
                            />
                        </Fieldset>
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
