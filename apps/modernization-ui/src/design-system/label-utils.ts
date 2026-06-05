export type HasVisibleLabel = { label: string };
export type HasAriaLabel = { 'aria-label': string };

export type Labeled = HasVisibleLabel | HasAriaLabel | (HasVisibleLabel & HasAriaLabel);

export const isLabelVisible = (labeled: Labeled): labeled is HasVisibleLabel => 'label' in labeled;
