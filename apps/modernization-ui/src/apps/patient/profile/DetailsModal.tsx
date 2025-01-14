import { Button, Grid, Icon, Modal, ModalRef } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { NoData } from 'components/NoData';

import styles from './details-modal.module.scss';
import classNames from 'classnames';

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

const renderField = (detail: Detail, index: number) => (
    <Grid key={index} col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
        <Grid row>
            <Grid col={6}>{detail.name}:</Grid>
            <Grid col={6}>{maybeRender(detail.value)}</Grid>
        </Grid>
    </Grid>
);

const maybeRender = (value: string | number | null | undefined) => value ?? <NoData />;

export const DetailsModal = ({ modal, title, onClose, details, onEdit, onDelete }: Props) => {
    return (
        <Modal id={`${title}-detail`} forceAction ref={modal} className={classNames(styles.modal)}>
            <header className={styles.header}>
                <h2>{title}</h2>
                <Icon.Close size={3} onClick={onClose} />
            </header>
            <div className={styles.content}>
                <section>{(details && <Grid row>{details.map(renderField)}</Grid>) || <NoData />}</section>
            </div>
            <footer className={styles.footer}>
                {onDelete && (
                    <Button unstyled className={styles.delete} type="button" onClick={onDelete}>
                        <Icon.Delete size={3} />
                        Delete
                    </Button>
                )}
                <Button onClick={onEdit} type="button" className={styles.cta}>
                    Edit
                </Button>
            </footer>
        </Modal>
    );
};
