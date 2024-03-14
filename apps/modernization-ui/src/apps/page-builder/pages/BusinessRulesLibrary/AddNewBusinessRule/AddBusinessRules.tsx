import { CreateRuleRequest, PageRuleControllerService, Rule } from 'apps/page-builder/generated';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { Breadcrumb } from 'breadcrumb';
import styles from './AddBusinessRule.module.scss';
import { Button, Form } from '@trussworks/react-uswds';
import { BusinessRulesForm } from './BusinessRulesForm';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { authorization } from 'authorization';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { useAlert } from 'alert';
import { useNavigate } from 'react-router-dom';

export type SourceValueProp = {
    name: string;
    value: string;
};

export const AddBusinessRule = () => {
    const form = useForm<CreateRuleRequest>({
        defaultValues: { targetType: Rule.targetType.QUESTION, anySourceValue: false }
    });
    const watch = useWatch(form);
    const { options, fetch } = useOptions();
    const navigate = useNavigate();

    const { page } = useGetPageDetails();

    const { showAlert } = useAlert();

    const onSubmit = form.handleSubmit(async (data) => {
        try {
            await PageRuleControllerService.createBusinessRuleUsingPost({
                authorization: authorization(),
                id: page?.id ?? 0,
                request: data
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
            watch.targetType &&
            watch.ruleFunction &&
            (watch.anySourceValue || (watch.comparator && watch.sourceValues))
        ) {
            return true;
        } else {
            return false;
        }
    };

    return (
        <>
            <div className="breadcrumb-wrap">
                <Breadcrumb start="../">Business rules</Breadcrumb>
            </div>

            <div className={styles.addRule}>
                <Form onSubmit={onSubmit}>
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
                        <Button disabled={!checkIsValid()} type="submit">
                            Add to library
                        </Button>
                    </div>
                </Form>
            </div>
        </>
    );
};
