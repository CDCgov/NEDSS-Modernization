import { useState, useRef, useEffect, ReactNode } from 'react';
import styles from './buttonActionMenu.module.scss';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import classNames from 'classnames';

type Props = {
    label?: string;
    ariaLabel?: string;
    disabled?: boolean;
    icon?: ReactNode;
    outline?: boolean;
    className?: string;
    labelPosition?: 'left' | 'right';
    children: ReactNode;
};

export const ButtonActionMenu = ({
    label,
    disabled,
    ariaLabel,
    icon,
    outline,
    className,
    labelPosition = 'left',
    children
}: Props) => {
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
            <Button
                aria-label={ariaLabel}
                data-tooltip-position="top"
                type="button"
                onClick={() => setOpen(!open)}
                className={classNames(styles.actionMenuButton, className)}
                disabled={disabled}
                outline={outline}
                labelPosition={labelPosition}
                icon={icon ? icon : <Icon name="arrow_drop_down" sizing="medium" />}>
                {label}
            </Button>
            {open && (
                <div className={styles.menu}>
                    <div className={styles.menuContent}>{children}</div>
                </div>
            )}
        </div>
    );
};
