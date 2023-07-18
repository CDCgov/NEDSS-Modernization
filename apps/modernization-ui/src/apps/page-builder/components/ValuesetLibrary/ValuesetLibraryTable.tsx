/* eslint-disable camelcase */
import { PageSummary } from 'apps/page-builder/generated';
import { ValueSet } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { usePage } from 'page';
import React, { useEffect, useState } from 'react';
import { Direction } from 'sorting';
import './ValuesetLibraryTable.scss';
import { SearchBar } from './SearchBar';
import { ClassicButton } from '../../../../classic';
import { Icon } from '@trussworks/react-uswds';

export enum Column {
    Type = 'Type',
    ValuesetName = 'Value set name',
    ValuesetDesc = 'Value set description'
}

const tableColumns = [
    { name: Column.Type, sortable: true },
    { name: Column.ValuesetName, sortable: true },
    { name: Column.ValuesetDesc, sortable: true },
    { name: '', sortable: false }
];

type Props = {
    sortChange: (sort?: string) => void;
    summaries: ValueSet[];
};
export const ValuesetLibraryTable = ({ summaries, sortChange }: Props) => {
    const { page, request } = usePage();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);

    const asTableRow = (page: ValueSet): TableBody => ({
        id: page.nbsUid,
        checkbox: true,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.valueSetTypeCd}</div> || null
            },
            { id: 2, title: <div className="event-text">{page?.valueSetNm}</div> || null },
            {
                id: 3,
                title: <div>{page?.codeSetDescTxt}</div> || null
            },
            {
                id: 4,
                title:
                    (
                        <div className="ds-u-text-align--right">
                            <Icon.ExpandMore style={{ cursor: 'pointer' }} size={4} color="black" />
                        </div>
                    ) || null
            }
        ]
    });
    const handleSelected = () => {};
    const asTableRows = (pages: PageSummary[] | undefined): TableBody[] => pages?.map(asTableRow) || [];

    /*
     * Converts header and Direction to API compatible sort string such as "name,asc"
     */
    const toSortString = (name: string, direction: Direction): string | undefined => {
        if (name && direction && direction !== Direction.None) {
            switch (name) {
                case Column.Type:
                    return `name,${direction}`;
                case Column.ValuesetName:
                    return `eventType,${direction}`;
                case Column.ValuesetDesc:
                    return `status,${direction}`;
                default:
                    return undefined;
            }
        }
        return undefined;
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        sortChange(toSortString(name, direction));
    };

    const footerActionBtn = (
        <div className="valueset-action-btn ds-u-text-align--right margin-bottom-1em">
            <ClassicButton className="cancel-btn" url="" onClick={() => {}}>
                Cancel
            </ClassicButton>
            <ClassicButton className="submit-btn" url="" onClick={() => {}} disabled>
                Add to question
            </ClassicButton>
        </div>
    );

    return (
        <TableComponent
            tableHeader=""
            tableHead={tableColumns}
            tableBody={tableRows}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={page.total}
            currentPage={page.current}
            handleNext={request}
            sortData={handleSort}
            buttons={<SearchBar />}
            handleSelected={handleSelected}
            footerAction={footerActionBtn}
        />
    );
};
