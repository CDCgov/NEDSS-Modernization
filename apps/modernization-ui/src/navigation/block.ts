type NavigationBlockProps = {
    /** Whether blocking navigation is allowed, for example, if a user opts in to bypassing the warning */
    activated?: boolean;
    /** Callback to handle when navigation is blocked. Use this to take action like opening a modal. */
    onBlock?: () => void;
};

type NavigationBlock = {
    /** Whether navigation is current blocked */
    blocked: boolean;
    /** Deactivates the navigation block allowing internal navigation to occur  */
    allow: () => void;
    /** Activates the navigation block preventing internal navigation from occurring */
    block: () => void;
    /** Invoke to allow navigation to continue */
    unblock: () => void;
    /** Reset the navigation blocker */
    reset: () => void;
};

export type { NavigationBlock, NavigationBlockProps };
