import { useState } from 'react';
import { Control, Controller, FieldValues } from 'react-hook-form';
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

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <MultiSelectControl
                            defaultValue={filter?.conditions}
                            control={control}
                            name="conditon"
                            label="Condition:"
                            options={searchCriteria.conditions.map((c) => {
                                return {
                                    name: c.conditionDescTxt,
                                    value: c.id
                                };
                            })}
                        />

                        <MultiSelectControl
                            defaultValue={filter?.programAreas}
                            control={control}
                            name="programArea"
                            label="Program Area:"
                            options={searchCriteria.programAreas.map((p) => {
                                return {
                                    name: p.id,
                                    value: p.id
                                };
                            })}
                        />

                        <MultiSelectControl
                            control={control}
                            defaultValue={filter?.jurisdictions}
                            name="jurisdiction"
                            label="Jurisdiction:"
                            options={searchCriteria.jurisdictions.map((j) => {
                                return {
                                    name: j.codeDescTxt,
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
                label="Pregnancy Test:"
                options={[
                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                ]}
            />

            <SelectControl
                control={control}
                name="eventIdType"
                label="Event ID Type:"
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
                        label="Event ID:"
                        htmlFor="eventId"
                        id="eventId"
                    />
                )}
            />

            <SelectControl
                control={control}
                name="eventDateType"
                label="Event Date Type:"
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
                    <DatePickerInput defaultValue={value} onChange={onChange} htmlFor={'from'} label="From" />
                )}
            />

            <Controller
                control={control}
                name="to"
                render={({ field: { onChange, value } }) => (
                    <DatePickerInput defaultValue={value} onChange={onChange} htmlFor={'to'} label="To" />
                )}
            />

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <SelectControl
                            control={control}
                            name="createdBy"
                            label="Event Created By User:"
                            options={searchCriteria.userResults.map((user) => {
                                return {
                                    name: `${user.userLastNm}, ${user.userFirstNm}`,
                                    value: user.userId
                                };
                            })}
                        />

                        <SelectControl
                            control={control}
                            name="lastUpdatedBy"
                            label="Event Updated By User:"
                            options={searchCriteria.userResults.map((user) => {
                                return {
                                    name: `${user.userLastNm}, ${user.userFirstNm}`,
                                    value: user.userId
                                };
                            })}
                        />
                    </>
                )}
            </SearchCriteriaContext.Consumer>

            <SelectControl
                control={control}
                name="entityType"
                label="Event Provider/Facility Type:"
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
