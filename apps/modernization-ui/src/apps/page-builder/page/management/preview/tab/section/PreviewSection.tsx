import { PagesSection } from 'apps/page-builder/generated';
import { Heading } from 'components/heading';

import styles from './preview-section.module.scss';

type PreviewSectionType = {
    section: PagesSection;
};

const PreviewSection = ({ section }: PreviewSectionType) => {
    return (
        <section className={styles.section}>
            <header>
                <Heading level={2}>{section.name}</Heading>
                <p>{section.subSections.length} sub sections</p>
            </header>
        </section>
    );
};

export { PreviewSection };
