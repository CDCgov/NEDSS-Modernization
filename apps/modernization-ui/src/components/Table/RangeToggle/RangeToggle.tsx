import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { useContext, useEffect, useMemo, useState } from 'react';
import { SelectInput } from 'components/FormInputs/SelectInput';
import './RangeToggle.scss';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';
import { QuestionsContext } from 'apps/page-builder/context/QuestionsContext';
import { ValueSetsContext } from 'apps/page-builder/context/ValueSetContext';

interface RangeToggleProps {
    contextName?: 'pages' | 'conditions' | 'questions' | 'valuesets' | 'templates';
}

export const RangeToggle = ({ contextName }: RangeToggleProps) => {
    const context = useMemo(() => {
        switch (contextName) {
            case 'pages':
                return useContext(PagesContext);
            case 'conditions':
                return useContext(ConditionsContext);
            case 'questions':
                return useContext(QuestionsContext);
            case 'valuesets':
                return useContext(ValueSetsContext);
            default:
                return useContext(PagesContext);
        }
    }, [contextName]);

    const { pageSize, setPageSize, currentPage, setCurrentPage } = context;
    const [range, setRange] = useState(10);
    const options = [
        { name: '10', value: '10' },
        { name: '25', value: '25' },
        { name: '50', value: '50' }
    ];

    useEffect(() => {
        if (currentPage > 1 && setCurrentPage) {
            setCurrentPage(1);
        }
        setPageSize(range);
    }, [range]);

    return (
        <div className="range-toggle">
            <SelectInput defaultValue={pageSize} options={options} onChange={(e) => setRange(Number(e.target.value))} />
        </div>
    );
};
