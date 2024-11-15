import { Icon } from '@trussworks/react-uswds';
import { DataElements, DataElementsConfiguration } from 'apps/deduplication/data-elements/DataElement';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { MatchingFieldOption } from '../../model/Matching';
import { MatchingConfiguration } from '../../model/Pass';
import styles from './matching-criteria-form.module.scss';
import { MatchingCriteriaRow } from './row/MatchingCriteriaRow';
import { MatchingCriteriaFieldSelection } from './modal/MatchingCriteriaFieldSelection';

type Props = {
    activePass: number;
    dataElementConfiguration: DataElementsConfiguration;
};
export const MatchingCriteriaForm = ({ activePass, dataElementConfiguration }: Props) => {
    const [logOddsTotal, setLogOddsTodal] = useState<number | undefined>();
    const [showModal, setShowModal] = useState<boolean>(false);
    const form = useFormContext<MatchingConfiguration>();
    const { append, remove } = useFieldArray<MatchingConfiguration>({ name: `passes.${activePass}.matchingCriteria` });
    const handleShowmodal = () => {
        setShowModal(true);
    };

    const handleModalAccept = (fields: MatchingFieldOption[]) => {
        const currentFields = form.getValues(`passes.${activePass}.matchingCriteria`).map((mc) => mc.field);
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

    useEffect(() => {
        const total = form
            .getValues(`passes.${activePass}.matchingCriteria`)
            ?.map((m) => dataElementConfiguration[m.field.value]?.logOdds)
            .filter((f) => f !== undefined)
            .reduce((a, b) => a + b, 0);
        setLogOddsTodal(total > 0 ? total : undefined);
    }, [form.getValues(`passes.${activePass}.matchingCriteria`)]);

    return (
        <section className={styles.matchingCriteriaForm}>
            <header>
                <Heading level={2}>Matching criteria</Heading>
                <p className={styles.headerText}>Include records that meet all these conditions</p>
            </header>
            <div className={styles.matchingCriteria}>
                {form
                    .getValues(`passes.${activePass}.matchingCriteria`)
                    ?.map((b, i) => (
                        <MatchingCriteriaRow
                            key={i}
                            activePass={activePass}
                            label={b.field.name}
                            index={i}
                            onRemove={() => handleRemove(i)}
                            logOdds={(dataElementConfiguration as DataElements)[b.field.value]?.logOdds ?? 0}
                        />
                    ))}
                {logOddsTotal && (
                    <div className={styles.logOddsTotal}>
                        <div />
                        <div>Log odds total: {logOddsTotal}</div>
                    </div>
                )}
            </div>
            <Button unstyled onClick={handleShowmodal}>
                <Icon.Add size={3} /> Add matching criteria
            </Button>
            <Shown when={showModal}>
                <MatchingCriteriaFieldSelection
                    activePass={activePass}
                    dataElementConfiguration={dataElementConfiguration}
                    onCancel={() => setShowModal(false)}
                    onAccept={handleModalAccept}
                />
            </Shown>
        </section>
    );
};
