import { useCallback, useEffect, useState } from 'react';
import { BlockerFunction, useBlocker, Location as RouterLocation, useNavigate } from 'react-router';
import { unblockableRoutes } from 'routes';
import { NavigationBlock, NavigationBlockProps } from './block';

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
 * @param {NavigationBlockProps} props - The properties object.
 * @return {NavigationBlock} Functions to control navigation.
 */
const useNavigationBlock = ({ activated = true, onBlock }: NavigationBlockProps): NavigationBlock => {
    //
    const [isEngaged, setEngaged] = useState<boolean>(false);

    const blockingFn = useCallback<BlockerFunction>(
        ({ currentLocation, nextLocation }) => {
            return isEngaged
                ? isNavigating(currentLocation, nextLocation) && isBlockedPath(nextLocation.pathname)
                : false;
        },
        [isEngaged]
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
