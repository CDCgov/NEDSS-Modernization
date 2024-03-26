import {
    RuleRequest,
    PageRuleControllerService,
    PagesQuestion,
    PagesSection,
    PagesSubSection,
    PagesTab,
    Rule
} from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useRef, useState } from 'react';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import styles from './EditBusinessRule.module.scss';
import { Breadcrumb } from 'breadcrumb/Breadcrumb';
import { BusinessRulesForm } from '../Form/BusinessRulesForm';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Button, Form, Icon, ModalRef } from '@trussworks/react-uswds';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { useAlert } from 'alert';
import { ConfirmationModal } from 'confirmation';

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
        PageRuleControllerService.viewRuleResponseUsingGet({
            authorization: authorization(),
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
                targetType: response.targetType
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

    const findTargetQuestion = (targets?: string[]): PagesQuestion[] => {
        const targetQuestions: PagesQuestion[] = [];
        page?.tabs?.map((tab: PagesTab) => {
            tab.sections?.map((section: PagesSection) => {
                section.subSections?.map((subsection: PagesSubSection) => {
                    subsection.questions?.map((question: PagesQuestion) => {
                        targets?.map((target) => {
                            if (target === question.question) {
                                targetQuestions.push(question);
                            }
                        });
                    });
                });
            });
        });
        return targetQuestions;
    };

    const findTargetSubsection = (targets?: string[]): PagesSubSection[] => {
        const targetQuestions: PagesSubSection[] = [];
        page?.tabs?.map((tab: PagesTab) => {
            tab.sections?.map((section: PagesSection) => {
                section.subSections?.map((subsection: PagesSubSection) => {
                    targets?.map((target) => {
                        if (target === subsection.questionIdentifier) {
                            targetQuestions.push(subsection);
                        }
                    });
                });
            });
        });
        return targetQuestions;
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
            await PageRuleControllerService.updatePageRuleUsingPut({
                authorization: authorization(),
                ruleId: Number(ruleId) ?? 0,
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
        console.log({ sourceValues });
        console.log({ selectedSourceValues });
        if (
            form.formState.isDirty ||
            JSON.stringify(watch.targetIdentifiers) !== JSON.stringify(initialTargetIdentifiers) ||
            JSON.stringify(watch.sourceIdentifier) !== JSON.stringify(initialSourceIdentifiers) ||
            JSON.stringify(sourceValues) !== JSON.stringify(selectedSourceValues)
        ) {
            if (checkIsValid()) {
                console.log('why');
                return true;
            }
        } else {
            return false;
        }
    };

    const onDelete = () => {
        PageRuleControllerService.deletePageRuleUsingDelete({
            authorization: authorization(),
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
                                    editSourceQuestion={findSourceQuestion(form.getValues('sourceIdentifier'))}
                                    editTargetQuestions={findTargetQuestion(form.getValues('targetIdentifiers'))}
                                    editTargetSubsections={findTargetSubsection(form.getValues('targetIdentifiers'))}
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
                            onClick={() => {
                                form.reset();
                                redirectToLibrary();
                            }}>
                            Cancel
                        </Button>
                        <Button type="submit" disabled={!ifDisabled()} onClick={onSubmit}>
                            Update
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
};
