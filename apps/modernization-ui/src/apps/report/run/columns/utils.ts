import { ReportColumn } from 'generated';

export const toSelectable = (columns: ReportColumn[]) => {
    return columns
        .filter(({ isDisplayable }) => isDisplayable)
        .map((c) => ({ value: c.id.toString(), name: c.title }))
        .sort((a, b) => (a.name < b.name ? -1 : 1));
};
