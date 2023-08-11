/* eslint-disable camelcase */
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { GetQuestionResponse, PageSummary, QuestionControllerService, ValueSet } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { useContext, useEffect, useRef, useState } from 'react';
import { Direction } from 'sorting';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { UserContext } from '../../../../providers/UserContext';
import { AddValueset } from '../../components/AddValueset/AddValueset';
import { PagesContext } from '../../context/PagesContext';
import { SearchBar } from './SearchBar';
import './ValuesetLibraryTable.scss';
import ValuesetLibraryTableRowExpanded from './ValuesetLibraryTableRowExpanded';

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
    summaries: ValueSet[];
    labModalRef?: any;
    pages?: any;
};
export const ValuesetLibraryTable = ({ summaries, labModalRef, pages }: Props) => {
    const { showAlert } = useAlert();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedValueSet, setSelectedValueSet] = useState<ValueSet>({});
    const [expandedRows, setExpandedRows] = useState<number[]>([]);
    const { searchQuery, setSearchQuery, setCurrentPage, setSortBy } = useContext(PagesContext);
    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;

    // @ts-ignore
    const asTableRow = (valueSet: ValueSet): TableBody => ({
        id: valueSet.nbsUid,
        checkbox: true,
        expanded: expandedRows.some((id) => id === valueSet.nbsUid),
        expandedViewComponent: <ValuesetLibraryTableRowExpanded data={valueSet} />,
        tableDetails: [
            {
                id: 1,
                title: <div className="page-name">{valueSet?.valueSetTypeCd}</div> || null
            },
            { id: 2, title: <div className="event-text">{valueSet?.valueSetNm}</div> || null },
            {
                id: 3,
                title: <div>{valueSet?.codeSetDescTxt}</div> || null
            },
            {
                id: 4,
                title:
                    (
                        <div className="ds-u-text-align--right">
                            {expandedRows.some((id) => id === valueSet.nbsUid) ? (
                                <Button type="button" unstyled aria-label="expand-less">
                                    <Icon.ExpandLess
                                        style={{ cursor: 'pointer' }}
                                        size={4}
                                        color="black"
                                        onClick={() => handleExpandLessClick(valueSet.nbsUid)}
                                    />
                                </Button>
                            ) : (
                                <Button type="button" unstyled aria-label="expand-more">
                                    <Icon.ExpandMore
                                        style={{ cursor: 'pointer' }}
                                        size={4}
                                        color="black"
                                        onClick={() => handleExpandMoreClick(valueSet.nbsUid)}
                                    />
                                </Button>
                            )}
                        </div>
                    ) || null
            }
        ]
    });

    const handleExpandMoreClick = (id: number | undefined) => {
        if (id) {
            setExpandedRows([...expandedRows, id]);
        }
    };

    const handleExpandLessClick = (id: number | undefined) => {
        const indexToRemove = expandedRows.findIndex((rowId) => rowId === id);

        if (indexToRemove !== -1) {
            const rows = [...expandedRows];
            rows.splice(indexToRemove, 1);
            setExpandedRows(rows);
        }
    };

    const handleSelected = ({ target }: any, item: any) => {
        if (target.checked) {
            const value: any = summaries.filter((val: any) => item.id === val.nbsUid);
            setSelectedValueSet(value);
        } else {
            setSelectedValueSet({});
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
                    return `valueSetTypeCd,${direction}`;
                case Column.ValuesetName:
                    return `valueSetNm,${direction}`;
                case Column.ValuesetDesc:
                    return `codeSetDescTxt,${direction}`;
                default:
                    return '';
            }
        }
        return '';
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries, expandedRows]);

    useEffect(() => {
        return () => localStorage.setItem('selectedQuestion', '0');
    }, []);

    const handleSort = (name: string, direction: Direction): void => {
        if (name && Direction) {
            setSortBy(toSortString(name, direction));
        }
    };

    const handleAddQsn = async () => {
        // @ts-ignore
        const id: number = parseInt(localStorage.getItem('selectedQuestion'));
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
            size: size,
            fieldSize
        };

        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id,
            request
        }).then((response: any) => {
            setSelectedValueSet({});
            showAlert({ type: 'success', header: 'Add', message: 'Question Added successfully' });
            return response;
        });
    };

    const footerActionBtn = (
        <div className="valueset-action-btn ds-u-text-align--right margin-bottom-1em">
            <ModalToggleButton
                closer
                modalRef={labModalRef}
                className="cancel-btn"
                type="button"
                onClick={() => setSelectedValueSet({})}>
                Cancel
            </ModalToggleButton>
            <ModalToggleButton
                closer
                modalRef={labModalRef}
                className="submit-btn"
                type="button"
                onClick={handleAddQsn}
                disabled={!Object.keys(selectedValueSet).length}>
                Add to question
            </ModalToggleButton>
        </div>
    );

    const modalRef = useRef<ModalRef>(null);
    const dataNotAvailableElement = (
        <div className="no-data-available">
            <label className="margin-bottom-1em no-text">
                {searchQuery ? `No results found for ‘${searchQuery}’` : 'No results found '}
            </label>
            <ModalToggleButton className="submit-btn" type="button" modalRef={modalRef} outline>
                Add value set
            </ModalToggleButton>
            <ModalComponent
                isLarge
                modalRef={modalRef}
                modalHeading={'Add value set'}
                modalBody={<AddValueset hideHeader modalRef={modalRef} />}
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
