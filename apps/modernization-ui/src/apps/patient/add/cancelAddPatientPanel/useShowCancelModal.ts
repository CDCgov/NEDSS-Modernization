import { useLocalStorage } from 'storage';

const MODAL_STORAGE_KEY = 'patient.create.extended.cancel';

type ShowCancelModalResult = {
    save: (value: boolean) => void;
    value: boolean | undefined;
};

/**
 * Manages the state of showing a modal.
 * @return {ShowCancelModalResult} Current value and function to modify it.
 */
export const useShowCancelModal = (): ShowCancelModalResult => {
    const { save, value } = useLocalStorage({ key: MODAL_STORAGE_KEY, initial: false });
    return { save, value };
};
