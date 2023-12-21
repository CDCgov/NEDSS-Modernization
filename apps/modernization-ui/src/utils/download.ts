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

export const download = (fileName: string, base64File: string, fileType: string) => {
    // auto gen methods force application/json so we have to convert from base64 to bytes to blob
    const binaryString = atob(base64File);
    const bytes = new Uint8Array(binaryString.length);
    for (let i = 0; i < binaryString.length; i++) {
        bytes[i] = binaryString.charCodeAt(i);
    }
    const blob = new Blob([bytes.buffer], { type: fileType });
    const a = document.createElement('a');
    a.download = fileName;
    a.href = window.URL.createObjectURL(blob);
    a.click();
};
