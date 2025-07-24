import { Fragment, ReactElement, useId } from 'react';
import classNames from 'classnames';
import { Orientation } from 'design-system/field';
import { OrElseNoData } from 'design-system/data';
import styles from './detail-view.module.scss';

type Children = ReactElement<DetailValueProps>;

type DetailViewProps = {
    className?: string;
    children?: Children | Children[];
};

const DetailView = ({ className, children }: DetailViewProps) => {
    return (
        <dl className={classNames(styles.details, className)}>
            {ensureArray(children).map((child, index) => (
                <Fragment key={index}>{child}</Fragment>
            ))}
        </dl>
    );
};

type DetailValueProps = {
    label: string;
    orientation?: Orientation;
    children?: number | string | null;
};

const DetailValue = ({ label, orientation, children }: DetailValueProps) => {
    const id = useId();

    const longText = typeof children === 'string' && children.length > 75;

    return (
        <>
            <dt id={id}>{label}</dt>
            <dd
                aria-labelledby={id}
                className={classNames({ [styles.vertical]: longText || orientation === 'vertical' })}>
                <OrElseNoData>{children}</OrElseNoData>
            </dd>
        </>
    );
};

const ensureArray = (children?: Children | Children[]) => (Array.isArray(children) ? children : [children]);

export { DetailView, DetailValue };
export type { DetailViewProps };
