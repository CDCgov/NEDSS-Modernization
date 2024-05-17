import { useState, JSX as ReactJSX } from 'react';

type Hide = () => void;
type Show = () => void;
type Toggle = () => void;

type Renderer = (element: ReactJSX.Element) => ReactJSX.Element | null;

type Interaction = {
    render: Renderer;
    show: Show;
    hide: Hide;
    toggle: Toggle;
};

const noop: Renderer = () => null;
const allow: Renderer = (element: ReactJSX.Element) => element;

const useConditionalRender = (): Interaction => {
    const [visible, setVisible] = useState<boolean>(false);

    const show = () => setVisible(true);
    const hide = () => setVisible(false);
    const toggle = () => setVisible((current) => !current);
    const render = visible ? allow : noop;

    return {
        render,
        show,
        hide,
        toggle
    };
};

export { useConditionalRender };
