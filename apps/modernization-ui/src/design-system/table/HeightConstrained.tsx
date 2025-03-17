import classNames from 'classnames';
import styles from './height-constrained.module.scss';
import { Button } from 'components/button';
import { ReactNode, useEffect, useMemo, useRef, useState } from 'react';
import { Sizing } from 'design-system/field';

type Props = {
    children: ReactNode;
    rowConstraint: Constraint;
    name: string;
    sizing?: Sizing;
    onChange: (value: Constraint) => void;
};

export type Constraint = 'bounded' | 'unbounded' | 'acceptable';

export const HeightConstrained = ({ children, onChange, rowConstraint, name, sizing }: Props) => {
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
                ref={measureRef}
                data-testid="height-constrained">
                {children}
            </div>
            {constraint === 'bounded' && matchesRow && (
                <Button
                    aria-label={`view more ` + name}
                    className={styles.button}
                    sizing={sizing}
                    onClick={() => onChange('unbounded')}>
                    View more
                </Button>
            )}
            {constraint === 'bounded' && rowConstraint === 'unbounded' && (
                <Button
                    aria-label={`view less ` + name}
                    className={styles.button}
                    sizing={sizing}
                    onClick={() => onChange('bounded')}>
                    View less
                </Button>
            )}
        </>
    );
};
