import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useState } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { BlockingField, MatchingConfiguration } from '../../Configuration';
import styles from './blocking-criteria.module.scss';
import { BlockingCriteriaFieldSelection } from './modal/BlockingCriteriaFieldSelection';

type Props = {
    activePass: number;
};
export const BlockingCriteriaForm = ({ activePass }: Props) => {
    const [showModal, setShowModal] = useState<boolean>(false);
    const form = useFormContext<MatchingConfiguration>();
    const { append, remove } = useFieldArray<MatchingConfiguration>({ name: `passes.${activePass}.blockingCriteria` });

    const handleShowmodal = () => {
        setShowModal(true);
    };

    const handleModalAccept = (fields: BlockingField[]) => {
        console.log('selected these fields:', fields);
        const currentFields = form.getValues(`passes.${activePass}.blockingCriteria`).map((bc) => bc.field);

        // add fields that were selected but not already in list
        fields.forEach((f) => {
            if (currentFields.indexOf(f) < 0) {
                append({ field: f, method: 'exact' });
            }
        });

        // remove fields that were removed from list
        const toRemove: number[] = [];
        currentFields.forEach((c, index) => {
            if (!fields.includes(c)) {
                toRemove.push(index);
            }
        });
        remove(toRemove);
        setShowModal(false);
    };

    return (
        <section className={styles.blockingCriteriaForm}>
            <header>
                <Heading level={2}>Blocking criteria</Heading>
                <p className={styles.headerText}>Include records that meet all these conditions</p>
            </header>
            {form.getValues('passes')[activePass].blockingCriteria.map((b, i) => (
                <div key={i}>
                    Blocking Criteria: {b.field} - {b.method}
                </div>
            ))}
            <Button unstyled onClick={handleShowmodal}>
                <Icon.Add size={3} /> Add blocking criteria
            </Button>
            <Shown when={showModal}>
                <BlockingCriteriaFieldSelection
                    activePass={activePass}
                    onCancel={() => setShowModal(false)}
                    onAccept={handleModalAccept}
                />
            </Shown>
        </section>
    );
};
