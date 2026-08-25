import { ReactNode } from 'react';

import { NoData } from 'design-system/data';

import styles from './item.module.scss';

type ItemType = 'address' | 'phone' | 'email' | 'name' | 'other';

type Props = { type?: ItemType; label?: string; children?: ReactNode };

const ItemGroup = ({ type, label, children }: Props) => {
    return (
        <div className={styles.itemgroup} {...(type && { 'data-item-type': type })}>
            {label && <header>{label}</header>}
            {children ? <p>{children}</p> : <NoData />}
        </div>
    );
};

export { ItemGroup };
