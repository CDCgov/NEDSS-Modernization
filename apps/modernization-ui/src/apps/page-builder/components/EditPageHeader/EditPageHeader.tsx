import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import './EditPageHeader.scss';
import { PageDetails } from 'apps/page-builder/generated/models/PageDetails';
import { useRef, useState } from 'react';
import { ModalComponent } from '../../../../components/ModalComponent/ModalComponent';
import { SaveTemplates } from '../SaveTemplate/SaveTemplate';

type PageProps = {
    page: PageDetails;
    handleSaveDraft: () => void;
};

export const EditPageHeader = ({ page, handleSaveDraft }: PageProps) => {
    const [isSaveTemplate, setIsSaveTemplate] = useState(false);
    const modalRef = useRef<ModalRef>(null);
    return (
        <div className="edit-page-header">
            <div className="edit-page-header__left">
                <h2>{page.Name}</h2>
                <h4>{page.pageDescription}</h4>
            </div>
            <div className="edit-page-header__right">
                {isSaveTemplate ? (
                    <>
                        <Button type="button" outline>
                            Business Rules
                        </Button>
                        <ModalToggleButton className="" outline type="button" modalRef={modalRef}>
                            Save as Template
                        </ModalToggleButton>
                        <Button type="button" outline>
                            Delete draft
                        </Button>
                    </>
                ) : (
                    <Button type="button" outline onClick={() => handleSaveDraft()}>
                        Save draft
                    </Button>
                )}
                <Button type="button" outline>
                    {isSaveTemplate ? 'Edit' : 'Cancel'}
                </Button>
                <Button type="button" onClick={() => setIsSaveTemplate(!isSaveTemplate)}>
                    {isSaveTemplate ? 'Publish' : 'Submit'}
                </Button>
            </div>
            <ModalComponent
                modalRef={modalRef}
                modalHeading={'Save as Template'}
                modalBody={<SaveTemplates modalRef={modalRef} />}
            />
        </div>
    );
};
