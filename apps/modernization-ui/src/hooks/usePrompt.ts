import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

type UnsavedChangesPromptProps = {
    shouldShowPrompt: boolean; // Flag to indicate unsaved changes
    onConfirmNavigation: () => void; // Callback for confirmed navigation
    onCancelNavigation: () => void; // Callback for cancelling navigation
};

export const useUnsavedChangesPrompt = ({ shouldShowPrompt }: UnsavedChangesPromptProps) => {
    const navigate = useNavigate();
    const [showPrompt, setShowPrompt] = useState(false);
    const history = useNavigate(); // Get history object

    const handleBeforeUnload = (event: BeforeUnloadEvent) => {
        if (shouldShowPrompt) {
            event.preventDefault();
            event.returnValue = ''; // Display custom warning message
            return '';
        }
    };

    useEffect(() => {
        const unblock = history.block((location) => {
            if (shouldShowPrompt) {
                setShowPrompt(true);
                return true; // Show prompt before navigation
            }
        });

        return () => unblock();
    }, [shouldShowPrompt, history]); // Dependency array

    const handleConfirmNavigation = () => {
        setShowPrompt(false);
        unblock(); // Allow navigation
        onConfirmNavigation(); // Call the provided callback
    };

    const handleCancelNavigation = () => {
        setShowPrompt(false);
        unblock(); // Allow navigation
    };

    return { showPrompt, onConfirmNavigation: handleConfirmNavigation, onCancelNavigation: handleCancelNavigation };
};
