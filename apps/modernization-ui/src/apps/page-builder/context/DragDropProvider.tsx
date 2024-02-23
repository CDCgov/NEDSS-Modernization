import React, { useContext, useEffect, useState } from 'react';
import { DragStart, DragUpdate, DraggableLocation, DropResult } from 'react-beautiful-dnd';
import { PagesResponse, PagesSection, PagesTab } from '../generated';
// import { moveQuestionInArray } from '../helpers/moveObjectInArray';
import { reorderObjects } from '../services/reorderObjectsAPI';
import { UserContext } from 'user';

type DragDropProps = (source: DraggableLocation, destination: DraggableLocation) => void;

type DragDropContextProps = {
    handleDragEnd: (result: DropResult) => void;
    handleDragStart: (event: DragStart) => void;
    handleDragUpdate: (event: DragUpdate) => void;
    tabs: PagesTab[];
    sections: PagesSection[];
    setSections: React.Dispatch<React.SetStateAction<PagesSection[]>>;
    closeId: { id: string; type: string };
    dragTarget: { droppableId: string; index: number; source: number };
};

const DragDropContext = React.createContext<DragDropContextProps | undefined>(undefined);

const DragDropProvider: React.FC<{
    children: React.ReactNode;
    pageData: PagesResponse | undefined;
    currentTab: number;
    successCallBack?: () => void;
}> = ({ children, pageData, currentTab, successCallBack }) => {
    const [sections, setSections] = useState<PagesSection[]>([]);
    const [tabs, setTabs] = useState<PagesTab[]>([]);
    const [closeId, setCloseId] = useState({ id: '', type: '' });
    const [dragTarget, setDragTarget] = useState({ droppableId: '', index: 999, source: 999 });
    const [moveId, setMoveId] = useState<number>(0);
    let afterId: number;
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;

    useEffect(() => {
        if (pageData) {
            if (pageData.tabs) {
                setTabs(pageData.tabs);
                if (pageData.tabs[currentTab]) {
                    setSections(pageData.tabs![currentTab].sections!);
                }
            }
        }
    }, [pageData, currentTab]);

    const moveSectionWithinSameTab: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const targetTab = pageData.tabs.filter((tab) => {
                return tab.id.toString() === destination.droppableId;
            });
            if (destination.index === tabs.length - 1) {
                afterId = targetTab[0].sections[tabs.length - 1].id!;
            } else if (destination.index === 0) {
                afterId = targetTab[0].id!;
            } else if (source.index < destination.index) {
                afterId = targetTab[0].sections[destination.index].id!;
            } else {
                afterId = targetTab[0].sections[destination.index - 1].id!;
            }
            targetTab[0].sections.splice(destination.index, 0, targetTab[0].sections.splice(source.index, 1)[0]);
        }
    };

    const moveSectionDifferentTab: DragDropProps = (source, destination) => {
        const destinationTab = pageData?.tabs?.filter((page) => {
            return page.id.toString() === destination.droppableId;
        });
        const originTab = pageData?.tabs?.filter((page) => {
            return page.id.toString() === source.droppableId;
        });
        if (destinationTab && originTab) {
            if (destination.index === destinationTab[0].sections?.length! - 1) {
                afterId = destinationTab[0].sections![destinationTab[0].sections?.length! - 1].id!;
            } else if (destination.index === 0) {
                afterId = Number(destination.droppableId);
            } else if (source.index < destination.index) {
                afterId = destinationTab[0].sections![destination.index].id!;
            } else {
                afterId = destinationTab[0].sections![destination.index - 1].id!;
            }
            destinationTab[0].sections.splice(destination.index, 0, originTab[0].sections.splice(source.index, 1)[0]);
        }
    };

    // handling movement of subsection in the same section
    const moveSubsectionWithinSameSection: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            const targetTab = pageData?.tabs?.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.id.toString() === destination.droppableId;
                });
            });
            const findId = targetTab[0].sections.filter((section) => {
                return section.id!.toString() === destination.droppableId;
            });
            if (destination.index === findId[0].subSections?.length! - 1) {
                afterId = findId[0].subSections![findId[0].subSections?.length! - 1].id!;
            } else if (destination.index === 0) {
                afterId = Number(destination.droppableId);
            } else if (source.index < destination.index) {
                afterId = findId[0].subSections![destination.index].id!;
            } else {
                afterId = findId[0].subSections![destination.index - 1].id!;
            }
            findId[0].subSections.splice(destination.index, 0, findId[0].subSections.splice(source.index, 1)[0]);
        }
    };

    // handling movement of subsection between sections
    const moveSubsectionDifferentSection: DragDropProps = (source, destination) => {
        // moving subsections between sections
        if (pageData?.tabs) {
            const [sourceTab] = pageData.tabs.filter((tab) =>
                tab.sections.some((section) => {
                    if (section.id!.toString() === source.droppableId.toString()) {
                        return section;
                    }
                })
            );
            const [destinationTab] = pageData.tabs.filter((tab) =>
                tab.sections.some((section) => {
                    if (section.id!.toString() === destination.droppableId.toString()) {
                        return section;
                    }
                })
            );
            // extract the source/destination subsections from the section
            const sourceSection = sourceTab.sections!.filter((section) => {
                if (section.id!.toString() === source.droppableId.toString()) {
                    return section;
                }
            });
            const destinationSection = destinationTab.sections!.filter((section) => {
                if (section.id!.toString() === destination.droppableId.toString()) {
                    afterId = section.id!;
                    return section;
                }
            });
            const sourceOrder =
                sourceSection.find((section) => section.id!.toString() === source.droppableId)?.subSections || [];
            const destinationOrder =
                destinationSection.find((section) => section.id!.toString() === destination.droppableId)?.subSections ||
                [];
            const [removed] = sourceOrder!.splice(source.index, 1);
            destinationOrder!.splice(destination.index, 0, removed);
            if (destinationOrder![destination.index - 1]) {
                afterId = destinationOrder![destination.index - 1].id!;
            }
        }
    };

    // handling movement of question in the same subsection
    const moveQuestionWithinSameSubsection: DragDropProps = (source, destination) => {
        if (pageData?.tabs) {
            // Drill down to Subsection
            const isolatedTab = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id!.toString() === source.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const isolatedSection = isolatedTab[0].sections.filter((section) => {
                return section.subSections!.some((subSection) => {
                    if (subSection.id!.toString() === source.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const isolatedSubsection = isolatedSection[0].subSections.filter((subsection) => {
                if (subsection.id!.toString() === source.droppableId.toString()) {
                    return subsection;
                }
            });

            if (destination.index === isolatedSubsection![0].questions!.length - 1) {
                afterId = isolatedSubsection![0].questions![isolatedSubsection![0].questions!.length - 1].id!;
            } else if (destination.index === 0) {
                afterId = isolatedSubsection![0].id!;
            } else if (source.index < destination.index) {
                afterId = isolatedSubsection![0].questions![destination.index].id!;
            } else {
                afterId = isolatedSubsection![0].questions![destination.index - 1].id!;
            }
            isolatedSubsection[0].questions.splice(
                destination.index,
                0,
                isolatedSubsection[0].questions.splice(source.index, 1)[0]
            );
        }
    };

    // handling movement of question between subsections
    const moveQuestionDifferentSubsection: DragDropProps = (source, destination) => {
        // Drill down source + destination
        if (pageData?.tabs) {
            const sourceTab = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id!.toString() === source.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const sourceSection = sourceTab[0].sections.filter((section) => {
                return section.subSections!.some((subSection) => {
                    if (subSection.id!.toString() === source.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const sourceSubsection = sourceSection[0].subSections.filter((subsection) => {
                if (subsection.id!.toString() === source.droppableId.toString()) {
                    return subsection;
                }
            });

            const destinationTab = pageData.tabs.filter((tab) => {
                return tab.sections.some((section) => {
                    return section.subSections.some((subSections) => {
                        if (subSections.id!.toString() === destination.droppableId.toString()) {
                            return subSections;
                        }
                    });
                });
            });
            const destinationSection = destinationTab[0].sections.filter((section) => {
                return section.subSections!.some((subSection) => {
                    if (subSection.id!.toString() === destination.droppableId.toString()) {
                        return subSection;
                    }
                });
            });
            const destinationSubsection = destinationSection[0].subSections.filter((subsection) => {
                if (subsection.id!.toString() === destination.droppableId.toString()) {
                    return subsection;
                }
            });

            const sourceOrder =
                sourceSubsection.find((subsection) => subsection.id!.toString() === source.droppableId)?.questions ||
                [];
            const destinationOrder =
                destinationSubsection.find((subsection) => subsection.id!.toString() === destination.droppableId)
                    ?.questions || [];
            const [removed] = sourceOrder!.splice(source.index, 1);
            destinationOrder!.splice(destination.index, 0, removed);

            if (destination.index === destinationSubsection[0].questions.length! - 1) {
                afterId = destinationSubsection[0].questions[destinationSubsection[0].questions.length - 1].id!;
            } else if (destination.index === 0) {
                afterId = Number(destination.droppableId);
            } else if (source.index < destination.index) {
                afterId = destinationSubsection[0].questions[destination.index].id!;
            } else {
                afterId = destinationSubsection[0].questions[destination.index - 1].id!;
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
        if (destination.index === tabs.length - 1) {
            afterId = tabs[tabs.length - 1].id!;
        } else if (destination.index === 0) {
            afterId = pageData!.root!;
        } else if (source.index < destination.index) {
            afterId = tabs[destination.index].id!;
        } else {
            afterId = tabs[destination.index - 1].id!;
        }
        tabs.splice(destination.index, 0, tabs.splice(source.index, 1)[0]);
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
        reorderObjects(token, afterId, moveId, pageData!.id).then(() => {
            successCallBack!();
        });
    };

    return (
        <DragDropContext.Provider
            value={{
                handleDragEnd,
                handleDragStart,
                handleDragUpdate,
                tabs,
                sections,
                setSections,
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
