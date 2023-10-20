import { useContext, useEffect, useState } from 'react';
import { BusinessRuleContext } from '../../context/BusinessContext';
import './BusinessRulesLibrary.scss';
import { BusinessRulesLibraryTable } from './BusinessRulesLibraryTable';
export const BusinessRulesLibrary = ({ modalRef }: any) => {
    const { searchQuery, sortBy, filter, currentPage, pageSize, setIsLoading } = useContext(BusinessRuleContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements] = useState(0);

    useEffect(() => {
        setIsLoading(true);
        setSummaries([]);
        setIsLoading(false);
    }, [searchQuery, currentPage, pageSize, sortBy, filter]);
    return (
        <div className="business-rules-library">
            <div className="business-rules-library__container">
                <div className="business-rules-library__table">
                    <BusinessRulesLibraryTable
                        summaries={summaries}
                        qtnModalRef={modalRef}
                        pages={{ currentPage, pageSize, totalElements }}
                    />
                </div>
            </div>
        </div>
    );
};
