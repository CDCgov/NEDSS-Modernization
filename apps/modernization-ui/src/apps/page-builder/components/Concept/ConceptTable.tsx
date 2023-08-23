/* eslint-disable camelcase */
import { PageSummary } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import React, { useContext, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import { SearchBar } from './SearchBar';
import { Icon } from '@trussworks/react-uswds';
import { ConceptsContext } from '../../context/ConceptContext';

export enum Column {
    localCode = 'Local Code',
    display = 'UI display Name',
    systemCode = 'Concept Code',
    conceptCode = 'Concept Code',
    effectiveFromTime = 'Effective Date'
}

const tableColumns = [
    { name: Column.localCode, sortable: true },
    { name: Column.display, sortable: true },
    { name: Column.systemCode, sortable: true },
    { name: Column.effectiveFromTime, sortable: true },
    { name: '', sortable: false }
];

type Props = {
    summaries: any;
    pages?: any;
};
export const ConceptTable = ({ summaries, pages }: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const { setSearchQuery, setCurrentPage, setSortDirection, setSortBy, setSelectedConcept } =
        useContext(ConceptsContext);

    const asTableRow = (page: any): TableBody => ({
        id: page.id,
        checkbox: false,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.localCode}</div> || null
            },
            { id: 2, title: <div className="event-text">{page?.display}</div> || null },
            {
                id: 3,
                title: <div className="page-name">{page?.conceptCode}</div> || null
            },
            {
                id: 4,
                title: <div>{new Date(page?.effectiveFromTime).toLocaleDateString()}</div> || null
            },
            {
                id: 5,
                title:
                    (
                        <div className="ds-u-text-align--right">
                            <Icon.Edit
                                onClick={() => handleSelected(page)}
                                style={{ cursor: 'pointer' }}
                                size={3}
                                color="black"
                            />
                        </div>
                    ) || null
            }
        ]
    });
    const handleSelected = (row: any) => {
        setSelectedConcept(row);
    };
    const asTableRows = (pages: PageSummary[] | undefined): TableBody[] => pages?.map(asTableRow) || [];

    const toSortString = (name: string) => {
        if (name) {
            switch (name) {
                case Column.display:
                    setSortBy('display');
                    break;
                case Column.localCode:
                    setSortBy('localCode');
                    break;
                case Column.conceptCode:
                    setSortBy('conceptCode');
                    break;
                case Column.effectiveFromTime:
                    setSortBy('effectiveFromTime');
                    break;
                default:
                    return '';
            }
        }
        return '';
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    useEffect(() => {
        return () => localStorage.setItem('selectedConcept', '0');
    }, []);

    const handleSort = (name: string, direction: Direction): void => {
        if (name && Direction) {
            toSortString(name);
            setSortDirection(direction);
        }
    };

    return (
        <div>
            <div>{<SearchBar onChange={setSearchQuery} />}</div>
            <TableComponent
                tableHeader=""
                tableHead={tableColumns}
                tableBody={tableRows}
                isPagination={true}
                pageSize={pages?.pageSize}
                totalResults={pages?.totalElements}
                currentPage={pages?.currentPage}
                handleNext={setCurrentPage}
                sortData={handleSort}
                rangeSelector={true}
            />
        </div>
    );
};
