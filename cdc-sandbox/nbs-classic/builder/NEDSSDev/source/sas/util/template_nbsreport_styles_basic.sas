proc template;                                                                
   define style Styles.Basic / store = TEMPLATE.NBSREPORT;                   
      style fonts /                                                           
         'docFont' = ("Arial, Helvetica, Helv",3)                             
         'headingFont' = ("Arial, Helvetica, Helv",2.5,Bold)                  
         'headingEmphasisFont' = ("Arial, Helvetica, Helv",4,Bold Italic)     
         'FixedFont' = ("Courier",2)                                          
         'BatchFixedFont' = ("SAS Monospace, Courier",2)                      
         'FixedHeadingFont' = ("Courier",2)                                   
         'FixedStrongFont' = ("Courier",2,Bold)                               
         'FixedEmphasisFont' = ("Courier",2,Italic)                           
         'EmphasisFont' = ("Arial, Helvetica, Helv",3,Italic)                 
         'StrongFont' = ("Arial, Helvetica, Helv",4,Bold)                     
         'TitleFont' = ("Arial, Helvetica, Helv",5,Bold)                      
         'FootNoteFont2' = ("Arial, Helvetica, Helv",2.5)                     
         'TitleFont2' = ("Arial, Helvetica, Helv",2.5);                       
      style color_list                                                        
         "Colors used in the default style" /                                 
         'bgA' = cxE0E0E0                                                     
         'fgA' = cx002288                                                     
         'bgA1' = cxF0F0F0                                                    
         'fgA1' = cx000000                                                    
         'bgA2' = cxB0B0B0                                                    
         'fgA2' = cx0033AA                                                    
         'bgA3' = cxD3D3D3                                                    
         'bgA4' = cx880000                                                    
         'fgA4' = cxAAFFAA                                                    
         'fgB1' = cx004488                                                    
         'fgB2' = cx0066AA;                                                   
      style colors                                                            
         "Abstract colors used in the default style" /                        
         'docbg' = color_list('bgA')                                          
         'docfg' = color_list('fgA')                                          
         'contentbg' = color_list('bgA2')                                     
         'contentfg' = color_list('fgA2')                                     
         'link1' = color_list('fgB1')                                         
         'link2' = color_list('fgB2')                                         
         'Contitlefg' = color_list('fgA')                                     
         'Confolderfg' = color_list('fgA')                                    
         'Conentryfg' = color_list('fgA')                                     
         'systitlebg' = color_list('bgA')                                     
         'systitlefg' = color_list('fgA')                                     
         'titlebg' = color_list('bgA')                                        
         'titlefg' = color_list('fgA')                                        
         'proctitlebg' = color_list('bgA')                                    
         'proctitlefg' = color_list('fgA')                                    
         'captionbg' = color_list('bgA')                                      
         'captionfg' = color_list('fgA1')                                     
         'bylinebg' = color_list('bgA2')                                      
         'bylinefg' = color_list('fgA2')                                      
         'notebg' = color_list('bgA')                                         
         'notefg' = color_list('fgA')                                         
         'tablebg' = color_list('bgA1')                                       
         'tableborder' = color_list('fgA1')                                   
         'batchbg' = color_list('bgA3')                                       
         'batchfg' = color_list('fgA1')                                       
         'databg' = color_list('bgA3')                                        
         'datafg' = color_list('fgA1')                                        
         'databgstrong' = color_list('bgA3')                                  
         'datafgstrong' = color_list('fgA1')                                  
         'databgemph' = color_list('bgA3')                                    
         'datafgemph' = color_list('fgA1')                                    
         'headerbg' = color_list('bgA2')                                      
         'headerfg' = color_list('fgA2')                                      
         'headerbgstrong' = color_list('bgA2')                                
         'headerfgstrong' = color_list('fgA2')                                
         'headerbgemph' = color_list('bgA2')                                  
         'headerfgemph' = color_list('fgA2');                                 
      style html                                                              
         "Common HTML text used in the default style" /                       
         'fake bullet' = %nrstr("<b>&#183;</b>")                              
         'PageBreakLine' =                                                    
         %nrstr("<p style=""page-break-after: always;"">&#160</p><HR size=3>" 
)                                                                             
         'Line' = "<HR size=3>"                                               
         'break' = "<br>"                                                     
         'prehtml flyover' = "<SPAN>"                                         
         'posthtml flyover' = "</SPAN>"                                       
         'prehtml flyover bullet' = %nrstr("<SPAN><b>&#183;</b>")             
         'prehtml flyover line' = "<SPAN><HR size=3>"                         
         'posthtml flyover line' = "</SPAN><HR size=3>"                       
         'expandAll' = "<SPAN onClick=""if(msie4==1)expandAll()"">";          
      style text                                                              
         "Common text." /                                                     
         'prefix1' = "The "                                                   
         'suffix1' = " Procedure"                                             
         'Content Title' = "Table of Contents"                                
         'Pages Title' = "Table of Pages"                                     
         'Note Banner' = "NOTE:"                                              
         'Warn Banner' = "WARNING:"                                           
         'Error Banner' = "ERROR:"                                            
         'Fatal Banner' = "FATAL:";                                           
      style StartUpFunction                                                   
         "Controls the StartUp Function. TAGATTR is only element used.";      
      style ShutDownFunction                                                  
         "Controls the Shut-Down Function. TAGATTR is only element used.";    
      style Container                                                         
         "Abstract. Controls all container oriented elements." /              
         background = colors('docbg')                                         
         foreground = colors('docfg')                                         
         font = Fonts('DocFont');                                             
      style Index from Container                                              
         "Abstract. Controls Contents and Pages." /                           
         background = colors('contentbg')                                     
         foreground = colors('contentfg');                                    
      style Document from Container                                           
         "Abstract. Controls the various document bodies." /                  
         visitedlinkcolor = colors('link1')                                   
         linkcolor = colors('link2')                                          
         protectspecialchars = auto                                           
         htmlcontenttype = "text/html"                                        
         htmldoctype =                                                        
         "<!DOCTYPE HTML PUBLIC ""-//W3C//DTD HTML 3.2 Final//EN"">";         
      style Body from Document                                                
         "Controls the Body file." /                                          
         pagebreakhtml = html('PageBreakLine')                                
         leftmargin = 8                                                       
         rightmargin = 8;                                                     
      style Frame from Document                                               
         "Controls the Frame file." /                                         
         frameborder = on                                                     
         frameborderwidth = 4                                                 
         framespacing = 1                                                     
         contentsize = 23%                                                    
         contentscrollbar = auto                                              
         bodysize = *                                                         
         bodyscrollbar = auto                                                 
         contentposition = L;                                                 
      style Contents from Document                                            
         "Controls the Contents file." /                                      
         leftmargin = 8                                                       
         rightmargin = 8                                                      
         background = colors('contentbg')                                     
         foreground = colors('contentfg')                                     
         pagebreakhtml = html('break')                                        
         tagattr = " onload=""if (msie4 == 1)expandAll()"""                   
         bullet = "decimal";                                                  
      style Pages from Document                                               
         "Controls the Pages file." /                                         
         leftmargin = 8                                                       
         rightmargin = 8                                                      
         background = colors('contentbg')                                     
         foreground = colors('contentfg')                                     
         pagebreakhtml = html('break')                                        
         tagattr = " onload=""if (msie4 == 1)expandAll()"""                   
         bullet = "decimal";                                                  
      style Date from Container                                               
         "Abstract. Controls how date fields look." /                         
         foreground = colors('contentfg')                                     
         background = colors('contentbg')                                     
         outputwidth = 100%;                                                  
      style BodyDate from Date                                                
         "Controls the date field in the Body file." /                        
         foreground = colors('docfg')                                         
         background = colors('docbg')                                         
         cellpadding = 0                                                      
         cellspacing = 0;                                                     
      style ContentsDate from Date                                            
         "Controls the date in the Contents file.";                           
      style PagesDate from Date                                               
         "Controls the date in the Pages file.";                              
      style IndexItem from Container                                          
         "Abstract. Controls list items and folders for Contents and Pages."  
         /                                                                    
         foreground = colors('conentryfg')                                    
         background = colors('contentbg')                                     
         bullet = NONE                                                        
         listentryanchor = on                                                 
         prehtml = html('prehtml flyover bullet')                             
         posthtml = html('posthtml flyover')                                  
         leftmargin = 6pt;                                                    
      style ContentFolder from IndexItem                                      
         "Controls the generic folder definition in the Contents file." /     
         foreground = colors('confolderfg')                                   
         listentryanchor = off;                                               
      style ByContentFolder from ContentFolder                                
         "Controls the byline folder in the Contents file.";                  
      style ContentItem from IndexItem                                        
         "Controls the leafnode item in the Contents file.";                  
      style PagesItem from IndexItem                                          
         "Controls the leafnode item in the Pages file.";                     
      style IndexProcName from Index                                          
         "Abstract. Controls the proc name in the list files." /              
         foreground = colors('contitlefg')                                    
         pretext = text('prefix1')                                            
         posttext = text('suffix1')                                           
         prehtml = html('prehtml flyover')                                    
         posthtml = html('posthtml flyover')                                  
         bullet = "decimal"                                                   
         listentryanchor = off;                                               
      style ContentProcName from IndexProcName                                
         "Controls the proc name in the Contents file.";                      
      style ContentProcLabel from ContentProcName                             
         "Controls the proc label in the Contents file." /                    
         pretext = _undef_                                                    
         posttext = _undef_;                                                  
      style PagesProcName from IndexProcName                                  
         "Controls the proc name in the Pages file.";                         
      style PagesProcLabel from PagesProcName                                 
         "Controls the proc label in the Pages file." /                       
         pretext = _undef_                                                    
         posttext = _undef_;                                                  
      style IndexAction from Index                                            
         "Abstract. Determines what happens on mouse-over events for foldersa 
nd items.";                                                                   
      style FolderAction from IndexAction                                     
         "Determines what happens on mouse-over events for folders.";         
      style IndexTitle from Index                                             
         "Abstract. Controls the title of Contents and Pages files." /        
         foreground = colors('contitlefg')                                    
         font = fonts('EmphasisFont')                                         
         prehtml = html('expandAll')                                          
         posthtml = html('posthtml flyover line');                            
      style ContentTitle from IndexTitle                                      
         "Controls the title of the Contents file." /                         
         pretext = text('content title');                                     
      style PagesTitle from IndexTitle                                        
         "Controls the title of the Pages file." /                            
         pretext = text('pages title');                                       
      style SysTitleAndFooterContainer from Container                         
         "Controls container for system page title and system page footer." / 
         borderwidth = 0                                                      
         cellspacing = 1                                                      
         cellpadding = 1                                                      
         outputwidth = 100%                                                   
         frame = VOID                                                         
         rules = NONE;                                                        
      style TitleAndNoteContainer from Container                              
         "Controls container for procedure defined titles and notes." /       
         borderwidth = 0                                                      
         cellspacing = 1                                                      
         cellpadding = 1                                                      
         outputwidth = 100%                                                   
         frame = VOID                                                         
         rules = NONE;                                                        
      style TitlesAndFooters from Container                                   
         "Abstract. Controls system page title text and system page footer te 
xt." /                                                                        
         foreground = colors('systitlefg')                                    
         background = colors('systitlebg')                                    
         font = Fonts('TitleFont2');                                          
      style BylineContainer from Container                                    
         "Controls container for the byline." /                               
         borderwidth = 0                                                      
         cellspacing = 1                                                      
         cellpadding = 1                                                      
         outputwidth = 100%                                                   
         frame = VOID                                                         
         rules = NONE                                                         
         background = colors('Docbg');                                        
      style SystemTitle from TitlesAndFooters                                 
         "Controls system title text." /                                      
         font = Fonts('TitleFont');                                           
      style SystemFooter from TitlesAndFooters                                
         "Controls system footer text." /                                     
         font = Fonts('FootNoteFont2');                                       
      style PageNo from TitlesAndFooters                                      
         "Controls page numbers for printer" /                                
         font = fonts('strongFont')                                           
         cellpadding = 0                                                      
         cellspacing = 0;                                                     
      style ExtendedPage from TitlesAndFooters                                
         "Msg when page won't fit." /                                         
         font = fonts('EmphasisFont')                                         
         frame = box                                                          
         pretext = "Continuing contents of page "                             
         posttext = ", which would not fit on a single physical page"         
         fillrulewidth = 0.5pt                                                
         borderwidth = 1pt                                                    
         cellpadding = 2pt                                                    
         just = C;                                                            
      style Byline from TitlesAndFooters                                      
         "Controls byline text." /                                            
         foreground = colors('bylinefg')                                      
         background = colors('bylinebg')                                      
         font = fonts('headingFont')                                          
         cellpadding = 0                                                      
         cellspacing = 0;                                                     
      style ProcTitle from TitlesAndFooters                                   
         "Controls procedure title text." /                                   
         foreground = colors('proctitlefg')                                   
         background = colors('proctitlebg');                                  
      style ProcTitleFixed from ProcTitle                                     
         "Controls procedure title text, fixed font." /                       
         font = fonts('FixedStrongFont');                                     
      style Output from Container                                             
         "Abstract. Controls basic output forms." /                           
         borderwidth = 1                                                      
         bordercolor = colors('tableborder')                                  
         cellspacing = 1                                                      
         cellpadding = 7                                                      
         frame = BOX                                                          
         rules = GROUPS                                                       
         background = colors('tablebg');                                      
      style Table from Output                                                 
         "Controls overall table style.";                                     
      style Batch from Output                                                 
         "Controls batch mode output." /                                      
         background = colors('batchbg')                                       
         foreground = colors('batchfg')                                       
         font = fonts('BatchFixedFont');                                      
      style Graph from Output                                                 
         "Controls rudimentary graph output." /                               
         background = colors('docbg')                                         
         cellspacing = 0                                                      
         cellpadding = 0;                                                     
      style Note from Container                                               
         "Abstract. Controls the container for note banners and note contents 
." /                                                                          
         foreground = colors('notefg')                                        
         background = colors('notebg');                                       
      style NoteBanner from Note                                              
         "Controls the banner for NOTE:s." /                                  
         pretext = text('Note Banner');                                       
      style NoteContent from Note                                             
         "Controls the contents for NOTE:s.";                                 
      style UserText from Note                                                
         "Controls the TEXT= style";                                          
      style NoteContentFixed from NoteContent                                 
         "Controls the contents for NOTE:s. Fixed font." /                    
         font = fonts('FixedFont');                                           
      style WarnBanner from Note                                              
         "Controls the banner for WARNING:s." /                               
         pretext = text('Warn Banner');                                       
      style WarnContent from Note                                             
         "Controls the contents of WARNING:s.";                               
      style WarnContentFixed from WarnContent                                 
         "Controls the contents for WARNING:s. Fixed font." /                 
         font = fonts('FixedFont');                                           
      style ErrorBanner from Note                                             
         "Controls the banner for ERROR:s." /                                 
         pretext = text('Error Banner');                                      
      style ErrorContent from Note                                            
         "Controls the contents of ERROR:s.";                                 
      style ErrorContentFixed from ErrorContent                               
         "Controls the contents for ERROR:s. Fixed font." /                   
         font = fonts('FixedFont');                                           
      style FatalBanner from Note                                             
         "Controls the banner for FATAL:s." /                                 
         pretext = text('Fatal Banner');                                      
      style FatalContent from Note                                            
         "Controls the contents of FATAL:s.";                                 
      style FatalContentFixed from FatalContent                               
         "Controls the contents for FATAL:s. Fixed font." /                   
         font = fonts('FixedFont');                                           
      style Cell from Container                                               
         "Abstract. Controls general cells.";                                 
      style Data from Cell                                                    
         "Default style for data cells in columns." /                         
         background = colors('databg')                                        
         foreground = colors('datafg');                                       
      style DataFixed from Data                                               
         "Default style for data cells in columns. Fixed font." /             
         font = fonts('FixedFont');                                           
      style DataEmpty from Data                                               
         "Controls empty data cells in columns.";                             
      style DataEmphasis from Data                                            
         "Controls emphasized data cells in columns." /                       
         font = fonts('EmphasisFont')                                         
         background = colors('databgemph')                                    
         foreground = colors('datafgemph');                                   
      style DataEmphasisFixed from DataEmphasis                               
         "Controls emphasized data cells in columns. Fixed font." /           
         font = fonts('FixedEmphasisFont');                                   
      style DataStrong from Data                                              
         "Controls strong (more emphasized) data cells in columns." /         
         font = fonts('StrongFont')                                           
         background = colors('databgstrong')                                  
         foreground = colors('datafgstrong');                                 
      style DataStrongFixed from DataStrong                                   
         "Controls strong (more emphasized) data cells in columns. Fixed font 
." /                                                                          
         font = fonts('FixedStrongFont');                                     
      style HeadersAndFooters from Cell                                       
         "Abstract. Controls table headers and footers." /                    
         background = colors('headerbg')                                      
         foreground = colors('headerfg')                                      
         font = fonts('HeadingFont');                                         
      style Caption from HeadersAndFooters                                    
         "Abstract. Controls caption field in proc tabulate." /               
         background = colors('captionbg')                                     
         foreground = colors('captionfg')                                     
         cellpadding = 0                                                      
         cellspacing = 0;                                                     
      style BeforeCaption from Caption                                        
         "Caption that comes before a table.";                                
      style AfterCaption from Caption                                         
         "Caption that comes after a table.";                                 
      style Header from HeadersAndFooters                                     
         "Controls the headers of a table.";                                  
      style HeaderFixed from Header                                           
         "Controls the header of a table. Fixed font." /                      
         font = fonts('FixedFont');                                           
      style HeaderEmpty from Header                                           
         "Controls empty table header cells.";                                
      style HeaderEmphasis from Header                                        
         "Controls emphasized table header cells." /                          
         font = fonts('EmphasisFont')                                         
         background = colors('headerbgemph')                                  
         foreground = colors('headerfgemph');                                 
      style HeaderEmphasisFixed from HeaderEmphasis                           
         "Controls emphasized table header cells. Fixed font." /              
         font = fonts('FixedEmphasisFont');                                   
      style HeaderStrong from Header                                          
         "Controls strong (more emphasized) table header cells." /            
         font = fonts('StrongFont')                                           
         background = colors('headerbgstrong')                                
         foreground = colors('headerfgstrong');                               
      style HeaderStrongFixed from HeaderStrong                               
         "Controls strong (more emphasized) table header cells. Fixed font."  
         /                                                                    
         font = fonts('FixedStrongFont');                                     
      style RowHeader from Header                                             
         "Controls row headers.";                                             
      style RowHeaderFixed from RowHeader                                     
         "Controls row headers. Fixed font." /                                
         font = fonts('FixedFont');                                           
      style RowHeaderEmpty from RowHeader                                     
         "Controls empty row headers.";                                       
      style RowHeaderEmphasis from RowHeader                                  
         "Controls emphasized row headers." /                                 
         font = fonts('EmphasisFont');                                        
      style RowHeaderEmphasisFixed from RowHeaderEmphasis                     
         "Controls emphasized row headers. Fixed font." /                     
         font = fonts('FixedEmphasisFont');                                   
      style RowHeaderStrong from RowHeader                                    
         "Controls strong (more emphasized) row headers." /                   
         font = fonts('StrongFont');                                          
      style RowHeaderStrongFixed from RowHeaderStrong                         
         "Controls strong (more emphasized) row headers. Fixed font." /       
         font = fonts('FixedStrongFont');                                     
      style Footer from HeadersAndFooters                                     
         "Controls table footers.";                                           
      style FooterFixed from Footer                                           
         "Controls table footers. Fixed font." /                              
         font = fonts('FixedFont');                                           
      style FooterEmpty from Footer                                           
         "Controls empty table footers.";                                     
      style FooterEmphasis from Footer                                        
         "Controls emphasized table footers." /                               
         font = fonts('EmphasisFont');                                        
      style FooterEmphasisFixed from FooterEmphasis                           
         "Controls emphasized table footers. Fixed font." /                   
         font = fonts('FixedEmphasisFont');                                   
      style FooterStrong from Footer                                          
         "Controls strong (more emphasized) table footers." /                 
         font = fonts('StrongFont');                                          
      style FooterStrongFixed from FooterStrong                               
         "Controls strong (more emphasized) table footers. Fixed font." /     
         font = fonts('FixedStrongFont');                                     
      style RowFooter from Footer                                             
         "Controls a row footer (label).";                                    
      style RowFooterFixed from RowFooter                                     
         "Controls a row footer (label). Fixed font." /                       
         font = fonts('FixedFont');                                           
      style RowFooterEmpty from RowFooter                                     
         "Controls an empty row footer (label).";                             
      style RowFooterEmphasis from RowFooter                                  
         "Controls an emphasized row footer (label)." /                       
         font = fonts('EmphasisFont');                                        
      style RowFooterEmphasisFixed from RowFooterEmphasis                     
         "Controls an emphasized row footer (label). Fixed font." /           
         font = fonts('FixedEmphasisFont');                                   
      style RowFooterStrong from RowFooter                                    
         "Controls a strong (more emphasized) row footer (label)." /          
         font = fonts('StrongFont');                                          
      style RowFooterStrongFixed from RowFooterStrong                         
         "Controls a strong (more emphasized) row footer (label). Fixed font. 
" /                                                                           
         font = fonts('FixedStrongFont');                                     
   end;                                                                       
run;
