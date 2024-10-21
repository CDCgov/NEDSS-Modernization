import { ReactNode } from 'react';
import styles from './item.module.scss';

type ItemType = 'address' | 'phone' | 'email' | 'name' | 'other';

type Props = { type?: ItemType; label?: string; children: ReactNode };

const ItemGroup = ({ type, label, children }: Props) => {
    return (
        <div className={styles.itemgroup} {...(type && { 'data-item-type': type })}>
            {label && <header>{label}</header>}
            <p>{children}</p>
        </div>
    );
};

export { ItemGroup };
