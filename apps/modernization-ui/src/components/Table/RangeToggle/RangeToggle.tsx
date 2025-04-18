import { useEffect, useState } from 'react';
import classNames from 'classnames';
import { usePagination } from 'pagination';
import styles from './range-toggle.module.scss';

export type SupportedContext = 'pages' | 'conditions' | 'questions' | 'valuesets' | 'templates' | 'businessRules';

type RangeToggleProps = {
    initial?: number;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange'>;

export const RangeToggle = ({
    initial = 10,
    id = 'range-toggle',
    name = 'range-toggle',
    ...props
}: RangeToggleProps) => {
    const pagination = usePagination();
    const [range, setRange] = useState(Number(initial));

    useEffect(() => {
        if (pagination && pagination.page.pageSize !== range) {
            pagination.resize(range);
        }
    }, [pagination, range]);

    return (
        <select
            {...props}
            className={classNames('usa-select', styles.toggle)}
            id={id}
            name={name}
            defaultValue={initial}
            onChange={(e) => setRange(Number(e.target.value))}>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
            <option value={100}>100</option>
        </select>
    );
};
