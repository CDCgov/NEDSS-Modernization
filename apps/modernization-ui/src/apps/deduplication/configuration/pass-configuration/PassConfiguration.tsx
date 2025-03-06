import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';
import { PassList } from './pass-list/PassList';
import styles from './pass-configuration.module.scss';
import { useState } from 'react';
import { Pass } from 'apps/deduplication/api/model/Pass';

export const PassConfiguration = () => {
    const { passes } = useMatchConfiguration();
    const [selectedPass, setSelectedPass] = useState<Pass | undefined>();
    return (
        <div className={styles.passConfiguration}>
            <PassList passes={passes} setSelectedPass={setSelectedPass} selectedPass={selectedPass} />
            Selected Pass: {selectedPass ? selectedPass.name : ''}
        </div>
    );
};
