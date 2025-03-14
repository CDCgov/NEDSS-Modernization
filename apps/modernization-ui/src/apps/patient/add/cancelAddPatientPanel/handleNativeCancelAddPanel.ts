export const handleNativeCancelAddPanel = (isDirty: boolean, isSubmitted: boolean) => {
    const handleBeforeUnload = (e: BeforeUnloadEvent) => {
        if (isDirty && !isSubmitted) {
            e.preventDefault();
        }
    };

    window.addEventListener('beforeunload', handleBeforeUnload);
    return () => {
        window.removeEventListener('beforeunload', handleBeforeUnload);
    };
};
