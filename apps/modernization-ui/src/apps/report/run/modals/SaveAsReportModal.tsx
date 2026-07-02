import { Button, Form, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import React, { RefObject } from 'react';
import { Input } from 'components/FormInputs/Input.tsx';
import { ModalComponent } from 'components/ModalComponent/ModalComponent.tsx';
import { ButtonGroup } from 'design-system/button';
import { TextAreaField } from 'design-system/input/text';
import { RadioGroup } from 'design-system/radio/RadioGroup.tsx';

import { Controller, useForm } from 'react-hook-form';

import styles from './save-as-report-modal.module.scss';
import { SingleSelect } from 'design-system/select';
import { useReportSections } from 'options/report';
import { SaveAsReportRequest } from 'generated';
import { getUserReportCreatePermissionsOptions } from '../../utils/getUserReportCreatePermissions.ts';
import { usePermissions } from 'libs/permission/usePermissions.ts';

export type SaveAsReportFormData = {
    reportTitle: string;
    description: string;
    sectionCode: string;
    group: SaveAsReportRequest.group;
};

type SaveAsReportModalProps = {
    saveAsReportModalRef: RefObject<ModalRef>;
    saving?: boolean;
    onSaveAs: (e: SaveAsReportFormData) => void;
};

const FORM_SIZE = 'large';

export const SaveAsReportModal = ({ saveAsReportModalRef, saving, onSaveAs }: SaveAsReportModalProps) => {
    const permissions = usePermissions();

    const reportGroupOptions = getUserReportCreatePermissionsOptions(permissions.permissions);
    const { control, handleSubmit } = useForm<SaveAsReportFormData>({
        defaultValues: {
            reportTitle: '',
            description: '',
            sectionCode: '',
            group: reportGroupOptions[0].valueOf(),
        },
    });

    const handleOnSaveAs = (formData) => {
        const { reportTitle, sectionCode, group, description } = formData;
        const request = {
            reportTitle,
            sectionCode: sectionCode.value,
            group: group.value,
            description,
        };
        onSaveAs(request);
    };

    return (
        <ModalComponent
            id="save-report-modal"
            className={styles.layout}
            modalRef={saveAsReportModalRef}
            modalHeading="Save as a new report"
            closer={true}
            modalBody={
                <div className={styles.form}>
                    <Form onSubmit={handleSubmit(onSaveAs)}>
                        <Controller
                            name="reportTitle"
                            control={control}
                            rules={{ required: 'Report name is required' }}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <Input
                                    id={name}
                                    sizing={FORM_SIZE}
                                    label="Report name"
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value || ''}
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
                            rules={{ required: 'Description is required' }}
                            render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                <TextAreaField
                                    id={name}
                                    sizing={FORM_SIZE}
                                    label="Description"
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
                            rules={{ required: 'Report section is required' }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <SingleSelect
                                    id={name}
                                    sizing={FORM_SIZE}
                                    label="Report section"
                                    value={value}
                                    onChange={onChange}
                                    name={name}
                                    error={error?.message}
                                    required
                                    options={useReportSections()}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="group"
                            rules={{ required: 'Access level is required' }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <RadioGroup
                                    id={name}
                                    sizing={FORM_SIZE}
                                    className={styles.radioGroupWrapper}
                                    label="Set access level"
                                    options={reportGroupOptions}
                                    onChange={onChange}
                                    error={error?.message}
                                    value={value}
                                    orientation="vertical"
                                    required
                                />
                            )}
                        />
                    </Form>
                </div>
            }
            modalFooter={
                <ButtonGroup>
                    <ModalToggleButton
                        modalRef={saveAsReportModalRef}
                        closer
                        outline
                        disabled={saving}
                    >
                        Cancel
                    </ModalToggleButton>
                    <Button onClick={handleSubmit(handleOnSaveAs)} data-testid="report-save-as-btn" disabled={saving}>
                        Save as new
                    </Button>
                </ButtonGroup>
            }
        ></ModalComponent>
    );
};
