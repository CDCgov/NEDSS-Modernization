import { useState } from 'react';
import { Control, Controller, FieldValues } from 'react-hook-form';
import {
    LaboratoryReportEventDateType,
    LaboratoryEventIdType,
    LabReportFilter,
    PregnancyStatus,
    ProviderType
} from '../../../../generated/graphql/schema';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { formatInterfaceString } from '../../../../utils/util';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { Input } from '../../../../components/FormInputs/Input';
import { MultiSelectControl } from '../../../../components/FormInputs/MultiSelectControl';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
    filter?: LabReportFilter;
};

export const LabGeneralSearch = ({ control, filter }: GeneralSearchProps) => {
    const [facilityType, setFacilityType] = useState(false);

    return (
        <>
            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <MultiSelectControl
                            defaultValue={filter?.programAreas}
                            control={control}
                            name="labprogramArea"
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
                            name="labjurisdiction"
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
                name="labpregnancyTest"
                label="Pregnancy Test:"
                options={[
                    { name: PregnancyStatus.Yes, value: PregnancyStatus.Yes },
                    { name: PregnancyStatus.No, value: PregnancyStatus.No },
                    { name: PregnancyStatus.Unknown, value: PregnancyStatus.Unknown }
                ]}
            />

            <SelectControl
                control={control}
                name="labeventIdType"
                label="Event ID Type:"
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
                        label="Event ID:"
                        htmlFor="eventId"
                        id="eventId"
                    />
                )}
            />

            <SelectControl
                control={control}
                name="labeventDateType"
                label="Event Date Type:"
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
                    <DatePickerInput defaultValue={value} onChange={onChange} htmlFor={'from'} label="From" />
                )}
            />

            <Controller
                control={control}
                name="labto"
                render={({ field: { onChange, value } }) => (
                    <DatePickerInput defaultValue={value} onChange={onChange} htmlFor={'to'} label="To" />
                )}
            />

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <SelectControl
                            control={control}
                            name="labcreatedBy"
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
                            name="lablastUpdatedBy"
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
                name="labentityType"
                label="Event Provider/Facility Type:"
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
