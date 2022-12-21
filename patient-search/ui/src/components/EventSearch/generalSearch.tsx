import { Control, Controller, FieldValues } from 'react-hook-form';
import {
    EventType,
    InvestigationEventDateType,
    InvestigationEventIdType,
    PregnancyStatus
} from '../../generated/graphql/schema';
import { SearchCriteriaContext } from '../../providers/SearchCriteriaContext';
import { SelectControl } from '../FormInputs/SelectControl';
import { formatInterfaceString } from '../../utils/util';
import { Input } from '../FormInputs/Input';
import { DatePickerInput } from '../FormInputs/DatePickerInput';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
};

export const GeneralSearch = ({ control, searchType = '' }: GeneralSearchProps) => {
    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        {searchType === EventType.Investigation && (
                            <SelectControl
                                // isMulti={true}
                                control={control}
                                name="condition"
                                label="Condition:"
                                options={searchCriteria.conditions.map((c) => {
                                    return {
                                        name: c.conditionDescTxt,
                                        value: c.id
                                    };
                                })}
                            />
                        )}

                        <SelectControl
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
                        <SelectControl
                            control={control}
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
        </>
    );
};
