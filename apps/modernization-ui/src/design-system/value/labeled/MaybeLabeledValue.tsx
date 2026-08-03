import { Shown } from 'conditional-render';
import { exists } from 'utils';

import { LabeledValue, LabeledValueProps } from './LabeledValue';

type MaybeLabeledValueProps = LabeledValueProps & {
    children?: LabeledValueProps['children'];
};

const MaybeLabeledValue = ({ children, ...remaining }: MaybeLabeledValueProps) => (
    <Shown when={exists(children)}>
        <LabeledValue {...remaining}>{children}</LabeledValue>
    </Shown>
);

export { MaybeLabeledValue };
