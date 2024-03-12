import { CreateRuleRequest, Rule } from 'apps/page-builder/generated';
import { FormProvider, useForm } from 'react-hook-form';
import { Breadcrumb } from 'breadcrumb';
import styles from './AddBusinessRule.module.scss';
import { Button, Form } from '@trussworks/react-uswds';
import { BusinessRulesForm } from './BusinessRulesForm';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';

export type FieldProps = {
    name: string;
    value: string;
};

export const AddBusinessRule = () => {
    const form = useForm<CreateRuleRequest>({
        defaultValues: { targetType: Rule.targetType.QUESTION, anySourceValue: false }
    });
    const { options, fetch } = useOptions();

    const onSubmit = () => {
        console.log('submit add business rules');
    };

    const handleSubmit = () => {
        console.log('handle submit here');
    };

    const handleSourceList = (valueSet?: string) => {
        fetch(valueSet ?? '');
    };

    return (
        <>
            <div className="breadcrumb-wrap">
                <Breadcrumb start="../">Business rules</Breadcrumb>
            </div>

            <div className={styles.addRule}>
                <Form onSubmit={onSubmit}>
                    <div className={styles.title}>
                        <h2>Add new business rule</h2>
                    </div>

                    <div className={styles.content}>
                        <FormProvider {...form}>
                            <BusinessRulesForm
                                isEdit={false}
                                sourceValues={options}
                                handleSourceValues={handleSourceList}
                            />
                        </FormProvider>
                    </div>

                    <div className={styles.footerBtns}>
                        <Button outline onClick={() => form.reset()} type="button">
                            Cancel
                        </Button>
                        <Button disabled={!form.formState.isValid} onClick={handleSubmit} type="button">
                            Add to library
                        </Button>
                    </div>
                </Form>
            </div>
        </>
    );
};
