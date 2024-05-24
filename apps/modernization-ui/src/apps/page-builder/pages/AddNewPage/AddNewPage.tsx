import { Button, Form, Modal, ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { CreateCondition } from 'apps/page-builder/components/CreateCondition/CreateCondition';
import { ImportTemplate } from 'apps/page-builder/components/ImportTemplate/ImportTemplate';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { ConditionSearch } from 'apps/page-builder/condition';
import { Condition, PageControllerService, PageCreateRequest, Template } from 'apps/page-builder/generated';
import { useFindConditionsNotInUse } from 'apps/page-builder/hooks/api/useFindConditionsNotInUse';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { fetchTemplates } from 'apps/page-builder/services/templatesAPI';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { useConfiguration } from 'configuration';
import { useEffect, useRef, useState } from 'react';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import './AddNewPage.scss';
import { AddNewPageFields } from './AddNewPageFields';

const eventType = [
    { value: 'CON', name: 'Contact Record' },
    { value: 'IXS', name: 'Interview' },
    { value: 'INV', name: 'Investigation' },
    { value: 'ISO', name: 'Lab Isolate Tracking' },
    { value: 'LAB', name: 'Lab Report' },
    { value: 'SUS', name: 'Lab Susceptibility' },
    { value: 'VAC', name: 'Vaccination' }
];

export const AddNewPage = () => {
    const conditionLookupModal = useRef<ModalRef>(null);
    const createConditionModal = useRef<ModalRef>(null);
    const importTemplateModal = useRef<ModalRef>(null);
    const navigate = useNavigate();
    const { conditions: availableConditions } = useFindConditionsNotInUse();
    const [conditions, setConditions] = useState<Condition[]>([]);
    const { options: mmgs } = useOptions('NBS_MSG_PROFILE');
    const [templates, setTemplates] = useState<Template[]>([]);
    const { showError } = useAlert();
    const form = useForm<PageCreateRequest>({
        mode: 'onBlur',
        defaultValues: {
            conditionIds: [],
            dataMartName: '',
            eventType: '',
            messageMappingGuide: '',
            name: '',
            pageDescription: '',
            templateId: undefined
        }
    });
    const watch = useWatch({ control: form.control });
    const config = useConfiguration();

    useEffect(() => {
        setConditions(availableConditions);
    }, [JSON.stringify(availableConditions)]);

    useEffect(() => {
        fetchTemplates(watch.eventType ?? ' ').then((data) => {
            setTemplates(data);
        });
    }, [watch.eventType]);

    const handleAddConditions = (conditions: number[]) => {
        const newConditions = conditions
            .map((id) => String(id))
            .filter((id) => !form.getValues('conditionIds').includes(id));
        form.setValue('conditionIds', newConditions.concat(form.getValues('conditionIds')));
        conditionLookupModal.current?.toggleModal();
    };

    const onSubmit = form.handleSubmit((data) => {
        PageControllerService.createPage({
            requestBody: data
        })
            .then((response) => {
                if (config.features.pageBuilder.page.management.edit.enabled) {
                    form.reset();
                    navigate(`/page-builder/pages/${response.pageId}/edit`);
                } else {
                    window.location.href = `/nbs/page-builder/api/v1/pages/${response.pageId}/edit`;
                }
            })
            .catch((error) => {
                showError({ message: error.body.message || 'Failed to create page' });
            });
    });

    const handleConditionCreated = (condition: Condition) => {
        // add newly created condition to condition array
        setConditions(conditions.concat([condition]));

        // select new condition
        form.setValue('conditionIds', [condition.id].concat(form.getValues('conditionIds')));
    };

    const eventTypeName = (value: string): string => {
        for (const element of eventType) {
            if (element.value === value) {
                return element.name;
            }
        }
        return '';
    };

    const handleCancel = () => {
        navigate(-1);
    };

    const handleSubmit = () => {
        if (watch.eventType !== undefined && watch.eventType != '' && watch.eventType !== 'INV') {
            window.location.href = '/nbs/page-builder/api/v1/pages/create';
        } else {
            onSubmit();
        }
    };

    const handleTemplateImported = (template: Template) => {
        const temp = [...templates, template];
        temp.sort((a, b) => a.templateNm.localeCompare(b.templateNm));
        setTemplates(temp);
        form.setValue('templateId', template.id);
        importTemplateModal.current?.toggleModal(undefined, false);
    };

    const handleCreateCondition = () => {
        conditionLookupModal.current?.toggleModal(undefined, false);
        setTimeout(() => {
            createConditionModal.current?.toggleModal(undefined, true);
        }, 100);
    };

    return (
        <div className="add-new-page createNewPage" id="createNewPage">
            <div className="breadcrumb-wrap">
                <PagesBreadcrumb currentPage="Create new page" />
            </div>
            <Form onSubmit={onSubmit} aria-label="create new page form">
                <div className="add-new-page__form">
                    <div className="add-new-page__content">
                        <h2 aria-label="Create new page">Create new page</h2>
                        <h4>Let's fill out some information about your new page before creating it.</h4>
                        <div className="fields-info">
                            All fields with <span className="mandatory-indicator">*</span> are required.
                        </div>
                        <Controller
                            control={form.control}
                            name="eventType"
                            rules={{ required: { value: true, message: 'Event type is required.' } }}
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <SelectInput
                                    label="Event type"
                                    dataTestid="eventTypeDropdown"
                                    value={value}
                                    onChange={onChange}
                                    options={eventType}
                                    error={error?.message}
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    required
                                />
                            )}
                        />
                        {watch.eventType !== undefined && watch.eventType !== '' && (
                            <>
                                {watch.eventType == 'INV' ? (
                                    <FormProvider {...form}>
                                        <AddNewPageFields
                                            conditions={conditions}
                                            templates={templates}
                                            mmgs={mmgs}
                                            createConditionModal={createConditionModal}
                                            conditionLookupModal={conditionLookupModal}
                                            importTemplateModal={importTemplateModal}
                                        />
                                    </FormProvider>
                                ) : (
                                    <div data-testid="event-type-warning" className="unsupported-event-type-message">
                                        {eventTypeName(watch.eventType).toUpperCase()} event type is not supported by
                                        the modern page design. Please click "Create page" to continue in classic design
                                        mode.
                                    </div>
                                )}
                            </>
                        )}
                    </div>
                </div>
                <div className="add-new-page__buttons">
                    <Button type="button" outline onClick={handleCancel} id="cancelBtn">
                        Cancel
                    </Button>
                    <Button
                        type="button"
                        className="createPage"
                        onClick={handleSubmit}
                        disabled={!form.formState.isValid}>
                        Create page
                    </Button>
                </div>
            </Form>

            <ModalComponent
                isLarge
                modalRef={createConditionModal}
                modalHeading={'Create new condition'}
                modalBody={<CreateCondition conditionCreated={handleConditionCreated} modal={createConditionModal} />}
            />
            <Modal id="import-template-modal" isLarge ref={importTemplateModal}>
                <ImportTemplate
                    onCancel={() => importTemplateModal.current?.toggleModal()}
                    onTemplateCreated={handleTemplateImported}
                />
            </Modal>
            <Modal
                forceAction
                id="condition-lookup-modal"
                className="add-condition-modal"
                isLarge
                ref={conditionLookupModal}>
                <ConditionSearch
                    onCancel={() => conditionLookupModal.current?.toggleModal()}
                    onConditionSelect={handleAddConditions}
                    onCreateNew={handleCreateCondition}
                />
            </Modal>
        </div>
    );
};
