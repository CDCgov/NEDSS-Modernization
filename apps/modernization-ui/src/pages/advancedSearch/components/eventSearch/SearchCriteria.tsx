import { Control, FieldValues } from 'react-hook-form';
import {
    CaseStatus,
    InvestigationFilter,
    InvestigationStatus,
    LabReportFilter,
    NotificationStatus,
    ProcessingStatus
} from '../../../../generated/graphql/schema';
import { SelectControl } from '../../../../components/FormInputs/SelectControl';
import { formatInterfaceString } from '../../../../utils/util';
import { CheckBoxControl } from '../../../../components/FormInputs/CheckBoxControl';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    filter?: InvestigationFilter | LabReportFilter;
};

export const SearchCriteria = ({ control }: GeneralSearchProps) => {
    return (
        <>
            <SelectControl
                control={control}
                name="investigationStatus"
                label="Investigation status"
                options={Object.values(InvestigationStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <>
                        <SelectControl
                            control={control}
                            name="outbreakNames"
                            label="Outbreak name"
                            options={Object.values(searchCriteria.outbreaks).map((outbreak) => {
                                return {
                                    name: formatInterfaceString(outbreak.codeShortDescTxt ?? ''),
                                    value: outbreak.id.code
                                };
                            })}
                        />
                    </>
                )}
            </SearchCriteriaContext.Consumer>

            <CheckBoxControl control={control} id="case" name="case" label="Including unassigned status" />
            <SelectControl
                control={control}
                name="statusList"
                label="Case status"
                options={Object.values(CaseStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <CheckBoxControl control={control} id="processing" name="processing" label="Including unassigned status" />
            <SelectControl
                control={control}
                name="processingStatus"
                label="Current processing status"
                options={Object.values(ProcessingStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <CheckBoxControl
                control={control}
                id="notification"
                name="notification"
                label="Including unassigned status"
            />
            <SelectControl
                control={control}
                name="notificationStatus"
                label="Notification status"
                options={Object.values(NotificationStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />
        </>
    );
};
