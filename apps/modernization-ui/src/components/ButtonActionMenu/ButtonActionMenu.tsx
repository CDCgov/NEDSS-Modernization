import { useState, useRef, useEffect, ReactNode } from 'react';
import styles from './buttonActionMenu.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button/Button';
import classNames from 'classnames';

type Props = {
    label?: string;
    disabled?: boolean;
    icon?: ReactNode;
    outline?: boolean;
    className?: string;
    labelPosition?: 'left' | 'right';
    children: ReactNode;
    header?: string;
};

export const ButtonActionMenu = ({
    label,
    disabled,
    icon,
    outline,
    className,
    labelPosition = 'right',
    header = '',
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
                aria-label={header}
                data-tooltip-position="top"
                type="button"
                onClick={() => setOpen(!open)}
                className={classNames(styles.actionMenuButton, className)}
                disabled={disabled}
                outline={outline}
                labelPosition={labelPosition}
                icon={icon ? icon : <Icon.ArrowDropDown size={4} />}>
                {label}
            </Button>
            {open && (
                <div className={styles.menu}>
                    {header && (
                        <div className={styles['menu-header']}>
                            {header}
                            <Button unstyled onClick={() => setOpen(false)} icon={<Icon.Close fontSize={32} />} />
                        </div>
                    )}
                    <div className={styles.menuContent}>{children}</div>
                </div>
            )}
        </div>
    );
};
