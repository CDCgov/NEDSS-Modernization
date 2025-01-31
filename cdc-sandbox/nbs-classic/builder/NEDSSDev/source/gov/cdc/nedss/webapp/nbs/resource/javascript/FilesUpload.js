/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/

/*
*  Changed for Part 2, by Ken Cochrane
*  http://KenCochrane.net , http://CampRate.com , http://PopcornMonsters.com
*/

function refreshProgress()
{
    UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
        var fileIndex = uploadInfo.fileIndex;
        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);
        getElementByIdOrByName('progressBarText').innerHTML = 'upload in progress: ' + progressPercent + '%';
        getElementByIdOrByName('progressBarBoxContent').style.width = parseInt(progressPercent * 3.5) + 'px';
        window.setTimeout('refreshProgress()', 1000);
    }
    return true;
}

function startProgress()
{
    alert("inside startProgress()");
    updateStatusMessage("");
    getElementByIdOrByName('progressBar').style.display = 'block';
    getElementByIdOrByName('progressBarText').innerHTML = 'upload in progress: 0%';

    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
    return true;
}

function hideProgressBar()
{
    getElementByIdOrByName('progressBar').style.display = 'none';
    getElementByIdOrByName('progressBarText').innerHTML = '';
}

function UploadMonitor() { }
      UploadMonitor._path = '';
      UploadMonitor.getUploadInfo = function(callback) {
      DWREngine._execute(UploadMonitor._path, 'UploadMonitor', 'getUploadInfo', callback);
}
