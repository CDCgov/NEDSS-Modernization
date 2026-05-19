import VisuallyHidden from 'components/VisuallyHidden/VisuallyHidden';
import { createContext, ReactNode, useContext, useState } from 'react';
import { getPathOfID, isRuleType, move, Path, RuleGroupTypeAny, RuleType } from 'react-querybuilder';

type RuleOrGroupType = RuleType | RuleGroupTypeAny;

type KeyboardDndContextType = {
    activeId?: string;
    activate: (ruleOrGroup: RuleOrGroupType, path: Path) => void;
    reset: (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void) => void;
    drop: (curPath: Path) => void;
    drag: (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void, dir: 'up' | 'down') => void;
};

const KeyboardDndContext = createContext<KeyboardDndContextType>({
    activeId: undefined,
    activate: () => {},
    reset: () => {},
    drag: () => {},
    drop: () => {},
});

type Props = {
    children: ReactNode;
};
const KeyboardDnDProvider = ({ children }: Props) => {
    const [activeRuleOrGroup, setActiveRuleOrGroup] = useState<RuleOrGroupType | null>(null);
    const [startPath, setStartPath] = useState<Path | null>(null);
    const [announcedMessage, setAnnouncedMessage] = useState<string>('');
    const activeId = activeRuleOrGroup?.id;

    const activate = (ruleOrGroup: RuleOrGroupType, path: Path) => {
        setActiveRuleOrGroup(ruleOrGroup);
        // make a copy to be safe
        setStartPath([...path]);
        announce(`You have lifted a ${isRuleType(ruleOrGroup) ? 'rule' : 'group'} at path ${describeLocation(path)}`);
    };

    const reset = (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void) => {
        if (!activeId) return;
        setActiveRuleOrGroup(null);
        setStartPath(null);
        const nextQuery = move(query, activeId, startPath ?? []);
        dispatchQuery(nextQuery);
        announce(`The ${isRuleType(activeRuleOrGroup) ? 'rule' : 'group'} has returned to its starting position`);
    };

    const drag = (query: RuleGroupTypeAny, dispatchQuery: (q: RuleGroupTypeAny) => void, dir: 'up' | 'down') => {
        if (!activeId) return;
        const nextQuery = move(query, activeId, dir);
        const nextPath = getPathOfID(activeId, nextQuery);
        dispatchQuery(nextQuery);
        announce(
            `You have moved the ${isRuleType(activeRuleOrGroup) ? 'rule' : 'group'} ${dir} 
                to path ${describeLocation(nextPath)}`
        );
    };

    const drop = (curPath: Path) => {
        setActiveRuleOrGroup(null);
        setStartPath(null);
        announce(
            `You have dropped the ${isRuleType(activeRuleOrGroup) ? 'rule' : 'group'} 
            at path ${describeLocation(curPath)}`
        );
    };

    const announce = (message: string): void => {
        // slight timeout improves screen reader flakiness
        setTimeout(() => setAnnouncedMessage(message), 100);
    };

    return (
        <KeyboardDndContext.Provider value={{ activeId, activate, reset, drag, drop }}>
            <VisuallyHidden id="keyboard-dnd-instructions">
                In the advanced filter builder below, you can drag and drop rules and groups to change the logic of how
                statements are combined. When focused on a drag handle for a rule or group, press space bar to start and
                stop a drag. When dragging you can use the arrow keys to move the item around and escape to cancel. Some
                screen readers may require you to be in focus mode or to use your pass through key
            </VisuallyHidden>
            <VisuallyHidden aria-live="assertive" data-testid="announcement">
                {announcedMessage}
            </VisuallyHidden>
            {children}
        </KeyboardDndContext.Provider>
    );
};

const useKeyboardDnd = () => {
    return useContext(KeyboardDndContext);
};

const describeLocation = (path: Path | null) => {
    if (!path) return 'unknown';
    return path.map((n) => n + 1).join('-');
};

export { KeyboardDnDProvider, useKeyboardDnd };
