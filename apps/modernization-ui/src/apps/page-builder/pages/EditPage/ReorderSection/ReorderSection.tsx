import { Icon } from 'components/Icon/Icon';
import './ReorderSection.scss';
import { PageSection } from 'apps/page-builder/generated/models/PageSection';
import { useState } from 'react';
import { ReorderSubsection } from '../ReorderSubsection/ReorderSubsection';

type Props = {
    section: PageSection;
};

export const ReorderSection = ({ section }: Props) => {
    const [subsectionOpen, setSubsectionOpen] = useState(true);

    return (
        <div className="reorder-section">
            <div className="reorder-section__tile">
                <div className="reorder-section__toggle" onClick={() => setSubsectionOpen(!subsectionOpen)}>
                    {subsectionOpen ? (
                        <Icon name={'expand-more'} size={'xs'} />
                    ) : (
                        <Icon name={'navigate-next'} size={'xs'} />
                    )}
                </div>
                <Icon name={'drag'} size={'m'} />
                <Icon name={'group'} size={'m'} />
                <p>{section.name}</p>
            </div>
            <div className={`reorder-section__subsections ${subsectionOpen ? '' : 'closed'}`}>
                {section && section.sectionSubSections
                    ? section.sectionSubSections.map((subsection: any, i: number) => {
                          if (subsection.visible === 'T') {
                              return <ReorderSubsection subsection={subsection} key={i} />;
                          }
                      })
                    : null}
            </div>
        </div>
    );
};
