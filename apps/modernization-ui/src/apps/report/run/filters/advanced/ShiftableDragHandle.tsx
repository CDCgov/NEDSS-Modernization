import { forwardRef, ForwardRefExoticComponent, KeyboardEventHandler, RefAttributes, useEffect, useState } from 'react';
import { DragHandleProps } from 'react-querybuilder';
import { useKeyboardDnd } from './useKeyboardDnd';
import { Icon } from 'design-system/icon';
import * as keyCodes from './keyCodes';
import supportedPageVisibilityEventName from './supportedPageVisibilityEventName';
import { AnyEventBinding, EventBinding } from './eventTypes';

// custom drag handle to add shifting action on keyboard up down when space enabled
const ShiftableDragHandle: ForwardRefExoticComponent<DragHandleProps & RefAttributes<HTMLSpanElement>> = forwardRef<
    HTMLSpanElement,
    DragHandleProps
>((props, dragRef) => {
    const { activeId, activate, reset, drag, drop } = useKeyboardDnd();
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

    const cancel = () => {
        setIsActive(false);
        reset(getQuery(), dispatchQuery);
    };

    const handleKeyDown: KeyboardEventHandler = (event) => {
        // space toggles, escape will turn off activity if active
        if (event.code === 'Space') {
            if (!isActive) {
                setIsActive(true);
                activate(props.ruleOrGroup, props.path);
            } else {
                setIsActive(false);
                drop(props.path);
            }
            event.preventDefault();
            return;
        } else if (isActive && event.code === 'Escape') {
            // restore move focus to the drag handle that was moved back
            // but otherwise let the general handler deal with logic
            setTimeout(() => {
                const thisEl = document.querySelector<HTMLSpanElement>(`#drag-handle-${id}`);
                thisEl?.focus();
            }, 50);
            return;
        }
    };

    // once the drag handle is active, move to window level events as we're in a
    // "captive" flow and want to make sure things are always handled.
    // This is adapted from pragmatic-dnd that's used elsewhere in the app
    useEffect(() => {
        if (isActive) {
            const bindings = getDraggingBindings((dir) => drag(getQuery(), dispatchQuery, dir), cancel);

            (bindings as EventBinding[]).forEach(({ eventName, fn, options = {} }) =>
                window.addEventListener(eventName, fn, {
                    capture: true,
                    passive: false,
                    ...options,
                })
            );

            return () =>
                (bindings as EventBinding[]).forEach(({ eventName, fn, options = {} }) =>
                    window.removeEventListener(eventName, fn, { capture: true, passive: false, ...options })
                );
        }
    }, [isActive]);

    return (
        <span
            id={`drag-handle-${id}`}
            data-testid={`drag-handle-${id}`}
            ref={dragRef}
            className={props.className}
            title={isActive ? 'Active drag handle' : 'Drag Handle'}
            role="button"
            tabIndex={0}
            onKeyDown={handleKeyDown}
        >
            <Icon name="drag" />
        </span>
    );
});

// Adapted from
// https://github.com/hello-pangea/dnd/blob/main/src/view/use-sensor-marshal/sensors/use-keyboard-sensor.ts#L23-L139
// to match behavior of other dnd in the app
interface KeyMap {
    [key: string]: true;
}
const preventedKeys: KeyMap = {
    // submission
    [keyCodes.enter]: true,
    // tabbing
    [keyCodes.tab]: true,
    // scrolling
    [keyCodes.arrowRight]: true,
    [keyCodes.arrowLeft]: true,
    [keyCodes.pageDown]: true,
    [keyCodes.pageUp]: true,
    [keyCodes.home]: true,
    [keyCodes.end]: true,
};

function getDraggingBindings(move: (dir: 'up' | 'down') => void, cancel: () => void): AnyEventBinding[] {
    return [
        {
            eventName: 'keydown',
            fn: (event: KeyboardEvent) => {
                if (event.code === keyCodes.escape) {
                    event.preventDefault();
                    cancel();
                    return;
                }

                // Movement

                if (event.code === keyCodes.arrowDown) {
                    event.preventDefault();
                    move('down');
                    return;
                }

                if (event.code === keyCodes.arrowUp) {
                    event.preventDefault();
                    move('up');
                    return;
                }

                if (preventedKeys[event.code]) {
                    event.preventDefault();
                    return;
                }
            },
        },
        // any mouse actions kills a drag
        {
            eventName: 'mousedown',
            fn: cancel,
        },
        {
            eventName: 'mouseup',
            fn: cancel,
        },
        {
            eventName: 'click',
            fn: cancel,
        },
        {
            eventName: 'touchstart',
            fn: cancel,
        },
        // resizing the browser kills a drag
        {
            eventName: 'resize',
            fn: cancel,
        },
        // kill if the user is using the mouse wheel
        // We are not supporting wheel / trackpad scrolling with keyboard dragging
        {
            eventName: 'wheel',
            fn: cancel,
            // chrome says it is a violation for this to not be passive
            // it is fine for it to be passive as we just cancel as soon as we get
            // any event
            options: { passive: true },
        },
        // Cancel on page visibility change
        {
            eventName: supportedPageVisibilityEventName,
            fn: cancel,
        },
    ];
}

export { ShiftableDragHandle };
