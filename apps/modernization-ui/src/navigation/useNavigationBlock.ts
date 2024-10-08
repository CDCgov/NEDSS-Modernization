import { useCallback, useEffect, useState } from 'react';
import { BlockerFunction, useBlocker } from 'react-router-dom';
import { unblockableRoutes } from 'routes';

type NavigationBlockProps = {
    /** Whether to block navigation, for example, if a form is dirty */
    shouldBlock: boolean;
    /** Callback to handle when navigation is blocked. Use this to take action like opening a modal. */
    onBlock?: () => void;
    /** When specified, only blocks navigation to these routes. */
    blockedRoutes?: string[];
    /** When true, blocks navigation to the current path (default: false) */
    blockCurrentRoute?: boolean;
};

type NavigationBlock = {
    /** Whether navigation is current blocked */
    blocked: boolean;
    /** Invoke to allow navigation to continue */
    unblock: () => void;
    /** Reset the navigation blocker */
    reset: () => void;
};

/**
 * Blocks the user from navigating away from the current page. Invokes onBlock when the attempt is made.
 * Currently uses useBlocker from react-router, but can be expanded in the future to use other methods like onbeforeunload.
 * @param {NavigationBlockProps} props - The properties object.
 * @return {NavigationBlock} Functions to control navigation.
 */
const useNavigationBlock = ({
    shouldBlock,
    onBlock,
    blockedRoutes,
    blockCurrentRoute = false
}: NavigationBlockProps): NavigationBlock => {
    const [bypassBlocker, setBypassBlocker] = useState<boolean>(false);
    const blocker = useBlocker(
        useCallback<BlockerFunction>(
            ({ currentLocation, nextLocation }) =>
                shouldBlock &&
                !bypassBlocker &&
                (blockCurrentRoute || currentLocation.pathname !== nextLocation.pathname) &&
                (!blockedRoutes || blockedRoutes.includes(nextLocation.pathname)) &&
                !unblockableRoutes.includes(nextLocation.pathname),
            [shouldBlock, bypassBlocker]
        )
    );

    // Reset the blocker if the user cleans the form
    useEffect(() => {
        if (blocker.state === 'blocked') {
            if (bypassBlocker) {
                blocker.proceed?.();
            } else {
                // navigation has been attempted, so fire onBlock event
                onBlock?.();
            }
        }
    }, [blocker.state, bypassBlocker, onBlock]);

    const unblock = () => {
        setBypassBlocker(true);
        blocker.proceed?.();
    };

    const reset = () => {
        setBypassBlocker(false);
        blocker.reset?.();
    };

    return {
        blocked: blocker.state === 'blocked',
        unblock: unblock,
        reset: reset
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
