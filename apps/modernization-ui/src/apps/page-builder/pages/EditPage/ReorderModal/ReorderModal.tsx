import { ModalRef, ModalFooter, ModalToggleButton, ButtonGroup } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject } from 'react';
import './ReorderModal.scss';
import { PageTab } from 'apps/page-builder/generated/models/PageTab';
import { ReorderSection } from '../ReorderSection/ReorderSection';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { Icon } from 'components/Icon/Icon';

type ReorderProps = {
    modalRef: RefObject<ModalRef>;
    pageName: string;
    content: PageTab;
    alertMessage?: string;
};

export const ReorderModal = ({ modalRef, pageName, content, alertMessage }: ReorderProps) => {
    return (
        <ModalComponent
            modalRef={modalRef}
            modalHeading={
                <div className="reorder-modal__header">
                    <h3>Reorder</h3>
                </div>
            }
            modalBody={
                <>
                    <div className="reorder-modal">
                        {alertMessage ? <AlertBanner type={'prompt'}>{alertMessage}</AlertBanner> : null}
                        <div className="reorder-modal__content">
                            <div className="reorder-modal__top">
                                <Icon name="folder" />
                                <p>{pageName}</p>
                            </div>
                            {content?.tabSections
                                ? content.tabSections.map((section: any, i: number) => {
                                      if (section.visible === 'T') {
                                          return <ReorderSection key={i} section={section} />;
                                      } else {
                                          return;
                                      }
                                  })
                                : null}
                        </div>
                    </div>
                    <ModalFooter className="padding-2 margin-left-auto footer">
                        <ButtonGroup className="flex-justify-end">
                            <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                                Close
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};
