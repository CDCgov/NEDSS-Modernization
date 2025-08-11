const displayNoData = () => '---';

const NoData = displayNoData;

const orNoData = (value?: string) => (value ? value : displayNoData());

export { NoData, displayNoData, orNoData };
