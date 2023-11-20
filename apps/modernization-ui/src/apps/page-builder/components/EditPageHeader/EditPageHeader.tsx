import { useRef, useState } from 'react';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { PagesResponse } from 'apps/page-builder/generated';
import { SaveTemplates } from 'apps/page-builder/components/SaveTemplate/SaveTemplate';
import './EditPageHeader.scss';
import { ClassicButton } from 'classic';

type PageProps = {
    page: PagesResponse;
    handleSaveDraft: () => void;
};

export const EditPageHeader = ({ page, handleSaveDraft }: PageProps) => {
    const [isSaveTemplate, setIsSaveTemplate] = useState(false);
    const modalRef = useRef<ModalRef>(null);

    return (
        <div className="edit-page-header">
            <div className="edit-page-header__left">
                <h2>{page.name}</h2>
                <h4>{page.description}</h4>
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
                    <Button type="button" outline onClick={handleSaveDraft}>
                        Save draft
                    </Button>
                )}
                <Button type="button" outline>
                    {isSaveTemplate ? 'Edit' : 'Cancel'}
                </Button>
                <ClassicButton outline url={`/nbs/page-builder/api/v1/pages/${page.id}/print`}>
                    <Icon.Print />
                </ClassicButton>
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
