import { SelectInput } from 'components/FormInputs/SelectInput';
import { handleChangeToDefaultValue } from 'forms/event';
import { CaseStatus, InvestigationStatus, NotificationStatus, ProcessingStatus } from 'generated/graphql/schema';
import { UseFormReturn, Controller } from 'react-hook-form';
import { formatInterfaceString } from 'utils/util';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import { MultiSelectInput } from 'components/selection/multi';

type Props = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const CriteriaSearchFields = ({ form }: Props) => {
    return (
        <>
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, value, name } }) => (
                    <SelectInput
                        data-testid={name}
                        name={name}
                        value={value as string | undefined}
                        onChange={(e) => handleChangeToDefaultValue(form, name, undefined, e, onChange)}
                        label="Investigation status"
                        htmlFor={name}
                        dataTestid={name}
                        options={Object.values(InvestigationStatus).map((type) => {
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
                name="outbreaks"
                render={({ field: { value, name } }) => (
                    <SelectInput
                        name={name}
                        value={value as string | undefined}
                        label="Outbreak names"
                        htmlFor={name}
                        dataTestid={name}
                        options={[
                            {
                                name: 'Coming soon',
                                value: ''
                            }
                        ]}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelectInput
                        data-testid={'caseStatuses'}
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
        </>
    );
};

export default CriteriaSearchFields;
