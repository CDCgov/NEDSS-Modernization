import { Fragment, ReactElement, useId } from 'react';
import classNames from 'classnames';
import { NoData } from 'design-system/data';
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
    children?: string;
};

const DetailValue = ({ label, children }: DetailValueProps) => {
    const id = useId();

    return (
        <>
            <dt id={id}>{label}</dt>
            <dd aria-labelledby={id}>{children ?? <NoData />}</dd>
        </>
    );
};

const ensureArray = (children?: Children | Children[]) => (Array.isArray(children) ? children : [children]);

export { DetailView, DetailValue };
export type { DetailViewProps };
