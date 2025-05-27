import { Button, Icon } from '@trussworks/react-uswds';
import { AvailableQuestion } from 'apps/page-builder/generated';
import { AddableQuestionSort, SortField } from '../../../../../../hooks/api/useFindAvailableQuestions';
import { Search } from 'components/Search';
import { SelectionMode, TableBody, TableComponent } from 'components/Table';
import { Status, usePagination } from 'pagination';
import { useEffect, useState } from 'react';
import { Direction } from 'libs/sorting';
import { ExpandedQuestion } from './ExpandedQuestion';
import styles from './question-search-table.module.scss';

type Props = {
    isLoading?: boolean;
    questions: AvailableQuestion[];
    query?: string;
    onSortChange?: (sort: AddableQuestionSort | undefined) => void;
    onQuerySubmit?: (query: string) => void;
    onSelectionChange?: (mode: SelectionMode, id: number) => void;
    onCreateNew: () => void;
};
export const QuestionSearchTable = ({
    isLoading,
    questions,
    query,
    onSortChange,
    onQuerySubmit,
    onSelectionChange,
    onCreateNew
}: Props) => {
    const { page, request } = usePagination();
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

    const tableHeaders = [
        { name: 'Type', sortable: true },
        { name: 'Unique ID', sortable: true },
        { name: 'Label', sortable: true },
        { name: 'Subgroup', sortable: true },
        { name: 'More info', sortable: false }
    ];

    const toTableRow = (question: AvailableQuestion): TableBody => {
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
                    title: question?.label
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
            case 'Status':
                sortField = SortField.STATUS;
                break;
            case 'Type':
                sortField = SortField.TYPE;
                break;
            case 'Unique ID':
                sortField = SortField.UNIQUE_ID;
                break;
            case 'Label':
                sortField = SortField.LABEL;
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

    return (
        <>
            <div className={styles.searchBar}>
                <div>
                    {query && (
                        <div className={styles.searchText}>
                            <div> {query}</div>
                            <Button
                                className={styles.clearSearchButton}
                                type="button"
                                onClick={() => onQuerySubmit?.('')}>
                                <Icon.Close />
                            </Button>
                        </div>
                    )}
                </div>
                <div>
                    <Search
                        onSearch={doQuerySearch}
                        placeholder="Search by unique name or unique ID"
                        name="question-search"
                        id="question-search"
                        value={query}
                    />
                    <Button
                        type="button"
                        className={`${styles.createNewButton} addQuestionCreateNewBtn`}
                        outline
                        onClick={onCreateNew}>
                        Create new
                    </Button>
                </div>
            </div>
            <div className={styles.tableContainer}>
                <TableComponent
                    tableHead={tableHeaders}
                    tableBody={tableRows}
                    isPagination={true}
                    pageSize={page.pageSize}
                    totalResults={page.total}
                    currentPage={page.current}
                    handleNext={request}
                    sortData={handleSort}
                    selectable={page.status === Status.Ready}
                    rangeSelector={isLoading === true || questions.length > 0}
                    isLoading={isLoading}
                    display="zebra"
                />
            </div>
        </>
    );
};
