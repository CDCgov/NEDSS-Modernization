import { Icon } from '@trussworks/react-uswds';
import { AddableQuestion } from 'apps/page-builder/generated';
import { AddableQuestionSort, SortField } from 'apps/page-builder/hooks/api/useFindAddableQuestions';
import { Search } from 'components/Search';
import { SelectionMode, TableBody, TableComponent } from 'components/Table';
import { Status, usePage } from 'page';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting';
import { ExpandedQuestion } from './ExpandedQuestion';
import styles from './addable-question-table.module.scss';

type Props = {
    isLoading?: boolean;
    questions: AddableQuestion[];
    onSortChange?: (sort: AddableQuestionSort | undefined) => void;
    onQuerySubmit?: (query: string) => void;
    onSelectionChange?: (mode: SelectionMode, id: number) => void;
};
export const AddableQuestionTable = ({
    isLoading,
    questions,
    onSortChange,
    onQuerySubmit,
    onSelectionChange
}: Props) => {
    const { page, request } = usePage();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [expanded, setExpanded] = useState<number | undefined>(undefined);

    const doQuerySearch = (query?: string) => {
        if (onQuerySubmit) {
            onQuerySubmit(query ?? '');
        }
    };

    useEffect(() => {
        if (questions && !isLoading) {
            setTableRows(questions.map(toTableRow) ?? []);
        }
    }, [questions, expanded]);

    const toTableRow = (question: AddableQuestion): TableBody => {
        return {
            id: question.id,
            key: question.id,
            checkbox: true,
            selectable: true,
            expanded: expanded === question.id,
            expandedViewComponent: <ExpandedQuestion question={question} />,
            onSelect: (mode: SelectionMode) => (onSelectionChange ? onSelectionChange(mode, question.id) : {}),
            tableDetails: [
                {
                    id: 1,
                    title: question?.type
                },
                { id: 2, title: question?.uniqueId },
                {
                    id: 3,
                    title: question?.uniqueName
                },
                {
                    id: 4,
                    title: question?.subgroupName
                },
                {
                    id: 5,
                    title:
                        expanded === question.id ? (
                            <Icon.ExpandLess
                                className={styles.expandButton}
                                onClick={() => setExpanded(undefined)}
                                size={4}
                            />
                        ) : (
                            <Icon.ExpandMore
                                className={styles.expandButton}
                                onClick={() => setExpanded(question.id)}
                                size={4}
                            />
                        )
                }
            ]
        };
    };

    const handleSort = (name: string, direction: Direction) => {
        if (!onSortChange) {
            return;
        }
        if (direction === Direction.None) {
            onSortChange(undefined);
            return;
        }

        let sortField: SortField | undefined = undefined;
        switch (name) {
            case 'Type':
                sortField = SortField.TYPE;
                break;
            case 'Unique ID':
                sortField = SortField.UNIQUE_ID;
                break;
            case 'Unique name':
                sortField = SortField.UNIQUE_NAME;
                break;
            case 'Subgroup':
                sortField = SortField.SUBGROUP;
                break;
        }
        if (sortField) {
            onSortChange({ field: sortField, direction });
        } else {
            onSortChange(undefined);
        }
    };

    const tableHeaders = [
        { name: 'Type', sortable: true },
        { name: 'Unique ID', sortable: true },
        { name: 'Unique name', sortable: true },
        { name: 'Subgroup', sortable: true },
        { name: '', sortable: false }
    ];

    return (
        <>
            <div className={styles.searchBar}>
                <Search
                    onSearch={doQuerySearch}
                    placeholder="Search by unique name or unique ID"
                    name="Test"
                    id="question-search"
                />
            </div>
            <div className={styles.tableContainer}>
                <TableComponent
                    contextName="questions"
                    tableHead={tableHeaders}
                    tableBody={tableRows}
                    isPagination={true}
                    pageSize={page.pageSize}
                    totalResults={page.total}
                    currentPage={page.current}
                    handleNext={request}
                    sortData={handleSort}
                    selectable={page.status === Status.Ready}
                    rangeSelector={page.total > 0}
                    isLoading={isLoading}
                />
            </div>
        </>
    );
};
