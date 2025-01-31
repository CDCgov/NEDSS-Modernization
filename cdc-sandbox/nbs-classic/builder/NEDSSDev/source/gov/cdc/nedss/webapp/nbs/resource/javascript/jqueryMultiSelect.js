if(jQuery) (function($){
	
	$.extend($.fn, {
	        text:function(o,callback){
	        
	        if( !o ) var o = {};
		if( o.selectAll == undefined ) o.selectAll = true;
		if( o.selectAllText == undefined ) o.selectAllText = "(Select All)";
		if( o.noneSelected == undefined ) o.noneSelected = 'Select options';
		if( o.oneOrMoreSelected == undefined ) o.oneOrMoreSelected = '% selected';
		if( o.actionMode == undefined)  o.actionMode  = 'InitLoad';
			
	        $(this).each( function() {
					var select = $(this);
					
					str = $(select).attr('name');
					var appendText = str.substring($(select).attr('name').indexOf("(")+1,($(select).attr('name').length)-1);					
					
					str = str.substring(0,$(select).attr('name').indexOf("("))+"Text"+str.substring($(select).attr('name').indexOf("("),$(select).attr('name').length);
					var flag = 'disabled';
					var valueOfFilter='';
					if(appendText=='SearchText1'){
						var valueOfFilter = getElementByIdOrByName("SearchText1").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}
					if(appendText=='SearchText2'){
						var valueOfFilter = getElementByIdOrByName("SearchText2").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}				
					if(appendText=='SearchText3'){
						var valueOfFilter = getElementByIdOrByName("SearchText3").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}
					if(appendText=='SearchText4'){
						var valueOfFilter = getElementByIdOrByName("SearchText4").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}
					if(appendText=='SearchText5'){
						var valueOfFilter = getElementByIdOrByName("SearchText5").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}
					if(appendText=='SearchText6'){
						var valueOfFilter = getElementByIdOrByName("SearchText6").value;					
						if(valueOfFilter.length >0){
						 flag = '';					 
						}
					}
				
				
					var html = '<img class="multiSelect" src="combo_select.gif" alt="Filter Select" tabIndex="0" title="Filter Select" id="queueIcon" align="top" border="0"/>';
					html += '<div class="multiTextOptions" style="position: absolute;z-index: 99999; display: none;">';
					html +='<label title="OK/Cancel buttons" style="text-align:center;background: #CFCFCF;white-space:nowrap;">';
					html +='&nbsp;&nbsp;&nbsp;&nbsp;<p style="display: none">OK/Cancel buttons</p><input id="b1'+appendText+'" type="button" onclick="selectfilterCriteria()" value=" OK " '+flag+' style="width:40%;" />';
					html +='&nbsp;<input title="sajfsajf" id="b2Text" type="button" value="Cancel" style="width:40%;" />&nbsp;&nbsp;&nbsp;&nbsp;</label>';
					html += '<br>';
					html += '<br>';					
					html += '<label><b>Contains:</b></label>';
					html += '<label title = "Contains" class="pText"><p style="display: none">Contains</p><input type="text" title="Contains" size="25" name="' + str +'" value="'+valueOfFilter+'" id="'+appendText+'" onKeyUp="onKeyUpValidate()"/></label>' ;
					html += '<br>';
					html += '<br>';
					html +='<label title="OK/Cancel buttons" style="text-align:center;background: #CFCFCF;white-space:nowrap;">';
					html +='&nbsp;&nbsp;&nbsp;&nbsp;<p style="display: none">OK/Cancel buttons</p><input id="b2'+appendText+'"  type="button" onclick="selectfilterCriteria()" value=" OK " '+flag+' style="width:40%;"/>';
					html +='&nbsp;<input id="b2Text"  type="button" value="Cancel" style="width:40%;" />&nbsp;&nbsp;&nbsp;&nbsp;';
					html += '</label></div>';
					
					html += '<div style="visibility:hidden">';
					$(select).find('OPTION').each( function() {
						if( $(this).val() != '' ) {
							html += '<label class="pText"><input type="checkbox" title="Select/Deselect checkbox" name="' + $(select).attr('name') + '" value="' + $(this).val() + '"';							
							html += 'checked="checked" />' + $(this).html() + '</label>';
						}
					});
					
					html += '</div>'; 
					
					//alert(html);
					$(select).after(html);

					// Events
					$(select).next('.multiSelect').mouseover( function() {
						$(this).addClass('hover');
					}).mouseout( function() {
						$(this).removeClass('hover');
					}).click( function() {						
						var divElt = getElementByIdOrByName("blockparent");
						divElt.style.display = "block";													

						// Show/hide on click
						if( $(this).hasClass('active') ) {
							$(this).multiTextHide();
						} else {
							$(this).multiTextShow();
						}
						return false;
						}).keydown( function(e) {

					if(e.keyCode==13){				
						var divElt = getElementByIdOrByName("blockparent");
						divElt.style.display = "block";													

						// Show/hide on click
						if( $(this).hasClass('active') ) {
							$(this).multiTextHide();
						} else {
							$(this).multiTextShow();
						}
						return false;
						
						}
					}).focus( function() {
						// So it can be styled with CSS
						$(this).addClass('focus');
					}).blur( function() {
						// So it can be styled with CSS
						$(this).removeClass('focus');
					});

					$(select).next('.multiSelect').next('.multiTextOptions').find('INPUT:button').click( function() {
					    var divElt = getElementByIdOrByName("blockparent");
					    divElt.style.display = "none";					
					    $(".button[@value != 'Cancel']").each( function() { this.disabled = false } );
						cancelFilter($(select).attr('name'));

					    $(this).parent().parent().prev('.multiSelect').removeClass('active').next('.multiTextOptions').hide();
					});

					// Eliminate the original form element
					$(select).remove();
			});
 
	        },
		multiSelect: function(o, callback) {
			// Default options
			if( !o ) var o = {};
			if( o.selectAll == undefined ) o.selectAll = true;
			if( o.selectAllText == undefined ) o.selectAllText = "(Select All)";
			if( o.noneSelected == undefined ) o.noneSelected = 'Select options';
			if( o.oneOrMoreSelected == undefined ) o.oneOrMoreSelected = '% selected';
			if( o.actionMode == undefined)  o.actionMode  = 'InitLoad';
			// Initialize each multiSelect
			$(this).each( function() {
				var select = $(this);
				//var html = '<input type="text" readonly="readonly" class="multiSelect" value="" style="cursor: default;" />';
				var html = '<img class="multiSelect" src="combo_select.gif" alt="Filter Select" tabIndex="0" title="Filter Select" id="queueIcon" align="top" border="0"/>';
				html += '<div class="multiSelectOptions" style="position: absolute;z-index: 99999; display: none;">';
				html +='<label title="OK/Cancel buttons" style="text-align:center;background: #CFCFCF;white-space:nowrap;"><p style="display: none">OK/Cancel buttons</p><input id="b1" class="button" type="button" onclick="selectfilterCriteria()" value=" OK " style="width:40%;" title="Apply Filter"/><input id="b2" class="button" type="button" value="Cancel" style="width:40%;" title="Cancel Filter"/></label>';
				if( o.selectAll ) html += '<label class="selectAll"><input type="checkbox" title="Select/Deselect checkbox" class="selectAll" />' + o.selectAllText + '</label>';
				$(select).find('OPTION').each( function() {
					if( $(this).val() != '' ) {
						html += '<label class="pText"><input type="checkbox" title="Select/Deselect checkbox" name="' + $(select).attr('name') + '" value="' + $(this).val() + '"';
						if( $(this).attr('selected') && $(this).attr('defaultSelected') ) {
							html += ' checked="checked"';
						} else {
						}
						html += ' />' + $(this).html() + '</label>';
					}
				});
				html +='<label style="text-align:center;background: #CFCFCF;white-space:nowrap;"><p style="display: none">OK/Cancel buttons</p><input id="b1" class="button" type="button" onclick="selectfilterCriteria()" value=" OK " style="width:40%;" title="Apply Filter"/><input id="b2" class="button" type="button" value="Cancel" style="width:40%;" title="Cancel Filter"/></label>';
				html += '</div>';
				$(select).after(html);
				
				// Events
				$(select).next('.multiSelect').mouseover( function() {
					$(this).addClass('hover');
				}).mouseout( function() {
					$(this).removeClass('hover');
				}).click( function() {
					//$("div.pamview").css({display: block});
					var divElt = getElementByIdOrByName("blockparent");
					divElt.style.display = "block";													
						
					// Show/hide on click
					if( $(this).hasClass('active') ) {
						$(this).multiSelectOptionsHide();
					} else {
						$(this).multiSelectOptionsShow();
					}
					return false;
				}).focus( function() {
					// So it can be styled with CSS
					$(this).addClass('focus');
				}).blur( function() {
					// So it can be styled with CSS
					$(this).removeClass('focus');
				});
				
				// Determine if Select All should be checked initially
				if( o.selectAll ) {
					var sa = true;
					$(select).next('.multiSelect').next('.multiSelectOptions').find('INPUT:checkbox').not('.selectAll').each( function() {
						if( !$(this).attr('checked') ) sa = false;
					});
					if( sa ) $(select).next('.multiSelect').next('.multiSelectOptions').find('INPUT.selectAll').attr('checked', true).parent().addClass('checked');
				}
				
				if( o.actionMode == 'InitLoad' ) {
					$(this).parent().parent().find('INPUT:checkbox').attr('checked', true).parent().addClass('checked');
				}
				// Handle Select All
				$(select).next('.multiSelect').next('.multiSelectOptions').find('INPUT.selectAll').click( function() {
					if( $(this).attr('checked') == true ) $(this).parent().parent().find('INPUT:checkbox').attr('checked', true).parent().addClass('checked'); else $(this).parent().parent().find('INPUT:checkbox').attr('checked', false).parent().removeClass('checked');
				});
				
				// Handle checkboxes
				$(select).next('.multiSelect').next('.multiSelectOptions').find('INPUT:checkbox').click( function() {																																					
					$(this).parent().parent().multiSelectUpdateSelected(o);
					$(this).parent().parent().find('LABEL').removeClass('checked').find('INPUT:checked').parent().addClass('checked');
					$(this).parent().parent().prev('.multiSelect').focus();
					if( !$(this).attr('checked') ) {					
						$(this).parent().parent().find('INPUT:checkbox.selectAll').attr('checked', false).parent().removeClass('checked');
					}
					if( callback ) callback($(this));
				});
				$(select).next('.multiSelect').next('.multiSelectOptions').find('INPUT:button').click( function() {
					    var divElt = getElementByIdOrByName("blockparent");
					    divElt.style.display = "none";					
					    $(".button[@value != 'Cancel']").each( function() { this.disabled = false } );
						cancelFilter($(select).attr('name'));
						
					$(this).parent().parent().prev('.multiSelect').removeClass('active').next('.multiSelectOptions').hide();
				});
				// Initial display
				$(select).next('.multiSelect').next('.multiSelectOptions').each( function() {
					$(this).multiSelectUpdateSelected(o);
					$(this).find('INPUT:checked').parent().addClass('checked');
				});
				
				// Handle hovers
				$(select).next('.multiSelect').next('.multiSelectOptions').find('LABEL').mouseover( function() {
					$(this).parent().find('LABEL').removeClass('hover');
					$(this).addClass('hover');
				}).mouseout( function() {
					$(this).parent().find('LABEL').removeClass('hover');
				});
				
				// Keyboard
				$(select).next('.multiSelect').keydown( function(e) {
					// Is dropdown visible?
					if( $(this).next('.multiSelectOptions').is(':visible') ) {
						// Dropdown is visible
						// Tab
						if( e.keyCode == 9 ) {
							$(this).addClass('focus').trigger('click'); // esc, left, right - hide
							$(this).focus().next(':input').focus();
							return true;
						}
						
						// ESC, Left, Right
						if( e.keyCode == 27 || e.keyCode == 37 || e.keyCode == 39 ) {
							// Hide dropdown
							$(this).addClass('focus').trigger('click');
						}
						// Down
						if( e.keyCode == 40 ) {
							if( !$(this).next('.multiSelectOptions').find('LABEL').hasClass('hover') ) {
								// Default to first item
								$(this).next('.multiSelectOptions').find('LABEL:first').addClass('hover');
							} else {
								// Move down, cycle to top if on bottom
								$(this).next('.multiSelectOptions').find('LABEL.hover').removeClass('hover').next('LABEL').addClass('hover');
								if( !$(this).next('.multiSelectOptions').find('LABEL').hasClass('hover') ) {
									$(this).next('.multiSelectOptions').find('LABEL:first').addClass('hover');
								}
							}
							return false;
						}
						// Up
						if( e.keyCode == 38 ) {
							if( !$(this).next('.multiSelectOptions').find('LABEL').hasClass('hover') ) {
								// Default to first item
								$(this).next('.multiSelectOptions').find('LABEL:first').addClass('hover');
							} else {
								// Move up, cycle to bottom if on top
								$(this).next('.multiSelectOptions').find('LABEL.hover').removeClass('hover').prev('LABEL').addClass('hover');
								if( !$(this).next('.multiSelectOptions').find('LABEL').hasClass('hover') ) {
									$(this).next('.multiSelectOptions').find('LABEL:last').addClass('hover');
								}
							}
							return false;
						}
						// Enter, Space
						if( e.keyCode == 13 || e.keyCode == 32 ) {
							// Select All
							if( $(this).next('.multiSelectOptions').find('LABEL.hover INPUT:checkbox').hasClass('selectAll') ) {
								if( $(this).next('.multiSelectOptions').find('LABEL.hover INPUT:checkbox').attr('checked') ) {
									// Uncheck all
									$(this).next('.multiSelectOptions').find('INPUT:checkbox').attr('checked', false).parent().removeClass('checked');
								} else {
									// Check all
									$(this).next('.multiSelectOptions').find('INPUT:checkbox').attr('checked', true).parent().addClass('checked');
								}
								$(this).next('.multiSelectOptions').multiSelectUpdateSelected(o);
								if( callback ) callback($(this));
								return false;
							}
							// Other checkboxes
							if( $(this).next('.multiSelectOptions').find('LABEL.hover INPUT:checkbox').attr('checked') ) {
								// Uncheck
								$(this).next('.multiSelectOptions').find('LABEL.hover INPUT:checkbox').attr('checked', false);
								$(this).next('.multiSelectOptions').multiSelectUpdateSelected(o);
								$(this).next('.multiSelectOptions').find('LABEL').removeClass('checked').find('INPUT:checked').parent().addClass('checked');
								// Select all status can't be checked at this point
								$(this).next('.multiSelectOptions').find('INPUT:checkbox.selectAll').attr('checked', false).parent().removeClass('checked');
								if( callback ) callback($(this));
							} else {
								// Check
								$(this).next('.multiSelectOptions').find('LABEL.hover INPUT:checkbox').attr('checked', true);
								$(this).next('.multiSelectOptions').multiSelectUpdateSelected(o);
								$(this).next('.multiSelectOptions').find('LABEL').removeClass('checked').find('INPUT:checked').parent().addClass('checked');
								if( callback ) callback($(this));
							}
						}
						return false;
					} else {
						// Dropdown is not visible
						if( e.keyCode == 38 || e.keyCode == 40 || e.keyCode == 13 || e.keyCode == 32 ) { // down, enter, space - show
							// Show dropdown
							$(this).removeClass('focus').trigger('click');
							$(this).next('.multiSelectOptions').find('LABEL:first').addClass('hover');
							return false;
						}
						//  Tab key
						if( e.keyCode == 9 ) {
							// Shift focus to next INPUT element on page
							$(this).focus().next(':input').focus();
							return true;
						}
					}
					// Prevent enter key from submitting form
					if( e.keyCode == 13 ) return false;
				});
				
				// Eliminate the original form element
				$(select).remove();
			});
			
		},
		
		// Hide the dropdown
		multiSelectOptionsHide: function() {
			//$(this).removeClass('active').next('.multiSelectOptions').hide();
		},
		
		// Show the dropdown
		multiSelectOptionsShow: function() {
			// Hide any open option boxes
			$('.multiSelect').multiSelectOptionsHide();
			$(this).next('.multiSelectOptions').find('LABEL').removeClass('hover');			
			$(this).addClass('active').next('.multiSelectOptions').show();
			
			// Position it
			var offset = $(this).offset();
			
			//Added for IE11
			var diff = (document.body.offsetWidth - offset.left);
			var width = $(this).next('.multiSelectOptions').css("width").replace("px","");

			if(diff<width){
				var left = width-diff;
				offset.left=offset.left-left;
			}
			
			$(this).next('.multiSelectOptions').css({ top:  offset.top + $(this).outerHeight() + 'px' });
			$(this).next('.multiSelectOptions').css({ left: offset.left + 'px' });
			
			// Disappear on hover out
			multiSelectCurrent = $(this);
			var timer = '';
			$(this).next('.multiSelectOptions').hover( function() {
				clearTimeout(timer);
			}, function() {
				timer = setTimeout('$(multiSelectCurrent).multiSelectOptionsHide(); $(multiSelectCurrent).unbind("hover");', 250);
			});
			
		},
		
		// Hide the dropdown
		multiTextHide: function() {
			//$(this).removeClass('active').next('.multiSelectOptions').hide();
		},
		
		// Show the dropdown
		multiTextShow: function() {
			// Hide any open option boxes
			$('.multiSelect').multiSelectOptionsHide();
			$(this).next('.multiTextOptions').find('LABEL').removeClass('hover');			
			$(this).addClass('active').next('.multiTextOptions').show();
			
			// Position it
			var offset = $(this).offset();
			
			//Added for IE11
			var diff = (document.body.offsetWidth - offset.left);
			var width = $(this).next('.multiTextOptions').css("width").replace("px","");

			if(diff<width){
				var left = width-diff;
				offset.left=offset.left-left;
			}
			
			$(this).next('.multiTextOptions').css({ top:  offset.top + $(this).outerHeight() + 'px' });
			$(this).next('.multiTextOptions').css({ left: offset.left + 'px' });
			$(this).next('.multiTextOptions').css({ overflow: 'hidden' });//Firefox
			$(this).next('.multiTextOptions').css({ height: '150px' });//Firefox
			// Disappear on hover out
			multiSelectCurrent = $(this);
			var timer = '';
			$(this).next('.multiTextOptions').hover( function() {
				clearTimeout(timer);
			}, function() {
				timer = setTimeout('$(multiSelectCurrent).multiTextHide(); $(multiSelectCurrent).unbind("hover");', 250);
			});
			
		},
		
		
		// Update the textbox with the total number of selected items
		multiSelectUpdateSelected: function(o) {
			var i = 0, s = '';
			$(this).find('INPUT:checkbox:checked').not('.selectAll').each( function() {
				i++;
			})
			if( i == 0 ) {
				$(this).prev('INPUT.multiSelect').val( o.noneSelected );
			$(".button[@value != 'Cancel']").each( function() { this.disabled = true } );

			} else {
				$(".button[@value != 'Cancel']").each( function() { this.disabled = false } );
				if( o.oneOrMoreSelected == '*' ) {					
					var display = '';
					$(this).find('INPUT:checkbox:checked').each( function() {
						if( $(this).parent().text() != o.selectAllText ) display = display + $(this).parent().text() + ', ';
					});
					display = display.substr(0, display.length - 2);
					$(this).prev('INPUT.multiSelect').val( display );
				} else {
					$(this).prev('INPUT.multiSelect').val( o.oneOrMoreSelected.replace('%', i) );
				}
			}
		}
		
	});
	
})(jQuery);