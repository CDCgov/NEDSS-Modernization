import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent.tsx';
import styles from './save-report-modal.module.scss';
import { ButtonGroup } from 'design-system/button';

type SaveReportModalProps = {
    saveReportModalRef: RefObject<ModalRef>;
    onSave: () => void;
};

export const SaveReportModal = ({ saveReportModalRef, onSave }: SaveReportModalProps) => {
    return (
        <ModalComponent
            id={'save-report-modal'}
            className={styles.layout}
            modalRef={saveReportModalRef}
            isLarge
            modalHeading={'Overwrite saved report?'}
            closer={true}
            modalBody={
                <div className={'margin-4'}>
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
                    >
                        Cancel
                    </ModalToggleButton>
                    <ModalToggleButton
                        modalRef={saveReportModalRef}
                        onClick={onSave}
                        closer
                        data-testid="report-save-btn"
                    >
                        Save
                    </ModalToggleButton>
                </ButtonGroup>
            }
        ></ModalComponent>
    );
};
