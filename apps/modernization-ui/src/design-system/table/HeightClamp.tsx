import classNames from 'classnames';
import styles from './height-clamp.module.scss';
import { Button } from 'components/button';
import { ReactNode, useEffect, useRef, useState } from 'react';

type Props = {
    children: ReactNode;
};

type ClampState = 'constrained' | 'expanded' | 'simple';

export const HeightClamp = ({ children }: Props) => {
    const measureRef = useRef<HTMLTableRowElement | null>(null);
    const [heightState, setState] = useState<ClampState>('simple');

    useEffect(() => {
        console.log(measureRef.current?.clientHeight);
        if ((measureRef.current?.offsetHeight ?? 0) > 84 && heightState !== 'expanded') {
            setState('constrained');
        }
    }, [measureRef.current?.offsetHeight]);

    return (
        <>
            <div
                className={classNames({
                    [styles.expander]: heightState === 'expanded',
                    [styles.constrained]: heightState === 'constrained'
                })}
                ref={measureRef}>
                {children}
            </div>
            {heightState === 'constrained' && (
                <Button aria-label="view more" unstyled className={styles.button} onClick={() => setState('expanded')}>
                    View more
                </Button>
            )}
            {heightState === 'expanded' && (
                <Button
                    aria-label="view less"
                    unstyled
                    className={styles.button}
                    onClick={() => setState('constrained')}>
                    View less
                </Button>
            )}
        </>
    );
};
