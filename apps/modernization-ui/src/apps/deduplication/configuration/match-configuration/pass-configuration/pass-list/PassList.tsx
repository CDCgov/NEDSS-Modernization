import { useAlert } from 'libs/alert';
import { Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useState } from 'react';
import { UpdatePassNameModal } from '../pass-form/save-modal/UpdatePassNameModal';
import { PassEntry } from './pass-entry/PassEntry';
import styles from './pass-list.module.scss';
import { Icon } from 'design-system/icon';

type Props = {
    passes: Pass[];
    selectedPass?: Pass;
    onSetSelectedPass: (pass: Pass) => void;
    onRenamePass: (pass: Pass, onSuccess: () => void) => void;
    onAddPass: () => void;
};
export const PassList = ({ passes, selectedPass, onSetSelectedPass, onAddPass, onRenamePass }: Props) => {
    const { showSuccess } = useAlert();
    const [passToUpdate, setPassToUpdate] = useState<Pass | undefined>(undefined);

    const handleUpdateName = (name: string, description?: string) => {
        if (passToUpdate) {
            onRenamePass({ ...passToUpdate, name, description }, () => {
                showSuccess(`You have successfully updated the pass configuration.`);
                setPassToUpdate(undefined);
            });
        }
    };
    return (
        <>
            <UpdatePassNameModal
                name={passToUpdate?.name ?? ''}
                description={passToUpdate?.description}
                visible={passToUpdate !== undefined}
                onAccept={handleUpdateName}
                onCancel={() => setPassToUpdate(undefined)}
            />
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
                            onEditName={() => setPassToUpdate(pass)}
                            isSelected={pass.id === selectedPass?.id}
                        />
                    ))}
                </Shown>
                <Button
                    icon={<Icon name="add" />}
                    labelPosition="right"
                    unstyled
                    onClick={onAddPass}
                    disabled={selectedPass !== undefined && selectedPass?.id === undefined}
                    className={styles.addPassButton}>
                    Add pass configuration
                </Button>
            </aside>
        </>
    );
};
