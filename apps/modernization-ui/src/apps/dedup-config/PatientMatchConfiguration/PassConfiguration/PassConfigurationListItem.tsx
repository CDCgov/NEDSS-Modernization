import styles from './PassConfigurationListItem.module.scss';

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

const PassConfigurationListItem = ({
    name = defaultValues.name,
    description = defaultValues.description,
    selected,
    active = false,
    onClick,
    index
}: Props) => {
    const activeStatus = active ? 'Active' : 'Inactive';

    return (
        <li className={selected ? styles.selected : ''} onClick={() => onClick(index)}>
            <div className={styles.leftBar}></div>
            <div className={styles.cardContent}>
                <label>{name}</label>
                <div className={styles.description}>{description}</div>
                <div className={active ? styles.active : styles.inactive}>{activeStatus}</div>
            </div>
        </li>
    );
};

export default PassConfigurationListItem;
