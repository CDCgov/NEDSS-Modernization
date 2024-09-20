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
    const [selectedConfigurationIndex, setSelectedConfigurationIndex] = useState<number | null>(null);
    const [isEditingConfiguration, setIsEditingConfiguration] = useState<boolean>(false);
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
        setIsEditingConfiguration(true);
        setBlockingCriteria([]);
        setMatchingCriteria([]);
        setUpperBound(0);
        setLowerBound(0);
        localStorage.setItem('passConfigurations', JSON.stringify(configs));
    };

    const handleConfigListItemClick = (index: number) => {
        setSelectedConfigurationIndex(index);
        setIsEditingConfiguration(true);
        const selectedConfig = configurations[index];
        setBlockingCriteria(selectedConfig.blockingCriteria ?? []);
        setMatchingCriteria(selectedConfig.matchingCriteria ?? []);
        setUpperBound(selectedConfig.upperBound ?? 0);
        setLowerBound(selectedConfig.lowerBound ?? 0);
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
        console.log('config', config);
        console.log('blocking', blockingCriteria);
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
                    <Button className={styles.addButton} unstyled type={'button'} onClick={handleAddConfiguration}>
                        <Icon.Add />
                        Add new pass configuration
                    </Button>
                </div>
                <div className={styles.configurationDetails}>
                    {showConfiguration ? (
                        <PatientMatchForm
                            passConfiguration={configurations[selectedConfigurationIndex]}
                            onSaveConfiguration={handleSaveConfiguration}
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
