import { Button, ButtonGroup, Form, Grid, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import React, { useEffect, useRef, useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import './EditBusinessRules.scss';
// import { EditBusinessRulesFields } from './EditBusinessRulesFields';
import { PageRuleControllerService, ViewRuleResponse } from '../../../generated';
import DeleteBusinessRuleWarningModal from './DeleteBusinessRuleWarningModal';
import { useAlert } from 'alert';
import { authorization } from 'authorization';

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

const EditBusinessRules = () => {
    const [selectedFieldType, setSelectedFieldType] = useState('');
    const navigate = useNavigate();
    const { pageId, ruleId } = useParams();
    const form = useForm<FormValues>({
        defaultValues: { targetType: 'SUBSECTION', anySourceValue: false },
        mode: 'onChange'
    });
    const deleteWarningModalModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    useEffect(() => {
        if (!ruleId) return;
        PageRuleControllerService.viewRuleResponseUsingGet({
            authorization: authorization(),
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
            // form.setValue('targetValueIdentifier', resp?.sourceValue!);
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
            sourceValue: {
                sourceValueId: data.sourceValue,
                sourceValueText: data.sourceValue
            },
            targetType: data.targetType,
            targetValueIdentifier: data.targetValueIdentifier,
            targetValueText: data.targetValueText
        };
        if (!ruleId) {
            PageRuleControllerService.createBusinessRuleUsingPost({
                authorization: authorization(),
                page: Number(pageId),
                request
            }).then((resp: any) => {
                console.log(resp);
            });
        } else {
            PageRuleControllerService.updatePageRuleUsingPut({
                authorization: authorization(),
                page: Number(pageId),
                ruleId: Number(ruleId),
                request
            }).then((resp: any) => {
                showAlert({ type: 'success', header: 'updated', message: resp.message });
            });
        }
        handleCancel();
    });

    const handleCancel = () => {
        navigate(-1);
    };

    const handleDeleteRule = async () => {
        if (pageId) {
            try {
                await PageRuleControllerService.deletePageRuleUsingDelete({
                    authorization: authorization(),
                    pageId: pageId ?? '',
                    ruleId: Number(ruleId)
                });
                showAlert({ type: 'success', header: 'Success', message: 'Rule deleted.' });
                navigate(-1);
            } catch (error: any) {
                showAlert({ type: 'error', header: 'error', message: error });
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
                        </div>
                    </div>
                    <div className="edit-rules__buttons">
                        {ruleId && (
                            <ModalToggleButton
                                opener
                                modalRef={deleteWarningModalModal}
                                type="button"
                                className="delete-btn"
                                unstyled>
                                <Icon.Delete size={3} className="margin-right-2px" />
                                <span> Delete</span>
                            </ModalToggleButton>
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

export default EditBusinessRules;
