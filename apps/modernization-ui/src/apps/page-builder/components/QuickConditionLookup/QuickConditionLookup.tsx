import {
    Button,
    ButtonGroup,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton,
    TextInput
} from '@trussworks/react-uswds';
import { RefObject, useContext, useEffect, useState } from 'react';
import './QuickConditionLookup.scss';
import { TableComponent, TableBody } from 'components/Table/Table';
import { Condition, ConditionControllerService } from 'apps/page-builder/generated';
import { UserContext } from 'user';

type Props = {
    modal: RefObject<ModalRef>;
    onClose?: () => void;
    addConditions: (conditions: Condition[]) => void;
};

const tableHeaders = [
    { name: 'Condition', sortable: true },
    { name: 'Code', sortable: true },
    { name: 'Program area', sortable: true },
    { name: 'Condition Family', sortable: true },
    { name: 'Investigateion page', sortable: true },
    { name: 'Status', sortable: true }
];

export const QuickConditionLookup = ({ modal, onClose, addConditions }: Props) => {
    const [conditions, setConditions] = useState<any[]>([]);
    const [selectedConditions, setSelectedConditions] = useState<Condition[]>([]);
    const [searchText, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalConditions, setTotalConditions] = useState(0);
    const [tableRows, setTableRows] = useState<TableBody[]>([]);
    const { state } = useContext(UserContext);

    const handleSelectConditions = (condition: Condition) => {
        setSelectedConditions([...selectedConditions, condition]);
    };

    const handleSearch = (value: string) => {
        setSearchTerm(value);
    };

    const handleSubmitSearch = () => {
        setLoading(true);
        const authorization = `Bearer ${state.getToken()}`;
        const request = { searchText };

        ConditionControllerService.searchConditionUsingPost({
            authorization,
            request,
            page: currentPage,
            size: 10
        }).then((response: any) => {
            // set conditions
            setLoading(false);
            console.log('response', response);
            setConditions(response.content);
            setTotalConditions(response.totalElements);
        });
    };

    useEffect(() => {
        handleSubmitSearch();
    }, []);

    useEffect(() => {
        setTableRows(asTableRows(conditions));
    }, [conditions]);

    const asTableRow = (condition: any): TableBody => ({
        id: condition.id,
        checkbox: true,
        expanded: true,
        tableDetails: [
            {
                id: 1,
                title: condition?.conditionShortNm || null
            },
            { id: 2, title: condition.id || null },
            {
                id: 3,
                title: condition?.progAreaCd || null
            },
            { id: 4, title: condition.familyCd },
            { id: 5, title: condition.investigationFormCd || null },
            { id: 6, title: condition.statusCd === 'A' ? 'Active' : 'Inactive' }
        ]
    });

    const asTableRows = (conditions: any | undefined): TableBody[] => conditions?.map(asTableRow) || [];

    const handleAddConditions = () => {
        addConditions(selectedConditions);
    };

    return (
        <Modal
            style={{ minWidth: '80%' }}
            ref={modal}
            forceAction
            id="quick-condition-lookup"
            isInitiallyOpen={true}
            isLarge
            aria-labelledby="incomplete-form-confirmation-modal-heading"
            className="padding-0"
            aria-describedby="incomplete-form-confirmation-modal-description">
            <ModalHeading
                id="incomplete-form-confirmation-modal-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2">
                <div className="header">
                    <div>Search and add condition(s)</div>
                    <Button type="button" unstyled onClick={onClose} className="close-btn">
                        <Icon.Close />
                    </Button>
                </div>
            </ModalHeading>
            <div className="modal-body">
                <p className="description">You can search for existing condition(s) or create a new one</p>
                <div className="search-container">
                    <div style={{ display: 'flex' }}>
                        <TextInput
                            inputSize="medium"
                            value={searchText}
                            onChange={(e) => handleSearch(e.target.value)}
                            placeholder="Type something here..."
                            id={'condition-search'}
                            style={{ height: '41px', border: 'none' }}
                            name={'condition-search'}
                            type="search"
                        />
                        <Button type="button" style={{ height: '41px', borderRadius: 0 }} onClick={handleSubmitSearch}>
                            <Icon.Search />
                        </Button>
                    </div>
                    <Button type="button" outline style={{ height: '41px' }}>
                        <Icon.FilterAlt />
                        Filter
                    </Button>
                    <Button type="button" style={{ height: '41px' }}>
                        Add new condition
                    </Button>
                </div>
                {conditions?.length ? (
                    <TableComponent
                        isLoading={loading}
                        tableHeader=""
                        tableHead={tableHeaders}
                        tableBody={tableRows}
                        isPagination={true}
                        pageSize={10}
                        totalResults={totalConditions}
                        currentPage={currentPage}
                        handleNext={setCurrentPage}
                        handleSelected={handleSelectConditions}
                        rangeSelector={true}
                    />
                ) : (
                    <div>No data </div>
                )}
            </div>
            <ModalFooter className="padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <ModalToggleButton modalRef={modal} closer onClick={onClose} outline>
                        Cancel
                    </ModalToggleButton>
                    <ModalToggleButton modalRef={modal} closer onClick={onClose}>
                        Add Condition
                    </ModalToggleButton>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
