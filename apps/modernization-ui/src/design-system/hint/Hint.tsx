import { ReactNode, useCallback, useState } from 'react';
import { createPortal } from 'react-dom';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Icon } from 'design-system/icon';
import { HintPanel } from './HintPanel';

import styles from './hint.module.scss';

type Target = ReactNode | ((id: string) => ReactNode);

type Placement = {
    top: number;
    left: number;
};

type HintProps = {
    id: string;
    /** When specified, uses this as the hover-over element, otherwise uses default info icon. */
    position?: 'left' | 'right' | 'center';
    enabled?: boolean;
    target?: Target;
    children: ReactNode | ReactNode[];
};
/**
 * Encapsulates a hint that can be displayed on hover.
 * By default, creates an `info_outline` icon that displays the supplied children within a popup on hover.
 * You can override this by supplying your own target component.
 *
 * An accompanying `aria-describedby` should be set on the described element.
 * Ex: `<div aria-describedby='my-hint'>Something confusing</div><Hint id='my-hint'>More info</Hint>`
 * @return {Hint}
 */
const Hint = ({ id, enabled = true, children, target, position }: HintProps) => {
    const [visible, setVisible] = useState(false);
    const [placement, setPlacement] = useState<Placement | undefined>();

    const targeted = useCallback(
        (element: HTMLElement | null) => {
            if (enabled && visible && element != null) {
                const { top, left, width, height } = element.getBoundingClientRect();

                setPlacement({
                    top: top + height + 1,
                    left: left + width / 2
                });
            }
        },
        [visible, enabled, setPlacement]
    );

    const hasTarget = Boolean(target);

    return (
        <span onMouseEnter={() => setVisible(true)} onMouseLeave={() => setVisible(false)}>
            <div ref={targeted}>
                <Shown
                    when={hasTarget}
                    fallback={
                        <Icon name="info_outline" sizing="small" className={styles.info} aria-describedby={id} />
                    }>
                    {renderTarget(id, target)}
                </Shown>
            </div>
            <Shown when={enabled}>
                {createPortal(
                    <div role="presentation" style={{ position: 'absolute', pointerEvents: 'none', ...placement }}>
                        <div
                            className={classNames(styles.container, {
                                [styles.visible]: visible,
                                [styles.left]: position === 'left',
                                [styles.center]: position === 'center'
                            })}>
                            <HintPanel id={id}>{children}</HintPanel>
                        </div>
                    </div>,
                    document.body
                )}
            </Shown>
        </span>
    );
};

const renderTarget = (id: string, target: Target) => (typeof target === 'function' ? target(id) : target);

export { Hint };
export type { HintProps };
