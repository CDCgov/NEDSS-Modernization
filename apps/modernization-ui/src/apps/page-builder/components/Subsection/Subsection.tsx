import { Counter } from '../Counter/Counter';
import { Button, Icon } from '@trussworks/react-uswds';
import { useState } from 'react';
import './Subsection.scss';
import { Question } from '../Question/Question';
import { Subsection } from 'apps/page-builder/generated/models/Subsection';
import { MoreOptions } from '../MoreOptions/MoreOptions';

export const SubsectionComponent = ({ subsection }: { subsection: Subsection }) => {
    const [open, setOpen] = useState(true);
    return (
        <div className="subsection">
            <div className="subsection__header">
                <div className="subsection__header--left">
                    <h2>{subsection.name}</h2>
                    <Counter count={subsection.pageQuestions.length} />
                </div>
                <div className="subsection__header--right">
                    <Button type="button" outline>
                        Add question
                    </Button>
                    <MoreOptions header={<Icon.MoreVert size={4} />}>
                        <Button type="button" onClick={() => console.log('BLAH')}>
                            <Icon.Edit size={3} /> Edit Subsection
                        </Button>
                        <Button type="button" onClick={() => console.log('BLAH')}>
                            <img src="/group.svg" /> Group Subsection
                        </Button>
                        <Button type="button" onClick={() => console.log('BLAH')}>
                            <Icon.Delete size={3} /> Delete
                        </Button>
                    </MoreOptions>
                    {open ? (
                        <Icon.ExpandLess size={4} onClick={() => setOpen(!open)} />
                    ) : (
                        <Icon.ExpandMore size={4} onClick={() => setOpen(!open)} />
                    )}
                </div>
            </div>
            {open ? (
                <div className="subsection__body">
                    {subsection.pageQuestions.map((question: any, i: number) => {
                        return <Question key={i} question={question} />;
                    })}
                </div>
            ) : null}
        </div>
    );
};
