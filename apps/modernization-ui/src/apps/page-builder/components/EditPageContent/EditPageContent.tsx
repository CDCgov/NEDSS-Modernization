import { SectionComponent } from '../Section/Section';
import './EditPageContent.scss';
import { PageTab } from '../../generated';

export const EditPageContentComponent = ({ content, onAddSection }: { content: PageTab; onAddSection: () => void }) => {
    return (
        <div className="edit-page-content">
            <div className="edit-page-content__sections">
                {content.tabSections?.map((section: any, i: number) => {
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
