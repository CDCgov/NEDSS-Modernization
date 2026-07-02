import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent.tsx';
import styles from './save-report-modal.module.scss';
import { Button, ButtonGroup } from 'design-system/button';

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
            modalBody={
                <div className="margin-4">
                    This will replace the saved criteria with your current criteria. This action cannot be undone.
                </div>
            }
            modalFooter={
                <ButtonGroup>
                    <ModalToggleButton modalRef={saveReportModalRef} outline disabled={saving}>
                        Cancel
                    </ModalToggleButton>
                    <Button onClick={onSave} disabled={saving}>
                        Save
                    </Button>
                </ButtonGroup>
            }
        ></ModalComponent>
    );
};
