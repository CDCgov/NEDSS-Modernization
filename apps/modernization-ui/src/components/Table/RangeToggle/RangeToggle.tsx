import { PagesContext } from 'apps/page-builder/context/PagesContext';
import { Context, useContext, useEffect, useState } from 'react';
import { SelectInput } from 'components/FormInputs/SelectInput';
import './RangeToggle.scss';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';
import { QuestionsContext } from 'apps/page-builder/context/QuestionsContext';
import { ValueSetsContext } from 'apps/page-builder/context/ValueSetContext';
import { ContextData } from 'apps/page-builder/context/contextData';
import { useSearchParams, useLocation } from 'react-router-dom';

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

    const location = useLocation();

    useEffect(() => {
        if (range !== pageSize) {
            setCurrentPage(1);
            setPageSize(range);
            setSearchParams({ page: '1', size: range.toString() });
        } else {
            setPageSize(range);
            setSearchParams({ size: range.toString(), page: currentPage.toString() });
        }
    }, [range, currentPage]);

    useEffect(() => {
        if (searchParams.get('page')) {
            const pageFromQuery = searchParams.get('page');
            setCurrentPage(parseInt(pageFromQuery ?? '') || 1);
        }

        if (searchParams.get('size') && parseInt(searchParams.get('size') || '') > 0) {
            const sizeFromQuery = searchParams.get('size');
            setPageSize(parseInt(sizeFromQuery ?? ''));
            setRange(parseInt(sizeFromQuery ?? ''));
        }
    }, [location]);

    return (
        <div className="range-toggle">
            <SelectInput defaultValue={pageSize} options={options} onChange={(e) => setRange(Number(e.target.value))} />
        </div>
    );
};
