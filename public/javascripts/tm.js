/* DataTable */

function getSelectedRowId(dataTable) {
    var anSelected = fnGetSelected(dataTable);
    var tr = anSelected[0];
    var selectedObject = $($(tr).children('td')[0]).text();
    return selectedObject;
}

/* Get the rows which are currently selected */
function fnGetSelected(oTableLocal) {
    var aReturn = new Array();
    var aTrs = oTableLocal.fnGetNodes();

    for (var i = 0; i < aTrs.length; i++) {
        if ($(aTrs[i]).hasClass('row_selected')) {
            aReturn.push(aTrs[i]);
        }
    }
    return aReturn;
}

function removeDialogs() {
    // bug with jQuery tabs / jQuery dialogs not disappearing after being added
    // in fact, the dialog divs are being added to the main html tree, outside of the tab
    // we have to remove all instances when a tab is reloaded
    $(".modalDialog").remove();
}

/* custom validation method for our select lists */
function registerSelectNoneValidator() {
    $.validator.addMethod("selectNone", function(value, element) {
        if (element.value == -1) {
            return false;
        }
        return true;
    }, "Please select an option");
}



