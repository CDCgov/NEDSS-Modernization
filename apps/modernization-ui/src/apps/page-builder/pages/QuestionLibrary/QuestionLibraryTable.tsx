/* eslint-disable camelcase */
import {
    CodedQuestion,
    DateQuestion,
    NumericQuestion,
    PageControllerService,
    PageSummary,
    TextQuestion
} from 'apps/page-builder/generated';
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { TableBody, TableComponent } from 'components/Table/Table';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { Direction } from 'sorting';
import './QuestionLibraryTable.scss';
import { SearchBar } from './SearchBar';
import { Icon } from '@trussworks/react-uswds';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { PagesContext } from '../../context/PagesContext';

export enum Column {
    Type = 'Type',
    UniqueId = 'Unique ID',
    UniqueName = 'Unique name',
    SubGroup = 'Subgroup'
}

const tableColumns = [
    { name: Column.Type, sortable: true },
    { name: Column.UniqueId, sortable: true },
    { name: Column.UniqueName, sortable: true },
    { name: Column.SubGroup, sortable: true },
    { name: '', sortable: false }
];

type Question = TextQuestion | DateQuestion | NumericQuestion | CodedQuestion;

type Props = {
    summaries: Question[];
    pages?: any;
};
export const QuestionLibraryTable = ({ summaries, pages }: Props) => {
    const { showAlert } = useAlert();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedQuestion, setSelectedQuestion] = useState<Question>({});
    const { searchQuery, setSearchQuery, setCurrentPage, setSortBy, setSortDirection } = useContext(PagesContext);

    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;
    const asTableRow = (page: Question): TableBody => ({
        id: page.id,
        checkbox: true,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.type || page.codeSet}</div> || null
            },
            { id: 2, title: <div className="event-text">{page?.uniqueId}</div> || null },
            {
                id: 3,
                title: <div>{page?.uniqueName}</div> || null
            },
            {
                id: 4,
                title: <div>{page?.subgroup}</div> || null
            },
            {
                id: 5,
                title:
                    (
                        <div className="ds-u-text-align--right">
                            <Icon.ExpandMore style={{ cursor: 'pointer' }} size={4} color="black" />
                        </div>
                    ) || null
            }
        ]
    });
    const handleSelected = ({ target }: any, item: any) => {
        if (target.checked) {
            const value: any = summaries.filter((val: any) => item.id === val.id);
            setSelectedQuestion(value);
        } else {
            setSelectedQuestion({});
        }
    };
    const asTableRows = (pages: PageSummary[] | undefined): TableBody[] => pages?.map(asTableRow) || [];

    /*
     * Converts header and Direction to API compatible sort string such as "name,asc"
     */
    const toSortString = (name: string): string | undefined => {
        if (name) {
            switch (name) {
                case Column.Type:
                    setSortBy('questionType');
                    break;
                case Column.UniqueId:
                    setSortBy('uniqueId');
                    break;
                case Column.UniqueName:
                    setSortBy('uniqueName');
                    break;
                case Column.SubGroup:
                    setSortBy('subgroup');
                    break;
                default:
                    return undefined;
            }
        }
        return undefined;
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    useEffect(() => {
        return () => localStorage.setItem('selectedQuestion', '0');
    }, []);

    const handleSort = (name: string, direction: Direction): void => {
        if (name && Direction) {
            toSortString(name);
            setSortDirection(direction);
        }
    };
    const handleAddQsntoPage = async () => {
        // TODO need to add logic for find orderNumber and Id
        const id: number = 0;
        const request = {
            orderNumber: 1,
            questionId: id
        };

        PageControllerService.addQuestionToPageUsingPost({
            authorization,
            id,
            request
        }).then((response: any) => {
            setSelectedQuestion({});
            showAlert({ type: 'success', header: 'Add', message: 'Add Question successfully on page' });
            return response;
        });
    };

    const footerActionBtn = (
        <div className="question-action-btn">
            <Button className="cancel-btn" type="button" onClick={() => setSelectedQuestion({})}>
                Cancel
            </Button>
            <Button
                className="submit-btn"
                type="button"
                onClick={handleAddQsntoPage}
                disabled={!Object.keys(selectedQuestion).length}>
                Add to page
            </Button>
        </div>
    );
    const modalRef = useRef<ModalRef>(null);
    const dataNotAvailableElement = (
        <div className="no-data-available">
            <label className="margin-bottom-1em no-text">
                {searchQuery ? `No results found for ‘${searchQuery}’` : 'No results found '}
            </label>
            <ModalToggleButton className="submit-btn" type="button" modalRef={modalRef} outline>
                Create New
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={modalRef}
                modalHeading={'Add question'}
                modalBody={<div> Add page </div>}
            />
        </div>
    );

    return (
        <div>
            <div>{<SearchBar onChange={setSearchQuery} />}</div>
            {summaries?.length ? (
                <TableComponent
                    tableHeader=""
                    tableHead={tableColumns}
                    tableBody={tableRows}
                    isPagination={true}
                    pageSize={pages.pageSize}
                    totalResults={pages.totalElements}
                    currentPage={pages.currentPage}
                    handleNext={setCurrentPage}
                    sortData={handleSort}
                    handleSelected={handleSelected}
                    rangeSelector={true}
                />
            ) : (
                dataNotAvailableElement
            )}
            <div className="footer-action">{footerActionBtn}</div>
        </div>
    );
};
