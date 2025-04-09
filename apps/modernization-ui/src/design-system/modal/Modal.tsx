import { ReactNode, KeyboardEvent as ReactKeyboardEvent, useRef, useEffect, ReactElement } from 'react';
import classNames from 'classnames';
import sprite from '@uswds/uswds/img/sprite.svg';

import styles from './modal.module.scss';

type Close = () => void;

type FooterRenderer = (close: Close) => ReactNode;

type ModalProps = {
    id: string;
    title: string;
    size?: 'small' | 'large' | 'auto';
    /** Whether to force interaction on the modal. This also hides the "X" button. */
    forceAction?: boolean;
    className?: string;
    ariaDescribedBy?: string;
    children: ReactNode;
    onClose: Close;
    footer?: FooterRenderer;
};

const Modal = ({
    id,
    title,
    size = 'auto',
    forceAction = false,
    children,
    className,
    ariaDescribedBy,
    onClose,
    footer
}: ModalProps): ReactElement<ModalProps> => {
    const focused = useRef<boolean>(false);
    const element = useRef<HTMLDialogElement>(null);
    const header = `${id}-header`;

    useEffect(() => {
        if (element.current && !focused.current) {
            element.current.focus();
            focused.current = true;
        }
    }, [element.current, focused]);

    const handleKeyDown = (event: ReactKeyboardEvent) => {
        if (event.key === 'Escape') {
            event.preventDefault();
            onClose();
        }
    };

    return (
        <div className="usa-modal-wrapper" onKeyDown={(!forceAction && handleKeyDown) || undefined}>
            <div className={classNames('usa-modal-overlay', styles.overlay)}>
                <dialog
                    ref={element}
                    id={id}
                    aria-labelledby={header}
                    aria-describedby={ariaDescribedBy}
                    className={classNames('usa-modal', styles.modal, className, {
                        [styles.small]: size === 'small',
                        [styles.large]: size === 'large'
                    })}
                    data-force-action={forceAction}
                    open>
                    <header id={header} className={'usa-modal__heading'}>
                        <h2>{title}</h2>
                        {!forceAction && (
                            <svg
                                tabIndex={0}
                                role="button"
                                width={'2rem'}
                                height={'2rem'}
                                aria-label={`Close ${title}`}
                                onClick={onClose}
                                data-close-modal>
                                <use xlinkHref={`${sprite}#close`}></use>
                            </svg>
                        )}
                    </header>
                    <div className={'usa-modal__content'}>
                        {footer && <footer className="usa-modal__footer">{footer(onClose)}</footer>}
                        <div className={classNames('usa-modal__main', styles.main)}>{children}</div>
                    </div>
                </dialog>
            </div>
        </div>
    );
};

export { Modal };
