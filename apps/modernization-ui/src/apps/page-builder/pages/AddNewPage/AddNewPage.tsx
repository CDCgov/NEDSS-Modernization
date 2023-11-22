import { Button, Form, Modal, ModalRef } from '@trussworks/react-uswds';
import { CreateCondition } from 'apps/page-builder/components/CreateCondition/CreateCondition';
import { ImportTemplate } from 'apps/page-builder/components/ImportTemplate/ImportTemplate';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { QuickConditionLookup } from 'apps/page-builder/components/QuickConditionLookup/QuickConditionLookup';
import { Concept, Condition, Template } from 'apps/page-builder/generated';
import { fetchConditions } from 'apps/page-builder/services/conditionAPI';
import { createPage } from 'apps/page-builder/services/pagesAPI';
import { fetchTemplates } from 'apps/page-builder/services/templatesAPI';
import { fetchMMGOptions } from 'apps/page-builder/services/valueSetAPI';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useContext, useEffect, useRef, useState } from 'react';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { UserContext } from 'user';
import './AddNewPage.scss';
import { AddNewPageFields } from './AddNewPageFields';

export type FormValues = {
    conditionIds: string[];
    dataMartName?: string;
    eventType: string;
    messageMappingGuide: string;
    name: string;
    pageDescription?: string;
    templateId: number;
};
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
    const { state } = useContext(UserContext);
    const [conditions, setConditions] = useState<Condition[]>([]);
    const [mmgs, setMmgs] = useState<Concept[]>([]);
    const [templates, setTemplates] = useState<Template[]>([]);
    const form = useForm<FormValues>({ mode: 'onBlur' });
    const watch = useWatch({ control: form.control });

    useEffect(() => {
        const token = `Bearer ${state.getToken()}`;
        fetchMMGOptions(token)
            .then((data) => {
                setMmgs(data);
            })
            .catch((error: any) => {
                console.log('Error', error);
            });
        fetchConditions(token).then((data) => {
            setConditions(data);
        });
        fetchTemplates(token).then((data) => {
            setTemplates(data);
        });
    }, []);

    const handleAddConditions = (conditions: string[]) => {
        form.setValue('conditionIds', conditions.concat(form.getValues('conditionIds')));
    };

    const onSubmit = form.handleSubmit(async (data) => {
        await createPage(
            `Bearer ${state.getToken()}`,
            data.conditionIds.filter(Boolean),
            data.eventType,
            data.messageMappingGuide,
            data.name,
            Number(data.templateId),
            data.pageDescription,
            data?.dataMartName
        ).then((response: any) => {
            navigate(`/page-builder/edit/page/${response.pageId}`);
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
            window.location.href = '/nbs/ManagePage.do?method=addPageLoad';
        } else {
            onSubmit();
        }
    };

    const handleTemplateImported = (template: Template) => {
        const temp = [...templates, template];
        temp.sort((a, b) => a.templateNm.localeCompare(b.templateNm));
        setTemplates(temp);
        form.setValue('templateId', template.id);
    };

    return (
        <div className="add-new-page">
            <div className="breadcrumb-wrap">
                <PagesBreadcrumb currentPage="Add new page" />
            </div>
            <Form onSubmit={onSubmit}>
                <div className="add-new-page__form">
                    <div className="add-new-page__content">
                        <h2>Add new page</h2>
                        <h4>Let's fill out some information about your new page before creating it</h4>
                        <label className="fields-info">
                            All fields with <span className="mandatory-indicator">*</span> are required
                        </label>
                        <Controller
                            control={form.control}
                            name="eventType"
                            rules={{ required: { value: true, message: 'Event type is required.' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    aria-labelledby="eventType"
                                    label="Event type"
                                    dataTestid="eventTypeDropdown"
                                    value={value}
                                    onChange={onChange}
                                    options={eventType}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                        {watch.eventType == undefined || watch.eventType == '' ? (
                            <></>
                        ) : (
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
                    <Button type="button" outline onClick={handleCancel}>
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

            <Modal id="create-condition-modal" isLarge ref={createConditionModal} title="Create new condition">
                <CreateCondition conditionCreated={handleConditionCreated} modal={createConditionModal} />
            </Modal>
            <Modal id="import-template-modal" isLarge ref={importTemplateModal}>
                <ImportTemplate modal={importTemplateModal} onTemplateCreated={handleTemplateImported} />
            </Modal>
            <QuickConditionLookup modal={conditionLookupModal} addConditions={handleAddConditions} />
        </div>
    );
};
