export const focusedTarget = (currentFocusTarget: string) => {
    const targetElement = document.getElementById(currentFocusTarget);
    if (targetElement) {
        targetElement.focus();
    }
};
