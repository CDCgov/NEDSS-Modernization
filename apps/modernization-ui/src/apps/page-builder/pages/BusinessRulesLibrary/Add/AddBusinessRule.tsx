import { Button, ButtonGroup, Form, Grid, Icon, Label, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import './AddBusinessRule.scss';
import { CreateRuleRequest, PageRuleControllerService, Rule, SourceQuestion, Target } from '../../../generated';
import { useAlert } from 'alert';
import BusinessRulesForm from '../BusinessRulesForm';
import { Breadcrumb } from 'breadcrumb';
import { authorization } from 'authorization';
import { ConfirmationModal } from 'confirmation';

const AddBusinessRule = () => {
    const navigate = useNavigate();
    const form = useForm<CreateRuleRequest>({
        defaultValues: { targetType: Rule.targetType.QUESTION, anySourceValue: false },
        mode: 'onChange'
    });

    const [selectedFieldType, setSelectedFieldType] = useState('');
    const [question, setQuestion] = useState<SourceQuestion>();
    const [targets, setTargets] = useState<Target[]>([]);
    const [sourceValues, setSourceValues] = useState<string[]>([]);
    const { pageId, ruleId } = useParams();
    const deleteWarningModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();
    const [loading, setIsLoading] = useState(false);

    useEffect(() => {
        if (ruleId) {
            setIsLoading(true);
            PageRuleControllerService.viewRuleResponseUsingGet({
                authorization: authorization(),
                ruleId: Number(ruleId)
            }).then((resp: Rule) => {
                const sourceQuestion = resp.sourceQuestion?.label || '';

                setSourceValues(resp.sourceValues || []);
                setQuestion(resp?.sourceQuestion);

                form.setValue('anySourceValue', resp?.anySourceValue);
                form.setValue('comparator', resp.comparator);
                form.setValue('description', resp.description);
                form.setValue('ruleFunction', resp.ruleFunction);
                form.setValue('sourceIdentifier', resp.sourceQuestion?.questionIdentifier || '');
                form.setValue('sourceText', `${sourceQuestion} (${resp?.sourceQuestion?.questionIdentifier!})`);
                form.setValue('targetIdentifiers', resp?.targets?.map((target) => target.targetIdentifier || '') || []);
                form.setValue(
                    'targetValueText',
                    resp.targets?.map((target) => target.label || '')
                );
                form.setValue('targetType', resp.targetType || Rule.targetType.QUESTION);

                setSelectedFieldType(resp.ruleFunction);
                setTargets(resp.targets || []);
                setIsLoading(false);
            });
        }
    }, [ruleId]);

    const onSubmit = form.handleSubmit(async (data) => {
        try {
            if (!ruleId) {
                data.anySourceValue ?? data.sourceValues?.push({ id: '', text: '' });
                await PageRuleControllerService.createBusinessRuleUsingPost({
                    authorization: authorization(),
                    id: Number(pageId),
                    request: data
                });

                showAlert({
                    type: 'success',
                    message: (
                        <>
                            The business rule <span className="bold-text">'{data.sourceText}'</span> is successfully
                            added. Please click the unique name to edit.
                        </>
                    )
                });
                handleCancel();
            } else {
                await PageRuleControllerService.updatePageRuleUsingPut({
                    authorization: authorization(),
                    id: Number(pageId),
                    ruleId: Number(ruleId),
                    request: data
                });

                showAlert({
                    type: 'success',
                    message: (
                        <>
                            The business rule <span className="bold-text">'{data.sourceText}'</span> is successfully
                            updated. Please click the unique name to edit.
                        </>
                    )
                });
                handleCancel();
            }
        } catch (error) {
            showAlert({
                type: 'error',
                message: 'There was an error. Please try again.'
            });
        }
    });

    const handleCancel = () => {
        navigate(`/page-builder/pages/${pageId}/business-rules`);
    };

    const handleDeleteRule = () => {
        PageRuleControllerService.deletePageRuleUsingDelete({
            authorization: authorization(),
            id: Number(pageId),
            ruleId: Number(ruleId)
        }).then(() => {
            handleCancel();
            showAlert({
                type: 'success',
                message: (
                    <>
                        The business rule <span className="bold-text">'{form.getValues('sourceText')}'</span> was
                        successfully deleted.
                    </>
                )
            });
        });
    };

    const fieldTypeTab = [
        { value: Rule.ruleFunction.ENABLE, display: 'Enable' },
        { value: Rule.ruleFunction.DISABLE, display: 'Disable' },
        { value: Rule.ruleFunction.DATE_COMPARE, display: 'Date validation' },
        { value: Rule.ruleFunction.HIDE, display: 'Hide' },
        { value: Rule.ruleFunction.UNHIDE, display: 'Unhide' },
        { value: Rule.ruleFunction.REQUIRE_IF, display: 'Require if' }
    ];

    const title = !ruleId ? 'Add new' : 'Edit';
    const ruleFunction = form.watch('ruleFunction');

    return (
        <>
            <ConfirmationModal
                modal={deleteWarningModal}
                title="Warning"
                message="Are you sure you want to delete this business rule?"
                detail="Once deleted, this business rule will be permanently removed from the system and will no longer be associated with the page."
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
                                {ruleId ? (
                                    <Grid row className="inline-field">
                                        <Grid col={3}>
                                            <Label className="input-label" htmlFor="ruleId">
                                                Rule Id
                                            </Label>
                                        </Grid>
                                        <Grid col={9}>
                                            <label>{ruleId}</label>
                                        </Grid>
                                    </Grid>
                                ) : null}
                                <Grid row className="inline-field">
                                    <Grid col={3}>
                                        <Label className="input-label" htmlFor="ruleFunction" requiredMarker>
                                            Function
                                        </Label>
                                    </Grid>
                                    <Grid col={9}>
                                        {ruleId ? (
                                            <label>
                                                {fieldTypeTab.find((tab) => tab.value === ruleFunction)?.display ||
                                                    'Enable'}
                                            </label>
                                        ) : (
                                            <ButtonGroup type="segmented">
                                                {fieldTypeTab.map((field, index) => (
                                                    <Button
                                                        key={index}
                                                        type="button"
                                                        outline={field.value !== selectedFieldType}
                                                        onClick={() => {
                                                            setSelectedFieldType(field.value);
                                                            form.reset({
                                                                ruleFunction: field.value,
                                                                targetType: Rule.targetType.QUESTION
                                                            });
                                                        }}>
                                                        {field.display}
                                                    </Button>
                                                ))}
                                            </ButtonGroup>
                                        )}
                                    </Grid>
                                </Grid>
                                {selectedFieldType == '' && !loading ? null : (
                                    <FormProvider {...form}>
                                        <BusinessRulesForm
                                            targets={targets}
                                            question={question}
                                            sourceValues={sourceValues}
                                        />
                                    </FormProvider>
                                )}
                            </div>
                        </div>
                    </div>
                    <div className="edit-rules">
                        <div className="edit-rules__buttons">
                            <div>
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
                            </div>
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
