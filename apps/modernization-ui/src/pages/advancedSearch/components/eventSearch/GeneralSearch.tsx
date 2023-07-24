import { useState } from 'react';
import { Control, Controller, FieldValues, useWatch } from 'react-hook-form';
import {
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    PregnancyStatus,
    ReportingEntityType
} from '../../../../generated/graphql/schema';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { formatInterfaceString } from '../../../../utils/util';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { Input } from '../../../../components/FormInputs/Input';
import { MultiSelectControl } from '../../../../components/FormInputs/MultiSelectControl';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    filter?: InvestigationFilter;
};

export const GeneralSearch = ({ control, filter }: GeneralSearchProps) => {
    const [facilityType, setFacilityType] = useState(false);
    const selectedEventDateType = useWatch({ control, name: 'eventDateType' });

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <MultiSelectControl
                            defaultValue={filter?.conditions}
                            control={control}
                            name="condition"
                            label="Condition"
                            options={searchCriteria.conditions.map((c) => {
                                return {
                                    label: c.conditionDescTxt,
                                    value: c.conditionDescTxt
                                };
                            })}
                        />

                        <MultiSelectControl
                            defaultValue={filter?.programAreas}
                            control={control}
                            name="programArea"
                            label="Program area"
                            options={searchCriteria.programAreas.map((p) => {
                                return {
                                    label: p.id,
                                    value: p.id
                                };
                            })}
                        />

                        <MultiSelectControl
                            control={control}
                            defaultValue={filter?.jurisdictions}
                            name="jurisdiction"
                            label="Jurisdiction"
                            options={searchCriteria.jurisdictions.map((j) => {
                                return {
                                    label: j.codeDescTxt,
                                    value: j.id
                                };
                            })}
                        />
                    </>
                )}
            </SearchCriteriaContext.Consumer>

            <SelectControl
                control={control}
                name="pregnancyTest"
                label="Pregnancy test"
                options={[
                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                ]}
            />

            <SelectControl
                control={control}
                name="eventIdType"
                label="Event id type"
                options={Object.values(InvestigationEventIdType).map((event) => {
                    return {
                        name: formatInterfaceString(event),
                        value: event
                    };
                })}
            />

            <Controller
                control={control}
                name="eventId"
                render={({ field: { onChange, value } }) => (
                    <Input
                        onChange={onChange}
                        defaultValue={value}
                        type="text"
                        label="Event id"
                        htmlFor="eventId"
                        id="eventId"
                    />
                )}
            />

            <SelectControl
                control={control}
                name="eventDateType"
                label="Event date type"
                options={Object.values(InvestigationEventDateType).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <Controller
                control={control}
                name="from"
                render={({ field: { onChange, value } }) => (
                    <DatePickerInput
                        disabled={!selectedEventDateType}
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={'from'}
                        label="From"
                    />
                )}
            />

            <Controller
                control={control}
                name="to"
                render={({ field: { onChange, value } }) => (
                    <DatePickerInput
                        disabled={!selectedEventDateType}
                        defaultValue={value}
                        onChange={onChange}
                        htmlFor={'to'}
                        label="To"
                    />
                )}
            />

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <SelectControl
                            control={control}
                            name="createdBy"
                            label="Event created by user"
                            options={searchCriteria.userResults.map((user) => {
                                return {
                                    name: `${user.userLastNm}, ${user.userFirstNm}`,
                                    value: user.nedssEntryId
                                };
                            })}
                        />

                        <SelectControl
                            control={control}
                            name="lastUpdatedBy"
                            label="Event updated by user"
                            options={searchCriteria.userResults.map((user) => {
                                return {
                                    name: `${user.userLastNm}, ${user.userFirstNm}`,
                                    value: user.nedssEntryId
                                };
                            })}
                        />
                    </>
                )}
            </SearchCriteriaContext.Consumer>

            <SelectControl
                control={control}
                name="entityType"
                label="Event provider/facility type"
                onChangeMethod={(e) => setFacilityType(e.target.value && e.target.value !== '- Select -')}
                options={Object.values(ReportingEntityType).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            {facilityType && (
                <Controller
                    control={control}
                    name="id"
                    render={({ field: { onChange, value } }) => (
                        <Input onChange={onChange} defaultValue={value} type="text" label="ID:" htmlFor="id" id="id" />
                    )}
                />
            )}
        </>
    );
};
