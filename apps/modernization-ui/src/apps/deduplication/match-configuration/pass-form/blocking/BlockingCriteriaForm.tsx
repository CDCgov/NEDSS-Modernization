import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useState } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { BlockingFieldOption } from '../../model/Blocking';
import { MatchingConfiguration } from '../../model/Pass';
import styles from './blocking-criteria.module.scss';
import { BlockingCriteriaRow } from './BlockingCriteriaRow';
import { BlockingCriteriaFieldSelection } from './modal/BlockingCriteriaFieldSelection';

type Props = {
    activePass: number;
};
export const BlockingCriteriaForm = ({ activePass }: Props) => {
    const [showModal, setShowModal] = useState<boolean>(false);
    const form = useFormContext<MatchingConfiguration>();
    const { append, remove } = useFieldArray<MatchingConfiguration>({
        name: `passes.${activePass}.blockingCriteria`,
        rules: { required: 'Blocking criteria is required', minLength: 1 }
    });

    const handleShowmodal = () => {
        setShowModal(true);
    };

    const handleModalAccept = (fields: BlockingFieldOption[]) => {
        const currentFields = form.getValues(`passes.${activePass}.blockingCriteria`).map((bc) => bc.field);
        // remove fields that were removed from list
        const toRemove: number[] = [];
        currentFields.forEach((c, index) => {
            if (!fields.includes(c)) {
                toRemove.push(index);
            }
        });
        remove(toRemove);

        // add fields that were selected but not already in list
        fields.forEach((f) => {
            if (currentFields.indexOf(f) < 0) {
                append({ field: f });
            }
        });

        setShowModal(false);
    };

    const handleRemove = (index: number) => {
        remove(index);
    };

    return (
        <section className={styles.blockingCriteriaForm}>
            <header>
                <Heading level={2}>Blocking criteria</Heading>
                <p className={styles.headerText}>Include records that meet all these conditions</p>
            </header>
            {form
                .getValues('passes')
                [
                    activePass
                ]?.blockingCriteria?.map((b, i) => <BlockingCriteriaRow key={i} activePass={activePass} label={b.field.name} index={i} onRemove={() => handleRemove(i)} />)}
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
