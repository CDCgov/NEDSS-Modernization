import { formatLabelName } from 'apps/report/utils';
import { ReportColumn } from 'generated';

export const toSelectable = (columns: ReportColumn[]) => {
    return columns
        .filter(({ isDisplayable }) => isDisplayable)
        .map((c) => ({ value: c.id.toString(), name: formatLabelName(c.title, c.name) }))
        .sort((a, b) => (a.name < b.name ? -1 : 1));
};
