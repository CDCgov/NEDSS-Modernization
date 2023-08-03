/* eslint-disable camelcase */
import { GetQuestionResponse, PageSummary, QuestionControllerService } from 'apps/page-builder/generated';
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Question } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { usePage } from 'page';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { Direction } from 'sorting';
import './QuestionLibraryTable.scss';
import { SearchBar } from './SearchBar';
import { Icon } from '@trussworks/react-uswds';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';

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

type Props = {
    sortChange: (sort?: string) => void;
    toSearch?: (search?: string, filter?: any) => void;
    summaries: Question[];
};
export const QuestionLibraryTable = ({ summaries, sortChange, toSearch }: Props) => {
    const { page, request } = usePage();
    const { showAlert } = useAlert();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [search, setSearch] = useState<string>('');
    const [selectedQuestion, setSelectedQuestion] = useState<Question>({});

    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;

    // @ts-ignore
    const asTableRow = (page: Question): TableBody => ({
        id: page.id,
        checkbox: true,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{page?.questionType || page.codeSet}</div> || null
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
    const toSortString = (name: string, direction: Direction): string | undefined => {
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
                    return undefined;
            }
        }
        return undefined;
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSearch = (search: string, filter: any) => {
        setSearch(search);
        toSearch?.(search, filter);
    };

    useEffect(() => {
        return () => localStorage.setItem('selectedQuestion', '0');
    }, []);

    const handleSort = (name: string, direction: Direction): void => {
        sortChange(toSortString(name, direction));
    };
    const handleAddQsn = async () => {
        // @ts-ignore
        // TODO :  we have to add logic for get question ID here
        const id: number = selectedQuestion[0]?.id;
        const { question }: GetQuestionResponse = await QuestionControllerService.getQuestionUsingGet({
            authorization,
            id
        }).then((response: GetQuestionResponse) => {
            return response;
        });

        const {
            valueSet,
            unitValue,
            description,
            messagingInfo,
            label,
            tooltip,
            displayControl,
            mask,
            dataMartInfo,
            uniqueName,
            fieldLength,
            size,
            fieldSize
        }: any = question;

        const request = {
            description,
            labelInMessage: messagingInfo.labelInMessage,
            messageVariableId: messagingInfo.messageVariableId,
            hl7DataType: messagingInfo.hl7DataType,
            label,
            tooltip,
            displayControl,
            mask,
            fieldLength,
            defaultLabelInReport: dataMartInfo.defaultLabelInReport,
            uniqueName,
            valueSet,
            unitValue,
            size,
            fieldSize
        };

        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id,
            request
        }).then((response: any) => {
            setSelectedQuestion({});
            showAlert({ type: 'success', header: 'Add', message: 'Question Added successfully' });
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
                onClick={handleAddQsn}
                disabled={!Object.keys(selectedQuestion).length}>
                Add to page
            </Button>
        </div>
    );
    const modalRef = useRef<ModalRef>(null);
    const dataNotAvailableElement = (
        <div className="no-data-available">
            <label className="margin-bottom-1em no-text">
                {search ? `No results found for ‘${search}’` : 'No results found '}
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
            <div>{<SearchBar onChange={handleSearch} />}</div>
            {summaries?.length ? (
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
                    handleSelected={handleSelected}
                />
            ) : (
                dataNotAvailableElement
            )}
            <div className="footer-action">{footerActionBtn}</div>
        </div>
    );
};
