import { Icon } from 'components/Icon/Icon';
import './ReorderSection.scss';
import { useState } from 'react';
import { ReorderSubsection } from '../ReorderSubsection/ReorderSubsection';
import { PagesSection } from 'apps/page-builder/generated';

type Props = {
    section: PagesSection;
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
                {section && section.subSections
                    ? section.subSections.map((subsection, i) => {
                          if (subsection.visible) {
                              return <ReorderSubsection subsection={subsection} key={i} />;
                          }
                      })
                    : null}
            </div>
        </div>
    );
};
