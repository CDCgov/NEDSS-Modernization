/* eslint-disable camelcase */
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { TableBody, TableComponent } from 'components/Table/Table';
import { RefObject, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import { SearchBar } from './SearchBar';
import { Link } from 'react-router-dom';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import './BusinessRulesLibraryTable.scss';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { Rule } from 'apps/page-builder/generated';
import React from 'react';
import { mapComparatorToString } from './helpers/mapComparatorToString';
import { mapRuleFunctionToString } from './helpers/mapRuleFunctionToString';
import { usePage } from 'page';
import { BusinessRuleSort, RuleSortField } from 'apps/page-builder/hooks/api/useFetchPageRules';

export enum Column {
    SourceQuestion = 'Source question',
    Logic = 'Logic',
    Values = 'Values',
    Function = 'Function',
    TargetQuestions = 'Target(s)',
    ID = 'ID'
}

// Sorting temporarily disabled until API is ready
const tableColumns = [
    { name: Column.SourceQuestion, sortable: true },
    { name: Column.Logic, sortable: true },
    { name: Column.Values, sortable: true },
    { name: Column.Function, sortable: true },
    { name: Column.TargetQuestions, sortable: false },
    { name: Column.ID, sortable: true }
];

type Props = {
    summaries: Rule[];
    onSortChange: (sort: BusinessRuleSort | undefined) => void;
    onQueryChange: (query: string) => void;
    qtnModalRef: RefObject<ModalRef>;
    isLoading?: boolean;
};

export const BusinessRulesLibraryTable = ({
    summaries,
    qtnModalRef,
    onSortChange,
    onQueryChange,
    isLoading
}: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedQuestion, setSelectedQuestion] = useState<Rule[]>([]);

    const { page } = useGetPageDetails();
    const { page: curPage, request } = usePage();

    const getSubsections = () => {
        if (page) {
            const sections = page.tabs?.map((tab) => tab.sections).flat();
            return sections?.map((section) => section.subSections).flat();
        }
    };

    const redirectRuleURL = `/page-builder/pages/${page?.id}/business-rules`;

    const asTableRow = (rule: Rule): TableBody => ({
        key: rule.id,
        id: rule.template.toString(),
        tableDetails: [
            {
                id: 1,
                title: (
                    <Link to={`/page-builder/pages/${page?.id}/${rule.id}`}>
                        {rule.sourceQuestion.label} ({rule.sourceQuestion.questionIdentifier})
                    </Link>
                )
            },
            { id: 2, title: <div className="event-text">{mapComparatorToString(rule.comparator)}</div> },
            {
                id: 3,
                title: (
                    <div>
                        {!rule.anySourceValue ? (
                            rule?.sourceValues?.map((value, index) => (
                                <React.Fragment key={index}>
                                    <span>{value}</span>
                                    <br />
                                </React.Fragment>
                            ))
                        ) : (
                            <div>Any source value</div>
                        )}
                    </div>
                )
            },
            {
                id: 4,
                title: <div>{mapRuleFunctionToString(rule.ruleFunction)}</div>
            },
            {
                id: 5,
                title: (
                    <div>
                        {rule.targets?.map((target, index) => {
                            if (rule.targetType == Rule.targetType.SUBSECTION) {
                                const subsections = getSubsections();
                                const subsection = subsections?.find(
                                    (sub) => sub.id == Number(target?.targetIdentifier)
                                );
                                return (
                                    <React.Fragment key={index}>
                                        <span>{subsection?.name}</span>
                                        <br />
                                    </React.Fragment>
                                );
                            } else {
                                return (
                                    <React.Fragment key={index}>
                                        <span>
                                            {target.label} ({target.targetIdentifier})
                                        </span>
                                        <br />
                                    </React.Fragment>
                                );
                            }
                        })}
                    </div>
                )
            },
            {
                id: 6,
                title: <div>{rule.id}</div>
            }
        ]
    });

    const asTableViewRow = (rule: Rule): TableBody => ({
        key: rule.id,
        id: rule.template.toString(),
        tableDetails: [
            {
                id: 1,
                title: (
                    <Link to={`/page-builder/pages/${page?.id}/business-rules/${rule.id}`}>
                        {rule.sourceQuestion.label} ({rule.sourceQuestion.questionIdentifier})
                    </Link>
                )
            },
            { id: 2, title: <div className="event-text">{mapComparatorToString(rule.comparator)}</div> },
            {
                id: 3,
                title: (
                    <div>
                        {!rule.anySourceValue ? (
                            rule?.sourceValues?.map((value, index) => (
                                <React.Fragment key={index}>
                                    <span>{value}</span>
                                    <br />
                                </React.Fragment>
                            ))
                        ) : (
                            <div>Any source value</div>
                        )}
                    </div>
                )
            },
            {
                id: 4,
                title: <div>{mapRuleFunctionToString(rule.ruleFunction)}</div>
            },
            {
                id: 5,
                title: (
                    <div>
                        {rule.targets?.map((target, index) => {
                            if (rule.targetType == Rule.targetType.SUBSECTION) {
                                const subsections = getSubsections();
                                const subsection = subsections?.find(
                                    (sub) => sub.id == Number(target?.targetIdentifier)
                                );
                                return (
                                    <React.Fragment key={index}>
                                        <span>{subsection?.name}</span>
                                        <br />
                                    </React.Fragment>
                                );
                            } else {
                                return (
                                    <React.Fragment key={index}>
                                        <span>
                                            {target.label} ({target.targetIdentifier})
                                        </span>
                                        <br />
                                    </React.Fragment>
                                );
                            }
                        })}
                    </div>
                )
            },
            {
                id: 6,
                title: <div>{rule.id}</div>
            }
        ]
    });

    const asTableRows = (rules: Rule[] | undefined): TableBody[] => {
        if (page?.status === 'Published') {
            return rules?.map(asTableViewRow) || [];
        } else {
            return rules?.map(asTableRow) || [];
        }
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction) => {
        if (direction === Direction.None) {
            onSortChange(undefined);
            return;
        }

        let sortField: RuleSortField | undefined = undefined;
        switch (name) {
            case Column.SourceQuestion:
                sortField = RuleSortField.SOURCE;
                break;
            case Column.Logic:
                sortField = RuleSortField.LOGIC;
                break;
            case Column.Values:
                sortField = RuleSortField.VALUE;
                break;
            case Column.Function:
                sortField = RuleSortField.FUNCTION;
                break;
            case Column.TargetQuestions:
                sortField = RuleSortField.TARGET;
                break;
            case Column.ID:
                sortField = RuleSortField.ID;
                break;
        }

        if (sortField) {
            onSortChange({ field: sortField, direction });
        } else {
            onSortChange(undefined);
        }
    };

    const footerActionBtn = (
        <div className="question-action-btn">
            <ModalToggleButton
                closer
                className="cancel-btn"
                type="button"
                modalRef={qtnModalRef}
                onClick={() => setSelectedQuestion([])}>
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
            <div className="no-data-info">
                <span className="no-items">No items to display</span>
                <p>Click 'Add new business rule' to add new rule</p>
                {page?.status === 'Published' ? (
                    <Button type="button" disabled>
                        Add new business rule
                    </Button>
                ) : (
                    <NavLinkButton className="submit-btn" to={`${redirectRuleURL}/add`}>
                        Add new business rule
                    </NavLinkButton>
                )}
            </div>
        </div>
    );

    return (
        <div>
            <div className="add-business-rules-block">
                <div className="business-rules-header">
                    <h3> {page?.name} | Business rules </h3>
                </div>
                {page?.status === 'Published' ? (
                    <Button type="button" disabled>
                        Add new business rule
                    </Button>
                ) : (
                    <NavLinkButton className="test-btn" to={`${redirectRuleURL}/add`}>
                        Add new business rule
                    </NavLinkButton>
                )}
            </div>
            <div>
                <SearchBar onChange={onQueryChange} />
            </div>
            <TableComponent
                display="zebra"
                contextName="businessRules"
                className="business-rules-table"
                tableHeader=""
                tableHead={tableColumns}
                tableBody={tableRows}
                isPagination
                pageSize={curPage.pageSize}
                totalResults={curPage.total}
                currentPage={curPage.current}
                handleNext={request}
                sortData={handleSort}
                rangeSelector={isLoading === true || summaries.length > 0}
                isLoading={isLoading}
            />
            {summaries.length === 0 && !isLoading && dataNotAvailableElement}
            <div className="footer-action display-none">{footerActionBtn}</div>
        </div>
    );
};
