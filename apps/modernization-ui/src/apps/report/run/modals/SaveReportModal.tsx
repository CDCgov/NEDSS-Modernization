import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import React, { RefObject } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent.tsx';
import styles from './save-report-modal.module.scss';
import { ButtonGroup } from 'design-system/button';

type SaveReportModalProps = {
    saveReportModalRef: RefObject<ModalRef>;
    saving?: boolean;
    onSave: () => void;
};

export const SaveReportModal = ({ saveReportModalRef, saving, onSave }: SaveReportModalProps) => {
    return (
        <ModalComponent
            id="save-report-modal"
            className={styles.layout}
            modalRef={saveReportModalRef}
            isLarge
            modalHeading="Overwrite saved report?"
            closer={true}
            modalBody={
                <div className="margin-4">
                    This will replace the saved criteria with your current criteria. This action cannot be undone.
                </div>
            }
            modalFooter={
                <ButtonGroup>
                    <ModalToggleButton
                        modalRef={saveReportModalRef}
                        closer
                        outline
                        data-testid="cancel-report-save-btn"
                        disabled={saving}
                    >
                        Cancel
                    </ModalToggleButton>
                    <Button onClick={onSave} disabled={saving} data-testid="report-save-btn">
                        Save
                    </Button>
                </ButtonGroup>
            }
        ></ModalComponent>
    );
};
