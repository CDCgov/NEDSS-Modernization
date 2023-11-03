import { useEffect, useRef } from 'react';
import { Button } from '@trussworks/react-uswds';

export const Actions = ({ handleAction, handleOutsideClick, notDeletable }: any) => {
    const actionRef: any = useRef(null);

    useEffect(() => {
        function handleClickOutside(event: any) {
            if (actionRef.current && !actionRef.current.contains(event.target)) {
                handleOutsideClick();
            }
        }
        // Bind the event listener
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            // Unbind the event listener on clean up
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, [actionRef]);

    return (
        <div className="actions-card" ref={actionRef}>
            <Button
                onClick={() => handleAction('details')}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Details
            </Button>
            <Button
                onClick={() => handleAction('edit')}
                unstyled
                type="button"
                className="text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                Edit
            </Button>
            {!notDeletable && (
                <Button
                    onClick={() => handleAction('delete')}
                    unstyled
                    type="button"
                    className="bold-delete text-base-dark display-block padding-1 border-bottom border-base-lighter text-no-underline width-full">
                    Delete
                </Button>
            )}
        </div>
    );
};
