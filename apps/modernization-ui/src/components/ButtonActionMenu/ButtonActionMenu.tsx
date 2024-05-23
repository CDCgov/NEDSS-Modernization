import { Button } from '@trussworks/react-uswds';
import { useState, useRef, useEffect } from 'react';
import styles from './buttonActionMenu.module.scss';
import { Icon } from '@trussworks/react-uswds';

type Action = {
    label: string;
    action: () => void;
};

type Props = {
    label: string;
    items: Action[];
    disabled?: boolean;
};

export const ButtonActionMenu = ({ label, items, disabled }: Props) => {
    const wrapperRef = useRef(null);
    const clickOutside = (ref: any) => {
        useEffect(() => {
            function handleClickOutside(e: any) {
                if (ref.current && !ref.current.contains(e.target)) {
                    setOpen(false);
                }
            }
            document.addEventListener('mousedown', handleClickOutside);
            return () => {
                document.removeEventListener('mousedown', handleClickOutside);
            };
        }, [ref]);
    };
    clickOutside(wrapperRef);

    const [open, setOpen] = useState(false);

    return (
        <div className={styles.actionMenu} ref={wrapperRef}>
            <Button type="button" onClick={() => setOpen(!open)} className="action-menu-button" disabled={disabled}>
                {label} <Icon.ArrowDropDown size={4} />
            </Button>
            {open ? (
                <div className={'menu ' + styles.menu}>
                    {items.map((item, i) => {
                        return (
                            <Button key={i} type="button" onClick={item.action}>
                                {item.label}
                            </Button>
                        );
                    })}
                </div>
            ) : null}
        </div>
    );
};
