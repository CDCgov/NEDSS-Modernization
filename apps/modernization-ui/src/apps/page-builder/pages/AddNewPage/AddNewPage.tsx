import { useState, useRef, useEffect, useContext } from 'react';
import { Input } from 'components/FormInputs/Input';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import './AddNewPage.scss';
import { MultiSelectInput } from 'components/selection/multi';
import { Button, Form, ModalRef } from '@trussworks/react-uswds';
import { NavLink } from 'react-router-dom';
import { QuickConditionLookup } from 'apps/page-builder/components/QuickConditionLookup/QuickConditionLookup';
import { UserContext } from 'user';
import { fetchCodingSystemOptions, fetchFamilyOptions } from 'apps/page-builder/services/valueSetAPI';
import { Controller, useForm } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';

type FormValues = {
    conditions: [];
    pageName: string;
    eventType: string;
    template: string;
    mmg: string;
    description?: string;
    dataMartName?: string;
};

type MMG = {
    localCode: string;
    value: string;
};

type CONDITION = {
    value: string;
    conceptCode: string;
};

export const AddNewPage = () => {
    const [showQuickConditionLookup, setShowQuickConditionLookup] = useState(false);
    const modal = useRef<ModalRef>(null);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [conditions, setConditions] = useState<CONDITION[]>([]);
    const [mmgs, setMMGs] = useState<MMG[]>([]);
    const { handleSubmit, control } = useForm<FormValues, any>();

    useEffect(() => {
        fetchCodingSystemOptions(token)
            .then((data: any) => {
                setMMGs(data);
            })
            .catch((error: any) => {
                console.log('Error', error);
            });
        fetchFamilyOptions(token).then((data: any) => {
            setConditions(data);
        });
    }, [token]);

    return (
        <PageBuilder page="add-new-page">
            <div className="add-new-page">
                <div className="add-new-page__form">
                    <Form onSubmit={handleSubmit((data) => console.log(data))}>
                        <PagesBreadcrumb />
                        <div className="add-new-page__content">
                            <h2>Add new page</h2>
                            <h4>Let’s fill out some information about your new page before creating it</h4>
                            <p>All fields with * are required</p>
                            <MultiSelectInput
                                options={conditions.map((m) => {
                                    return {
                                        name: m.value,
                                        value: m.conceptCode
                                    };
                                })}
                                label="Condition(s)"
                                required></MultiSelectInput>
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
                                name="pageName"
                                render={({ field: { onChange, value } }) => (
                                    <Input
                                        onChange={onChange}
                                        defaultValue={value}
                                        label="Page name"
                                        type="text"
                                        required
                                    />
                                )}
                            />
                            <SelectInput label="Event type" options={[]} required></SelectInput>
                            <SelectInput label="Templates" options={[]} required></SelectInput>
                            <p>
                                Can’t find the template you’re looking for?
                                <br />
                                <NavLink to={'add/template'}>Import a new template here</NavLink>
                            </p>
                            <SelectInput
                                label="MMG"
                                name="mmg"
                                options={mmgs.map((m) => {
                                    return {
                                        name: m.localCode,
                                        value: m.value
                                    };
                                })}
                                required></SelectInput>
                            <p>
                                Would you like to add any additional information?
                                <br />
                                These fields are optional, you can make changes to this later.
                            </p>
                            <Controller
                                control={control}
                                name="description"
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
                        onClose={() => setShowQuickConditionLookup(!showQuickConditionLookup)}
                    />
                ) : null}
            </div>
        </PageBuilder>
    );
};
