import { useCallback, useEffect, useState } from 'react';
import { BlockerFunction, useBlocker, Location as RouterLocation, useNavigate } from 'react-router-dom';
import { unblockableRoutes } from 'routes';

const isNavigating = (current: RouterLocation, next: RouterLocation) => current.pathname !== next.pathname;

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

/**
 * When activated the user is prevented from navigating away from the current page. If navigation is attempted when the block is activated the navigation block will enter a blocked state.
 *
 * Currently uses useBlocker from react-router, but can be expanded in the future to use other methods like onbeforeunload.
 *
 * @param {NavigationBlockProps} props - The properties object.
 * @return {NavigationBlock} Functions to control navigation.
 */
const useNavigationBlock = ({ activated = true, onBlock }: NavigationBlockProps): NavigationBlock => {
    //
    const [isEngaged, setEngaged] = useState<boolean>(false);

    const isBlockedPath = (path: string) => !unblockableRoutes.includes(path);

    const blockingFn = useCallback<BlockerFunction>(
        ({ currentLocation, nextLocation }) =>
            isEngaged && isBlockedPath(nextLocation.pathname) && isNavigating(currentLocation, nextLocation),
        [isEngaged]
    );

    const { state, proceed, reset: blockerReset, location } = useBlocker(blockingFn);
    const navigate = useNavigate();

    // Reset the blocker if the user cleans the form
    useEffect(() => {
        if (state === 'blocked') {
            // navigation has been attempted, so fire onBlock event
            onBlock?.();
        } else if (state === 'proceeding' && location) {
            //  The block has been resolved, navigate the user to the location that was blocked.
            navigate(location);
        }
    }, [state, onBlock, navigate]);

    const block = useCallback(() => {
        if (activated) {
            setEngaged(true);
        }
    }, [activated, setEngaged]);

    const allow = useCallback(() => setEngaged(false), [setEngaged]);

    const unblock = useCallback(() => {
        if (state === 'blocked') {
            proceed?.();
        }
    }, [state, proceed]);

    const reset = useCallback(() => {
        if (state === 'blocked') {
            blockerReset?.();
        }
    }, [state, blockerReset]);

    return {
        blocked: state === 'blocked',
        allow,
        block,
        unblock,
        reset
    };

    // These are other ways to block navigation
    // in the future, if we need to block outside of react-router

    // Alt method 1: uses history.pushState to prevent back button
    // useEffect(() => {
    //     // const handlePopState = (e: PopStateEvent) => {
    //     //     e.preventDefault();
    //     //     e.returnValue = false;
    //     //     handleCancel();
    //     //     return false;
    //     // };
    //     // window.addEventListener('popstate', handlePopState);
    //     // window.history.pushState({ modalOpened: false }, '');
    //     // return () => {
    //     //     window.removeEventListener('popstate', handlePopState);
    //     // };
    // }, []);

    // Alt method 2: uses beforeunload to prevent back button or page reload navigation
    // this unfortunately is not consistent and may be deprecated in the future as browsers increasingly prevent this behavior
    // useEffect(() => {
    //     const handleBeforeUnload = (e: BeforeUnloadEvent) => {
    //         e.preventDefault();
    //         e.returnValue = false;
    //         handleCancel();
    //     };
    //     window.addEventListener('beforeunload', handleBeforeUnload);
    //     return () => {
    //         window.removeEventListener('beforeunload', handleBeforeUnload);
    //     };
    // }, []);
};

export { useNavigationBlock };
