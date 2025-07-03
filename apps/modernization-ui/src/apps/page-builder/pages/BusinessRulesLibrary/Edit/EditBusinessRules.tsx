import { Button, Form, Icon, ModalRef } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import {
    PageRuleControllerService,
    PagesQuestion,
    PagesSection,
    PagesSubSection,
    PagesTab,
    Rule,
    RuleRequest
} from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { Breadcrumb } from 'breadcrumb/Breadcrumb';
import { ConfirmationModal } from 'confirmation';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router';
import { BusinessRulesForm } from '../Form/BusinessRulesForm';
import styles from './EditBusinessRule.module.scss';
import { findTargetQuestion, findTargetSubsection } from '../helpers/findTargetQuestions';

export const EditBusinessRule = () => {
    const form = useForm<RuleRequest>();
    const watch = useWatch(form);
    const navigate = useNavigate();
    const { ruleId } = useParams();
    const deleteWarningModal = useRef<ModalRef>(null);

    const { options, fetch } = useOptions();
    const { page } = useGetPageDetails();

    const { showAlert } = useAlert();

    const [selectedSourceValues, setSelectedSourceValues] = useState<string[] | undefined>(undefined);

    const [initialSourceIdentifiers, setInitialSourceIdentifiers] = useState<string>('');
    const [initialTargetIdentifiers, setInitialTargetIdentifiers] = useState<string[]>([]);

    useEffect(() => {
        PageRuleControllerService.viewRuleResponse({
            ruleId: Number(ruleId)
        }).then((response: Rule) => {
            fetchSourceValues(response.sourceQuestion.codeSetName ?? '');
            setSelectedSourceValues(response.sourceValues?.map((s) => s.trim()));
            setInitialSourceIdentifiers(response.sourceQuestion.questionIdentifier ?? '');
            setInitialTargetIdentifiers(response.targets.map((target) => target.targetIdentifier ?? '') ?? []);
            form.reset({
                anySourceValue: response.anySourceValue,
                comparator: response.comparator,
                ruleFunction: response.ruleFunction,
                description: response.description,
                sourceIdentifier: response.sourceQuestion.questionIdentifier,
                sourceText: `${response.sourceQuestion.label} (${response.sourceQuestion.questionIdentifier})`,
                targetIdentifiers: response.targets.map((target) => target.targetIdentifier),
                targetValueText: response.targets.map((target) => target.label),
                targetType: response.targetType ?? Rule.targetType.QUESTION
            });
        });
    }, [ruleId]);

    const findSourceQuestion = (questionIdentifier?: string): PagesQuestion | undefined => {
        let result: PagesQuestion | undefined = undefined;
        page?.tabs?.map((tab: PagesTab) => {
            tab.sections?.map((section: PagesSection) => {
                section.subSections?.map((subsection: PagesSubSection) => {
                    subsection.questions?.map((question: PagesQuestion) => {
                        if (question.question === questionIdentifier) {
                            result = question;
                        }
                    });
                });
            });
        });
        return result;
    };

    const fetchSourceValues = (valueSet?: string) => {
        if (valueSet) {
            fetch(valueSet);
        }
    };

    useEffect(() => {
        if (selectedSourceValues && options) {
            const matchedValues = options.filter((opt) => selectedSourceValues?.find((val) => val === opt.name));
            const newValues = matchedValues.map((value) => ({ id: value.value, text: value.name }));
            form.setValue('sourceValues', newValues);
        }
    }, [JSON.stringify(selectedSourceValues), JSON.stringify(options)]);

    const redirectToLibrary = () => {
        if (page?.id) {
            navigate(`/page-builder/pages/${page.id}/business-rules`);
        } else {
            navigate(`../`);
        }
    };

    const onSubmit = form.handleSubmit(async (data) => {
        try {
            await PageRuleControllerService.updatePageRule({
                ruleId: Number(ruleId),
                requestBody: data
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
            redirectToLibrary();
        } catch {
            showAlert({
                type: 'error',
                message: 'There was an error. Please try again.'
            });
        }
    });

    const checkIsValid = () => {
        if (
            watch.sourceIdentifier &&
            (watch.targetIdentifiers?.length ?? 0 > 0) &&
            watch.targetType &&
            watch.ruleFunction &&
            (watch.anySourceValue || (watch.comparator && watch.sourceValues))
        ) {
            return true;
        } else if (watch.ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            if (watch.sourceIdentifier && watch.comparator && watch.targetIdentifiers) {
                return true;
            }
        } else {
            return false;
        }
    };

    const ifDisabled = () => {
        const sourceValues = watch.sourceValues?.map((val) => val.text);
        if (watch.ruleFunction !== Rule.ruleFunction.DATE_COMPARE) {
            if (
                checkIsValid() &&
                (form.formState.isDirty ||
                    JSON.stringify(watch.targetIdentifiers) !== JSON.stringify(initialTargetIdentifiers) ||
                    JSON.stringify(watch.sourceIdentifier) !== JSON.stringify(initialSourceIdentifiers) ||
                    JSON.stringify(sourceValues) !== JSON.stringify(selectedSourceValues))
            ) {
                return true;
            }
        } else if (watch.ruleFunction === Rule.ruleFunction.DATE_COMPARE) {
            if (
                checkIsValid() &&
                (form.formState.isDirty ||
                    JSON.stringify(watch.targetIdentifiers) !== JSON.stringify(initialTargetIdentifiers) ||
                    JSON.stringify(watch.sourceIdentifier) !== JSON.stringify(initialSourceIdentifiers))
            ) {
                return true;
            }
        } else {
            return false;
        }
    };

    const onDelete = () => {
        PageRuleControllerService.deletePageRule({
            id: page?.id ?? 0,
            ruleId: Number(ruleId)
        }).then(() => {
            redirectToLibrary();
            showAlert({
                type: 'success',
                message: (
                    <>
                        The business rule <span className="bold-text">'{form.getValues('sourceText')}'</span> was
                        successfully deleted.
                    </>
                )
            });
            deleteWarningModal.current?.toggleModal(undefined, true);
        });
    };

    return (
        <>
            <ConfirmationModal
                modal={deleteWarningModal}
                title="Warning"
                message="Are you sure you want to delete this business rule?"
                detail="Once deleted, this business rule will be permanently removed from the system and will no longer be associated with the page."
                confirmText="Yes, delete"
                onConfirm={onDelete}
                onCancel={() => deleteWarningModal.current?.toggleModal(undefined, false)}
            />
            <div className={styles.breadCrumb}>
                <Breadcrumb start="../">Business rules</Breadcrumb>
            </div>
            <div className={styles.editRules}>
                <Form onSubmit={onSubmit} className={styles.form}>
                    <div className={styles.container}>
                        <div className={styles.title}>
                            <h2>Edit business rules</h2>
                        </div>

                        <div className={styles.content}>
                            <FormProvider {...form}>
                                <BusinessRulesForm
                                    isEdit
                                    sourceValues={options}
                                    onFetchSourceValues={fetchSourceValues}
                                    editSourceQuestion={findSourceQuestion(initialSourceIdentifiers)}
                                    editTargetQuestions={findTargetQuestion(initialTargetIdentifiers, page)}
                                    editTargetSubsections={findTargetSubsection(initialTargetIdentifiers, page)}
                                />
                            </FormProvider>
                        </div>
                    </div>
                </Form>
                <div className={styles.footerBtns}>
                    <div className={styles.deleteBtn}>
                        <Button
                            type="button"
                            outline
                            data-testid="deleteBtnEditBusinessRulesPage"
                            onClick={() => deleteWarningModal.current?.toggleModal(undefined, true)}
                            className={styles.button}>
                            <Icon.Delete size={3} />
                            Delete
                        </Button>
                    </div>
                    <div className={styles.submitBtns}>
                        <Button
                            type="button"
                            outline
                            data-testid="cancelEditBusinessRulesModel"
                            onClick={() => {
                                form.reset();
                                redirectToLibrary();
                            }}>
                            Cancel
                        </Button>
                        <Button
                            type="submit"
                            disabled={!ifDisabled()}
                            data-testid="updateBtnEditBusinessRulesPage"
                            onClick={onSubmit}>
                            Update
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
};
