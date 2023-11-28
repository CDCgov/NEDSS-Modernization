import { Button, Icon } from '@trussworks/react-uswds';
import { PagesSubSection } from 'apps/page-builder/generated';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { useState } from 'react';
import { Counter } from '../Counter/Counter';
import { MoreOptions } from '../MoreOptions/MoreOptions';
import { Question } from '../Question/Question';
import './Subsection.scss';

type Props = {
    subsection: PagesSubSection;
    onShowAddQuestion?: () => void;
};
export const SubsectionComponent = ({ subsection, onShowAddQuestion }: Props) => {
    const [open, setOpen] = useState(true);

    return (
        <div className="subsection">
            <div className="subsection__header">
                <div className="subsection__header--left">
                    <h2>{subsection.name}</h2>
                    <Counter count={subsection.questions?.length ?? 0} />
                </div>
                <div className="subsection__header--right">
                    <Button type="button" outline onClick={onShowAddQuestion}>
                        Add question
                    </Button>
                    <MoreOptions header={<Icon.MoreVert size={4} />}>
                        <Button type="button" onClick={() => {}}>
                            <Icon.Edit size={3} /> Edit Subsection
                        </Button>
                        <Button type="button" onClick={() => {}}>
                            <IconComponent name={'group'} size={'s'} /> Group Subsection
                        </Button>
                        <Button type="button" onClick={() => {}}>
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
                    {subsection.questions?.map((question, i) => {
                        return question.display ? <Question key={i} question={question} /> : null;
                    })}
                </div>
            ) : null}
        </div>
    );
};
