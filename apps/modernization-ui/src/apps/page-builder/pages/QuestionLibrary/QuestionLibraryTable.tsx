/* eslint-disable camelcase */
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import {
    CodedQuestion,
    DateQuestion,
    NumericQuestion,
    PageQuestionControllerService,
    PageSummary,
    TextQuestion
} from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { useContext, useEffect, useRef, useState } from 'react';
import { Direction } from 'sorting';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { UserContext } from '../../../../providers/UserContext';
import { PagesContext } from '../../context/PagesContext';
import './QuestionLibraryTable.scss';
import { SearchBar } from './SearchBar';

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
    const { searchQuery, setSearchQuery, setCurrentPage, setSortBy } = useContext(PagesContext);

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
    const toSortString = (name: string, direction: Direction): string => {
        if (name && direction && direction !== Direction.None) {
            switch (name) {
                case Column.Type:
                    return `questionType,${direction}`;
                case Column.UniqueId:
                    return `uniqueId,${direction}`;
                case Column.UniqueName:
                    return `uniqueName,${direction}`;
                case Column.SubGroup:
                    return `subgroup,${direction}`;
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
        return () => localStorage.setItem('selectedQuestion', '0');
    }, []);

    const handleSort = (name: string, direction: Direction): void => {
        if (name && Direction) {
            setSortBy(toSortString(name, direction));
        }
    };
    const handleAddQsntoPage = async () => {
        // TODO need to add logic for find orderNumber and Id
        const id: number = 0;
        const request = {
            orderNumber: 1,
            questionId: id
        };

        PageQuestionControllerService.addQuestionToPageUsingPost({
            authorization,
            page: id,
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
                    pageSize={pages?.pageSize || 0}
                    totalResults={pages?.total || 0}
                    currentPage={pages?.current || 0}
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
