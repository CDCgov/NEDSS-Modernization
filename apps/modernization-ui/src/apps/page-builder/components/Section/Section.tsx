import { Button, Icon } from '@trussworks/react-uswds';
import { Counter } from '../Counter/Counter';
import './Section.scss';
import { useState } from 'react';
import { Subsection as SubsectionProps } from 'apps/page-builder/generated/models/Subsection';
import { SubsectionComponent as Subsection } from '../Subsection/Subsection';
import { Section as SectionProps } from 'apps/page-builder/generated/models/Section';

export const SectionComponent = ({ section }: { section: SectionProps }) => {
    const [open, setOpen] = useState(true);

    return (
        <div className="section">
            <div className="section__header">
                <div className="section__header--left">
                    <h2>{section.name}</h2>
                    <Counter count={section.sectionSubSections.length} />
                </div>
                <div className="section__header--right">
                    <Button type="button" outline>
                        Add subsection
                    </Button>
                    <Icon.MoreVert size={4} />
                    {open ? (
                        <Icon.ExpandLess size={4} onClick={() => setOpen(!open)} />
                    ) : (
                        <Icon.ExpandMore size={4} onClick={() => setOpen(!open)} />
                    )}
                </div>
            </div>
            {open ? (
                <div className="section__body">
                    {section.sectionSubSections.map((subsection: SubsectionProps, i: number) => {
                        return <Subsection key={i} subsection={subsection} />;
                    })}
                </div>
            ) : null}
        </div>
    );
};
