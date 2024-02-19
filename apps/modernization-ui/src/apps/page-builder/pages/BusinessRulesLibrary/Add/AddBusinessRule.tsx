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
        defaultValues: { targetType: Rule.targetType.SUBSECTION, anySourceValue: false },
        mode: 'onChange'
    });

    const [selectedFieldType, setSelectedFieldType] = useState('');
    const [question, setQuestion] = useState<SourceQuestion>();
    const [targets, setTargets] = useState<Target[]>([]);
    const [sourceValues, setSourceValues] = useState<string[]>([]);
    const { pageId, ruleId } = useParams();
    const deleteWarningModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    useEffect(() => {
        if (ruleId) {
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
                form.setValue('targetType', resp.targetType);

                setSelectedFieldType(resp.ruleFunction);
                setTargets(resp.targets || []);
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
                id: Number(pageId),
                ruleId: Number(ruleId),
                request: data
            })
                .then((resp) => {
                    showAlert({ type: 'success', header: 'updated', message: resp.message });
                })
                .catch((err) => {
                    console.log('error', err);
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
        { name: Rule.ruleFunction.ENABLE },
        { name: Rule.ruleFunction.DISABLE },
        { name: Rule.ruleFunction.DATE_COMPARE },
        { name: Rule.ruleFunction.HIDE },
        { name: Rule.ruleFunction.UNHIDE },
        { name: Rule.ruleFunction.REQUIRE_IF }
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
                                <Grid row className="inline-field">
                                    <Grid col={3}>
                                        <Label className="input-label" htmlFor="ruleFunction" requiredMarker>
                                            Function
                                        </Label>
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
