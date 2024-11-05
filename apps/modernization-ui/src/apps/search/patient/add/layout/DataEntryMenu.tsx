import { ReactNode } from 'react';
import classNames from 'classnames';
import { Heading } from 'components/heading';
import styles from './add-layout.module.scss';

type DataEntryMenuProps = {
    children: ReactNode;
};

export const DataEntryMenu = ({ children }: DataEntryMenuProps) => {
    return (
        <div className={classNames(styles.dataEntryMenu)}>
            <Heading level={1}>Data entry</Heading>
            {children}
        </div>
    );
};
