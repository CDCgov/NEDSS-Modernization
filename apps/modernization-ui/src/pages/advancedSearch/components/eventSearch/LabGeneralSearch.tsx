import { useState } from 'react';
import { Control, Controller, FieldValues, useWatch } from 'react-hook-form';
import {
    LaboratoryReportEventDateType,
    LaboratoryEventIdType,
    LabReportFilter,
    PregnancyStatus,
    ProviderType,
    EntryMethod,
    UserType,
    LaboratoryReportStatus,
    EventStatus
} from '../../../../generated/graphql/schema';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { formatInterfaceString } from '../../../../utils/util';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { Input } from '../../../../components/FormInputs/Input';
import { MultiSelectControl } from '../../../../components/FormInputs/MultiSelectControl';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';
import { Checkbox, Label } from '@trussworks/react-uswds';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
    filter?: LabReportFilter;
};

let entryMethodArr: string[] = [];
let enteredByArr: string[] = [];
let eventStatusArr: string[] = [];
let processingStatusArr: string[] = [];

export const LabGeneralSearch = ({ control, filter }: GeneralSearchProps) => {
    const [facilityType, setFacilityType] = useState(false);
    const selectedEventDateType = useWatch({ control, name: 'labeventDateType' });

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <MultiSelectControl
                            defaultValue={filter?.programAreas}
                            control={control}
                            name="labprogramArea"
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
                            name="labjurisdiction"
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
                name="labpregnancyTest"
                label="Pregnancy test"
                options={[
                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                ]}
            />

            <SelectControl
                control={control}
                name="labeventIdType"
                label="Event id type"
                options={Object.values(LaboratoryEventIdType).map((event) => {
                    return {
                        name: formatInterfaceString(event),
                        value: event
                    };
                })}
            />

            <Controller
                control={control}
                name="labeventId"
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
                name="labeventDateType"
                label="Event date type"
                options={Object.values(LaboratoryReportEventDateType).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <Controller
                control={control}
                name="labfrom"
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
                name="labto"
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

            <Label htmlFor={'entryMethod'}>
                Entry method
                <Controller
                    control={control}
                    name="entryMethod"
                    render={({ field: { onChange, value } }) => {
                        entryMethodArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(EntryMethod).map((status, index) => (
                                    <Checkbox
                                        checked={value?.includes(status)}
                                        key={index}
                                        id={status}
                                        name={status}
                                        label={formatInterfaceString(status.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                entryMethodArr.push(status);
                                                onChange(entryMethodArr);
                                            } else {
                                                const index = entryMethodArr.indexOf(status);
                                                if (index > -1) {
                                                    entryMethodArr.splice(index, 1);
                                                }
                                                onChange(entryMethodArr);
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

            <Label htmlFor={'enteredBy'}>
                Entered by
                <Controller
                    control={control}
                    name="enteredBy"
                    render={({ field: { onChange, value } }) => {
                        enteredByArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(UserType).map((item, index) => (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                enteredByArr.push(item);
                                                onChange(enteredByArr);
                                            } else {
                                                const index = enteredByArr.indexOf(item);
                                                if (index > -1) {
                                                    enteredByArr.splice(index, 1);
                                                }
                                                onChange(enteredByArr);
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

            <Label htmlFor={'eventStatus'}>
                Event status
                <Controller
                    control={control}
                    name="eventStatus"
                    render={({ field: { onChange, value } }) => (
                        <div className="grid-row">
                            {Object.values(EventStatus).map((item, index) => {
                                eventStatusArr = value || [];
                                return (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                eventStatusArr.push(item);
                                                onChange(eventStatusArr);
                                            } else {
                                                const index = eventStatusArr.indexOf(item);
                                                if (index > -1) {
                                                    eventStatusArr.splice(index, 1);
                                                }
                                                onChange(eventStatusArr);
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                );
                            })}
                        </div>
                    )}
                />
            </Label>

            <Label htmlFor={'processingStatus'}>
                Processing status
                <Controller
                    control={control}
                    name="processingStatus"
                    render={({ field: { onChange, value } }) => {
                        processingStatusArr = value || [];
                        return (
                            <div className="grid-row">
                                {Object.values(LaboratoryReportStatus).map((item, index) => (
                                    <Checkbox
                                        checked={value?.includes(item)}
                                        key={index}
                                        id={item}
                                        name={item}
                                        label={formatInterfaceString(item.toLowerCase())}
                                        onChange={(e) => {
                                            if (e.target.checked) {
                                                processingStatusArr.push(item);
                                                onChange(processingStatusArr);
                                            } else {
                                                const index = processingStatusArr.indexOf(item);
                                                if (index > -1) {
                                                    processingStatusArr.splice(index, 1);
                                                }
                                                onChange(processingStatusArr);
                                            }
                                        }}
                                        className="checkbox-input"
                                    />
                                ))}
                            </div>
                        );
                    }}
                />
            </Label>

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <SelectControl
                            control={control}
                            name="labcreatedBy"
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
                            name="lablastUpdatedBy"
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
                name="labentityType"
                label="Event provider/facility type"
                onChangeMethod={(e) => setFacilityType(e.target.value && e.target.value !== '- Select -')}
                options={Object.values(ProviderType).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            {facilityType && (
                <Controller
                    control={control}
                    name="labid"
                    render={({ field: { onChange, value } }) => (
                        <Input onChange={onChange} defaultValue={value} type="text" label="ID:" htmlFor="id" id="id" />
                    )}
                />
            )}
        </>
    );
};
