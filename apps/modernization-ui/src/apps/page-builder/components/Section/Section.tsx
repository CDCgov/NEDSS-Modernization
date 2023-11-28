import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Counter } from '../Counter/Counter';
import './Section.scss';
import { useRef, useState } from 'react';
import { PagesSection } from '../../generated';
import { SubsectionComponent as Subsection } from '../Subsection/Subsection';
import AddSectionModal from '../AddSection/AddSectionModal';
import { useParams } from 'react-router-dom';
import { MoreOptions } from '../MoreOptions/MoreOptions';

type Props = {
    section: PagesSection;
    onAddSection: () => void;
    onShowAddQuestion?: (subsection: number) => void;
};
export const SectionComponent = ({ section, onAddSection, onShowAddQuestion }: Props) => {
    const [open, setOpen] = useState(true);
    const { pageId } = useParams();
    const addSectionModalRef = useRef<ModalRef>(null);

    return (
        <>
            <div className="section">
                <div className="section__header">
                    <div className="section__header--left">
                        <h2>{section.name}</h2>
                        <Counter count={section.subSections?.length ?? 0} />
                    </div>
                    <div className="section__header--right">
                        <ModalToggleButton type="button" outline modalRef={addSectionModalRef} opener>
                            Add subsection
                        </ModalToggleButton>
                        <MoreOptions header={<Icon.MoreVert size={4} />}>
                            <Button type="button" onClick={() => console.log('BLAH')}>
                                <Icon.Edit size={3} /> Edit section
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
                    <div className="section__body">
                        {section.subSections?.map((subsection, i) => {
                            if (subsection.visible) {
                                return (
                                    <Subsection
                                        key={i}
                                        onShowAddQuestion={() =>
                                            onShowAddQuestion ? onShowAddQuestion(subsection.id ?? -1) : {}
                                        }
                                        subsection={subsection}
                                    />
                                );
                            } else {
                                return;
                            }
                        })}
                    </div>
                ) : null}
            </div>
            {pageId && section ? (
                <AddSectionModal
                    isSubSection={true}
                    modalRef={addSectionModalRef}
                    pageId={pageId}
                    sectionId={section.id}
                    onAddSection={onAddSection}
                />
            ) : null}
        </>
    );
};
