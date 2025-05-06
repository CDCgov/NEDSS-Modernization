import { useEffect } from 'react';
/**
 * This hook is going to handle the tab interaction for when we have patient results and want the user to be able to navigate
 * using the tab keyboard to the "Add new patient" button instead of other elements in between.
 */

function useFocusAddNewPatientButton() {
    useEffect(() => {
        const handleTab = (event: KeyboardEvent) => {
            if (event.key === 'Tab' && !event.shiftKey) {
                const resultsContainer = document.querySelector('.search-results-container');
                const addButton = document.querySelector<HTMLButtonElement>('#add-new-patient-button');

                if (!resultsContainer || !addButton) return;

                const focusableElement = Array.from(
                    document.querySelectorAll<HTMLElement>(
                        'a, button, input, textarea, select, svg, [tabidex]:not((tabindex="-1"])'
                    )
                ).filter((element) => {
                    const style = window.getComputedStyle(element);
                    return style.display !== 'none' && style.visibility !== 'hidden';
                });
                const resultsElement = focusableElement.filter((element) => resultsContainer.contains(element));

                if (resultsElement.includes(document.activeElement as HTMLElement)) {
                    const activeElementIndex = resultsElement.indexOf(document.activeElement as HTMLElement);

                    if (activeElementIndex === resultsElement.length - 1) {
                        event.preventDefault();
                        addButton.focus();
                        return;
                    }
                }
            }
        };

        document.addEventListener('keydown', handleTab);
        return () => {
            document.removeEventListener('keydown', handleTab);
        };
    }, []);
}

export { useFocusAddNewPatientButton };
