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

    const blockingFn = useCallback<BlockerFunction>(
        ({ currentLocation, nextLocation }) => {
            return activated && isEngaged
                ? isNavigating(currentLocation, nextLocation) &&
                      !isAllowedPath(allowed, nextLocation.pathname) &&
                      isBlockedPath(nextLocation.pathname)
                : false;
        },
        [isEngaged, activated]
    );

    const { state: blockerState, proceed, reset: blockerReset, location } = useBlocker(blockingFn);
    const navigate = useNavigate();

    // Reset the blocker if the user cleans the form
    useEffect(() => {
        if (blockerState === 'blocked') {
            // navigation has been attempted, so fire onBlock event
            onBlock?.();
        } else if (blockerState === 'proceeding' && location) {
            //  The block has been resolved, navigate the user to the location that was blocked.
            navigate(location);
        }
    }, [blockerState, onBlock, navigate]);

    // Prompt
    useEffect(() => {
        const handleBeforeUnload = (e: BeforeUnloadEvent) => {
            if (isEngaged) {
                e.preventDefault();
            }
        };
        window.addEventListener('beforeunload', handleBeforeUnload);
        return () => window.removeEventListener('beforeunload', handleBeforeUnload);
    }, [isEngaged]);

    const block = useCallback(() => {
        if (activated) {
            setEngaged(true);
        }
    }, [activated, setEngaged]);

    const allow = useCallback(() => {
        setEngaged(false);
    }, [setEngaged]);

    const unblock = useCallback(() => {
        if (blockerState === 'blocked') {
            proceed?.();
        }
    }, [blockerState, proceed]);

    const reset = useCallback(() => {
        if (blockerState === 'blocked') {
            blockerReset?.();
        }
    }, [blockerState, blockerReset]);

    return {
        blocked: blockerState === 'blocked',
        allow,
        block,
        unblock,
        reset
    };
};

export { useNavigationBlock };
export type { NavigationBlockInteraction, NavigationBlockSettings };
