import { Selectable } from 'options';
import { PageBuilderOptionsService } from 'apps/page-builder/generated';
import { usePageBuilderOptions } from 'apps/page-builder/options/usePageBuilderOptions';
import { authorization } from 'authorization';

type ConceptOptions = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const usePageNameOptions = ({ lazy = true }: Settings): ConceptOptions => {
    const resolver = () =>
        PageBuilderOptionsService.pageNames({
            authorization: authorization()
        });

    const { options, load } = usePageBuilderOptions({ lazy, resolver });

    return {
        options,
        load
    };
};

export { usePageNameOptions };
