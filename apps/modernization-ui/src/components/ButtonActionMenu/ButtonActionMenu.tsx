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
    labelPosition?: string;
    onClose?: () => void;
    menuTitle?: string;
    menuAction?: () => void;
    menuActionTitle?: string;
    children: ReactNode;
};

export const ButtonActionMenu = ({
    label,
    disabled,
    icon,
    outline,
    className,
    labelPosition,
    onClose,
    menuTitle,
    menuAction,
    menuActionTitle,
    children
}: Props) => {
    const wrapperRef = useRef(null);
    const clickOutside = (ref: any) => {
        useEffect(() => {
            function handleClickOutside(e: any) {
                if (ref.current && !ref.current.contains(e.target)) {
                    setOpen(false);
                    onClose ? onClose() : null;
                }
            }
            document.addEventListener('mousedown', handleClickOutside);
            return () => {
                document.removeEventListener('mousedown', handleClickOutside);
            };
        }, [ref]);
    };
    clickOutside(wrapperRef);

    const close = () => {
        setOpen(false);
        if (onClose) onClose();
    };

    const [open, setOpen] = useState(false);

    return (
        <div className={styles.actionMenu} ref={wrapperRef}>
            <Button
                type="button"
                onClick={() => setOpen(!open)}
                className={classNames(styles.actionMenuButton, className, 'action-button')}
                disabled={disabled}
                outline={outline}
                labelPosition={labelPosition === 'left' ? 'left' : 'right'}
                icon={icon ? icon : <Icon.ArrowDropDown size={4} />}
                data-testid="action">
                {label ? label : ''}
            </Button>
            {open ? (
                <div className={'menu ' + styles.menu}>
                    {menuTitle ? (
                        <div className={styles.menuHeader}>
                            <h2>{menuTitle}</h2>
                            <Icon.Close size={3} onClick={() => close()} />
                        </div>
                    ) : null}
                    <div className={styles.menuContent}>{children}</div>
                    {menuAction ? (
                        <div className={styles.menuFooter}>
                            <Button type="button" onClick={menuAction}>
                                {menuActionTitle}
                            </Button>
                        </div>
                    ) : null}
                </div>
            ) : null}
        </div>
    );
};
