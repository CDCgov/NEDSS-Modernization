import { PagesTab } from 'apps/page-builder/generated';
import { SectionComponent } from '../../../components/Section/Section';
import './EditPageContent.scss';

export const EditPageContentComponent = ({
    content,
    onAddSection
}: {
    content: PagesTab;
    onAddSection: () => void;
}) => {
    return (
        <div className="edit-page-content">
            <div className="edit-page-content__sections">
                {content.sections?.map((section, i) => {
                    if (section.visible) {
                        return <SectionComponent key={i} section={section} onAddSection={onAddSection} />;
                    } else {
                        return;
                    }
                })}
            </div>
        </div>
    );
};
