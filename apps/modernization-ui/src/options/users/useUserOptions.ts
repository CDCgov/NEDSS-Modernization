import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('users.options', '/nbs/api/options/users');

const useUserOptions = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useUserOptions };
