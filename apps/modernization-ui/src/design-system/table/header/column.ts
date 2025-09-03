import { FilterDescriptor } from 'design-system/filter';
import { ReactNode } from 'react';
import { Mapping } from 'utils/mapping';

type SortIconType = 'alpha' | 'numeric';

type CellValue = string | number | boolean | Date;

type HasRenderFunction<R> = { render: (value: R, index: number) => ReactNode | undefined };
type HasValueFunction<R, C = CellValue> = { value: Mapping<R, C | null | undefined> };

type Rendered<R, C = CellValue> =
    | HasRenderFunction<R>
    | HasValueFunction<R, C>
    | (HasRenderFunction<R> & HasValueFunction<R, C>);

type BaseColumn<R, C> = {
    id: string;
    fixed?: boolean;
    className?: string;
} & Rendered<R, C>;

type NamedColumn<R, C = CellValue> = BaseColumn<R, C> & {
    name: string;
    filter?: FilterDescriptor;
    sortable?: boolean;
    sortIconType?: SortIconType;
};
type LabeledColumn<R, C = CellValue> = BaseColumn<R, C> & { label: string };

type Column<R, C = CellValue> = NamedColumn<R, C> | LabeledColumn<R, C>;

export type {
    CellValue,
    Column,
    NamedColumn,
    LabeledColumn,
    SortIconType,
    HasValueFunction,
    HasRenderFunction,
    Rendered
};

const isNamed = <R, C>(column: Column<R, C>): column is NamedColumn<R, C> => 'name' in column;

export { isNamed };
