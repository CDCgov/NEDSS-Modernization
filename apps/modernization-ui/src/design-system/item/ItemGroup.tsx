import { ReactNode } from 'react';
import styles from './item.module.scss';

type AddressableType = 'address' | 'phone' | 'email';
type ItemType = AddressableType | 'name' | 'other';

type Props = { type?: ItemType; label?: string; children: ReactNode };

const isAddressType = (type?: ItemType): type is AddressableType =>
    type != null && ['address', 'phone', 'email'].includes(type);

const ItemGroup = ({ type, label, children }: Props) => {
    const isAddress = isAddressType(type);
    return (
        <div className={styles.itemgroup} {...(type && { 'data-item-type': type })}>
            {label && <header>{label}</header>}
            {isAddress ? <address>{children}</address> : <p role="group">{children}</p>}
        </div>
    );
};

export { ItemGroup };
