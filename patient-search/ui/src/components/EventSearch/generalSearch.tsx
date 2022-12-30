import { Control, Controller, FieldValues } from 'react-hook-form';
import {
    InvestigationEventDateType,
    InvestigationEventIdType,
    InvestigationFilter,
    LabReportFilter,
    PregnancyStatus,
    ProviderType,
    ReportingEntityType
} from '../../generated/graphql/schema';
import { SearchCriteriaContext } from '../../providers/SearchCriteriaContext';
import { SelectControl } from '../FormInputs/SelectControl';
import { formatInterfaceString } from '../../utils/util';
import { Input } from '../FormInputs/Input';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { MultiSelectControl } from '../FormInputs/MultiSelectControl';
import { useState } from 'react';
import { SEARCH_TYPE } from '../../pages/advancedSearch/AdvancedSearch';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
    filter?: InvestigationFilter | LabReportFilter;
};

export const GeneralSearch = ({ control, searchType = '', filter }: GeneralSearchProps) => {
    const [facilityType, setFacilityType] = useState(false);

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        {searchType === SEARCH_TYPE.INVESTIGATION && (
                            <MultiSelectControl
                                // defaultValue={data?.conditions as any}
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
                        )}

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
                options={Object.values(
                    searchType === SEARCH_TYPE.INVESTIGATION ? ReportingEntityType : ProviderType
                ).map((type) => {
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
