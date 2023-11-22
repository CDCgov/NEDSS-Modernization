import { Option } from 'generated';
import { useUser } from 'user';
import { PageBuilderOptionsService } from 'apps/page-builder/generated';
import { usePageBuilderOptions } from 'apps/page-builder/options/usePageBuilderOptions';

type ConceptOptions = {
    options: Option[];
    load: () => void;
};

type Parameters = {
    lazy?: boolean;
};

const usePageNameOptions = ({ lazy = true }: Parameters): ConceptOptions => {
    const {
        state: { getToken }
    } = useUser();

    const resolver = () =>
        PageBuilderOptionsService.pageNames({
            authorization: `Bearer ${getToken()}`
        });

    const { options, load } = usePageBuilderOptions({ lazy, resolver });

    return {
        options,
        load
    };
};

export { usePageNameOptions };
