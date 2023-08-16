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
import { RefObject, useState } from 'react';
import './QuickConditionLookup.scss';
import { TableComponent } from 'components/Table/Table';

type Props = {
    modal: RefObject<ModalRef>;
    onClose?: () => void;
};

export const QuickConditionLookup = ({ modal, onClose }: Props) => {
    const conditions = ['hello', 'world', 'this', 'is', 'a', 'test'];
    const [selectedConditions, setSelectedConditions] = useState([]);

    const addCondition = (condition: string) => {
        setSelectedConditions([...selectedConditions, condition]);
    };

    const removeCondition = (condition: string) => {
        setSelectedConditions(selectedConditions.filter((c: string) => c !== condition));
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
                            placeholder="Type something here..."
                            id={''}
                            style={{ height: '41px', border: 'none' }}
                            name={'condition-search'}
                            type="search"
                        />
                        <Button type="button" style={{ height: '41px', borderRadius: 0 }}>
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
