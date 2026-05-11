import { forwardRef, ForwardRefExoticComponent, KeyboardEventHandler, RefAttributes, useEffect, useState } from 'react';
import { DragHandleProps, isRuleType, move } from 'react-querybuilder';
import { describeLocation, useKeyboardDnd } from './useKeyboardDnd';
import { Icon } from 'design-system/icon';

// custom drag handle to add shifting action on keyboard up down when space enabled
const ShiftableDragHandle: ForwardRefExoticComponent<DragHandleProps & RefAttributes<HTMLSpanElement>> = forwardRef<
    HTMLSpanElement,
    DragHandleProps
>((props, dragRef) => {
    const { activeId, activate, reset, commit } = useKeyboardDnd();
    const id = props.ruleOrGroup.id!;
    const [isActive, setIsActive] = useState<boolean>(activeId === id);
    const { getQuery, dispatchQuery } = props.schema;

    // When a rule group changes level, the component re-mounts and we need to move focus
    // back to the drag handle
    useEffect(() => {
        if (isActive) {
            const thisEl = document.querySelector<HTMLSpanElement>(`#drag-handle-${id}`);
            thisEl?.focus();
        }
    }, []);

    const handleKeyDown: KeyboardEventHandler = (event) => {
        // space toggles, escape will turn off activity if active
        if (event.code === 'Space') {
            if (!isActive) {
                setIsActive(true);
                activate(props.ruleOrGroup, props.path);
            } else {
                setIsActive(false);
                commit(props.path);
            }
            event.preventDefault();
            return;
        } else if (isActive && event.code === 'Escape') {
            setIsActive(false);
            reset(getQuery(), dispatchQuery, props.path);
            event.preventDefault();
            return;
        }

        if (!isActive) return;

        let dir: 'up' | 'down' | undefined = undefined;
        if (event.code === 'ArrowUp') {
            dir = 'up';
        } else if (event.code === 'ArrowDown') {
            dir = 'down';
        } else {
            // only allow stated actions while activated
            event.preventDefault();
            event.stopPropagation();
        }

        if (!dir) return;

        // move the rule and update the query
        const nextQuery = move(getQuery(), props.path, dir);
        dispatchQuery(nextQuery);
        event.preventDefault();
    };

    return (
        <span
            id={`drag-handle-${id}`}
            data-testid={`drag-handle-${id}`}
            ref={dragRef}
            className={props.className}
            title={`Drag handle for ${isRuleType(props.ruleOrGroup) ? 'rule' : 'group'} at 
            path ${describeLocation(props.path)}`}
            aria-describedby="keyboard-dnd-instructions"
            role="button"
            tabIndex={0}
            onKeyDown={handleKeyDown}>
            <Icon name="drag" />
        </span>
    );
});

export { ShiftableDragHandle };
