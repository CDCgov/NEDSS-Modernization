import { Control, FieldValues } from 'react-hook-form';
import {
    CaseStatus,
    EventFilter,
    EventType,
    InvestigationStatus,
    NotificationStatus,
    ProcessingStatus
} from '../../generated/graphql/schema';
import { SelectControl } from '../FormInputs/SelectControl';
import { formatInterfaceString } from '../../utils/util';
import { CheckBoxControl } from '../FormInputs/CheckBoxControl';

type GeneralSearchProps = {
    control: Control<FieldValues, any>;
    searchType?: string;
    data?: EventFilter;
};

export const SearchCriteria = ({ control, searchType = '', data }: GeneralSearchProps) => {
    console.log(searchType === EventType.Investigation);

    return (
        <>
            <SelectControl
                control={control}
                name="investigationStatus"
                label="Investigation Status:"
                options={Object.values(InvestigationStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <SelectControl
                control={control}
                name="outbreakNames"
                label="Outbreak Name:"
                options={Object.values(NotificationStatus).map((type) => {
                    return {
                        name: formatInterfaceString(type),
                        value: type
                    };
                })}
            />

            <CheckBoxControl control={control} id="case" name="case" label="Including unassigned status" />
            <SelectControl
                control={control}
                name="statusList"
                label="Case Status:"
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
                label="Current Processing Status:"
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
                label="Notification Status:"
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
