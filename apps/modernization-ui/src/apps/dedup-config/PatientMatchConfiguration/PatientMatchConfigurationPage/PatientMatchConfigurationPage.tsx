import { Button, Icon, ModalRef } from '@trussworks/react-uswds';
import styles from './patient-match-configuration-page.module.scss';
import NoPassConfigurations from '../PassConfiguration/NoPassConfigurations';
import PassConfigurationListItem from '../PassConfiguration/PassConfigurationListItem';
import { useEffect, useRef, useState } from 'react';
import PatientMatchForm from './PatientMatchForm';
import { PassConfiguration } from 'apps/dedup-config/types';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';
import { ConfirmationModal } from 'confirmation';
import { DragDropContext, Droppable } from 'react-beautiful-dnd';

const PatientMatchConfigurationPage = () => {
    const [configurations, setConfigurations] = useState<PassConfiguration[]>([]);
    const [selectedConfigurationIndex, setSelectedConfigurationIndex] = useState<number>();
    const [isAddingConfiguration, setIsAddingConfiguration] = useState<boolean>(false);
    const {
        blockingCriteria,
        matchingCriteria,
        setBlockingCriteria,
        setMatchingCriteria,
        setUpperBound,
        setLowerBound
    } = usePatientMatchContext();
    const deleteModalRef = useRef<ModalRef>(null);

    const handleAddConfiguration = () => {
        const configs = [...configurations];
        configs.push({
            name: `New configuration`,
            description: 'a description will go here',
            active: true,
            blockingCriteria: [],
            matchingCriteria: []
        });
        setConfigurations(configs);
        setSelectedConfigurationIndex(configurations.length);
        setIsAddingConfiguration(true);
        setBlockingCriteria([]);
        setMatchingCriteria([]);
        setUpperBound(0);
        setLowerBound(0);
    };

    const handleConfigListItemClick = (index: number) => {
        if (isAddingConfiguration) return;
        setSelectedConfigurationIndex(index);
        const selectedConfig = configurations[index];
        setBlockingCriteria(selectedConfig.blockingCriteria ?? []);
        setMatchingCriteria(selectedConfig.matchingCriteria ?? []);
        setUpperBound(selectedConfig.upperBound ?? 0);
        setLowerBound(selectedConfig.lowerBound ?? 0);
    };

    useEffect(() => {
        const storedConfiguration = localStorage.getItem('passConfigurations');
        if (storedConfiguration) {
            const configs = JSON.parse(storedConfiguration);
            setConfigurations(configs);
            setSelectedConfigurationIndex(0);
            const selectedConfig = configs[0];
            setBlockingCriteria(selectedConfig?.blockingCriteria ?? []);
            setMatchingCriteria(selectedConfig?.matchingCriteria ?? []);
            setUpperBound(selectedConfig?.upperBound ?? 0);
            setLowerBound(selectedConfig?.lowerBound ?? 0);
        } else {
            setConfigurations([]);
            setSelectedConfigurationIndex(0);
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
                setSelectedConfigurationIndex(0);
                setBlockingCriteria([]);
                setMatchingCriteria([]);
            }
            localStorage.setItem('passConfigurations', JSON.stringify(configs));
            deleteModalRef.current?.toggleModal();
        }
    };

    const handleDragEnd = (result: any) => {
        const { source, destination } = result;
        if (!destination) return;
        if (source.index === destination.index) return;

        const updatedConfigurations = Array.from(configurations);
        const [movedConfiguration] = updatedConfigurations.splice(source.index, 1);
        updatedConfigurations.splice(destination.index, 0, movedConfiguration);

        setConfigurations(updatedConfigurations);
        localStorage.setItem('passConfigurations', JSON.stringify(updatedConfigurations));
    };

    const handleSaveConfiguration = (config: PassConfiguration) => {
        const configs = [...configurations];
        const newConfig = {
            ...config,
            blockingCriteria,
            matchingCriteria,
            lowerBound: config.lowerBound, // Make sure these are included
            upperBound: config.upperBound
        };

        configs[selectedConfigurationIndex || 0] = newConfig;
        setConfigurations(configs);
        localStorage.setItem('passConfigurations', JSON.stringify(configs));
    };

    const handleCancel = () => {
        if (isAddingConfiguration) {
            const configs = [...configurations];
            setIsAddingConfiguration(false);
            setSelectedConfigurationIndex(configurations.length - 2);
            setBlockingCriteria([]);
            setMatchingCriteria([]);
            setUpperBound(0);
            setLowerBound(0);
            configs.pop();
            setConfigurations(configs);
        } else {
            const config = configurations[selectedConfigurationIndex ?? 0];
            setBlockingCriteria(config.blockingCriteria ?? []);
            setMatchingCriteria(config.matchingCriteria ?? []);
            setUpperBound(config.upperBound ?? 0);
            setLowerBound(config.lowerBound ?? 0);
        }
    };

    const showConfiguration = isAddingConfiguration || configurations.length;

    return (
        <>
            {selectedConfigurationIndex != null ? (
                <ConfirmationModal
                    modal={deleteModalRef}
                    title={'Delete pass configuration'}
                    message={
                        <>
                            Are you sure you would like to delete the{' '}
                            <span style={{ fontWeight: 'bold' }}>
                                {configurations[selectedConfigurationIndex].name}
                            </span>{' '}
                            pass configuration?
                        </>
                    }
                    onConfirm={handleDeleteConfiguration}
                    onCancel={() => deleteModalRef.current?.toggleModal()}
                />
            ) : null}

            <div className={styles.wrapper}>
                <div className={styles.configurationList}>
                    <h3>Pass configurations</h3>
                    <DragDropContext onDragEnd={handleDragEnd}>
                        <Droppable droppableId="configurations-list">
                            {(provided, snapshot) => (
                                <div
                                    {...provided.droppableProps}
                                    ref={provided.innerRef}
                                    style={{ backgroundColor: snapshot.isDraggingOver ? '#d9e8f6' : 'white' }}>
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
                                        <p className={styles.noConfigurationText}>
                                            No pass configurations have been created.
                                        </p>
                                    )}
                                    {provided.placeholder}
                                </div>
                            )}
                        </Droppable>
                    </DragDropContext>
                    <Button
                        className={styles.addButton}
                        unstyled
                        type={'button'}
                        onClick={handleAddConfiguration}
                        disabled={isAddingConfiguration}>
                        <Icon.Add />
                        Add new pass configuration
                    </Button>
                </div>
                <div className={styles.configurationDetails}>
                    {showConfiguration ? (
                        <PatientMatchForm
                            passConfiguration={configurations[selectedConfigurationIndex ?? 0]}
                            onSaveConfiguration={handleSaveConfiguration}
                            onDeleteConfiguration={() => deleteModalRef.current?.toggleModal()}
                            onCancel={handleCancel}
                            isAdding={isAddingConfiguration}
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
