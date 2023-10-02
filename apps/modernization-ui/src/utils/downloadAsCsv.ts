/**
 * downloadAsCsv
 * @param {BlobPart} data
 * @param {string} fileName
 * @return {void}
 */
export const downloadAsCsv = ({ data, fileName, fileType }: { data: any; fileName: string; fileType: string }) => {
    const blob = new Blob([data], { type: fileType });
    const a = document.createElement('a');
    a.download = fileName;
    a.href = window.URL.createObjectURL(blob);
    a.click();
};
