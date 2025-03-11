import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { SelectPass } from '../notification-cards/SelectPass';
import { PassList } from './pass-list/PassList';
import styles from './pass-configuration.module.scss';
import { PassForm } from './pass-form/PassForm';

export const PassConfiguration = () => {
    const { passes } = useMatchConfiguration();
    const [newPass, setNewPass] = useState<Pass | undefined>();
    const [passList, setPassList] = useState<Pass[]>([]);
    const [selectedPass, setSelectedPass] = useState<Pass | undefined>();

    const handleEditPassName = (pass: Pass) => {
        console.log('edit pass name clicked', pass);
    };

    const handleAddPass = () => {
        // Need to confirm data loss if a pass is already selected selected
        const newPass: Pass = {
            name: 'New pass configuration',
            blockingCriteria: [BlockingAttribute.FIRST_NAME],
            matchingCriteria: [],
            active: false
        };
        setNewPass(newPass);
        setSelectedPass(newPass);
    };

    useEffect(() => {
        const passList = [newPass, ...passes].filter((p) => p !== undefined);
        setPassList(passList);
    }, [newPass, passes]);

    return (
        <div className={styles.passConfiguration}>
            <PassList
                passes={passList}
                onSetSelectedPass={setSelectedPass}
                onEditPassName={handleEditPassName}
                onAddPass={handleAddPass}
                selectedPass={selectedPass}
            />
            <Shown when={selectedPass !== undefined} fallback={<SelectPass passCount={passes.length} />}>
                <PassForm initial={selectedPass!} />
            </Shown>
        </div>
    );
};
