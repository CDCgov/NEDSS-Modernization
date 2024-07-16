import { Controller, useFormContext, useWatch } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    dateTypeOptions,
    entityOptions,
    investigationEventTypeOptions
} from './InvestigationFormTypes';
import { PregnancyStatus } from 'generated/graphql/schema';
import { Autocomplete } from '../../../design-system/autocomplete';
import {
    // ConditionOptionsService,
    // JurisdictionOptionsService,
    // ProgramAreaOptionsService,
    UserOptionsService
} from 'generated';
import { MultiSelect, SingleSelect } from 'design-system/select';
// import { Selectable } from 'options';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { ProviderAutocomplete } from 'options/autocompete/ProviderAutocomplete';
import { ErrorMessage } from '@trussworks/react-uswds';
import { FacilityAutocomplete } from 'options/autocompete/FacilityAutocomplete';

const GeneralSearchFields = () => {
    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();
    const watch = useWatch({ control: form.control });

    // const handleChangeConditions = (e: Selectable[]) => {
    //     form.setValue('conditions', e);
    // };

    // const handleChangeJurisdictions = (e: Selectable[]) => {
    //     form.setValue('jurisdictions', e);
    // };

    // const handleChangeProgramAreas = (e: Selectable[]) => {
    //     form.setValue('programAreas', e);
    // };

    // const condtionsResolver = (criteria: string, limit?: number) =>
    //     ConditionOptionsService.complete({
    //         criteria: criteria,
    //         limit: limit
    //     }).then((response) => response);

    // const jurisdictionsResolver = (criteria: string, limit?: number) =>
    //     JurisdictionOptionsService.jurisdictionAutocomplete({
    //         criteria: criteria,
    //         limit: limit
    //     }).then((response) => response);

    const usersResolver = (criteria: string, limit?: number) => {
        return UserOptionsService.userAutocomplete({ criteria, limit }).then((response) => response);
    };

    // const programAreaResolver = (criteria: string, limit?: number) => {
    //     return ProgramAreaOptionsService.programAreaAutocomplete({ criteria, limit }).then((response) => response);
    // };

    return (
        <SearchCriteriaContext.Consumer>
            {({ searchCriteria }) => (
                <>
                    <Controller
                        name="conditions"
                        control={form.control}
                        render={({ field: { name, onChange, value } }) => (
                            <MultiSelect
                                label="Conditions"
                                onChange={onChange}
                                value={value}
                                name={name}
                                options={searchCriteria.conditions.map((c) => {
                                    return {
                                        name: c.conditionDescTxt ?? '',
                                        value: c.id,
                                        label: c.conditionDescTxt ?? ''
                                    };
                                })}
                                id={name}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="programAreas"
                        render={({ field: { name, onChange, value } }) => (
                            <MultiSelect
                                label="Program area"
                                onChange={onChange}
                                value={value}
                                name={name}
                                options={searchCriteria.programAreas.map((p) => {
                                    return {
                                        name: p.id ?? '',
                                        value: p.id,
                                        label: p.id
                                    };
                                })}
                                id={name}
                            />
                        )}
                    />
                    <Controller
                        control={form.control}
                        name="jurisdictions"
                        render={({ field: { onChange, name, value } }) => (
                            <MultiSelect
                                label="Jurisdiction"
                                onChange={onChange}
                                value={value}
                                name={name}
                                options={searchCriteria.jurisdictions.map((p) => {
                                    return {
                                        name: p.codeDescTxt ?? '',
                                        value: p.id,
                                        label: p.codeDescTxt ?? ''
                                    };
                                })}
                                id={name}
                            />
                        )}
                    />
                    <Controller
                        control={form.control}
                        name="pregnancyStatus"
                        render={({ field: { name, onChange, value } }) => (
                            <SingleSelect
                                data-testid="pregnancyStatus"
                                name={name}
                                value={value}
                                id="pregnancyStatus"
                                label="Pregnancy test"
                                onChange={onChange}
                                options={[
                                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes, label: 'Yes' },
                                    { name: PregnancyStatus.No, value: PregnancyStatus.No, label: 'No' },
                                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown, label: 'Unknown' }
                                ]}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="identification.type"
                        render={({ field: { name, value, onChange } }) => (
                            <SingleSelect
                                name={name}
                                label="Event id type"
                                data-testid={name}
                                id={name}
                                value={value}
                                onChange={onChange}
                                options={investigationEventTypeOptions}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name={'eventDate.type'}
                        render={({ field: { name, value, onChange } }) => (
                            <SingleSelect
                                data-testid={name}
                                name={name}
                                value={value}
                                onChange={onChange}
                                id="eventDateType"
                                label="Event date type"
                                options={dateTypeOptions}
                            />
                        )}
                    />

                    {watch.eventDate?.type ? (
                        <>
                            <Controller
                                shouldUnregister
                                control={form.control}
                                name="eventDate.from"
                                rules={{
                                    required: { value: true, message: 'From date is required' }
                                }}
                                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                    <DatePickerInput
                                        disabled={!watch.eventDate?.type}
                                        defaultValue={value}
                                        onBlur={onBlur}
                                        onChange={onChange}
                                        label="From"
                                        required
                                        name={name}
                                        errorMessage={error?.message}
                                    />
                                )}
                            />

                            <Controller
                                shouldUnregister
                                control={form.control}
                                name="eventDate.to"
                                rules={{
                                    required: { value: true, message: 'To date is required' }
                                }}
                                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                    <DatePickerInput
                                        disabled={!watch.eventDate?.type}
                                        defaultValue={value}
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        name={name}
                                        label="To"
                                        required
                                        errorMessage={error?.message}
                                    />
                                )}
                            />
                        </>
                    ) : null}

                    <Controller
                        control={form.control}
                        name="createdBy"
                        render={({ field: { name, onBlur } }) => (
                            <Autocomplete
                                data-testid="createdBy"
                                name={name}
                                label="Event creeated by"
                                id={'createdBy'}
                                onBlur={onBlur}
                                resolver={usersResolver}
                            />
                        )}
                    />
                    <Controller
                        control={form.control}
                        name="updatedBy"
                        render={({ field: { name, onBlur } }) => (
                            <Autocomplete
                                name={name}
                                label="Event updated by"
                                id={'updatedBy'}
                                resolver={usersResolver}
                                onBlur={onBlur}
                            />
                        )}
                    />

                    <Controller
                        control={form.control}
                        name="reportingFacility"
                        render={({ field: { name, value, onChange } }) => (
                            <SingleSelect
                                id={name}
                                value={value}
                                onChange={onChange}
                                data-testid={name}
                                name="reportingFacility"
                                label="Event provider/facility type"
                                options={entityOptions}
                            />
                        )}
                    />

                    {watch.reportingFacility?.value == 'REPORTING_PROVIDER' && (
                        <Controller
                            shouldUnregister
                            control={form.control}
                            name="reportingProvider"
                            rules={{
                                required: { value: true, message: `Provider is required` }
                            }}
                            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                                <>
                                    <ProviderAutocomplete
                                        id={name}
                                        label="Event provider type"
                                        placeholder=""
                                        onChange={onChange}
                                        required={true}
                                        onBlur={onBlur}
                                    />
                                    {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                                </>
                            )}
                        />
                    )}

                    {watch.reportingFacility?.value == 'REPORTING_FACILITY' && (
                        <Controller
                            shouldUnregister
                            control={form.control}
                            name="reportingProvider"
                            rules={{
                                required: { value: true, message: `Facility is required` }
                            }}
                            render={({ field: { onBlur, onChange, name }, fieldState: { error } }) => (
                                <>
                                    <FacilityAutocomplete
                                        id={name}
                                        label="Event facility type"
                                        placeholder=""
                                        onChange={(e) => onChange(e?.value)}
                                        required={true}
                                        onBlur={onBlur}
                                    />
                                    {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                                </>
                            )}
                        />
                    )}
                </>
            )}
        </SearchCriteriaContext.Consumer>
    );
};

export default GeneralSearchFields;
