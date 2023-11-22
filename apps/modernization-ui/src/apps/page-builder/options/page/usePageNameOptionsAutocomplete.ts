import { useUser } from 'user';
import { PageBuilderOptionsService } from 'apps/page-builder/generated';
import {
    AutocompleteOptionsResolver,
    PageBuilderOptionsAutocompletion,
    usePageBuilderOptionsAutocomplete
} from 'apps/page-builder/options/usePageBuilderOptionsAutocomplete';

type Options = {
    initialCriteria?: string;
    limit?: number;
};

const usePageNameOptionsAutocomplete = ({ initialCriteria = '', limit }: Options): PageBuilderOptionsAutocompletion => {
    const {
        state: { getToken }
    } = useUser();

    const resolver: AutocompleteOptionsResolver = (criteria: string, limit?: number) =>
        PageBuilderOptionsService.pageNamesAutocomplete({
            authorization: `Bearer ${getToken()}`,
            criteria,
            limit
        });

    const { criteria, options, suggest, complete, reset } = usePageBuilderOptionsAutocomplete({
        resolver,
        criteria: initialCriteria,
        limit
    });

    return {
        criteria,
        options,
        suggest,
        complete,
        reset
    };
};

export { usePageNameOptionsAutocomplete };
