/* eslint-disable camelcase */
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Rule } from 'apps/page-builder/generated';
import { BusinessRuleSort, RuleSortField } from 'apps/page-builder/hooks/api/useFetchPageRules';
import { useGetPageDetails } from 'apps/page-builder/page/management';
import { TableBody, TableComponent } from 'components/Table/Table';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { usePagination } from 'pagination';
import React, { RefObject, useEffect, useState } from 'react';
import { Link } from 'react-router';
import { Direction } from 'libs/sorting';
import './BusinessRulesLibraryTable.scss';
import { RuleSearchBar } from './RuleSearchBar';
import { mapComparatorToString } from './helpers/mapComparatorToString';
import { mapRuleFunctionToString } from './helpers/mapRuleFunctionToString';

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
    rules: Rule[];
    onSortChange: (sort: BusinessRuleSort | undefined) => void;
    onQueryChange: (query: string) => void;
    qtnModalRef: RefObject<ModalRef>;
    isLoading?: boolean;
    onDownloadCsv: () => void;
    onDownloadPdf: () => void;
};

export const BusinessRulesLibraryTable = ({
    rules,
    qtnModalRef,
    onSortChange,
    onQueryChange,
    isLoading,
    onDownloadCsv,
    onDownloadPdf
}: Props) => {
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedQuestion, setSelectedQuestion] = useState<Rule[]>([]);

    const { page } = useGetPageDetails();
    const { page: curPage, request } = usePagination();

    const getSubsections = () => {
        if (page) {
            const sections = page.tabs?.map((tab) => tab.sections).flat();
            return sections?.map((section) => section.subSections).flat();
        }
    };

    const redirectRuleURL = `/page-builder/pages/${page?.id}/business-rules`;

    const asTableRow = (rule: Rule): TableBody => {
        let url: string;
        if (page?.status === 'Published') {
            url = `/page-builder/pages/${page?.id}/business-rules/${rule.id}`;
        } else {
            url = `/page-builder/pages/${page?.id}/business-rules/edit/${rule.id}`;
        }
        return {
            key: rule.id,
            id: rule.template.toString(),
            tableDetails: [
                {
                    id: 1,
                    title: (
                        <Link to={url}>
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
                                        (sub) => sub.questionIdentifier === target.targetIdentifier
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
        };
    };

    useEffect(() => {
        setTableRows(rules.map(asTableRow) ?? []);
    }, [rules]);

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
                <RuleSearchBar onChange={onQueryChange} onDownloadCsv={onDownloadCsv} onDownloadPdf={onDownloadPdf} />
            </div>
            <TableComponent
                display="zebra"
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
                rangeSelector={isLoading === true || rules.length > 0}
                isLoading={isLoading}
            />
            {rules.length === 0 && !isLoading && dataNotAvailableElement}
            <div className="footer-action display-none">{footerActionBtn}</div>
        </div>
    );
};
