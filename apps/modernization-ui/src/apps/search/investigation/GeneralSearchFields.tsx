import { Controller, useFormContext, useWatch } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    dateTypeOptions,
    entityOptions,
    investigationEventTypeOptions
} from './InvestigationFormTypes';
import { PregnancyStatus } from 'generated/graphql/schema';
import { AutocompleteMulti, Autocomplete } from '../../../design-system/autocomplete';
import {
    ConditionOptionsService,
    JurisdictionOptionsService,
    ProgramAreaOptionsService,
    UserOptionsService
} from 'generated';
import { SingleSelect } from 'design-system/select';
import { Selectable } from 'options';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';

const GeneralSearchFields = () => {
    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();
    const watch = useWatch({ control: form.control });

    const handleChangeConditions = (e: Selectable[]) => {
        form.setValue('conditions', e);
    };

    const handleChangeJurisdictions = (e: Selectable[]) => {
        form.setValue('jurisdictions', e);
    };

    const handleChangeProgramAreas = (e: Selectable[]) => {
        form.setValue('programAreas', e);
    };

    const condtionsResolver = (criteria: string, limit?: number) =>
        ConditionOptionsService.complete({
            criteria: criteria,
            limit: limit
        }).then((response) => response);

    const jurisdictionsResolver = (criteria: string, limit?: number) =>
        JurisdictionOptionsService.jurisdictionAutocomplete({
            criteria: criteria,
            limit: limit
        }).then((response) => response);

    const usersResolver = (criteria: string, limit?: number) => {
        return UserOptionsService.userAutocomplete({ criteria, limit }).then((response) => response);
    };

    const programAreaResolver = (criteria: string, limit?: number) => {
        return ProgramAreaOptionsService.programAreaAutocomplete({ criteria, limit }).then((response) => response);
    };

    return (
        <>
            <Controller
                name="conditions"
                control={form.control}
                render={({ field: { name } }) => (
                    <AutocompleteMulti
                        id="conditions"
                        name={name}
                        label="Conditions"
                        resolver={condtionsResolver}
                        onChange={handleChangeConditions}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="programAreas"
                render={({ field: { name } }) => (
                    <AutocompleteMulti
                        data-testid={name}
                        name={name}
                        label="Program areas"
                        id={name}
                        onChange={handleChangeProgramAreas}
                        resolver={programAreaResolver}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="jurisdictions"
                render={({ field: { name } }) => (
                    <AutocompleteMulti
                        data-testid={name}
                        id={name}
                        label="Jurisdiction"
                        name={name}
                        onChange={handleChangeJurisdictions}
                        resolver={jurisdictionsResolver}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="pregnancyStatus"
                render={({ field: { name, value } }) => (
                    <SingleSelect
                        data-testid="pregnancyStatus"
                        name={name}
                        id="pregnancyStatus"
                        label="Pregnancy test"
                        onChange={(e) => form.setValue('pregnancyStatus', e)}
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
                render={({ field: { name } }) => (
                    <SingleSelect
                        name={name}
                        label="Event id type"
                        data-testid={name}
                        options={dateTypeOptions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name={'eventDate.type'}
                render={({ field: { name } }) => (
                    <SingleSelect
                        data-testid={name}
                        name={name}
                        id="eventDateType"
                        label="Event date type"
                        options={investigationEventTypeOptions}
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
                render={({ field: { name } }) => (
                    <SingleSelect
                        id={name}
                        data-testid={name}
                        name="reportingFacility"
                        label="Event provider/facility type"
                        options={entityOptions}
                    />
                )}
            />
        </>
    );
};

export default GeneralSearchFields;
