import { Section } from '../sections';
import { SectionNavigation } from 'design-system/sectionNavigation/SectionNavigation';

export const AddPatientExtendedNav = ({ sections }: { sections: Section[] }) => {
    return (
        <aside>
            <SectionNavigation title="On this page" sections={sections} />
        </aside>
    );
};
