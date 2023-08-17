import { Button, Form, ModalRef } from '@trussworks/react-uswds';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { QuickConditionLookup } from 'apps/page-builder/components/QuickConditionLookup/QuickConditionLookup';
import { Concept } from 'apps/page-builder/generated';
import { createPage } from 'apps/page-builder/services/pagesAPI';
import { fetchTemplates } from 'apps/page-builder/services/templatesAPI';
import { fetchFamilyOptions, fetchMMGOptions } from 'apps/page-builder/services/valueSetAPI';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { useContext, useEffect, useRef, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { NavLink, useNavigate } from 'react-router-dom';
import { UserContext } from 'user';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './AddNewPage.scss';

type FormValues = {
    conditionIds: string[];
    dataMartName?: string;
    eventType: string;
    messageMappingGuide: string;
    name: string;
    pageDescription?: string;
    templateId: number;
};

type CONDITION = {
    conceptCode: string;
    display: string;
};

type TEMPLATE = {
    id: string;
    templateNm: string;
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
    const [showQuickConditionLookup, setShowQuickConditionLookup] = useState(false);
    const modal = useRef<ModalRef>(null);
    const navigate = useNavigate();
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [conditions, setConditions] = useState<CONDITION[]>([]);
    const [mmgs, setMMGs] = useState<Concept[]>([]);
    const [templates, setTemplates] = useState<TEMPLATE[]>([]);
    const { handleSubmit, control, setValue } = useForm<FormValues, any>();

    useEffect(() => {
        fetchMMGOptions(token)
            .then((data: any) => {
                setMMGs(data);
            })
            .catch((error: any) => {
                console.log('Error', error);
            });
        fetchFamilyOptions(token).then((data: any) => {
            setConditions(data);
        });
        fetchTemplates(token).then((data: any) => {
            setTemplates(data);
        });
    }, [token]);

    const handleAddConditions = (conditions: string[]) => {
        setValue('conditionIds', conditions);
    };

    const onSubmit = handleSubmit(async (data) => {
        await createPage(
            token,
            data.conditionIds,
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

    return (
        <PageBuilder page="add-new-page">
            <div className="add-new-page">
                <div className="add-new-page__form">
                    <Form onSubmit={onSubmit}>
                        <PagesBreadcrumb />
                        <div className="add-new-page__content">
                            <h2>Add new page</h2>
                            <h4>Let’s fill out some information about your new page before creating it</h4>
                            <p>All fields with * are required</p>
                            <Controller
                                control={control}
                                name="conditionIds"
                                render={({ field: { onChange, value } }) => (
                                    <MultiSelectInput
                                        onChange={onChange}
                                        value={value}
                                        options={conditions.map((m) => {
                                            return {
                                                name: m.display,
                                                value: m.conceptCode
                                            };
                                        })}
                                        label="Condition(s)"></MultiSelectInput>
                                )}
                            />
                            <p>
                                Can’t find the condition you’re looking for?
                                <br />
                                <a
                                    onClick={() => {
                                        setShowQuickConditionLookup(!showQuickConditionLookup);
                                    }}>
                                    Quick condition lookup
                                </a>
                                &nbsp; or &nbsp;
                                <NavLink to={'add/condition'}>Create new condition here</NavLink>
                            </p>
                            <Controller
                                control={control}
                                name="name"
                                rules={{ required: { value: true, message: 'Name is required.' } }}
                                render={({ field: { onChange, value }, fieldState: { error } }) => (
                                    <Input
                                        onChange={onChange}
                                        defaultValue={value}
                                        label="Page name"
                                        type="text"
                                        error={error?.message}
                                        required
                                    />
                                )}
                            />
                            <Controller
                                control={control}
                                name="eventType"
                                rules={{ required: { value: true, message: 'Event type is required.' } }}
                                render={({ field: { onChange, value }, fieldState: { error } }) => (
                                    <SelectInput
                                        label="Event type"
                                        defaultValue={value}
                                        onChange={onChange}
                                        options={eventType}
                                        error={error?.message}
                                        required></SelectInput>
                                )}
                            />
                            <Controller
                                control={control}
                                name="templateId"
                                rules={{ required: { value: true, message: 'Template is required.' } }}
                                render={({ field: { onChange, value }, fieldState: { error } }) => (
                                    <SelectInput
                                        label="Templates"
                                        defaultValue={value}
                                        onChange={onChange}
                                        options={templates.map((template) => {
                                            return {
                                                name: template.templateNm,
                                                value: template.id
                                            };
                                        })}
                                        error={error?.message}
                                        required></SelectInput>
                                )}
                            />
                            <p>
                                Can’t find the template you’re looking for?
                                <br />
                                <NavLink to={'add/template'}>Import a new template here</NavLink>
                            </p>
                            <Controller
                                control={control}
                                name="messageMappingGuide"
                                rules={{ required: { value: true, message: 'MMG is required.' } }}
                                render={({ field: { onChange, value }, fieldState: { error } }) => (
                                    <SelectInput
                                        label="MMG"
                                        name="messageMappingGuide"
                                        onChange={onChange}
                                        defaultValue={value}
                                        options={mmgs.map((m) => {
                                            return {
                                                name: m.display ?? '',
                                                value: m.conceptCode ?? ''
                                            };
                                        })}
                                        error={error?.message}
                                        required></SelectInput>
                                )}
                            />
                            <p>
                                Would you like to add any additional information?
                                <br />
                                These fields are optional, you can make changes to this later.
                            </p>
                            <Controller
                                control={control}
                                name="pageDescription"
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        onChange={(d: any) => {
                                            onChange(d);
                                        }}
                                        label="Page description"
                                        type="text"
                                        multiline
                                        defaultValue={value}
                                    />
                                )}
                            />
                            <Controller
                                control={control}
                                name="dataMartName"
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        label="Data mart name"
                                        type="text"
                                        onChange={onChange}
                                        defaultValue={value}
                                    />
                                )}
                            />
                        </div>
                        <div className="add-new-page__buttons">
                            <Button type="button" outline>
                                Cancel
                            </Button>
                            <Button type="submit">Create page</Button>
                        </div>
                    </Form>
                </div>
                {showQuickConditionLookup ? (
                    <QuickConditionLookup
                        modal={modal}
                        addConditions={handleAddConditions}
                        onClose={() => setShowQuickConditionLookup(!showQuickConditionLookup)}
                    />
                ) : null}
            </div>
        </PageBuilder>
    );
};
