import { ModalRef, ModalFooter, ModalToggleButton, ButtonGroup } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RefObject } from 'react';
import './ReorderModal.scss';
import { PagesTab } from 'apps/page-builder/generated';
import { ReorderSection } from '../ReorderSection/ReorderSection';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { Icon } from 'components/Icon/Icon';

type ReorderProps = {
    modalRef: RefObject<ModalRef>;
    pageName: string;
    content: PagesTab;
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
                            {content?.sections
                                ? content.sections.map((section, i) => {
                                      if (section.visible) {
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
