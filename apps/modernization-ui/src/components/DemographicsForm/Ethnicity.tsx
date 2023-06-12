import { Controller, useForm } from 'react-hook-form';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { SelectInput } from '../FormInputs/SelectInput';
import { formatInterfaceString } from '../../utils/util';
import { SearchCriteriaContext } from '../../providers/SearchCriteriaContext';
import { BaseSyntheticEvent, useState } from 'react';

export const EthnicityForm = ({ setEthnicityForm }: any) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = () => {
        setEthnicityForm();
    };

    const [selectedEthinicity, setSelectedEthinicity] = useState<'2135-2' | '2186-5' | 'UNK' | 'NOT-TO-ANS' | ''>('');

    const handleOnChange = (fn?: (...event: any[]) => void) => (changed: BaseSyntheticEvent) => {
        setSelectedEthinicity(changed?.currentTarget?.value);
        fn && fn(changed);
    };

    return (
        <>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1 label-text">
                    As of:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="nameAsOf"
                        render={({ field: { onChange, value } }) => (
                            <DatePickerInput
                                defaultValue={value}
                                onChange={onChange}
                                name="nameAsOf"
                                htmlFor={'nameAsOf'}
                            />
                        )}
                    />
                </Grid>
            </Grid>
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Ethnicity:
                </Grid>
                <Grid col={6}>
                    <SearchCriteriaContext.Consumer>
                        {({ searchCriteria }) => (
                            <Controller
                                control={control}
                                name="ethnicity"
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        dataTestid="ethnicity"
                                        name="ethnicity"
                                        defaultValue={value}
                                        onChange={handleOnChange(onChange)}
                                        htmlFor={'ethnicity'}
                                        options={Object.values(searchCriteria.ethnicities).map((ethnicity) => {
                                            return {
                                                name: formatInterfaceString(ethnicity.codeDescTxt),
                                                value: ethnicity.id.code
                                            };
                                        })}
                                    />
                                )}
                            />
                        )}
                    </SearchCriteriaContext.Consumer>
                </Grid>
            </Grid>
            {selectedEthinicity === '2135-2' && (
                <Grid row className="flex-justify flex-align-center padding-2">
                    <Grid col={6} className="margin-top-1">
                        Spanish origin:
                    </Grid>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="spanish"
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'spanish'}
                                    options={[]}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            )}
            <Grid row className="flex-justify flex-align-center padding-2">
                <Grid col={6} className="margin-top-1">
                    Reason unknowm:
                </Grid>
                <Grid col={6}>
                    <Controller
                        control={control}
                        name="reason"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput defaultValue={value} onChange={onChange} htmlFor={'reason'} options={[]} />
                        )}
                    />
                </Grid>
            </Grid>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button
                        type="button"
                        className="margin-top-0"
                        data-testid="cancel-btn"
                        outline
                        onClick={setEthnicityForm}>
                        Cancel
                    </Button>
                    <Button
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        Save
                    </Button>
                </ButtonGroup>
            </div>
        </>
    );
};
