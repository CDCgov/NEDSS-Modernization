import { PagesTab } from 'apps/page-builder/generated';
import { PreviewSection } from './section/PreviewSection';

type PreviewTabProps = {
    tab: PagesTab;
};

const PreviewTab = ({ tab }: PreviewTabProps) => {
    return (
        <>
            {tab.sections.map((section, index) => (
                <PreviewSection key={index} section={section} />
            ))}
        </>
    );
};

export { PreviewTab };
