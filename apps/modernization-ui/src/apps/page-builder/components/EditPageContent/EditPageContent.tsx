import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import './EditPageContent.scss';
import { useRef } from 'react';
import { useParams } from 'react-router-dom';
import AddSectionModal from '../AddSection/AddSectionModal';

type Props = {
    content: string;
};

export const EditPageContent = ({ content }: Props) => {
    const addSubSectionModalRef = useRef<ModalRef>(null);
    const { pageId } = useParams();

    return (
        <div className="edit-page-content">
            {content} Content
            {/** temporary delete when merging  ##########*/}
            <ModalToggleButton opener modalRef={addSubSectionModalRef}>
                add subsection
            </ModalToggleButton>
            {/** ####### */}
            {pageId ? (
                <AddSectionModal
                    modalRef={addSubSectionModalRef}
                    pageId={pageId}
                    isSubSection={true}
                    sectionId={'1101165'}
                />
            ) : null}
        </div>
    );
};
