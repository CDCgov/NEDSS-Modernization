import { useCallback, useState } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { useFormNavigationBlock } from 'navigation';
import { useLocalStorage } from 'storage';
import { Shown } from 'conditional-render';
import { Confirmation } from 'design-system/modal';
import { Checkbox } from 'design-system/checkbox';

type Paths = string | string[];

type NavigationGuardProps<V extends FieldValues, C, D extends FieldValues | undefined = undefined> = {
    /** unique identifier of the  */
    id: string;
    /** The form being guarded from navigation  */
    form: UseFormReturn<V, C, D>;
    /** When true navigating away from the form will be prevented */
    activated?: boolean;
    /** A list of routes that do not block navigation. */
    allowed?: Paths;
};

const NavigationGuard = <V extends FieldValues, C, D extends FieldValues | undefined = undefined>({
    id,
    form,
    activated = true,
    allowed
}: NavigationGuardProps<V, C, D>) => {
    const { value, save } = useLocalStorage({ key: id, initial: false });

    const blocker = useFormNavigationBlock({ activated: activated ? !value : false, form, allowed });

    const [isPermanent, setPermanent] = useState<boolean>(false);

    const handleConfirm = useCallback(() => {
        save(isPermanent);
        blocker.unblock();
    }, [blocker.unblock, save]);

    return (
        <Shown when={blocker.blocked}>
            <Confirmation
                title="Warning"
                confirmText="Yes, cancel"
                cancelText="No, back to form"
                forceAction
                onConfirm={handleConfirm}
                onCancel={blocker.reset}>
                Canceling the form will result in the loss of all additional data entered. Are you sure you want to
                cancel?
                <Checkbox label="Don't show again" id={'cancel-message-bypass'} onChange={setPermanent} />
            </Confirmation>
        </Shown>
    );
};

export { NavigationGuard };
