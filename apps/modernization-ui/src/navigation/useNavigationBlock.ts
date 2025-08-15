import { useCallback, useEffect, useState } from 'react';
import { BlockerFunction, useBlocker, Location as RouterLocation, useNavigate } from 'react-router';
import { unblockableRoutes } from 'routes';

type Paths = string | string[];

type NavigationBlockSettings = {
    /** Whether blocking navigation is allowed, for example, if a user opts in to bypassing the warning */
    activated?: boolean;
    /** A list of routes that do not block navigation. */
    allowed?: Paths;
    /** Callback to handle when navigation is blocked. Use this to take action like opening a modal. */
    onBlock?: () => void;
};

type NavigationBlockInteraction = {
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

const isAllowedPath = (allowed: Paths | undefined, path: string) => {
    if (typeof allowed === 'string') {
        return allowed === path;
    } else if (Array.isArray(allowed)) {
        return allowed.includes(path);
    }

    return false;
};
const isBlockedPath = (path: string) => !unblockableRoutes.includes(path);
const isNavigating = (current: RouterLocation, next: RouterLocation) => current.pathname !== next.pathname;

/**
 * When activated the user is prevented from navigating away from the current page. If navigation
 * is attempted when the block is activated the navigation block will enter a blocked state.
 *
 * Blocked navigation within the SPA triggers a call to the onBlock function. Any external
 * navigation that occurs when the block is activated will use the browser native message
 * to confirm navigation away from the page.
 *
 * @param {NavigationBlockSettings} props - The properties object.
 * @return {NavigationBlockInteraction} Functions to control navigation.
 */
const useNavigationBlock = ({
    activated = true,
    onBlock,
    allowed
}: NavigationBlockSettings): NavigationBlockInteraction => {
    const [isEngaged, setEngaged] = useState<boolean>(false);

    const shouldBlock = useCallback<BlockerFunction>(
        ({ currentLocation, nextLocation }) => {
            if (activated && isEngaged) {
                const navigating = isNavigating(currentLocation, nextLocation);
                const blocked = isBlockedPath(nextLocation.pathname);
                const exempt = !isAllowedPath(allowed, nextLocation.pathname);
                const result = navigating && blocked && exempt;

                return result;
            }

            return false;
        },
        [isEngaged, activated]
    );

    const blocker = useBlocker(shouldBlock);
    const navigate = useNavigate();

    // Reset the blocker if the user cleans the form
    useEffect(() => {
        if (blocker.state === 'blocked') {
            // navigation has been attempted, so fire onBlock event
            onBlock?.();
        } else if (blocker.state === 'proceeding' && blocker.location) {
            //  The block has been resolved, navigate the user to the location that was blocked.
            navigate(blocker.location);
        }
    }, [blocker.state, onBlock, navigate, blocker.location]);

    // Prompt
    useEffect(() => {
        if (activated && isEngaged) {
            const handleBeforeUnload = (e: BeforeUnloadEvent) => {
                e.preventDefault();
            };
            window.addEventListener('beforeunload', handleBeforeUnload);
            return () => window.removeEventListener('beforeunload', handleBeforeUnload);
        }
    }, [isEngaged, activated]);

    const block = useCallback(() => {
        if (activated) {
            setEngaged(true);
        }
    }, [activated, setEngaged]);

    const allow = useCallback(() => {
        setEngaged(false);
    }, [setEngaged]);

    const unblock = useCallback(() => {
        if (blocker.state === 'blocked') {
            blocker.proceed();
        }
    }, [blocker.state, blocker.proceed]);

    const reset = useCallback(() => {
        if (blocker.state === 'blocked') {
            blocker.reset();
        }
    }, [blocker.state, blocker.reset]);

    return {
        blocked: blocker.state === 'blocked',
        allow,
        block,
        unblock,
        reset
    };
};

export { useNavigationBlock };
export type { NavigationBlockInteraction, NavigationBlockSettings };
