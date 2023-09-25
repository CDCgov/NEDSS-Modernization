/**
 * downloadAsCsv
 * @param {BlobPart} data
 * @param {string} fileName
 * @return {void}
 */
export const downloadAsCsv = ({ data, fileName }: { data: BlobPart; fileName: string }) => {
    const blob = new Blob([data], { type: 'text/csv' });
    const a = document.createElement('a');
    a.download = fileName;
    a.href = window.URL.createObjectURL(blob);

    const clickEvt = new MouseEvent('click', {
        view: window,
        bubbles: true,
        cancelable: true
    });

    a.dispatchEvent(clickEvt);
    a.remove();
};
