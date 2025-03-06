import { Pass } from 'apps/deduplication/api/model/Pass';
import styles from './pass-list.module.scss';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { NoPasses } from '../no-passes/NoPasses';
import { Button } from 'design-system/button';
import { Icon } from '@trussworks/react-uswds';
import { PassEntry } from '../pass-entry/PassEntry';

type Props = {
    passes: Pass[];
    selectedPass?: Pass;
    setSelectedPass: (pass: Pass) => void;
};
export const PassList = ({ passes, selectedPass, setSelectedPass }: Props) => {
    const handleAddPass = () => {
        console.log('clicked add pass');
    };

    const handleEditPassName = (pass: Pass) => {
        console.log('edit pass name', pass);
    };

    return (
        <aside className={styles.passList}>
            <div className={styles.heading}>
                <Heading level={2}>Pass configurations</Heading>
            </div>
            <Shown when={passes !== undefined && passes.length > 0} fallback={<NoPasses />}>
                {passes.map((pass, k) => (
                    <PassEntry
                        key={k}
                        pass={pass}
                        selectPass={() => setSelectedPass(pass)}
                        editName={() => handleEditPassName(pass)}
                        isSelected={pass === selectedPass}
                    />
                ))}
            </Shown>
            <Button
                icon={<Icon.Add size={3} />}
                labelPosition="right"
                unstyled
                onClick={handleAddPass}
                className={styles.addPassButton}>
                Add pass configuration
            </Button>
        </aside>
    );
};
