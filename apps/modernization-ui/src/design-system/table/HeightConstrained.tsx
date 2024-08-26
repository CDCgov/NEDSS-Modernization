import classNames from 'classnames';
import styles from './height-constrained.module.scss';
import { ReactNode, useEffect, useMemo, useRef, useState } from 'react';

type Props = {
    children: ReactNode;
    rowConstraint: Constraint;
    onChange: (value: Constraint) => void;
};

export type Constraint = 'bounded' | 'unbounded' | 'acceptable';

export const HeightConstrained = ({ children, onChange, rowConstraint }: Props) => {
    const measureRef = useRef<HTMLTableRowElement | null>(null);
    const [constraint, setConstraint] = useState<Constraint>('acceptable');
    const matchesRow = useMemo(() => constraint === rowConstraint, [constraint, rowConstraint]);

    useEffect(() => {
        if ((measureRef.current?.offsetHeight ?? 0) > 42) {
            setConstraint('bounded');
        }
    }, [measureRef.current?.offsetHeight]);

    useEffect(() => {
        if (constraint === 'bounded' && rowConstraint !== 'unbounded') {
            onChange(constraint);
        }
    }, [constraint, rowConstraint]);

    return (
        <>
            <div
                className={classNames({
                    [styles.unbounded]: constraint === 'unbounded' && matchesRow,
                    [styles.bounded]: constraint === 'bounded' && matchesRow
                })}
                ref={measureRef}>
                {children}
            </div>
            {constraint === 'bounded' && matchesRow && (
                <a aria-label="view more" className={styles.button} onClick={() => onChange('unbounded')}>
                    View more
                </a>
            )}

            {constraint === 'bounded' && rowConstraint === 'unbounded' && (
                <a aria-label="view less" className={styles.button} onClick={() => onChange('bounded')}>
                    View less
                </a>
            )}
        </>
    );
};
