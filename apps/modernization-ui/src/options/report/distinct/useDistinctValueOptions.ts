import { cachedSelectableResolver } from 'options/cache/cachedSelectableResolver';
import { Selectable } from 'options/selectable';
import { SelectableOptionsInteraction, useSelectableOptions } from 'options/useSelectableOptions';

const resolver = (columnUid?: string | null) => {
    return columnUid
        ? cachedSelectableResolver(
              `report.distinct.options.${columnUid}`,
              `/nbs/api/options/report/distinct/${columnUid}`
          )()
        : Promise.resolve<Selectable[]>([]);
};

const useDistinctValueOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useDistinctValueOptions };
