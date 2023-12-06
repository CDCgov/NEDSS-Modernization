import { Context, useContext, useEffect, useState } from 'react';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';
import { QuestionsContext } from 'apps/page-builder/context/QuestionsContext';
import { ValueSetsContext } from 'apps/page-builder/context/ValueSetContext';
import { ContextData, NoopContext } from 'apps/page-builder/context/contextData';
import { usePageMaybe } from 'page';
import { Select } from '@trussworks/react-uswds';
import styles from './range-toggle.module.scss';

type SupportedContext = 'pages' | 'conditions' | 'questions' | 'valuesets';

type RangeToggleProps = {
    contextName?: SupportedContext;
    initial?: number;
};

export const RangeToggle = ({ contextName, initial = 10 }: RangeToggleProps) => {
    const context = resolveContext(contextName);

    const fromContext = useContext(context);

    const pagination = usePageMaybe();
    const [range, setRange] = useState(Number(initial));

    useEffect(() => {
        if (!('type' in fromContext)) {
            if (fromContext.currentPage > 1 && fromContext.setCurrentPage) {
                fromContext.setCurrentPage(1);
            }
            fromContext.setPageSize(range);
        }
    }, [fromContext, range]);

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

const resolveContext = (name?: SupportedContext): Context<ContextData> => {
    if (name === 'conditions') {
        return ConditionsContext;
    } else if (name === 'questions') {
        return QuestionsContext;
    } else if (name === 'valuesets') {
        return ValueSetsContext;
    }
    return NoopContext;
};
