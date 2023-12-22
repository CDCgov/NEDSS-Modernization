import { useEffect, useRef } from 'react';
import { Button } from '@trussworks/react-uswds';

import styles from './actions.module.scss';

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
        <div className={styles.actions} ref={actionRef}>
            <Button onClick={() => handleAction('details')} unstyled type="button">
                Details
            </Button>
            <Button onClick={() => handleAction('edit')} unstyled type="button">
                Edit
            </Button>
            {!notDeletable && (
                <Button onClick={() => handleAction('delete')} unstyled type="button">
                    Delete
                </Button>
            )}
        </div>
    );
};
