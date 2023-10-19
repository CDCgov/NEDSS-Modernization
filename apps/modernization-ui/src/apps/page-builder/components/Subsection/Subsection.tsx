import { Counter } from '../Counter/Counter';
import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useRef, useState } from 'react';
import './Subsection.scss';
import { Question } from '../Question/Question';
import { PagesSubSection } from 'apps/page-builder/generated';
import { MoreOptions } from '../MoreOptions/MoreOptions';
import { Icon as IconComponent } from 'components/Icon/Icon';
import { useNavigate } from 'react-router-dom';
import GroupSectionModal from '../GroupSection/GroupSectionModal';

export const SubsectionComponent = ({ subsection }: { subsection: PagesSubSection }) => {
    const [open, setOpen] = useState(true);
    const groupSectionModalRef = useRef<ModalRef>(null);
    const navigateTo = useNavigate();
    return (
        <div className="subsection">
            <div className="subsection__header">
                <div className="subsection__header--left">
                    <h2>{subsection.name}</h2>
                    <Counter count={subsection.questions?.length || 0} />
                </div>
                <div className="subsection__header--right">
                    <Button
                        type="button"
                        onClick={() => {
                            navigateTo('/page-builder/add/question');
                        }}
                        outline>
                        Add question
                    </Button>
                    <MoreOptions header={<Icon.MoreVert size={4} />}>
                        <Button type="button" onClick={() => console.log('BLAH')}>
                            <Icon.Edit size={3} /> Edit Subsection
                        </Button>
                        <ModalToggleButton
                            outline
                            modalRef={groupSectionModalRef}
                            opener
                            type="button"
                            onClick={() => console.log('BLAH')}>
                            <IconComponent name={'group'} size={'s'} /> Group Subsection
                        </ModalToggleButton>
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
                    {subsection.questions?.map((question, i) => {
                        return question.display ? <Question key={i} question={question} /> : null;
                    })}
                </div>
            ) : null}
            <GroupSectionModal type="warning" modalRef={groupSectionModalRef} isConfirm={false} />
        </div>
    );
};
