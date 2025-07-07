import { Button, Form } from '@trussworks/react-uswds';
import { useAlert } from 'libs/alert';
import { PageRuleControllerService, Rule, RuleRequest } from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { Breadcrumb } from 'breadcrumb';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { BusinessRulesForm } from '../Form/BusinessRulesForm';
import styles from './AddBusinessRule.module.scss';

export type SourceValueProp = {
    name: string;
    value: string;
};

export const AddBusinessRule = () => {
    const form = useForm<RuleRequest>({
        defaultValues: { targetType: Rule.targetType.QUESTION, anySourceValue: false }
    });
    const watch = useWatch(form);
    const { options, fetch } = useOptions();
    const navigate = useNavigate();

    const { page } = useGetPageDetails();

    const { showAlert } = useAlert();

    const onSubmit = form.handleSubmit(async (data) => {
        try {
            await PageRuleControllerService.createBusinessRule({
                id: page?.id ?? 0,
                requestBody: data
            });
            showAlert({
                type: 'success',
                message: (
                    <>
                        The business rule <span className="bold-text">'{data.sourceText}'</span> is successfully added.
                        Please click the unique name to edit.
                    </>
                )
            });
            redirectToLibrary();
        } catch (error) {
            showAlert({
                type: 'error',
                message: 'There was an error. Please try again.'
            });
            redirectToLibrary();
        }
    });

    const redirectToLibrary = () => {
        if (page?.id) {
            navigate(`/page-builder/pages/${page.id}/business-rules`);
        } else {
            navigate(`../`);
        }
    };

    const fetchSourceValues = (valueSet?: string) => {
        fetch(valueSet ?? '');
    };

    const checkIsValid = () => {
        if (
            watch.sourceIdentifier &&
            (watch.targetIdentifiers?.length ?? 0 > 0) &&
            (watch.targetIdentifiers?.length ?? 0) < 11 &&
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

    return (
        <>
            <div className={styles.breadCrumb}>
                <Breadcrumb start="../">Business rules</Breadcrumb>
            </div>

            <div className={styles.addRule}>
                <Form onSubmit={onSubmit} className={styles.form}>
                    <div className={styles.container}>
                        <div className={styles.title}>
                            <h2>Add new business rules</h2>
                        </div>
                        <div className={styles.content}>
                            <FormProvider {...form}>
                                <BusinessRulesForm
                                    isEdit={false}
                                    sourceValues={options}
                                    onFetchSourceValues={fetchSourceValues}
                                />
                            </FormProvider>
                        </div>
                    </div>
                </Form>
                <div className={styles.footerBtns}>
                    <Button
                        outline
                        onClick={() => {
                            form.reset();
                            redirectToLibrary();
                        }}
                        type="button">
                        Cancel
                    </Button>
                    <Button
                        disabled={!checkIsValid()}
                        type="submit"
                        data-testid="AddToLibraryNewBusinessRulesModel"
                        onClick={onSubmit}>
                        Add to library
                    </Button>
                </div>
            </div>
        </>
    );
};
