import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { useEffect } from 'react';
import { useFormContext } from 'react-hook-form';
import styles from './blocking-criteria.module.scss';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from '@trussworks/react-uswds';

type Props = {
    onShowAttributes: () => void;
};
export const BlockingCriteria = ({ onShowAttributes }: Props) => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();

    useEffect(() => {
        console.log('form values', form.getValues());
    }, [form.getValues()]);

    return (
        <div className={styles.blockingCriteria}>
            <div className={styles.heading}>
                <Heading level={2}>1. Blocking criteria</Heading>
                <span>Include records that meet all these conditions</span>
            </div>
            <div className={styles.body}>
                <Shown
                    when={form.getValues().blockingCriteria.length > 0}
                    fallback={
                        <div className={styles.noBlockingCriteriaText}>Please add blocking criteria to get started</div>
                    }>
                    BlockingCriteria: {form.getValues().blockingCriteria}
                </Shown>
                <div className={styles.buttonContainer}>
                    <Button
                        icon={<Icon.Add size={3} />}
                        labelPosition="right"
                        outline
                        onClick={onShowAttributes}
                        sizing="small"
                        className={styles.addButton}>
                        Add blocking attribute(s)
                    </Button>
                </div>
            </div>
        </div>
    );
};
