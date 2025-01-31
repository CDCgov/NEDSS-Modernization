options linesize=max;
data templatePA02;
length descrip $35 col1 8 col2 8 col3 8 col4 8 col5 8;
informat _CHAR_ col1 _NUMERIC_ col2 _NUMERIC_ col3 _NUMERIC_ col4 _NUMERIC_ col5 _NUMERIC_;
format descrip $35. col1 best9. col2 best9. col3 best9. col4 best9. col5 best9.;
infile datalines truncover dsd dlm='!' ;
input descrip  col1  col2  col3 col4  col5 ;
datalines ;
Assigned:!0!0!0!0!0
Dispositioned:!0!0!0!0!0
Exam'd:!0!0!0!0!0
Exam'd w/in 3:!0!0!0!0!0
Exam'd w/in 5:!0!0!0!0!0
Exam'd w/in 7:!0!0!0!0!0
Exam'd w/in 14:!0!0!0!0!0
Dispo A:!0!0!0!0!0
Dispo B:!0!0!0!0!0
Dispo C:!0!0!0!0!0
Dispo D:!0!0!0!0!0
Dispo F:!0!0!0!0!0
Not Examined:!0!0!0!0!0
Dispo G:!0!0!0!0!0
Dispo H:!0!0!0!0!0
Dispo J:!0!0!0!0!0
Dispo K:!0!0!0!0!0
Dispo L:!0!0!0!0!0
Dispo V:!0!0!0!0!0
Dispo X:!0!0!0!0!0
Dispo Z:!0!0!0!0!0
Dispo E:!0!0!0!0!0
Open:!0!0!0!0!0
Non-assigned Dispos:!0!0!0!0!0
run;