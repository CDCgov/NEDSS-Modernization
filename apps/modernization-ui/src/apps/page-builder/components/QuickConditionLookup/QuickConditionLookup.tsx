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
import { RefObject, useContext, useState } from 'react';
import './QuickConditionLookup.scss';
import { TableComponent } from 'components/Table/Table';
import { Spinner } from '@cmsgov/design-system';
import { ConditionControllerService, SearchConditionRequest } from 'apps/page-builder/generated';
import { UserContext } from 'user';

type Props = {
    modal: RefObject<ModalRef>;
    onClose?: () => void;
    addConditions?: () => void;
};

export const QuickConditionLookup = ({ modal, onClose }: Props) => {
    const conditions = ['hello', 'world', 'this', 'is', 'a', 'test'];
    // const [selectedConditions, setSelectedConditions] = useState([]);
    const [searchText, setSearchText] = useState('');
    const [loading, setLoading] = useState(false);
    const { state } = useContext(UserContext);

    // const addCondition = (condition: string) => {
    //     setSelectedConditions([...selectedConditions, condition]);
    // };

    // const removeCondition = (condition: string) => {
    //     setSelectedConditions(selectedConditions.filter((c: string) => c !== condition));
    // };

    const handleSearch = (e: string) => {
        setSearchText(e);
    };

    const handleSubmitSearch = () => {
        console.log('searching for', searchText);
        setLoading(true);
        const authorization = `Bearer ${state.getToken()}`;
        const request = { searchText };

        ConditionControllerService.searchConditionUsingPost({
            authorization,
            request
        }).then((response: any) => {
            // set conditions
            setLoading(false);
            console.log('response', response);
        });
    };

    const tableHeaders = [
        { name: 'Condition', sortable: true },
        { name: 'Code', sortable: true },
        { name: 'Program area', sortable: true },
        { name: 'Condition Family', sortable: true },
        { name: 'Investigateion page', sortable: true },
        { name: 'Status', sortable: true }
    ];

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
                            value={searchTerm}
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
                {loading && <Spinner />}
                {conditions?.length ? (
                    <TableComponent
                        tableHeader=""
                        tableHead={tableHeaders}
                        tableBody={[]}
                        isPagination={true}
                        pageSize={10}
                        totalResults={10}
                        currentPage={1}
                        handleNext={() => null}
                        sortData={() => null}
                        handleSelected={() => null}
                        rangeSelector={true}
                    />
                ) : (
                    <div>No data </div>
                )}
            </div>
            <ModalFooter className="padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <ModalToggleButton modalRef={modal} closer onClick={onClose}>
                        Go back
                    </ModalToggleButton>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
