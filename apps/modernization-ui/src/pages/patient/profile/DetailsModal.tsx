import { Button, Grid, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import './DetailsModal.scss';

export type Detail = {
    name: string;
    value: string | number | undefined | null;
};

type Props = {
    modal: RefObject<ModalRef>;
    title: string;
    details?: Detail[];
    onClose?: () => void;
    onEdit?: () => void;
    onDelete?: () => void;
};

const noData = <span className="no-data">No Data</span>;

const renderField = (detail: Detail, index: number) => (
    <Grid key={index} col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
        <Grid row>
            <Grid col={6}>{detail.name}:</Grid>
            <Grid col={6}>{maybeRender(detail.value)}</Grid>
        </Grid>
    </Grid>
);

const maybeRender = (value: string | number | null | undefined) => <>{value}</> ?? noData;

export const DetailsModal = ({ modal, title, onClose, details, onEdit, onDelete }: Props) => {
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
                className="border-bottom border-base-lighter font-sans-lg padding-2 margin-0 modal-1-heading display-flex flex-align-center flex-justify">
                {title}
                <Icon.Close className="cursor-pointer" onClick={onClose} />
            </ModalHeading>
            {(details && (
                <div className="modal-body">
                    <Grid row>{details.map(renderField)}</Grid>
                </div>
            )) ||
                noData}
            <ModalFooter className="padding-2 margin-left-auto flex-justify display-flex details-footer">
                <Button
                    unstyled
                    className={`text-red display-flex flex-align-center delete--modal-btn ${
                        title.includes('Administrative') ? 'ds-u-visibility--hidden' : ''
                    }`}
                    type="button"
                    onClick={onDelete}>
                    <Icon.Delete className="delete-icon" />
                    Delete
                </Button>
                <Button type="button" onClick={onEdit}>
                    Edit
                </Button>
            </ModalFooter>
        </Modal>
    );
};
