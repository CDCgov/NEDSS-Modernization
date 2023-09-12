import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import {
    CaseStatus,
    InvestigationFilter,
    InvestigationStatus,
    NotificationStatus,
    ProcessingStatus
} from 'generated/graphql/schema';
import { SearchCriteriaContext } from 'providers/SearchCriteriaContext';
import { ReactElement } from 'react';
import { Controller, UseFormReturn } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';

type InvestigationCriteriaProps = {
    form: UseFormReturn<InvestigationFilter>;
};
export const InvestigationCriteria = ({ form }: InvestigationCriteriaProps): ReactElement => {
    return (
        <div id="criteria">
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        onChange={onChange}
                        label="Investigation status"
                        htmlFor={name}
                        options={Object.values(InvestigationStatus).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />

            <SearchCriteriaContext.Consumer>
                {({ searchCriteria }) => (
                    <Controller
                        control={form.control}
                        name="outbreakNames"
                        render={({ field: { onChange, value, name } }) => (
                            <SelectInput
                                name={name}
                                value={value as string | undefined}
                                onChange={onChange}
                                label="Outbreak name"
                                htmlFor={name}
                                options={Object.values(searchCriteria.outbreaks).map((outbreak) => {
                                    return {
                                        name: formatInterfaceString(outbreak.codeShortDescTxt ?? ''),
                                        value: outbreak.id.code
                                    };
                                })}
                            />
                        )}
                    />
                )}
            </SearchCriteriaContext.Consumer>
            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelectInput
                        label="Case status"
                        onChange={onChange}
                        value={value as string[] | undefined}
                        name={name}
                        options={Object.values(CaseStatus).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="processingStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelectInput
                        label="Current processing status"
                        onChange={onChange}
                        value={value as string[] | undefined}
                        name={name}
                        options={Object.values(ProcessingStatus).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="notificationStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelectInput
                        label="Notification status"
                        onChange={onChange}
                        value={value as string[] | undefined}
                        name={name}
                        options={Object.values(NotificationStatus).map((type) => {
                            return {
                                name: formatInterfaceString(type),
                                value: type
                            };
                        })}
                    />
                )}
            />
        </div>
    );
};
