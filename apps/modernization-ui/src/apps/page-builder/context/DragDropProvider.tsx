import React, { useContext, useEffect, useState } from 'react';
import { DragStart, DragUpdate, DraggableLocation, DropResult } from 'react-beautiful-dnd';
import { PagesSection, PagesTab } from '../generated';
import { moveSubsectionInArray, moveQuestionInArray } from '../helpers/moveObjectInArray';

type DragDropProps = (source: DraggableLocation, destination: DraggableLocation) => void;

// handle the manipulation of placeholder for subsection
type SubsectionDropshadowProps = (event: any, destinationIndex: number, sourceIndex: number) => void;

// handle the manipulation of placeholder for section
type SectionDropshadowProps = (event: any, destinationIndex: number, sourceIndex: number) => void;

type SubsectionDropshadow = { marginTop: number; height: number };
type SectionDropshadow = { marginLeft: number; height: number };

type DragDropContextProps = {
    handleDragEnd: (result: DropResult) => void;
    handleDragStart: (event: DragStart) => void;
    handleDragUpdate: (event: DragUpdate) => void;
    subsectionDropshadowProps: SubsectionDropshadow;
    sectionDropshadowProps: SectionDropshadow;
    sections: PagesSection[];
    setSections: React.Dispatch<React.SetStateAction<PagesSection[]>>;
    closeId: string;
};

const DragDropContext = React.createContext<DragDropContextProps | undefined>(undefined);

// grabbing element currently being dragged from the dom
const getDraggedElement = (draggableId: string) => {
    const queryAttr = 'data-rbd-drag-handle-draggable-id';
    const domQuery = `[${queryAttr}='${draggableId}']`;
    const draggedElement = document.querySelector(domQuery);
    return draggedElement;
};

// updating the array of the placeholder by switching out the source and destination section Index
const getUpdatedChildrenArray = (draggedElement: Element, destinationIndex: number, sourceIndex: number) => {
    // grab children of the node
    const child: Element[] = [...Array.from(draggedElement!.parentNode!.children)];

    // if the indexes are the same (onDragStart) just return the dom array
    if (destinationIndex === sourceIndex) return child;
    // get the div of item being dragged
    const draggedItem = child[sourceIndex];

    // remove source
    child.splice(sourceIndex, 1);

    // return updated array by inputting dragged item
    return child.splice(0, destinationIndex, draggedItem);
};

// isolate the number of style desired to pass as props
const getStyle = (
    updatedChildrenArray: Element[],
    destinationIndex: number,
    property: string,
    clientDirection: 'clientHeight' | 'clientWidth'
) =>
    updatedChildrenArray.slice(0, destinationIndex).reduce((total, curr) => {
        // get the style object of the item
        const style = window.getComputedStyle(curr);
        // isolate the # of the property desired
        const prop = parseFloat(style.getPropertyValue(property));
        return total + curr[clientDirection] + prop;
    }, 0);

const DragDropProvider: React.FC<{ children: React.ReactNode; data: PagesTab | undefined }> = ({ children, data }) => {
    const [sections, setSections] = useState<PagesSection[]>([]);
    const [sectionDropshadowProps, setSectionDropshadowProps] = useState<SectionDropshadow>({
        marginLeft: 0,
        height: 0
    });
    const [subsectionDropshadowProps, setSubsectionDropshadowProps] = useState<SubsectionDropshadow>({
        marginTop: 0,
        height: 0
    });
    const [closeId, setCloseId] = useState('');

    useEffect(() => {
        if (data && data.sections) setSections(data.sections);
    }, [data]);

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
        setSections(updatedSections);
    };

    // handling movement of subsection between sections
    const moveSubsectionDifferentSection: DragDropProps = (source, destination) => {
        const sourceOrder = sections.find((section) => section.id!.toString() === source.droppableId)?.subSections;
        const destinationOrder = sections.find(
            (section) => section.id!.toString() === destination.droppableId
        )?.subSections;
        const [removed] = sourceOrder!.splice(source.index, 1);
        destinationOrder!.splice(destination.index, 0, removed);

        const updatedSections = sections.map((section) =>
            section.id!.toString() === source.droppableId
                ? { ...section, subSections: sourceOrder }
                : section.id!.toString() === destination.droppableId
                ? { ...section, subSections: destinationOrder }
                : section
        );
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
                return subsection;
            }
        });

        const sourceOrder = sourceSubsection.find(
            (subsection) => subsection.id!.toString() === source.droppableId
        )?.questions;
        const destinationOrder = destinationSubsection.find(
            (subsection) => subsection.id!.toString() === destination.droppableId
        )?.questions;
        const [removed] = sourceOrder!.splice(source.index, 1);
        destinationOrder!.splice(destination.index, 0, removed);

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
        setSections(reorderedSections);
    };

    const handleDropshadowTile: SubsectionDropshadowProps = (event, destinationIndex, sourceIndex) => {
        // isolating the element being dragged
        const draggedElement = getDraggedElement(event.draggableId);
        // if we aint draggin anything return
        if (!draggedElement) return;
        // isolate the height of element to determine the height of element being dragged
        const { clientHeight } = draggedElement as Element;
        // returning the manipulated array of dom elements
        const updatedChildrenArray: Element[] = getUpdatedChildrenArray(
            draggedElement as Element,
            destinationIndex,
            sourceIndex
        );
        // grabbing the # for marginTop
        const marginTop = getStyle(updatedChildrenArray, destinationIndex, 'marginBottom', 'clientHeight');
        // setting our props
        setSubsectionDropshadowProps({
            height: clientHeight + 2,
            marginTop: marginTop + 2 * destinationIndex
        });
    };

    const handleDropshadowSection: SectionDropshadowProps = (event, destinationIndex, sourceIndex) => {
        // isolate element we are dragging
        const draggedElement: Element | Node | null = getDraggedElement(event.draggableId)!.parentNode!.parentNode;
        // if nothing is being dragged return
        if (!draggedElement) return;
        // isolate the height of element to determine the height of element being dragged
        const { clientHeight } = draggedElement as Element;
        // returning the manipulated array of dom elements
        const updatedChildrenArray: Element[] = getUpdatedChildrenArray(
            draggedElement as Element,
            destinationIndex,
            sourceIndex
        );
        // grabbing the # for marginLeft
        const marginLeft = getStyle(updatedChildrenArray, destinationIndex, 'marginRight', 'clientWidth');
        // setting props
        setSectionDropshadowProps({
            height: clientHeight,
            marginLeft
        });
    };

    const handleDragUpdate = (event: DragUpdate) => {
        const { source, destination } = event;
        if (!destination) return;
        if (event.type === 'section') {
            handleDropshadowSection(event, destination.index, source.index);
        } else {
            handleDropshadowTile(event, destination.index, source.index);
        }
    };

    const handleDragStart = (event: DragStart) => {
        // the destination and source section Index will be the same for start
        const { index } = event.source;
        setCloseId(event.draggableId);
        if (event.type === 'section') {
            handleDropshadowSection(event, index, index);
        } else {
            handleDropshadowTile(event, index, index);
        }
    };

    const handleDragEnd = (result: DropResult) => {
        setCloseId('');
        if (!result.destination) return;
        const { source, destination, type } = result;
        if (source.droppableId === 'all-sections') {
            handleSectionMove(source, destination);
        } else if (type === 'question') {
            handleQuestionMove(source, destination);
        } else {
            // else its a Subsection move so we go here
            handleSubsectionMove(source, destination);
        }
    };

    return (
        <DragDropContext.Provider
            value={{
                handleDragEnd,
                handleDragStart,
                handleDragUpdate,
                subsectionDropshadowProps,
                sectionDropshadowProps,
                sections,
                setSections,
                closeId
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
