import { Button, ButtonGroup, Form, Grid, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import './AddBusinessRule.scss';
import { CreateRuleRequest, PageRuleControllerService, ViewRuleResponse } from '../../../generated';
import { useAlert } from 'alert';
import BusinessRulesForm from '../BusinessRulesForm';
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
    const form = useForm<CreateRuleRequest>({
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
                const sourceText = resp?.sourceValue?.sourceValueText || '';
                form.setValue('anySourceValue', resp?.anySourceValue!);
                form.setValue('comparator', resp?.comparator!);
                form.setValue('ruleDescription', resp?.ruleDescription!);
                form.setValue('ruleFunction', resp?.ruleFunction!);
                form.setValue('sourceIdentifier', resp?.sourceIdentifier!);
                form.setValue('sourceText', `${sourceText} (${resp?.sourceIdentifier!})`);
                form.setValue('targetValueIdentifier', resp?.targetValueIdentifier!);
                form.setValue('targetValueText', resp?.targetValueText!);
                form.setValue('targetType', resp?.targetType!);
                setSelectedFieldType(resp.ruleFunction!);
            });
        }
    }, [ruleId]);

    const onSubmit = form.handleSubmit(async (data) => {
        if (!ruleId) {
            PageRuleControllerService.createBusinessRuleUsingPost({
                authorization: authorization(),
                id: Number(pageId),
                request: data
            }).then((resp) => {
                showAlert({ type: 'success', header: 'added', message: resp.message });
            });
        } else {
            PageRuleControllerService.updatePageRuleUsingPut({
                authorization: authorization(),
                page: Number(pageId),
                ruleId: Number(ruleId),
                request: data
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
            showAlert({ type: 'success', header: 'Deleted', message: resp });
        });
    };

    const fieldTypeTab = [
        { name: 'Enable' },
        { name: 'Disable' },
        { name: 'Date validation' },
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
                                    <Grid col={3}>
                                        <label className="input-label">Function</label>
                                    </Grid>
                                    <Grid col={9}>
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
                    <div className="edit-rules">
                        <div className="edit-rules__buttons">
                            {ruleId ? (
                                <ModalToggleButton
                                    opener
                                    modalRef={deleteWarningModal}
                                    type="button"
                                    className="delete-btn"
                                    unstyled>
                                    <Icon.Delete size={3} className="margin-right-2px" />
                                    <span>Delete</span>
                                </ModalToggleButton>
                            ) : null}
                            <div>
                                <Button type="button" outline onClick={handleCancel}>
                                    Cancel
                                </Button>
                                <Button type="submit" className="lbr">
                                    {ruleId ? 'Update' : 'Add to library'}
                                </Button>
                            </div>
                        </div>
                    </div>
                </Form>
            </div>
        </>
    );
};

export default AddBusinessRule;
