import { Button } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { useState } from 'react';
import styles from './_TabbedButtonBar.module.scss';

interface Props {
    entries: string[];
    onChange: (active: string) => void;
}
export const TabbedButtonBar = ({ entries, onChange }: Props) => {
    const [active, setActive] = useState<string>(entries[0]);
    const handleChange = (label: string) => {
        setActive(label);
        onChange(label);
    };
    return (
        <div className={styles.buttonBar}>
            {entries.map((entry, key) => (
                <Button
                    type="button"
                    outline
                    key={key}
                    className={classNames([active === entry ? styles.active : '', styles.button])}
                    onClick={() => handleChange(entry)}>
                    {entry}
                </Button>
            ))}
        </div>
    );
};
