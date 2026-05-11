import VisuallyHidden from 'components/VisuallyHidden/VisuallyHidden';
import { createContext, ReactNode, useCallback, useContext, useRef, useState } from 'react';
import { isRuleType, move, Path, RuleGroupTypeAny, RuleType } from 'react-querybuilder';

type RuleOrGroupType = RuleType | RuleGroupTypeAny;

type KeyboardDndContextType = {
    activeId?: string;
    activate: (ruleOrGroup: RuleOrGroupType, path: Path) => void;
    reset: (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void, curPath: Path) => void;
    commit: (curPath: Path) => void;
};

const KeyboardDndContext = createContext<KeyboardDndContextType>({
    activeId: undefined,
    activate: () => {},
    reset: () => {},
    commit: () => {},
});

type Props = {
    children: ReactNode;
};
const KeyboardDnDProvider = ({ children }: Props) => {
    const ref = useRef<HTMLSpanElement>(null);
    const [activeRuleOrGroup, setActiveRuleOrGroup] = useState<RuleOrGroupType | null>(null);
    const [startPath, setStartPath] = useState<Path | null>(null);
    const activeId = activeRuleOrGroup?.id;

    const activate = (ruleOrGroup: RuleOrGroupType, path: Path) => {
        setActiveRuleOrGroup(ruleOrGroup);
        // make a copy to be safe
        setStartPath([...path]);
        announce(`You have lifted a ${isRuleType(ruleOrGroup) ? 'rule' : 'group'} at path ${describeLocation(path)}`);
    };

    const reset = (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void, curPath: Path) => {
        if (activeRuleOrGroup && startPath) {
            setActiveRuleOrGroup(null);
            setStartPath(null);
            const nextQuery = move(query, curPath, startPath);
            dispatchQuery(nextQuery);
            announce(`The ${isRuleType(activeRuleOrGroup) ? 'rule' : 'group'} has returned to its starting position`);
        }
    };

    const commit = (curPath: Path) => {
        setActiveRuleOrGroup(null);
        setStartPath(null);
        announce(
            `You have moved the ${isRuleType(activeRuleOrGroup) ? 'rule' : 'group'} from 
            path ${describeLocation(startPath ?? [])} to path ${describeLocation(curPath)}`
        );
    };

    const announce = useCallback((message: string): void => {
        const el: HTMLSpanElement | null = ref.current;
        if (el) {
            el.textContent = message;
            return;
        }

        // eslint-disable-next-line no-console
        console.warn(`
        A screen reader message was trying to be announced but it was unable to do so.

        Message not passed to screen reader:

        "${message}"
        `);
    }, []);

    return (
        <KeyboardDndContext.Provider value={{ activeId, activate, reset, commit }}>
            <VisuallyHidden id="keyboard-dnd-instructions">
                Press space bar to start a drag. When dragging you can use the arrow keys to move the item around and
                escape to cancel. Some screen readers may require you to be in focus mode or to use your pass through
                key
            </VisuallyHidden>
            <VisuallyHidden>
                <span ref={ref} aira-live="assertive" aria-atomic="true"></span>
            </VisuallyHidden>
            {children}
        </KeyboardDndContext.Provider>
    );
};

const useKeyboardDnd = () => {
    return useContext(KeyboardDndContext);
};

const describeLocation = (path: Path) => {
    return path.map((n) => n + 1).join('-');
};

export { KeyboardDnDProvider, useKeyboardDnd, describeLocation };
