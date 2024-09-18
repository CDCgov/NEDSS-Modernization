import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import styles from './patient-match-configuration-page.module.scss';
import NoPassConfigurations from '../PassConfiguration/NoPassConfigurations';
import PassConfigurationListItem from '../PassConfiguration/PassConfigurationListItem';
import { useEffect, useRef, useState } from 'react';
import PatientMatchForm from './PatientMatchForm';
import { PassConfiguration } from 'apps/dedup-config/types';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';
import { ConfirmationModal } from 'confirmation';

const PatientMatchConfigurationPage = () => {
    const [configurations, setConfigurations] = useState<PassConfiguration[]>([]);
    const [selectedConfigurationIndex, setSelectedConfigurationIndex] = useState<number | null>(null);
    const [isEditingConfiguration, setIsEditingConfiguration] = useState<boolean>(false);
    const { setBlockingCriteria, setMatchingCriteria } = usePatientMatchContext();
    const deleteModalRef = useRef<ModalRef>(null);

    const handleAddConfiguration = () => {
        const configs = [...configurations];
        configs.push({
            name: `New configuration (${configurations.length})`,
            description: 'a description will go here',
            active: true,
            blockingCriteria: [],
            matchingCriteria: []
        });
        setConfigurations(configs);
        setSelectedConfigurationIndex(configurations.length);
        setIsEditingConfiguration(true);
        setBlockingCriteria([]);
        setMatchingCriteria([]);
        localStorage.setItem('passConfigurations', JSON.stringify(configs));
    };

    const handleConfigListItemClick = (index: number) => {
        setSelectedConfigurationIndex(index);
        setIsEditingConfiguration(true);
        const selectedConfig = configurations[index];
        setBlockingCriteria(selectedConfig.blockingCriteria ?? []);
        setMatchingCriteria(selectedConfig.matchingCriteria ?? []);
    };

    const showConfiguration = isEditingConfiguration && configurations.length && selectedConfigurationIndex !== null;

    useEffect(() => {
        const storedConfiguration = localStorage.getItem('passConfigurations');
        if (storedConfiguration) {
            setConfigurations(JSON.parse(storedConfiguration));
            setSelectedConfigurationIndex(0);
            setIsEditingConfiguration(true);
        } else {
            setConfigurations([]);
            setSelectedConfigurationIndex(null);
            setIsEditingConfiguration(false);
        }
    }, []);

    const handleDeleteConfiguration = () => {
        const configs = [...configurations];
        if (selectedConfigurationIndex != null) {
            configs.splice(selectedConfigurationIndex, 1);
            setConfigurations(configs);
            if (selectedConfigurationIndex > 0) {
                setSelectedConfigurationIndex(configs.length - 1);
                setBlockingCriteria(configs[configs.length - 1].blockingCriteria ?? []);
                setMatchingCriteria(configs[configs.length - 1].matchingCriteria ?? []);
            } else {
                setSelectedConfigurationIndex(null);
                setBlockingCriteria([]);
                setMatchingCriteria([]);
            }
            localStorage.setItem('passConfigurations', JSON.stringify(configs));
            deleteModalRef.current?.toggleModal();
        }
    };

    return (
        <>
            <ConfirmationModal
                modal={deleteModalRef}
                title={'Delete'}
                message={'are you sure'}
                onConfirm={handleDeleteConfiguration}
                onCancel={() => deleteModalRef.current?.toggleModal()}
            />
            <div className={styles.wrapper}>
                <div className={styles.configurationList}>
                    <h3>Pass configurations</h3>
                    {configurations.length ? (
                        <ul>
                            {configurations.map((configuration, index) => (
                                <PassConfigurationListItem
                                    {...configuration}
                                    key={index}
                                    selected={index === selectedConfigurationIndex}
                                    onClick={handleConfigListItemClick}
                                    index={index}
                                />
                            ))}
                        </ul>
                    ) : (
                        <p className={styles.noConfigurationText}>No pass configurations have been created.</p>
                    )}

                    <Button className={styles.addButton} unstyled type={'button'} onClick={handleAddConfiguration}>
                        <Icon.Add />
                        Add new pass configuration
                    </Button>
                </div>
                <div className={styles.configurationDetails}>
                    {showConfiguration ? (
                        <PatientMatchForm
                            passConfiguration={configurations[selectedConfigurationIndex]}
                            onDeleteConfiguration={() => deleteModalRef.current?.toggleModal()}
                        />
                    ) : (
                        <NoPassConfigurations />
                    )}
                </div>
            </div>
        </>
    );
};

export default PatientMatchConfigurationPage;
