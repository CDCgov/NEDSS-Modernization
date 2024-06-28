import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { Controller, UseFormReturn, useWatch } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { MultiSelect } from 'design-system/select';
import { InvestigationEventIdType, PregnancyStatus, ReportingEntityType } from 'generated/graphql/schema';
import { formatInterfaceString } from 'utils/util';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ErrorMessage } from '@trussworks/react-uswds';
import { Input } from 'components/FormInputs/Input';

type Props = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const GeneralSearchFields = ({ form }: Props) => {
    const watch = useWatch({ control: form.control });
    const { register } = form;
    const handleChange = (e: any) => {
        console.log('e', e);
    };

    console.log('jurisdictions', form.watch('jurisdictions'));

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => {
                    console.log('searchCriteria', searchCriteria);
                    return (
                        <>
                            <MultiSelect
                                id="conditions"
                                label="Condition"
                                {...register('conditions')}
                                onChange={handleChange}
                                options={searchCriteria.conditions.map((c) => {
                                    return {
                                        label: 'asdsdfsdf',
                                        name: c.conditionDescTxt ?? '',
                                        value: c.id
                                    };
                                })}
                            />

                            <MultiSelect
                                id="programArea"
                                label="Program area"
                                {...register('programAreas')}
                                onChange={handleChange}
                                options={searchCriteria.programAreas.map((p) => {
                                    return {
                                        label: 'asdfasdf',
                                        name: p.id,
                                        value: p.id
                                    };
                                })}
                            />
                            <MultiSelect
                                id="jurisdiction"
                                label="Jurisdiction"
                                {...register('jurisdictions')}
                                onChange={handleChange}
                                options={searchCriteria.jurisdictions.map((j) => {
                                    return {
                                        label: 'asdfasdf',
                                        name: j.codeDescTxt ?? '',
                                        value: j.id
                                    };
                                })}
                            />
                            <SelectInput
                                id="pregnancyStatus"
                                label="Pregnancy test"
                                {...register('pregnancyStatus')}
                                onChange={handleChange}
                                options={[
                                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                                ]}
                            />
                            <SelectInput
                                id="eventType"
                                label="Event date type"
                                {...register('eventDate')}
                                onChange={handleChange}
                                options={Object.values(InvestigationEventIdType).map((event) => {
                                    return {
                                        label: formatInterfaceString(event),
                                        name: formatInterfaceString(event),
                                        value: event
                                    };
                                })}
                            />
                            {watch.eventId?.investigationEventType ? (
                                <Controller
                                    control={form.control}
                                    name="eventId.id"
                                    rules={{
                                        required: { value: true, message: 'Event Id is required' }
                                    }}
                                    render={({
                                        field: { onBlur, onChange, value, name },
                                        fieldState: { error }
                                    }: any) => (
                                        <>
                                            <Input
                                                onChange={onChange}
                                                onBlur={onBlur}
                                                data-testid={name}
                                                defaultValue={value}
                                                type="text"
                                                id={name}
                                                required
                                            />
                                            {error && (
                                                <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>
                                            )}
                                        </>
                                    )}
                                />
                            ) : null}
                            <Input
                                {...register('createdBy')}
                                label="Event creeated by"
                                type="text"
                                id={'createdBy'}
                                required
                            />
                            <Input
                                {...register('lastUpdatedBy')}
                                label="Event updated by"
                                type="text"
                                id={'lastUpdatedBy'}
                                required
                            />
                            <SelectInput
                                {...register('providerFacilitySearch')}
                                label="Event provider/facility type"
                                options={Object.values(ReportingEntityType).map((type) => {
                                    return {
                                        name: formatInterfaceString(type),
                                        value: type
                                    };
                                })}
                            />
                        </>
                    );
                }}
            </SearchCriteriaContext.Consumer>
        </>
    );
};

export default GeneralSearchFields;
