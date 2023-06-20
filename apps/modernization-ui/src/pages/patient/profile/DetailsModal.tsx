import {
    ButtonGroup,
    Grid,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import { RefObject } from 'react';

export type Detail = {
    name: string;
    value: string | number | undefined | null;
};

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    details?: Detail[];
    onClose?: () => void;
};

const noData = <span className="no-data">No data</span>;

const renderField = (detail: Detail) => (
    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
        <Grid row>
            <Grid col={6}>{detail.name}:</Grid>
            <Grid col={6}>{maybeRender(detail.value)}</Grid>
        </Grid>
    </Grid>
);

const maybeRender = (value: string | number | null | undefined) => <>{value}</> ?? noData;

export const DetailsModal = ({ modal, title, details, onClose = () => {} }: Props) => {
    return (
        <Modal
            ref={modal}
            forceAction
            id="details-modal"
            aria-labelledby="modal-1-heading"
            className="padding-0"
            aria-describedby="modal-1-description">
            <ModalHeading
                id="modal-1-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2 margin-0 modal-1-heading">
                {title}
            </ModalHeading>
            {(details && (
                <div className="modal-body">
                    <Grid row>{details.map(renderField)}</Grid>
                </div>
            )) ||
                noData}
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
