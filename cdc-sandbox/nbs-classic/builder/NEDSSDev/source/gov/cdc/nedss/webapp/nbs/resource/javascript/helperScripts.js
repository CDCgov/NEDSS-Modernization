
function getYear() {
 var dt = new Date();
 var year = dt.getFullYear();
 return( year );
}
     

function getWeekNum() {
 var now = new Date();
 return( getWeek(y2k(now.getFullYear()),now.getMonth(),now.getDate()) );
}

function y2k(number) { return ((number < 1000) ? number + 1900 : number); }

function getWeek(year,month,day) {
    var when = new Date(year,month,day);
    var newYear = new Date(year,0,1);
    var modDay = newYear.getDay();
    if (modDay == 0) modDay=6; else modDay--;

    var daynum = ((Date.UTC(y2k(year),when.getMonth(),when.getDate(),0,0,0) -
                 Date.UTC(y2k(year),0,1,0,0,0)) /1000/60/60/24) + 1;

    if (modDay < 4 ) {
        var weeknum = Math.floor((daynum+modDay-1)/7)+1;
    }
    else {
        var weeknum = Math.floor((daynum+modDay-1)/7);
        if (weeknum == 0) {
            year--;
            var prevNewYear = new Date(year,0,1);
            var prevmodDay = prevNewYear.getDay();
            if (prevmodDay == 0) prevmodDay = 6; else prevmodDay--;
            if (prevmodDay < 4) weeknum = 53; else weeknum = 52;
        }
    }

    return( weeknum );
}


function getWindowWidth() {
  var width = -1;
  if (document.layers) {
    width = window.innerWidth;
  }
  else if (document.all) {
    width = document.body.clientWidth;
  }
  return( width );
}


function getWindowHeight() {
  var height = -1;
  if (document.layers) {
    height = window.innerHeight;
  }
  else if (document.all) {
    height = document.body.clientHeight;
  }
  return( height );
}





