import { Button, Icon } from '@trussworks/react-uswds';
import styles from './MatchConfiguration.module.scss';
import NoPassConfigurations from './PassConfiguration/NoPassConfigurations';
import PassConfigurationListItem from './PassConfiguration/PassConfigurationListItem';
import { useState } from 'react';
import PassConfiguration from './PassConfiguration/PassConfiguration';

type ConfigurationListItem = {
    name: string;
    description: string;
    active: boolean;
};

const MatchConfiguration = () => {
    const [configurations, setConfigurations] = useState<ConfigurationListItem[]>([]);
    const [selectedConfigurationIndex, setSelectedConfigurationIndex] = useState<number | null>(null);
    const [isEditingConfiguration, setIsEditingConfiguration] = useState<boolean>(false);

    const handleAddConfiguration = () => {
        const configs = [...configurations];
        configs.push({
            name: `New configuration (${configurations.length})`,
            description: 'a description will go here',
            active: false
        });
        setConfigurations(configs);
        setSelectedConfigurationIndex(configurations.length);
        setIsEditingConfiguration(true);
    };

    const handleConfigListItemClick = (index: number) => {
        setSelectedConfigurationIndex(index);
        setIsEditingConfiguration(true);
    };

    const showConfiguration = isEditingConfiguration && configurations.length;

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
                {showConfiguration ? <PassConfiguration /> : <NoPassConfigurations />}
            </div>
        </div>
    );
};

export default MatchConfiguration;
