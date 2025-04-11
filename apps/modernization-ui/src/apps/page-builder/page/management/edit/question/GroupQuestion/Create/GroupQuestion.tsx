import { Button } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { ButtonBar } from 'apps/page-builder/components/ButtonBar/ButtonBar';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { PagesSubSection } from 'apps/page-builder/generated';
import { GroupRequest, useGroupSubsection } from 'apps/page-builder/hooks/api/useGroupSubsection';
import { Spinner } from 'components/Spinner';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { RepeatingBlock } from '../RepeatingBlock';
import { SubsectionDetails } from '../SubsectionDetails';
import styles from './group-question.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

type Props = {
    page: number;
    subsection: PagesSubSection;
    onSuccess: () => void;
    onCancel: () => void;
};

export const GroupQuestion = ({ page, subsection, onSuccess, onCancel }: Props) => {
    const { showAlert } = useAlert();
    const { isLoading, error, response, group } = useGroupSubsection();
    const [valid, setValid] = useState(false);
    const form = useForm<GroupRequest>({
        mode: 'onChange',
        defaultValues: {
            name: subsection.name,
            batches: subsection.questions.map((question) => ({
                appearsInTable: true,
                width: 0,
                label: question.name,
                id: question.id
            })),
            blockName: '',
            visible: subsection.visible,
            repeatingNbr: 0
        }
    });

    useEffect(() => {
        form.reset({
            name: subsection.name,
            batches: subsection.questions.map((question) => ({
                appearsInTable: true,
                width: 0,
                label: question.name,
                id: question.id
            })),
            blockName: '',
            visible: subsection.visible,
            repeatingNbr: 0
        });
    }, [JSON.stringify(subsection)]);

    const batches = useWatch({ control: form.control, name: 'batches' });

    const handleCancel = () => {
        form.reset();
        onCancel();
    };

    const handleSubmit = () => {
        group(page, subsection.id, { ...form.getValues() });
    };

    useEffect(() => {
        if (response) {
            showAlert({
                type: 'success',
                title: 'Grouped',
                message: `You've successfully grouped ${form.getValues('blockName')} subsection`
            });
            form.reset();
            onSuccess();
        } else if (error) {
            showAlert({
                type: 'error',
                title: 'error',
                message: 'Failed to group subsection'
            });
        }
    }, [error, response]);

    return (
        <div className={styles.groupQuestion}>
            {isLoading && (
                <div className={styles.loadingIndicator}>
                    <Spinner />
                </div>
            )}
            <CloseableHeader title="Edit subsection" onClose={handleCancel} />
            {batches.length > 20 && (
                <AlertBanner type="error">Cannot group a subsection with more than 20 questions.</AlertBanner>
            )}
            <div className={styles.content}>
                <FormProvider {...form}>
                    <SubsectionDetails />
                    <RepeatingBlock questions={subsection.questions} valid={valid} setValid={setValid} />
                </FormProvider>
            </div>
            <ButtonBar>
                <Button onClick={handleCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    onClick={handleSubmit}
                    type="button"
                    data-testid="group-questions-submit-btn"
                    disabled={batches.length > 20 || !valid || !form.formState.isValid}>
                    Submit
                </Button>
            </ButtonBar>
        </div>
    );
};
