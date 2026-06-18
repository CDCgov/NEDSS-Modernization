import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('users.profiles.options', '/nbs/api/options/users/profiles');

const useUserProfileOptions = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
};

export { useUserProfileOptions };
