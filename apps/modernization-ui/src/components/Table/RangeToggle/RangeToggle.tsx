import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { Context, useContext, useEffect, useState } from 'react';
import { SelectInput } from 'components/FormInputs/SelectInput';
import './RangeToggle.scss';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';
import { QuestionsContext } from 'apps/page-builder/context/QuestionsContext';
import { ValueSetsContext } from 'apps/page-builder/context/ValueSetContext';
import { ContextData } from 'apps/page-builder/context/contextData';
import { useSearchParams } from 'react-router-dom';

interface RangeToggleProps {
    contextName?: 'pages' | 'conditions' | 'questions' | 'valuesets' | 'templates';
}

export const RangeToggle = ({ contextName }: RangeToggleProps) => {
    const [context, setContext] = useState<Context<ContextData>>(PagesContext);
    const [searchParams, setSearchParams] = useSearchParams();

    useEffect(() => {
        switch (contextName) {
            case 'pages':
                setContext(PagesContext);
                break;
            case 'conditions':
                setContext(ConditionsContext);
                break;
            case 'questions':
                setContext(QuestionsContext);
                break;
            case 'valuesets':
                setContext(ValueSetsContext);
                break;
            default:
                setContext(PagesContext);
                break;
        }
    }, [contextName]);

    const { pageSize, setPageSize, currentPage, setCurrentPage } = useContext(context);
    const [range, setRange] = useState(pageSize);
    const options = [
        { name: '10', value: '10' },
        { name: '20', value: '20' },
        { name: '30', value: '30' },
        { name: '50', value: '50' },
        { name: '100', value: '100' }
    ];

    useEffect(() => {
        const pageFromQuery = searchParams.get('page');
        const sizeFromQuery = searchParams.get('size');

        if (pageFromQuery && sizeFromQuery) {
            setCurrentPage(parseInt(pageFromQuery ?? '') || 1);
            setPageSize(parseInt(sizeFromQuery || ''));
            setRange(parseInt(sizeFromQuery || ''));
        } else {
            setCurrentPage(1);
            setPageSize(10);
            setRange(10);
        }
    }, []);

    useEffect(() => {
        if (range !== pageSize) {
            setCurrentPage(1);
            setPageSize(range);
            setSearchParams({ page: '1', size: range.toString() });
        } else {
            const page = searchParams.get('page');
            const size = searchParams.get('size');
            setCurrentPage(parseInt(page ?? '') || 1);
            setRange(parseInt(size || '') || 10);
            setPageSize(parseInt(size || '') || 10);
        }
    }, [range, currentPage]);

    return (
        <div className="range-toggle">
            <SelectInput defaultValue={pageSize} options={options} onChange={(e) => setRange(Number(e.target.value))} />
        </div>
    );
};
