import classNames from 'classnames';
import styles from './height-clamp.module.scss';
import { Button } from 'components/button';
import { ReactNode, useEffect, useRef, useState } from 'react';

type Props = {
    children: ReactNode;
    rowHeightState: ClampState;
    onClamp: (value: ClampState) => void;
};

export type ClampState = 'constrained' | 'expanded' | 'simple';

export const HeightClamp = ({ children, onClamp, rowHeightState }: Props) => {
    const measureRef = useRef<HTMLTableRowElement | null>(null);
    const [cellHeightState, setState] = useState<ClampState>('simple');

    useEffect(() => {
        if ((measureRef.current?.offsetHeight ?? 0) > 42 && rowHeightState !== 'expanded') {
            onClamp('constrained');
            setState('constrained');
        }
    }, [measureRef.current?.offsetHeight]);

    return (
        <>
            <div
                className={classNames({
                    [styles.expander]: rowHeightState === 'expanded' && cellHeightState === 'expanded',
                    [styles.constrained]: rowHeightState === 'constrained' && cellHeightState === 'constrained'
                })}
                ref={measureRef}>
                {children}
            </div>
            {rowHeightState === 'constrained' && cellHeightState === 'constrained' && (
                <Button aria-label="view more" unstyled className={styles.button} onClick={() => onClamp('expanded')}>
                    View more
                </Button>
            )}
            {rowHeightState === 'expanded' && cellHeightState === 'constrained' && (
                <Button
                    aria-label="view less"
                    unstyled
                    className={styles.button}
                    onClick={() => onClamp('constrained')}>
                    View less
                </Button>
            )}
        </>
    );
};
