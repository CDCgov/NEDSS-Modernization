import { Select } from '@trussworks/react-uswds';
import { usePageMaybe } from 'page';
import { useEffect, useState } from 'react';
import styles from './range-toggle.module.scss';

export type SupportedContext = 'pages' | 'conditions' | 'questions' | 'valuesets' | 'templates' | 'businessRules';

type RangeToggleProps = {
    initial?: number;
};

export const RangeToggle = ({ initial = 10 }: RangeToggleProps) => {
    const pagination = usePageMaybe();
    const [range, setRange] = useState(Number(initial));

    useEffect(() => {
        if (pagination && pagination.page.pageSize !== range) {
            pagination.resize(range);
        }
    }, [pagination, range]);

    return (
        <Select
            className={styles.toggle}
            id="range-toggle"
            name="range-toggle"
            defaultValue={initial}
            onChange={(e) => setRange(Number(e.target.value))}>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
            <option value={100}>100</option>
        </Select>
    );
};
