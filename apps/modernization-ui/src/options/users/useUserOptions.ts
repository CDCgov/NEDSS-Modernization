import { selectableResolver, useSelectableOptions } from 'options';

const resolver = () => selectableResolver('/nbs/api/options/users');

const useUserOptions = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useUserOptions };
