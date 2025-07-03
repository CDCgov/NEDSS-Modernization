import { useCallback, useState } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { useFormNavigationBlock } from 'navigation';
import { useLocalStorage } from 'storage';
import { Shown } from 'conditional-render';
import { Confirmation } from 'design-system/modal';
import { Checkbox } from 'design-system/checkbox';

type Paths = string | string[];

type NavigationGuardProps<V extends FieldValues, C, D extends FieldValues | undefined = undefined> = {
    /** unique identifier of the storage key used to persist this value */
    id: string;
    /** The form being guarded from navigation  */
    form: UseFormReturn<V, C, D>;
    /** When true navigating away from the form will be prevented */
    activated?: boolean;
    /** When provided, will show this cancel text */
    cancelText?: string;
    /** A list of routes that do not block navigation. */
    allowed?: Paths;
};

/**
 * Adds a guard to a form that prompts the user to confirm navigation away from a page that contains
 * pending data.  The guard can be bypassed by the user to prevent any future warnings from appearing.
 *
 * @param {NavigationGuardProps} props
 * @return {NavigationGuard}
 */
const NavigationGuard = <V extends FieldValues, C, D extends FieldValues | undefined = undefined>({
    id,
    form,
    activated = true,
    cancelText,
    allowed
}: NavigationGuardProps<V, C, D>) => {
    const { value, save } = useLocalStorage({ key: id, initial: false });

    const blocker = useFormNavigationBlock({ activated: activated ? !value : false, form, allowed });

    const [isPermanent, setPermanent] = useState<boolean>(false);

    const handleConfirm = useCallback(() => {
        save(isPermanent);
        blocker.unblock();
    }, [blocker.unblock, save, isPermanent]);

    return (
        <Shown when={blocker.blocked}>
            <Confirmation
                title="Discard unsaved data?"
                confirmText="Yes, cancel"
                cancelText="No, back to form"
                forceAction
                onConfirm={handleConfirm}
                onCancel={blocker.reset}>
                {cancelText ?? (
                    <>If you cancel, any data you entered will be lost. Are you sure you want to continue?</>
                )}
                <Checkbox label="Don't show again" id={'cancel-message-bypass'} onChange={setPermanent} />
            </Confirmation>
        </Shown>
    );
};

export { NavigationGuard };
