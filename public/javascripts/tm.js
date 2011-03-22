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
        var selectedId = getSelectedNode(data);
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
 * Checks whether a tree node in a listener callback is one of the given types
 * @param data the jsTree data object from a callback
 * @param types the types to check against
 */
function isSelectedNodeType(data, types) {
    return isSelectedNodeOfType(data.inst, data.rslt.obj, types);
}

/**
 * Checks whether a tree node is of a given type
 * @param treeInstance the jsTree instance
 * @param node the node to check the type of
 * @param types the types
 */
function isSelectedNodeOfType(treeInstance, node, types) {
    return $.inArray(treeInstance._get_type(node), types) > -1;
}


/**
 * Gets the selected tree node
 * @param data the jsTree callback data
 */
function getSelectedNode(data) {
    return data.rslt.obj.attr("id").replace("node_", "");
}

/**
 * Get a node within a tree by matching its type and ID
 *
 * FIXME fetch the objectId, somehow
 *
 * @param treeId the DOM ID of the tree
 * @param type the type of the node
 * @param nodeId the id of the object (it's not the DOM ID)
 */
function getTreeNode(treeId, type, nodeId) {
    return $('#' + treeId + ' li[id=node_' + nodeId + '][rel=' + type + ']');
}

/**
 * Refreshes the active tree node, for trees with the UI plugin (all of them until now)
 *
 * FIXME THIS DOES NOT WORK (see with jsTree folks)
 *
 * @param treeId the ID of the tree
 */
function refreshActiveTreeNode(treeId) {
    var treeContainer = $("#" + treeId);
    var $tree = jQuery.jstree._reference(treeContainer);
    var _selected = $tree.get_selected();
    alert(_selected);
    $tree.refresh(_selected);
}

/**
 * Refreshes a tree
 * @param treeId the ID of the tree container
 */
function refreshTree(treeId) {
  var treeContainer = $("#" + treeId);
  jQuery.jstree._reference(treeContainer).refresh();
  jQuery.jstree._reference(treeContainer).deselect_all();
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