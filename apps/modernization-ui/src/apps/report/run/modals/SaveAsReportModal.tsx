import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent.tsx';
import { Button, ButtonGroup } from 'design-system/button';
import { TextAreaField, TextInputField } from 'design-system/input/text';
import { RadioGroup } from 'design-system/radio/RadioGroup.tsx';

import { Controller, useForm } from 'react-hook-form';

import styles from './save-as-report-modal.module.scss';
import { SingleSelect } from 'design-system/select';
import { useReportSections } from 'options/report';
import { AdminReportRequest, ReportConfiguration, SaveAsReportRequest } from 'generated';
import { usePermissions } from 'libs/permission/usePermissions';
import { GROUP_OPTIONS, PERMISSION_GROUP_MAP, SIZING } from '../../constants.ts';
import { Selectable } from 'options';
import { validateRequiredRule } from 'validation/entry';
import { EnumSelectable } from '../../utils.ts';
import { permits } from 'libs/permission';

type SaveAsForm = {
    reportTitle: string;
    description: string;
    group: EnumSelectable<AdminReportRequest.group>;
    sectionCode: Selectable;
};

export type SaveAsReportFormData = Omit<SaveAsReportRequest, 'executionRequest'>;

type SaveAsReportModalProps = {
    saveAsReportModalRef: RefObject<ModalRef>;
    saving: boolean;
    onSaveAs: (e: SaveAsReportFormData) => void;
};

const INPUT_LABELS = {
    reportTitle: 'Name',
    description: 'Description',
    sectionCode: 'Section name',
    group: 'Group',
};

const getUserReportCreatePermissionsOptions = (
    userPermissions: string[]
): EnumSelectable<ReportConfiguration.group>[] =>
    GROUP_OPTIONS.filter(({ value }) => permits(PERMISSION_GROUP_MAP[value].create)(userPermissions));

export const SaveAsReportModal = ({ saveAsReportModalRef, saving, onSaveAs }: SaveAsReportModalProps) => {
    const permissions = usePermissions();

    const reportGroupOptions = getUserReportCreatePermissionsOptions(permissions.permissions);

    const { control, handleSubmit } = useForm<SaveAsForm>({
        defaultValues: {
            reportTitle: '',
            description: '',
            sectionCode: undefined,
            group: reportGroupOptions[0],
        },
    });

    const handleOnSaveAs = ({ reportTitle, sectionCode, group, description }: SaveAsForm) => {
        onSaveAs({
            reportTitle,
            sectionCode: sectionCode.value,
            group: group.value,
            description,
        });
    };

    return (
        <ModalComponent
            id="save-report-modal"
            className={styles.layout}
            modalRef={saveAsReportModalRef}
            modalHeading="Save as a new report"
            modalBody={
                <div className={styles.form}>
                    <form onSubmit={handleSubmit(handleOnSaveAs)}>
                        <Controller
                            name="reportTitle"
                            control={control}
                            rules={validateRequiredRule(INPUT_LABELS.reportTitle)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <TextInputField
                                    id={name}
                                    sizing={SIZING}
                                    label={INPUT_LABELS.reportTitle}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    required
                                    error={error?.message}
                                    maxLength={100}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="description"
                            rules={validateRequiredRule(INPUT_LABELS.description)}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <TextAreaField
                                    id={name}
                                    sizing={SIZING}
                                    label={INPUT_LABELS.description}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    error={error?.message}
                                    required
                                    maxLength={300}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="sectionCode"
                            rules={validateRequiredRule(INPUT_LABELS.sectionCode)}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <SingleSelect
                                    id={name}
                                    sizing={SIZING}
                                    label={INPUT_LABELS.sectionCode}
                                    value={value}
                                    onChange={onChange}
                                    name={name}
                                    error={error?.message}
                                    required
                                    options={useReportSections()}
                                    helperText="The heading under which the report appears."
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="group"
                            rules={validateRequiredRule(INPUT_LABELS.group)}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <RadioGroup
                                    id={name}
                                    sizing={SIZING}
                                    className={styles.radioGroupWrapper}
                                    label={INPUT_LABELS.group}
                                    options={reportGroupOptions}
                                    onChange={onChange}
                                    error={error?.message}
                                    value={value}
                                    orientation="vertical"
                                    required
                                    helperText="The level of visibility for the report."
                                />
                            )}
                        />
                    </form>
                </div>
            }
            modalFooter={
                <ButtonGroup>
                    <ModalToggleButton modalRef={saveAsReportModalRef} outline disabled={saving}>
                        Cancel
                    </ModalToggleButton>
                    <Button onClick={handleSubmit(handleOnSaveAs)} disabled={saving}>
                        Save as new
                    </Button>
                </ButtonGroup>
            }
        ></ModalComponent>
    );
};
