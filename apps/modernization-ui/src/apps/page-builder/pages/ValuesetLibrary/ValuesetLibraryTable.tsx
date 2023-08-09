/* eslint-disable camelcase */
import { GetQuestionResponse, PageSummary, QuestionControllerService } from 'apps/page-builder/generated';
import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ValueSet } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { Direction } from 'sorting';
import './ValuesetLibraryTable.scss';
import { SearchBar } from './SearchBar';
import { Icon } from '@trussworks/react-uswds';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { AddValueset } from '../../components/AddValueset/AddValueset';
import { PagesContext } from '../../context/PagesContext';

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
    const { searchQuery, setSearchQuery, setCurrentPage, setSortBy, setSortDirection } = useContext(PagesContext);

    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;
    setSortBy('');
    setSortDirection('');
    // @ts-ignore
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
    const toSortString = (name: string): string | undefined => {
        if (name) {
            switch (name) {
                case Column.Type:
                    setSortBy('valueSetTypeCd');
                    break;
                case Column.ValuesetName:
                    setSortBy('valueSetNm');
                    break;
                case Column.ValuesetDesc:
                    setSortBy('codeSetDescTxt');
                    break;
                default:
                    return '';
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
