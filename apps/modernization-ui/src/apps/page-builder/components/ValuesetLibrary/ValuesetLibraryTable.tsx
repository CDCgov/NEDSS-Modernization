/* eslint-disable camelcase */
import { GetQuestionResponse, PageSummary, QuestionControllerService } from 'apps/page-builder/generated';
import { Button } from '@trussworks/react-uswds';
import { ValueSet } from 'apps/page-builder/generated';
import { TableBody, TableComponent } from 'components/Table/Table';
import { usePage } from 'page';
import React, { useContext, useEffect, useState } from 'react';
import { Direction } from 'sorting';
import './ValuesetLibraryTable.scss';
import { SearchBar } from './SearchBar';
import { Icon } from '@trussworks/react-uswds';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';

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
    sortChange: (sort?: string) => void;
    summaries: ValueSet[];
};
export const ValuesetLibraryTable = ({ summaries, sortChange }: Props) => {
    const { page, request } = usePage();
    const { showAlert } = useAlert();
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const [selectedValueSet, setSelectedValueSet] = useState<ValueSet>({});

    const { state } = useContext(UserContext);
    const authorization = `Bearer ${state.getToken()}`;

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
    const handleSelected = ({ target }: any, index: number) => {
        if (target.checked) {
            setSelectedValueSet(summaries[index]);
        } else {
            setSelectedValueSet({});
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
                    return `valueSetTypeCd,${direction}`;
                case Column.ValuesetName:
                    return `valueSetNm,${direction}`;
                case Column.ValuesetDesc:
                    return `codeSetDescTxt,${direction}`;
                default:
                    return undefined;
            }
        }
        return undefined;
    };

    useEffect(() => {
        setTableRows(asTableRows(summaries));
    }, [summaries]);

    const handleSort = (name: string, direction: Direction): void => {
        sortChange(toSortString(name, direction));
    };
    const handleAddQsn = async () => {
        // @ts-ignore
        // TODO :  we have to add logic for get quetion ID here
        const id: number = selectedValueSet?.codeSetGroupId;

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
            fieldLength
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
            unitValue
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
            <Button className="cancel-btn" type="button" onClick={() => setSelectedValueSet({})}>
                Cancel
            </Button>
            <Button
                className="submit-btn"
                type="button"
                onClick={handleAddQsn}
                disabled={!Object.keys(selectedValueSet).length}>
                Add to question
            </Button>
        </div>
    );

    return (
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
            buttons={<SearchBar />}
            handleSelected={handleSelected}
            footerAction={footerActionBtn}
        />
    );
};
