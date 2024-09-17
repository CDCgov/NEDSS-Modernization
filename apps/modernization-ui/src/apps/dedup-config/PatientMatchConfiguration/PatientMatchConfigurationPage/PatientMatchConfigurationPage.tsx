import { Button, Icon } from '@trussworks/react-uswds';
import styles from './patient-match-configuration-page.module.scss';
import NoPassConfigurations from '../PassConfiguration/NoPassConfigurations';
import PassConfigurationListItem from '../PassConfiguration/PassConfigurationListItem';
import { useEffect, useState } from 'react';
import PatientMatchForm from './PatientMatchForm';
import { PassConfiguration } from 'apps/dedup-config/types';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';

const PatientMatchConfigurationPage = () => {
    const [configurations, setConfigurations] = useState<PassConfiguration[]>([]);
    const [selectedConfigurationIndex, setSelectedConfigurationIndex] = useState<number | null>(null);
    const [isEditingConfiguration, setIsEditingConfiguration] = useState<boolean>(false);
    const { setBlockingCriteria } = usePatientMatchContext();

    const handleAddConfiguration = () => {
        const configs = [...configurations];
        configs.push({
            name: `New configuration (${configurations.length})`,
            description: 'a description will go here',
            active: false,
            blockingCriteria: [],
            matchingCriteria: []
        });
        setConfigurations(configs);
        setSelectedConfigurationIndex(configurations.length);
        setIsEditingConfiguration(true);
        setBlockingCriteria([]);
        localStorage.setItem('passConfigurations', JSON.stringify(configs));
    };

    const handleConfigListItemClick = (index: number) => {
        setSelectedConfigurationIndex(index);
        setIsEditingConfiguration(true);
        const selectedConfig = configurations[index];
        if (selectedConfig.blockingCriteria?.length) {
            setBlockingCriteria(selectedConfig.blockingCriteria);
        } else {
            setBlockingCriteria([]);
        }
    };

    const showConfiguration = isEditingConfiguration && configurations.length && selectedConfigurationIndex !== null;
    console.log('show', {
        isEditingConfiguration: isEditingConfiguration,
        selectedConfigurationIndex: selectedConfigurationIndex,
        configs: configurations
    });

    useEffect(() => {
        const storedConfiguration = localStorage.getItem('passConfigurations');
        if (storedConfiguration) {
            setConfigurations(JSON.parse(storedConfiguration));
        } else {
            setConfigurations([]);
        }
    }, []);

    return (
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
                    <PatientMatchForm passConfiguration={configurations[selectedConfigurationIndex]} />
                ) : (
                    <NoPassConfigurations />
                )}
            </div>
        </div>
    );
};

export default PatientMatchConfigurationPage;
