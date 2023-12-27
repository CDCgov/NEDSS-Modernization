import { Button, ButtonGroup, Form, Grid, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import { UserContext } from 'user';
import './EditBusinessRules.scss';
// import { EditBusinessRulesFields } from './EditBusinessRulesFields';
import { PageRuleControllerService, ViewRuleResponse } from '../../../generated';
import DeleteBusinessRuleWarningModal from './DeleteBusinessRuleWarningModal';
import { useAlert } from 'alert';

export type FormValues = {
    ruleFunction: string;
    sourceIdentifier: string;
    sourceText: string;
    comparator?: string;
    sourceValue: string[];
    targetValueIdentifier?: string[];
    targetType: string;
    ruleDescription: string;
    targetValueText?: string[];
    anySourceValue?: boolean;
};

export const EditBusinessRules = () => {
    const [selectedFieldType, setSelectedFieldType] = useState('');
    const navigate = useNavigate();
    const { state } = useContext(UserContext);
    const { pageId, ruleId } = useParams();
    const form = useForm<FormValues>({
        defaultValues: { targetType: 'SUBSECTION', anySourceValue: false },
        mode: 'onChange'
    });
    const token = `Bearer ${state.getToken()}`;
    const deleteWarningModalModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    useEffect(() => {
        if (!ruleId) return;
        PageRuleControllerService.viewRuleResponseUsingGet({
            authorization: token,
            ruleId: Number(ruleId)
        }).then((resp: ViewRuleResponse) => {
            // form.setValue('anySourceValue', resp?.!);
            form.setValue('ruleDescription', resp?.ruleDescription!);
            form.setValue('ruleFunction', resp?.ruleFunction!);
            form.setValue('comparator', resp?.comparator!);
            form.setValue('targetType', resp?.targetType!);
            form.setValue('sourceIdentifier', resp?.sourceIdentifier!);
            form.setValue('sourceText', resp?.sourceIdentifier!);
            form.setValue('sourceValue', resp?.sourceValue!);
            form.setValue('targetValueIdentifier', resp?.targetValueIdentifier!);
            setSelectedFieldType(resp.ruleFunction!);
        });
    }, [ruleId]);

    const onSubmit = form.handleSubmit(async (data) => {
        const request = {
            comparator: data.comparator,
            ruleDescription: data.ruleDescription,
            ruleFunction: selectedFieldType,
            sourceIdentifier: data.sourceIdentifier,
            sourceText: data.sourceText,
            sourceValue: [
                {
                    sourceValueId: data.sourceValue,
                    sourceValueText: data.sourceValue
                }
            ],
            targetType: data.targetType,
            targetValueIdentifier: data.targetValueIdentifier,
            targetValueText: data.targetValueText
        };
        if (!ruleId) {
            PageRuleControllerService.createBusinessRuleUsingPost({
                authorization: token,
                page: Number(pageId),
                request
            }).then((resp: any) => {
                console.log(resp);
            });
        } else {
            PageRuleControllerService.updatePageRuleUsingPut({
                authorization: token,
                page: Number(pageId),
                ruleId: Number(ruleId),
                request
            }).then((resp: any) => {
                console.log(resp);
                console.log(resp);
                // showAlert({ type: 'success', header: 'updated', message: resp.message });
            });
        }
        handleCancel();
    });

    const handleCancel = () => {
        navigate(-1);
    };

    const handleDeleteRule = async () => {
        console.log('delete rule', { pageId, ruleId });
        if (pageId) {
            try {
                const response = await PageRuleControllerService.deletePageRuleUsingDelete({
                    authorization: token,
                    pageId: pageId ?? '',
                    ruleId: Number(ruleId)
                });
                // show alert
                console.log('response', response);
                showAlert({ type: 'success', header: 'deleted', message: response.message });
            } catch (error: any) {
                showAlert({ type: 'error', header: 'error', message: error.message });
                console.error('Error', error);
            }
        }
    };

    const fieldTypeTab = [
        { name: 'Enable' },
        { name: 'Date Compare' },
        { name: 'Disable' },
        { name: 'Hide' },
        { name: 'Unhide' },
        { name: 'Require If' }
    ];

    const title = !ruleId ? 'Add new' : 'Edit';
    const ruleFunction = form.watch('ruleFunction');

    return (
        <>
            <DeleteBusinessRuleWarningModal
                deleteWarningModalModalRef={deleteWarningModalModal}
                onDeleteRule={handleDeleteRule}
            />
            <div className="edit-rules">
                <div className="breadcrumb-wrap">
                    <PagesBreadcrumb currentPage={`${title} business rules`} />
                </div>
                <Form onSubmit={onSubmit}>
                    <div className="edit-rules__form">
                        <div className="edit-rules__content">
                            <h2>{`${title} business rules`}</h2>
                            <Grid row className="inline-field">
                                <Grid col={3}>
                                    <label className="input-label">Function</label>
                                </Grid>
                                <Grid col={8}>
                                    {ruleId ? (
                                        <label>{ruleFunction}</label>
                                    ) : (
                                        <ButtonGroup type="segmented">
                                            {fieldTypeTab.map((field, index) => (
                                                <Button
                                                    key={index}
                                                    type="button"
                                                    outline={field.name !== selectedFieldType}
                                                    onClick={() => {
                                                        setSelectedFieldType(field.name);
                                                        form.setValue('ruleFunction', field.name);
                                                    }}>
                                                    {field.name}
                                                </Button>
                                            ))}
                                        </ButtonGroup>
                                    )}
                                </Grid>
                            </Grid>
                            {selectedFieldType == '' ? (
                                <></>
                            ) : (
                                <FormProvider {...form}>{/* <EditBusinessRulesFields /> */}</FormProvider>
                            )}
                        </div>
                    </div>
                    <div className="edit-rules__buttons">
                        {ruleId ? (
                            <ModalToggleButton
                                opener
                                modalRef={deleteWarningModalModal}
                                type="button"
                                className="delete-btn"
                                unstyled>
                                <Icon.Delete size={3} className="margin-right-2px" />
                                <span> Delete</span>
                            </ModalToggleButton>
                        ) : (
                            <div />
                        )}
                        <div>
                            <Button type="button" outline onClick={handleCancel}>
                                Cancel
                            </Button>
                            <Button type="submit" className="lbr" disabled={!form.formState.isValid}>
                                Add to Library
                            </Button>
                        </div>
                    </div>
                </Form>
            </div>
        </>
    );
};
