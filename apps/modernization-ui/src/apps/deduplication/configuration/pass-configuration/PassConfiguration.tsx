import { Pass } from 'apps/deduplication/api/model/Pass';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';
import { Shown } from 'conditional-render';
import { useState } from 'react';
import { SelectPass } from '../notification-cards/SelectPass';
import { PassList } from './pass-list/PassList';
import styles from './pass-configuration.module.scss';

export const PassConfiguration = () => {
    const { passes } = useMatchConfiguration();
    const [selectedPass, setSelectedPass] = useState<Pass | undefined>();

    const handleEditPassName = (pass: Pass) => {
        console.log('edit pass name clicked', pass);
    };

    const handleAddPass = () => {
        console.log('Add pass clicked');
    };

    return (
        <div className={styles.passConfiguration}>
            <PassList
                passes={passes}
                onSetSelectedPass={setSelectedPass}
                onEditPassName={handleEditPassName}
                onAddPass={handleAddPass}
                selectedPass={selectedPass}
            />
            <Shown when={selectedPass !== undefined} fallback={<SelectPass passCount={passes.length} />}>
                Select pass: {selectedPass?.name}
            </Shown>
        </div>
    );
};
