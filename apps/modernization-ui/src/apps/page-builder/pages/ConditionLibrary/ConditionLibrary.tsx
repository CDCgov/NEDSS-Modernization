import { useContext, useEffect, useState } from 'react';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { UserContext } from 'user';
import { searchConditions } from 'apps/page-builder/services/conditionAPI';
import ConditionLibraryTable from './ConditionLibraryTable';
import './ConditionLibrary.scss';
import { Condition } from '../../generated';
import { ConditionsContext } from 'apps/page-builder/context/ConditionsContext';

const ConditionLibrary = () => {
    const [conditions, setConditions] = useState<Condition[]>([]);
    const { searchQuery, sortBy, sortDirection, currentPage, pageSize, setIsLoading } = useContext(ConditionsContext);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [totalElements, setTotalElements] = useState(0);

    useEffect(() => {
        setIsLoading(true);
        // get conditions
        searchConditions(token, currentPage, pageSize, `${sortBy},${sortDirection}`, {
            searchText: searchQuery
        })
            .then((data) => {
                setConditions(data.content ?? []);
                setTotalElements(data.totalElements ?? 0);
                setIsLoading(false);
            })
            .catch((error) => {
                console.log('error', error);
            });
    }, [searchQuery, currentPage, pageSize, sortBy, sortDirection]);

    return (
        <PageBuilder nav>
            <div className="condition-local-library">
                <div className="condition-local-library__container">
                    <div className="condition-local-library__table">
                        <ConditionLibraryTable
                            conditions={conditions}
                            currentPage={currentPage}
                            pageSize={pageSize}
                            totalElements={totalElements}
                        />
                    </div>
                </div>
            </div>
        </PageBuilder>
    );
};

export default ConditionLibrary;
