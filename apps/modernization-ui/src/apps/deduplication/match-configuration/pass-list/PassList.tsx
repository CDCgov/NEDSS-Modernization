import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { useFieldArray, useFormContext } from 'react-hook-form';
import styles from './pass-list.module.scss';
import { PassListEntry } from './PassListEntry';
import { MatchingConfiguration } from '../model/Pass';

type Props = {
    activeIndex: number;
    onSetActive: (index: number) => void;
};
export const PassList = ({ activeIndex, onSetActive }: Props) => {
    const form = useFormContext<MatchingConfiguration>();
    const { append } = useFieldArray<MatchingConfiguration>({ control: form.control, name: 'passes' });

    const handleAddPass = () => {
        append({
            name: `Pass #${form.getValues('passes').length + 1}`,
            description: 'Description',
            active: true,
            blockingCriteria: [],
            matchingCriteria: [],
            lowerBound: 0,
            upperBound: 0
        });
    };
    return (
        <div className={styles.passList}>
            <Heading level={2}>Pass configurations</Heading>
            <div className={styles.passEntries}>
                {form.getValues('passes').map((p, k) => (
                    <PassListEntry pass={p} key={k} activePass={activeIndex == k} onClick={() => onSetActive(k)} />
                ))}
                <Button unstyled onClick={handleAddPass}>
                    <Icon.Add size={3} /> Add pass configuration
                </Button>
            </div>
        </div>
    );
};
