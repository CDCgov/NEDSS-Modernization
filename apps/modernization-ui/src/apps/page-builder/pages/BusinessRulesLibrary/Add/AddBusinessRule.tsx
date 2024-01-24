import { Button, ButtonGroup, Form, Grid, Icon, ModalRef } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import './AddBusinessRule.scss';
import { PageRuleControllerService, ViewRuleResponse } from '../../../generated';
import { useAlert } from 'alert';
import BusinessRulesForm from '../BusinessRulesForm';
import { PageBuilder } from '../../PageBuilder/PageBuilder';
import { Breadcrumb } from 'breadcrumb';
import { authorization } from 'authorization';
import { ConfirmationModal } from 'confirmation';

export type FormValues = {
    ruleFunction: string;
    sourceIdentifier: string;
    sourceText: string;
    comparator?: string;
    sourceValueIds?: string[];
    sourceValueText?: string[];
    sourceValue?: {
        sourceValueId?: string[];
        sourceValueText?: string[];
    };
    targetValueIdentifier?: string[];
    targetType: string;
    ruleDescription: string;
    targetValueText?: string[];
    anySourceValue?: boolean;
};

const AddBusinessRule = () => {
    const navigate = useNavigate();
    const form = useForm<FormValues>({
        defaultValues: { targetType: 'SUBSECTION', anySourceValue: false },
        mode: 'onChange'
    });

    const [selectedFieldType, setSelectedFieldType] = useState('');
    const { pageId, ruleId } = useParams();
    const deleteWarningModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    useEffect(() => {
        if (ruleId) {
            PageRuleControllerService.viewRuleResponseUsingGet({
                authorization: authorization(),
                ruleId: Number(ruleId)
            }).then((resp: ViewRuleResponse) => {
                form.setValue('anySourceValue', resp?.anySourceValue!);
                form.setValue('ruleDescription', resp?.ruleDescription!);
                form.setValue('ruleFunction', resp?.ruleFunction!);
                form.setValue('comparator', resp?.comparator!);
                form.setValue('targetType', resp?.targetType!);
                form.setValue('sourceIdentifier', resp?.sourceIdentifier!);
                form.setValue('sourceText', resp?.sourceIdentifier!);
                form.setValue('sourceValueIds', resp?.sourceValue?.sourceValueId!);
                form.setValue('sourceValueText', resp?.sourceValue?.sourceValueText!);
                form.setValue('targetValueIdentifier', resp?.targetValueIdentifier!);
                form.setValue('targetValueText', ['testing']);
                setSelectedFieldType(resp.ruleFunction!);
            });
        }
    }, [ruleId]);

    const onSubmit = form.handleSubmit(async (data) => {
        const sourceValue = {
            sourceValueId: data.sourceValueIds,
            sourceValueText: data.sourceValueText
        };
        const request = {
            anySourceValue: data.anySourceValue,
            comparator: data.comparator,
            ruleDescription: data.ruleDescription,
            ruleFunction: data.ruleFunction,
            sourceIdentifier: data.sourceIdentifier,
            sourceText: data.sourceText,
            sourceValue: sourceValue,
            targetType: data.targetType,
            targetValueIdentifier: data.targetValueIdentifier,
            targetValueText: data.targetValueText
        };

        if (!ruleId) {
            PageRuleControllerService.createBusinessRuleUsingPost({
                authorization: authorization(),
                id: Number(pageId),
                request
            }).then((resp) => {
                showAlert({ type: 'success', header: 'added', message: resp.message });
            });
        } else {
            PageRuleControllerService.updatePageRuleUsingPut({
                authorization: authorization(),
                page: Number(pageId),
                ruleId: Number(ruleId),
                request
            }).then((resp) => {
                showAlert({ type: 'success', header: 'updated', message: resp.message });
            });
        }
        handleCancel();
    });

    const handleCancel = () => {
        navigate('../');
    };

    const handleDeleteRule = () => {
        PageRuleControllerService.deletePageRuleUsingDelete({
            authorization: authorization(),
            id: Number(pageId),
            ruleId: Number(ruleId)
        }).then((resp: any) => {
            handleCancel();
            showAlert({ type: 'success', header: 'Deleted', message: resp.message });
        });
    };

    const fieldTypeTab = [
        { name: 'Enable' },
        { name: 'Disable' },
        { name: 'Data validation' },
        { name: 'Hide' },
        { name: 'Unhide' },
        { name: 'Require If' }
    ];

    const title = !ruleId ? 'Add new' : 'Edit';
    const ruleFunction = form.watch('ruleFunction');

    return (
        <>
            <ConfirmationModal
                modal={deleteWarningModal}
                title="Warning"
                message="Are you sure you want to delete this business rule?"
                detail="Once deleted, this business rule will be permanently removed from the system and will nolonger be associated with the page."
                confirmText="Yes, delete"
                onConfirm={handleDeleteRule}
                onCancel={handleCancel}
            />
            <header className="add-business-rule-header">
                <h2>Page library</h2>
            </header>

            <PageBuilder>
                <div className="breadcrumb-wrap">
                    <Breadcrumb start="../">Business rules</Breadcrumb>
                </div>

                <div className="edit-rules">
                    <Form onSubmit={onSubmit}>
                        <div className="edit-rules__form">
                            <div className="edit-rules__container">
                                <div className="edit-rules-title">
                                    <h2>{`${title} business rules`}</h2>
                                </div>
                                <div className="edit-rules__content">
                                    <Grid row className="inline-field">
                                        <Grid col={2}>
                                            <label className="input-label">Function</label>
                                        </Grid>
                                        <Grid col={10}>
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
                                    {selectedFieldType == '' ? null : (
                                        <FormProvider {...form}>
                                            <BusinessRulesForm />
                                        </FormProvider>
                                    )}
                                </div>
                            </div>
                        </div>
                        <div className="edit-rules__buttons">
                            {ruleId ? (
                                <Button type="button" className="delete-btn" unstyled onClick={handleDeleteRule}>
                                    <Icon.Delete size={3} className="margin-right-2px" />
                                    <span> Delete</span>
                                </Button>
                            ) : null}
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
            </PageBuilder>
        </>
    );
};

export default AddBusinessRule;
