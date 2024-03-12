import { Button } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { ButtonBar } from 'apps/page-builder/components/ButtonBar/ButtonBar';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import { PagesQuestion, PagesSubSection } from 'apps/page-builder/generated';
import { GroupRequest, useGroupSubsection } from 'apps/page-builder/hooks/api/useGroupSubsection';
import { Spinner } from 'components/Spinner';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { RepeatingBlock } from './RepeatingBlock';
import { SubsectionDetails } from './SubsectionDetails';
import styles from './group-question.module.scss';

type Props = {
    page: number;
    subsection: PagesSubSection;
    questions: PagesQuestion[];
    onSuccess: () => void;
    onCancel: () => void;
};

export const GroupQuestion = ({ page, subsection, questions, onSuccess, onCancel }: Props) => {
    const { showAlert } = useAlert();
    const { isLoading, error, response, group } = useGroupSubsection();
    const [valid, setValid] = useState(false);
    const form = useForm<GroupRequest>({
        mode: 'onChange',
        defaultValues: {
            name: subsection.name,
            batches: questions.map((question) => ({
                appearsInTable: true,
                width: 0,
                label: '',
                id: question.id
            })),
            blockName: '',
            visible: subsection.visible,
            repeatingNbr: 1
        }
    });

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
                header: 'Grouped',
                message: `You've successfully grouped ${form.getValues('blockName')} subsection`
            });
            form.reset();
            onSuccess();
        } else if (error) {
            showAlert({
                type: 'error',
                header: 'error',
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
            <div className={styles.content}>
                <FormProvider {...form}>
                    <SubsectionDetails />
                    <RepeatingBlock questions={questions} valid={valid} setValid={setValid} />
                </FormProvider>
            </div>
            <ButtonBar>
                <Button onClick={handleCancel} type="button" outline>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} type="button" disabled={!valid}>
                    Submit
                </Button>
            </ButtonBar>
        </div>
    );
};
