import { Draggable } from 'react-beautiful-dnd';
import styles from './PassConfigurationListItem.module.scss';
import { Icon } from 'components/Icon/Icon';

type Props = {
    name?: string;
    description?: string;
    selected: boolean;
    active: boolean;
    onClick: (index: number) => void;
    index: number;
};

const defaultValues = {
    name: 'New Pass Configuration',
    description: 'a description goes here'
};

const PassConfigurationListItem = ({ name = defaultValues.name, selected, active = false, onClick, index }: Props) => {
    const activeStatus = active ? 'Active' : 'Inactive';

    return (
        <Draggable draggableId={index.toString()} index={index} key={index}>
            {(provided) => (
                <li ref={provided.innerRef} {...provided.draggableProps} className={selected ? styles.selected : ''}>
                    <div className={styles.cardContent} onClick={() => onClick(index)}>
                        <label>{name}</label>
                        <div className={active ? styles.active : styles.inactive}>{activeStatus}</div>
                    </div>
                    <div className={styles.rightBar} {...provided.dragHandleProps}>
                        <Icon name="drag" size="m" />
                    </div>
                </li>
            )}
        </Draggable>
    );
};

export default PassConfigurationListItem;
