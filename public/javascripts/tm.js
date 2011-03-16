/*************/
/* DataTable */
/*************/

/**
 * Gets the ID of the currently selected row
 * @param dataTable DataTable instance
 */
function getSelectedRowId(dataTable) {
    var anSelected = fnGetSelected(dataTable);
    var tr = anSelected[0];
    var selectedObject = $($(tr).children('td')[0]).text();
    return selectedObject;
}

/**
 * Get the rows which are currently selected
 * @param oTableLocal DataTable instance
 */
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

/**
 * Selection handler for "tree menus".
 *
 * @param tabLinks array of links in the order of the tabs on the page
 * @param selectionNodeTypes types of the nodes that can be selected in the menu
 * @param selectionType type of the object that is selected (used for the URL query parameter name)
 * @param tabsContainer DOM node containing the tabs
 * @param data jsTree data object of the selection callback
 */
function treeNodeSelectionHandler(tabLinks, selectionNodeTypes, selectionType, tabsContainer, data) {
    if (isSelectedNodeType(data, selectionNodeTypes)) {
        var selectedId = data.rslt.obj.attr("id").replace("node_", "");
        selectTab(tabLinks, selectedId, selectionType, tabsContainer);
    }
}

/**
 * Selects a jQuery AJAX tab and updates the URLs given a new object ID
 *
 * @param tabLinks array of links in the order of the tabs on the page
 * @param selectedId the ID of the selected object
 * @param selectionType type of the object that is selected (used for the URL query parameter name)
 * @param tabsContainer DOM node containing the tabs
 */
function selectTab(tabLinks, selectedId, selectionType, tabsContainer) {
    var $tabs = tabsContainer.tabs();
    $.each(tabLinks, function(i, url) {
        $tabs.tabs('url', i, url + '?' + selectionType + 'Id=' + selectedId);
    });
    removeDialogs();

    var selected = $tabs.tabs('option', 'selected');
    $tabs.tabs('load', selected);
}


/**
 * Checks whether a tree node is of the given type
 * @param data the jsTree data object from a callback
 * @param types the types to check against
 */
function isSelectedNodeType(data, types) {
    return $.contains(types, data.inst._get_type(data.rslt.obj));
}

/**
 * Remove all jQuery dialogs in the DOM tree. This is a bug of jQuery, which adds parts of the dialog
 * to the root DOM node instead of the child to which it is added, causing troubles when using a dialog
 * inside of a AJAX tab for example
 */
function removeDialogs() {
    $(".modalDialog").remove();
}

/********************/
/* Form validation  */
/********************/

/**
 * Registers a custom validator for use with select drop-down boxes (makes sure a value is selected)
 * This looks for "-1" as a "unselected" value, hence this is too what needs to be passed for the "Please selection an option" option.
 */
function registerSelectNoneValidator() {
    $.validator.addMethod("selectNone", function(value, element) {
        if (element.value == -1) {
            return false;
        }
        return true;
    }, "Please select an option");
}