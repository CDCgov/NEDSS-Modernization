/* eslint-disable camelcase */
import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { TableBody, TableComponent } from 'components/Table/Table';
import { RefObject, useContext, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import { BusinessRuleContext } from '../../context/BusinessContext';
import { SearchBar } from './SearchBar';
import { Link } from 'react-router-dom';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import './BusinessRulesLibraryTable.scss';
import { useGetPageDetails } from 'apps/page-builder/page/management';

export enum Column {
    SourceFields = 'Source Fields',
    Logic = 'Logic',
    Values = 'Values',
    Function = 'Function',
    Target = 'Target Fields',
    ID = 'ID'
}

// Sorting temporarily disabled until API is ready
const tableColumns = [
    { name: Column.SourceFields, sortable: true },
    { name: Column.Logic, sortable: true },
    { name: Column.Values, sortable: false },
    { name: Column.Function, sortable: true },
    { name: Column.Target, sortable: false },
    { name: Column.ID, sortable: true }
];

type TargetQuestion = {
    label: string;
    id: string;
};

type Rules = {
    ruleId?: number;
    templateUid?: number;
    ruleFunction?: string;
    ruleDescription?: string;
    sourceIdentifier?: string;
    sourceValue?: any;
    comparator?: string;
    targetType?: string;
    errorMsgText?: string;
    targetValueIdentifier?: any;
    targetQuestions?: TargetQuestion[];
};

type Props = {
    summaries: Rules[];
    pages?: any;
    qtnModalRef: RefObject<ModalRef>;
};

export const BusinessRulesLibraryTable = ({ summaries, pages, qtnModalRef }: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedQuestion, setSelectedQuestion] = useState<Rules>({});
    const { searchQuery, setSearchQuery, setCurrentPage, setSortBy, isLoading } = useContext(BusinessRuleContext);
    const { page } = useGetPageDetails();

    const mapLogic = ({ comparator, ruleFunction }: any) => {
        if (ruleFunction === 'Date Compare') {
            switch (comparator) {
                case '<':
                    return 'Less than';
                case '<=':
                    return 'Less or equal to';
                case '>=':
                    return 'Greater than';
                default:
                    return 'Greater than';
            }
        } else {
            switch (comparator) {
                case '=':
                    return 'Equal to';
                default:
                    return 'Not equal to';
            }
        }
    };

    const redirectRuleURL = `/page-builder/pages/${page?.id}/business-rules-library`;

    const asTableRow = (rule: Rules): TableBody => ({
        id: rule.templateUid,
        tableDetails: [
            {
                id: 1,
                title: <Link to={`${redirectRuleURL}/edit/${rule?.ruleId}`}>{rule.sourceIdentifier}</Link>
            },
            { id: 2, title: <div className="event-text">{mapLogic(rule)}</div> || null },
            {
                id: 3,
                title: <div>{rule.sourceValue?.join(' ')}</div> || null
            },
            {
                id: 4,
                title: <div>{rule.ruleFunction}</div> || null
            },
            {
                id: 5,
                title:
                    (
                        <div>
                            {rule.targetQuestions?.map((tq) => (
                                <>
                                    <span>{tq.label}</span>
                                    <br />
                                </>
                            ))}
                        </div>
                    ) || null
            },
            {
                id: 6,
                title: <div>{rule.ruleId}</div> || null
            }
        ]
    });

    // @ts-ignore
    const asTableRows = (pages: Rules[] | undefined): TableBody[] => pages?.map(asTableRow) || [];
    /*
     * Converts header and Direction to API compatible sort string such as "name,asc"
     */
    const toSortString = (name: string, direction: Direction): string => {
        if (name && direction && direction !== Direction.None) {
            switch (name) {
                case Column.SourceFields:
                    return `sourceQuestionIdentifier,${direction}`;
                case Column.Logic:
                    return `logic,${direction}`;
                case Column.Values:
                    return `sourceValue,${direction}`;
                case Column.Function:
                    return `ruleCd,${direction}`;
                case Column.Target:
                    return `targetValueIdentifier,${direction}`;
                case Column.ID:
                    return `id,${direction}`;
                default:
                    return '';
            }
        }
        return '';
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        if (pages?.currentPage > 1 && setCurrentPage) {
            setCurrentPage(1);
        }
        if (name && Direction) {
            setSortBy(toSortString(name, direction));
        }
    };

    const footerActionBtn = (
        <div className="question-action-btn">
            <ModalToggleButton
                closer
                className="cancel-btn"
                type="button"
                modalRef={qtnModalRef}
                onClick={() => setSelectedQuestion({})}>
                Cancel
            </ModalToggleButton>
            <ModalToggleButton
                className="submit-btn"
                modalRef={qtnModalRef}
                type="button"
                onClick={() => {}}
                disabled={!Object.keys(selectedQuestion).length}>
                Add to page
            </ModalToggleButton>
        </div>
    );

    const dataNotAvailableElement = (
        <div className="no-data-available">
            <label className="margin-bottom-1em no-text">
                {searchQuery ? `No results found for ‘${searchQuery}’` : 'No results found '}
            </label>
            <NavLinkButton className="submit-btn" type="outline" to={`${redirectRuleURL}/add`}>
                Create New
            </NavLinkButton>
        </div>
    );

    const searchAvailableElement = (
        <div className="no-data-available">
            <label className="no-text">Still can't find what are you're looking for?</label>
            <label className="margin-bottom-1em search-desc">
                Please try searching in the local library before creating new
            </label>
            <div>
                <NavLinkButton className="submit-btn" type="outline" to={`${redirectRuleURL}/add`}>
                    Create New
                </NavLinkButton>
            </div>
        </div>
    );

    return (
        <div>
            <div className="add-business-rules-block">
                <div className="business-rules-header">
                    <h3> {page?.name} | business rules </h3>
                </div>
                <NavLinkButton className="test-btn" to={`${redirectRuleURL}/add`}>
                    Add new business rule
                </NavLinkButton>
            </div>
            <div>{<SearchBar onChange={setSearchQuery} />}</div>

            {summaries?.length ? (
                <TableComponent
                    display="zebra"
                    contextName="businessRules"
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
                    isLoading={isLoading}
                />
            ) : (
                dataNotAvailableElement
            )}
            {summaries?.length > 0 && searchQuery && searchAvailableElement}
            <div className="footer-action display-none">{footerActionBtn}</div>
        </div>
    );
};
