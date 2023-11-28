import { Button } from '@trussworks/react-uswds';
import { ButtonBar } from 'apps/page-builder/components/ButtonBar/ButtonBar';
import { CloseableHeader } from 'apps/page-builder/components/CloseableHeader/CloseableHeader';
import styles from './create-question-form.module.scss';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';

type Props = {
    onCancel?: (question?: number) => void;
    onCreated?: (question: number) => void;
};

export const CreateQuestionForm = ({ onCancel }: Props) => {
    return (
        <>
            <CloseableHeader onClose={() => (onCancel ? onCancel() : null)} title="Add question" />
            <div className={styles.form}>
                <div className={styles.heading}>
                    <div>Create new question</div>
                </div>
                <AlertBanner type="warning">
                    <span style={{ marginRight: '4px' }}>This feature is not yet implemented</span>
                </AlertBanner>
            </div>
            <ButtonBar>
                <Button onClick={() => (onCancel ? onCancel() : null)} type="button" outline>
                    Cancel
                </Button>
                <Button disabled onClick={() => (onCancel ? onCancel() : null)} type="button">
                    Create and add to page
                </Button>
            </ButtonBar>
        </>
    );
};
