import React, { useContext, useState } from 'react';
import { DragStart, DragUpdate, DraggableLocation, DropResult } from '@hello-pangea/dnd';
import { PagesResponse } from '../generated';
import { reorderObjects } from '../services/reorderObjectsAPI';

type DragDropProps = (source: DraggableLocation, destination: DraggableLocation) => void;

type DragDropContextProps = {
    handleDragEnd: (result: DropResult) => void;
    handleDragStart: (event: DragStart) => void;
    handleDragUpdate: (event: DragUpdate) => void;
    closeId: { id: string; type: string };
    dragTarget: { droppableId: string; index: number; source: number };
};

const DragDropContext = React.createContext<DragDropContextProps | undefined>(undefined);

const DragDropProvider: React.FC<{
    children: React.ReactNode;
    pageData: PagesResponse | undefined;
    successCallBack?: () => void;
}> = ({ children, pageData, successCallBack }) => {
    const [closeId, setCloseId] = useState({ id: '', type: '' });
    const [dragTarget, setDragTarget] = useState({ droppableId: '', index: 999, source: 999 });
    const [moveId, setMoveId] = useState<number>(0);
    let afterId: number;

    const moveSectionWithinSameTab: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const [targetTab] = pageData.tabs.filter((tab) => {
                return tab.id.toString() === destination.droppableId;
            });
            if (destination.index === pageData.tabs.length - 1) {
                afterId = targetTab.sections[pageData.tabs.length - 1].id;
            } else if (destination.index === 0) {
                afterId = targetTab.id;
            } else if (source.index < destination.index) {
                afterId = targetTab.sections[destination.index].id;
            } else {
                afterId = targetTab.sections[destination.index - 1].id;
            }
            targetTab.sections.splice(destination.index, 0, targetTab.sections.splice(source.index, 1)[0]);
        }
    };

    const moveSectionDifferentTab: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const [destinationTab] = pageData.tabs.filter((page) => {
                return page.id.toString() === destination.droppableId;
            });
            const [sourceTab] = pageData.tabs.filter((page) => {
                return page.id.toString() === source.droppableId;
            });
            if (destinationTab.sections[destination.index - 1]) {
                afterId = destinationTab.sections[destination.index - 1].id;
            } else {
                afterId = destinationTab.id;
            }
            destinationTab.sections.splice(destination.index, 0, sourceTab.sections.splice(source.index, 1)[0]);
        }
    };

    const moveSubsectionWithinSameSection: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const [targetTab] = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.id.toString() === destination.droppableId;
                });
            });
            const [findId] = targetTab.sections.filter((section) => {
                return section.id.toString() === destination.droppableId;
            });
            if (destination.index === findId.subSections.length - 1) {
                afterId = findId.subSections[findId.subSections.length - 1].id;
            } else if (destination.index === 0) {
                afterId = Number(destination.droppableId);
            } else if (source.index < destination.index) {
                afterId = findId.subSections[destination.index].id;
            } else {
                afterId = findId.subSections[destination.index - 1].id;
            }
            findId.subSections.splice(destination.index, 0, findId.subSections.splice(source.index, 1)[0]);
        }
    };

    const moveSubsectionDifferentSection: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const [sourceTab] = pageData.tabs.filter((tab) =>
                tab.sections.some((section) => {
                    if (section.id.toString() === source.droppableId.toString()) {
                        return section;
                    }
                })
            );
            const [destinationTab] = pageData.tabs.filter((tab) =>
                tab.sections.some((section) => {
                    if (section.id.toString() === destination.droppableId.toString()) {
                        return section;
                    }
                })
            );
            const sourceSection = sourceTab.sections.filter((section) => {
                if (section.id.toString() === source.droppableId.toString()) {
                    return section;
                }
            });
            const destinationSection = destinationTab.sections.filter((section) => {
                if (section.id.toString() === destination.droppableId.toString()) {
                    afterId = section.id;
                    return section;
                }
            });
            const sourceOrder =
                sourceSection.find((section) => section.id.toString() === source.droppableId)?.subSections || [];
            const destinationOrder =
                destinationSection.find((section) => section.id.toString() === destination.droppableId)?.subSections ||
                [];
            destinationOrder.splice(destination.index, 0, sourceOrder.splice(source.index, 1)[0]);
            if (destinationOrder[destination.index - 1]) {
                afterId = destinationOrder[destination.index - 1].id;
            } else {
                afterId = destinationSection[0].id;
            }
        }
    };

    // handling movement of question in the same subsection
    const moveQuestionWithinSameSubsection: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            // Drill down to Subsection
            const [isolatedTab] = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id.toString() === source.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const [isolatedSection] = isolatedTab.sections.filter((section) => {
                return section.subSections.some((subSection) => {
                    if (subSection.id.toString() === source.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const [isolatedSubsection] = isolatedSection.subSections.filter((subsection) => {
                if (subsection.id.toString() === source.droppableId.toString()) {
                    return subsection;
                }
            });

            if (destination.index === isolatedSubsection.questions.length - 1) {
                afterId = isolatedSubsection.questions[isolatedSubsection.questions.length - 1].id;
            } else if (destination.index === 0) {
                afterId = isolatedSubsection.id;
            } else if (source.index < destination.index) {
                afterId = isolatedSubsection.questions[destination.index].id;
            } else {
                afterId = isolatedSubsection.questions[destination.index - 1].id;
            }
            isolatedSubsection.questions.splice(
                destination.index,
                0,
                isolatedSubsection.questions.splice(source.index, 1)[0]
            );
        }
    };

    const moveQuestionDifferentSubsection: DragDropProps = (source, destination) => {
        // Drill down source + destination
        if (pageData?.tabs) {
            const sourceTab = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id.toString() === source.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const sourceSection = sourceTab[0].sections.filter((section) => {
                return section.subSections.some((subSection) => {
                    if (subSection.id.toString() === source.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const sourceSubsection = sourceSection[0].subSections.filter((subsection) => {
                if (subsection.id.toString() === source.droppableId.toString()) {
                    return subsection;
                }
            });

            const destinationTab = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id.toString() === destination.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const destinationSection = destinationTab[0].sections.filter((section) => {
                return section.subSections.some((subSection) => {
                    if (subSection.id.toString() === destination.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const destinationSubsection = destinationSection[0].subSections.filter((subsection) => {
                if (subsection.id.toString() === destination.droppableId.toString()) {
                    return subsection;
                }
            });

            const sourceOrder =
                sourceSubsection.find((subsection) => subsection.id.toString() === source.droppableId)?.questions || [];
            const destinationOrder =
                destinationSubsection.find((subsection) => subsection.id.toString() === destination.droppableId)
                    ?.questions || [];
            destinationOrder.splice(destination.index, 0, sourceOrder.splice(source.index, 1)[0]);

            if (destination.index === destinationSubsection[0].questions.length - 1) {
                afterId = destinationSubsection[0].questions[destinationSubsection[0].questions.length - 1].id;
            } else if (destination.index === 0) {
                afterId = Number(destination.droppableId);
            } else if (source.index < destination.index) {
                afterId = destinationSubsection[0].questions[destination.index].id;
            } else {
                afterId = destinationSubsection[0].questions[destination.index - 1].id;
            }
        }
    };

    // determining if its diff section or same section for subsection movement
    const handleSubsectionMove: DragDropProps = (source, destination) => {
        if (source.droppableId !== destination.droppableId) {
            moveSubsectionDifferentSection(source, destination);
        } else {
            moveSubsectionWithinSameSection(source, destination);
        }
    };

    const handleQuestionMove: DragDropProps = (source, destination) => {
        if (source.droppableId !== destination.droppableId) {
            moveQuestionDifferentSubsection(source, destination);
        } else {
            moveQuestionWithinSameSubsection(source, destination);
        }
    };

    // move sections
    const handleSectionMove: DragDropProps = (source, destination) => {
        if (source.droppableId !== destination.droppableId) {
            moveSectionDifferentTab(source, destination);
        } else {
            moveSectionWithinSameTab(source, destination);
        }
    };

    const handleTabMove: DragDropProps = (source, destination) => {
        if (pageData?.tabs && pageData.root) {
            if (destination.index === pageData.tabs.length - 1) {
                afterId = pageData.tabs[pageData.tabs.length - 1].id;
            } else if (destination.index === 0) {
                afterId = pageData.root;
            } else if (source.index < destination.index) {
                afterId = pageData.tabs[destination.index].id;
            } else {
                afterId = pageData.tabs[destination.index - 1].id;
            }
            pageData.tabs.splice(destination.index, 0, pageData.tabs.splice(source.index, 1)[0]);
        }
    };

    const handleDragUpdate = (event: DragUpdate) => {
        const { destination } = event;
        if (!destination) return;
        setDragTarget({ droppableId: destination.droppableId, index: destination.index, source: destination.index });
    };

    const handleDragStart = (event: DragStart) => {
        setCloseId({ id: event.draggableId, type: event.type });
        setMoveId(Number(event.draggableId));
    };

    const handleDragEnd = (result: DropResult) => {
        setCloseId({ id: '', type: '' });
        setDragTarget({ droppableId: '', index: 999, source: 999 });
        if (!result.destination) return;
        const { source, destination, type } = result;
        if (type === 'tab') {
            handleTabMove(source, destination);
        } else if (type === 'section') {
            handleSectionMove(source, destination);
        } else if (type === 'subsection') {
            handleSubsectionMove(source, destination);
        } else if (type === 'question') {
            handleQuestionMove(source, destination);
        }
        if (pageData && successCallBack) {
            reorderObjects(afterId, moveId, pageData.id).then(() => {
                successCallBack();
            });
        }
    };

    return (
        <DragDropContext.Provider
            value={{
                handleDragEnd,
                handleDragStart,
                handleDragUpdate,
                closeId,
                dragTarget
            }}>
            {children}
        </DragDropContext.Provider>
    );
};

export function useDragDrop() {
    const context = useContext(DragDropContext);
    if (context === undefined) {
        throw new Error('useDragDrop must be used inside DragDropProvider');
    }

    return context;
}

export default DragDropProvider;
