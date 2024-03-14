import {
    CreateRuleRequest,
    PageRuleControllerService,
    PagesQuestion,
    PagesSection,
    PagesSubSection,
    PagesTab,
    Rule
} from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import styles from './EditBusinessRule.module.scss';
import { Breadcrumb } from 'breadcrumb/Breadcrumb';
import { BusinessRulesForm } from './BusinessRulesForm';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { Button, Form } from '@trussworks/react-uswds';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { useAlert } from 'alert';

export const EditBusinessRule = () => {
    const form = useForm<CreateRuleRequest>();
    const navigate = useNavigate();
    const { ruleId } = useParams();

    const { options, fetch, isLoading } = useOptions();
    const { page } = useGetPageDetails();

    const { showAlert } = useAlert();

    const [selectedSourceValues, setSelectedSourceValues] = useState<string[] | undefined>(undefined);

    useEffect(() => {
        PageRuleControllerService.viewRuleResponseUsingGet({
            authorization: authorization(),
            ruleId: Number(ruleId)
        }).then((response: Rule) => {
            fetchSourceValues(response.sourceQuestion.codeSetName ?? '');
            setSelectedSourceValues(response.sourceValues);

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
        } else {
            const matchedValues = options.filter((opt) => selectedSourceValues?.find((val) => val === opt.name));
            const newValues = matchedValues.map((value) => ({ id: value.value, text: value.name }));

            form.setValue('sourceValues', newValues);
        }
    };

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
                id: page?.id ?? 0,
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

    return (
        <div className={styles.editBusinessRules}>
            {!isLoading && (
                <>
                    <div className="breadcrumb-wrap">
                        <Breadcrumb start="../">Business rules</Breadcrumb>
                    </div>
                    <Form onSubmit={onSubmit}>
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

                        <div className={styles.footerBtns}>
                            <Button
                                type="button"
                                outline
                                onClick={() => {
                                    form.reset();
                                    redirectToLibrary();
                                }}>
                                Cancel
                            </Button>
                            <Button type="submit" disabled={!form.formState.isValid}>
                                Update
                            </Button>
                        </div>
                    </Form>
                </>
            )}
        </div>
    );
};
