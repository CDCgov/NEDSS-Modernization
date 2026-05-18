// Adapted from https://github.com/hello-pangea/dnd/blob/cd34005527ace937c8e0b6fcaca8be3abab7dfad/src/view/event-bindings/event-types.ts

export interface EventOptions {
    passive?: boolean;
    capture?: boolean;
    // sometimes an event might only event want to be bound once
    once?: boolean;
}

export interface EventBinding<TEvent = Event> {
    eventName: string;
    fn: (event: TEvent) => void;
    options?: EventOptions;
}

export type DragEventBinding = EventBinding<DragEvent>;
export type ErrorEventBinding = EventBinding<ErrorEvent>;
export type FocusEventBinding = EventBinding<FocusEvent>;
export type KeyboardEventBinding = EventBinding<KeyboardEvent>;
export type MouseEventBinding = EventBinding<MouseEvent>;
export type TouchEventBinding = EventBinding<TouchEvent>;
export type PointerEventBinding = EventBinding<PointerEvent>;
export type TransitionEventBinding = EventBinding<TransitionEvent>;
export type UIEventBinding = EventBinding<UIEvent>;
export type WheelEventBinding = EventBinding<WheelEvent>;

export type AnyEventBinding =
    | EventBinding
    | DragEventBinding
    | ErrorEventBinding
    | FocusEventBinding
    | KeyboardEventBinding
    | MouseEventBinding
    | TouchEventBinding
    | PointerEventBinding
    | TransitionEventBinding
    | UIEventBinding
    | WheelEventBinding;
