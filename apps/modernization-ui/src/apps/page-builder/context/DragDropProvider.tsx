import React, { useContext, useEffect, useState } from 'react';
import { DragStart, DragUpdate, DraggableLocation, DropResult } from 'react-beautiful-dnd';
import { PagesResponse, PagesSection, PagesTab } from '../generated';
import { moveSubsectionInArray, moveQuestionInArray, moveTabInArray } from '../helpers/moveObjectInArray';
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
            }
            if (pageData.tabs![currentTab]) {
                setSections(pageData.tabs![currentTab].sections!);
            }
        }
    }, [pageData, currentTab]);

    // handling movement of subsection in the same section
    const moveSubsectionWithinSection: DragDropProps = (source, destination) => {
        const updatedOrder = moveSubsectionInArray(
            sections.find((section) => section.id!.toString() === source.droppableId)!.subSections!,
            source.index,
            destination.index
        );
        const updatedSections = sections.map((section) =>
            section.id!.toString() !== source.droppableId ? section : { ...section, subSections: updatedOrder }
        );
        const findId = sections.filter((section) => {
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
        setSections(updatedSections);
    };

    // handling movement of subsection between sections
    const moveSubsectionDifferentSection: DragDropProps = (source, destination) => {
        const sourceOrder = sections.find((section) => section.id.toString() === source.droppableId)?.subSections || [];
        const destinationOrder =
            sections.find((section) => section.id.toString() === destination.droppableId)?.subSections || [];
        const [removed] = sourceOrder!.splice(source.index, 1);
        destinationOrder!.splice(destination.index, 0, removed);

        const updatedSections = sections.map((section) =>
            section.id.toString() === source.droppableId
                ? { ...section, subSections: sourceOrder }
                : section.id.toString() === destination.droppableId
                ? { ...section, subSections: destinationOrder }
                : section
        );
        const findId = sections.filter((section) => {
            return section.id.toString() === destination.droppableId;
        });
        if (findId[0].subSections![destination.index - 1]) {
            afterId = findId[0].subSections![destination.index - 1].id!;
        } else {
            afterId = Number(destination.droppableId);
        }
        setSections(updatedSections);
    };

    // handling movement of question in the same subsection
    const moveQuestionWithinSubsection: DragDropProps = (source, destination) => {
        const isolatedSection = sections.filter((section) =>
            section.subSections!.some((subSections) => {
                if (subSections.id!.toString() === source.droppableId.toString()) {
                    return subSections;
                }
            })
        );
        // isolate subSection
        const [isolatedSubsection] = isolatedSection.map((section) =>
            section.subSections?.filter((subsection) => {
                if (subsection.id!.toString() === source.droppableId.toString()) {
                    return subsection;
                }
            })
        );
        if (destination.index === isolatedSubsection![0].questions!.length - 1) {
            afterId = isolatedSubsection![0].questions![isolatedSubsection![0].questions!.length - 1].id!;
        } else if (destination.index === 0) {
            afterId = isolatedSubsection![0].id!;
        } else if (source.index < destination.index) {
            afterId = isolatedSubsection![0].questions![destination.index].id!;
        } else {
            afterId = isolatedSubsection![0].questions![destination.index - 1].id!;
        }
        const updatedOrder = moveQuestionInArray(isolatedSubsection![0]!.questions!, source.index, destination.index);

        const updatedSections = sections.map((section) => {
            return {
                ...section,
                subSections: section.subSections!.map((subsection) =>
                    subsection.id!.toString() !== source.droppableId
                        ? subsection
                        : { ...subsection, questions: updatedOrder }
                )
            };
        });
        setSections(updatedSections);
    };

    // handling movement of question between subsections
    const moveQuestionDifferentSubsection: DragDropProps = (source, destination) => {
        // moving subsections between sections
        const [sourceSection] = sections.filter((section) =>
            section.subSections!.some((sectionsubsection) => {
                if (sectionsubsection.id!.toString() === source.droppableId.toString()) {
                    return sectionsubsection;
                }
            })
        );
        const [destinationSection] = sections.filter((section) =>
            section.subSections!.some((sectionsubsection) => {
                if (sectionsubsection.id!.toString() === destination.droppableId.toString()) {
                    return sectionsubsection;
                }
            })
        );

        // extract the source/destination subsections from the section
        const sourceSubsection = sourceSection.subSections!.filter((subsection) => {
            if (subsection.id!.toString() === source.droppableId.toString()) {
                return subsection;
            }
        });
        const destinationSubsection = destinationSection.subSections!.filter((subsection) => {
            if (subsection.id!.toString() === destination.droppableId.toString()) {
                afterId = subsection.id!;
                return subsection;
            }
        });

        const sourceOrder =
            sourceSubsection.find((subsection) => subsection.id!.toString() === source.droppableId)?.questions || [];
        const destinationOrder =
            destinationSubsection.find((subsection) => subsection.id!.toString() === destination.droppableId)
                ?.questions || [];
        const [removed] = sourceOrder!.splice(source.index, 1);
        destinationOrder!.splice(destination.index, 0, removed);
        if (destinationOrder![destination.index - 1]) {
            afterId = destinationOrder![destination.index - 1].id!;
        }

        const updatedSections = sections.map((section) => {
            return {
                ...section,
                subSections: section.subSections!.map((subsection) =>
                    subsection.id!.toString() === source.droppableId
                        ? { ...subsection, questions: sourceOrder }
                        : subsection.id!.toString() === destination.droppableId
                        ? { ...subsection, questions: destinationOrder }
                        : subsection
                )
            };
        });
        setSections(updatedSections);
    };

    // determining if its diff section or same section for subsection movement
    const handleSubsectionMove: DragDropProps = (source, destination) => {
        if (source.droppableId !== destination.droppableId) {
            moveSubsectionDifferentSection(source, destination);
        } else {
            moveSubsectionWithinSection(source, destination);
        }
    };

    const handleQuestionMove: DragDropProps = (source, destination) => {
        if (source.droppableId !== destination.droppableId) {
            moveQuestionDifferentSubsection(source, destination);
        } else {
            moveQuestionWithinSubsection(source, destination);
        }
    };

    // move sections
    const handleSectionMove: DragDropProps = (source, destination) => {
        const reorderedSections = [...sections];
        const [removed] = reorderedSections.splice(source.index, 1);
        reorderedSections.splice(destination.index, 0, removed);
        if (destination.index === sections.length - 1) {
            afterId = sections[sections.length - 1].id!;
        } else if (destination.index === 0) {
            afterId = pageData!.tabs![currentTab].id!;
        } else if (source.index < destination.index) {
            afterId = sections[destination.index].id!;
        } else {
            afterId = sections[destination.index - 1].id!;
        }
        setSections(reorderedSections);
    };

    const handleTabMove: DragDropProps = (source, destination) => {
        const reorderedTabs = moveTabInArray(tabs, source.index, destination.index);
        if (destination.index === tabs.length - 1) {
            afterId = tabs[tabs.length - 1].id!;
        } else if (destination.index === 0) {
            afterId = pageData!.root!;
        } else if (source.index < destination.index) {
            afterId = tabs[destination.index].id!;
        } else {
            afterId = tabs[destination.index - 1].id!;
        }
        setTabs(reorderedTabs);
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
        if (source.droppableId === 'all-sections') {
            handleSectionMove(source, destination);
        } else if (type === 'question') {
            handleQuestionMove(source, destination);
        } else if (type === 'subsection') {
            handleSubsectionMove(source, destination);
        } else {
            handleTabMove(source, destination);
        }
        setTimeout(() => reorderObjects(token, afterId, moveId, pageData!.id), 100);
        setTimeout(() => successCallBack!(), 500);
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
