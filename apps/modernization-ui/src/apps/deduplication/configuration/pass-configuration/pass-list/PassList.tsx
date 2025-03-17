import { Icon } from '@trussworks/react-uswds';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { PassEntry } from './pass-entry/PassEntry';
import styles from './pass-list.module.scss';

type Props = {
    passes: Pass[];
    selectedPass?: Pass;
    onSetSelectedPass: (pass: Pass) => void;
    onEditPassName: (pass: Pass) => void;
    onAddPass: () => void;
};
export const PassList = ({ passes, selectedPass, onSetSelectedPass, onEditPassName, onAddPass }: Props) => {
    return (
        <aside className={styles.passList}>
            <div className={styles.heading}>
                <Heading level={2}>Pass configurations</Heading>
            </div>
            <Shown
                when={passes !== undefined && passes.length > 0}
                fallback={<div className={styles.noPassesText}>No pass configurations have been created.</div>}>
                {passes.map((pass, k) => (
                    <PassEntry
                        key={k}
                        pass={pass}
                        onSelectPass={onSetSelectedPass}
                        onEditName={onEditPassName}
                        isSelected={pass === selectedPass}
                    />
                ))}
            </Shown>
            <Button
                icon={<Icon.Add size={3} />}
                labelPosition="right"
                unstyled
                onClick={onAddPass}
                className={styles.addPassButton}>
                Add pass configuration
            </Button>
        </aside>
    );
};
