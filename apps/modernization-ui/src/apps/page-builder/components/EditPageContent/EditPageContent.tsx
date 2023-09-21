import { SectionComponent } from '../Section/Section';
import './EditPageContent.scss';
import { EditPageContent } from 'apps/page-builder/generated/models/EditPageContent';

export const EditPageContentComponent = ({
    content,
    onAddSection
}: {
    content: EditPageContent;
    onAddSection: () => void;
}) => {
    return (
        <div className="edit-page-content">
            <div className="edit-page-content__sections">
                {content.tabSections.map((section: any, i: number) => {
                    if (section.visible === 'T') {
                        return <SectionComponent key={i} section={section} onAddSection={onAddSection} />;
                    } else {
                        return;
                    }
                })}
            </div>
        </div>
    );
};
