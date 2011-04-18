/*************/
/* DataTable */
/*************/

/**
 * Selection handler for a table row (the table must have as first column an "id" column, typically hidden)
 * @param tableId the table ID
 * @param handler the handler to execute on selection
 */
function handleRowSelection(tableId, handler) {
    $("#" + tableId + " tbody").click(function(event) {
        var dataTable = $("#" + tableId).dataTable();
        $(dataTable.fnSettings().aoData).each(function () {
            $(this.nTr).removeClass('row_selected');
        });
        $(event.target.parentNode).addClass('row_selected');

        var selectedRowId = getSelectedRowId(dataTable);
        if (handler !== null) {
            handler(selectedRowId);
        }
    });
}

/**
 * Refreshes he contents of a table by calling fnDraw. If a partial refresh is done, the selection state is also kept
 * @param tableId the ID of the table
 * @param completeRefresh whether this should also re-draw pagination etc.
 */
function refreshTableContents(tableId, completeRefresh) {
    var dataTable = $("#" + tableId).dataTable();
    var selected = getSelectedRowId(dataTable);
    if (!completeRefresh && (selected != null || selected !== 'undefined')) {
        // this is the internal API, let's hope it won't change
        dataTable.fnSettings().aoDrawCallback.push({
            "fn": function () {
                // re-select the selected row
                fnSelectRow(dataTable, selected);
            },
            "sName": "user"
        });
    }
    dataTable.fnDraw(completeRefresh);
}

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
 * Programatically selects a row given an ID (the table must have as first column an "id" column, typically hidden)
 * @param oTableLocal DataTable instance
 * @param rowId the ID of the row to select
 */
function fnSelectRow(oTableLocal, rowId) {
    var data = oTableLocal.fnGetData();
    var index = -1;
    for (var i = 0; i < data.length; i++) {
        if ((data[i])[0] == rowId) {
            index = i;
            break;
        }
    }
    if (index > -1) {
        $(oTableLocal.fnGetNodes()[index]).addClass('row_selected');
    }
}

/**
 * Reloads all tables in the DOM
 */
function reloadTables() {
    $.each($('table.display'), function(index, el) {
        $(el).dataTable().fnDraw()
    });
}


/***********/
/* JSTree */
/*********/


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
        var selectedId = getNodeIdFromData(data);
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
function getNodeIdFromData(data) {
    return extractId(data.rslt.obj.attr("id"));
}


function getSelectedNodeId(treeId) {
    var tree = $.jstree._reference($('#' + treeId));
    if (typeof tree.get_selected().attr('id') !== 'undefined') {
        return extractId(tree.get_selected().attr('id'));
    }
    return null;
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
    return $('#' + treeId + ' li[id=node_' + type + '_' + nodeId + '][rel=' + type + ']');
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

function extractId(id) {
    return id.substring(id.lastIndexOf("_") + 1);
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

/********************/
/* Form utilities  */
/******************/

/**
 * Remove all jQuery dialogs in the DOM tree. This is a bug of jQuery, which adds parts of the dialog
 * to the root DOM node instead of the child to which it is added, causing troubles when using a dialog
 * inside of a AJAX tab for example
 */
function removeDialogs() {
    $(".modalDialog").remove();
}


/**
 * jQuery plugin for ox.forms
 */
(function($) {

    var methods = {
        init : function(options) {
            return this.each(function() {

                var $this = $(this);
                var data = $this.data('loadForm');
                if (!data) {
                    if (!options.loadAction) {
                        if ($this.validate({meta: 'validate'}).form()) {
                            $this.ajaxForm(function() {
                                if (typeof options.submissionCallback == 'function') {
                                    options.submissionCallback.call();
                                }
                                $('#' + $this.attr('id') + '_submit').button('disable');
                                $this.resetForm();
                            });
                        }
                    } else {
                        $this.data('loadForm', {
                            'loadAction': options.loadAction,
                            'submissionCallback': options.submissionCallback,
                            'fields': getFields($this),
                            'viewModel': {
                                'authenticityToken': $this.find('input[name="authenticityToken"]').val(),
                                'submitForm': function(formElement) {
                                    if ($(formElement).validate({meta: 'validate'}).form()) {
                                        $.postKnockoutJSJson($(formElement).attr('action'), this, function(submitData) {
                                            var sCallback = $(formElement).data('loadForm').submissionCallback;
                                            if (typeof sCallback == 'function') {
                                                sCallback.call();
                                            }
                                            $('#' + $(formElement).attr('id') + '_submit').button('disable');
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            });
        },
        load : function(id) {
            return this.each(function() {
                var $this = $(this);
                var loadFormData = $this.data('loadForm');
                if (!loadFormData.loadAction) {
                    alert("Error: Cannot use load() method when no loadAction is passed at initialization");
                    return;
                }
                var query = {"baseObjectId": id, "fields": loadFormData.fields };
                var viewModel = loadFormData.viewModel;
                $.getJSON(loadFormData.loadAction, query, function(data) {
                    if (!ko.mapping.isMapped(viewModel)) {
                        viewModel = $.extend(viewModel, ko.mapping.fromJS(data));
                        ko.applyBindings(viewModel);
                    } else {
                        ko.mapping.updateFromJS(viewModel, data);
                    }
                });
            });
        }
    }

    $.fn.loadForm = function(method) {

        // Method calling logic
        if (methods[method]) {
            return methods[ method ].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || ! method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.loadForm');
        }

    };

})
        (jQuery);


/**
 * Helper method to fetch all fields for the ox.forms
 * @param form the form to harvest the fields of
 */
function getFields(form) {
    var fields = [];
    $.each(form.find('.oxfield'), function(index, el) {
        fields.push($(el).attr('name'));
    });
    return fields;
}

/**
 * Post knockoutJS form data as JSON string
 * @param url the URL to submit to
 * @param data the data object, of which keys are of the sort value_some_thing
 * @param callback the callback to run after successful execution
 */
$.postKnockoutJSJson = function (url, data, callback) {
    var formData = {};
    $.each(ko.toJS(data), function(key, value) {
        if (key.startsWith('value_')) {
            formData[key.substring(6).replace(/_/g, '.')] = value;
        }
        if (key == 'authenticityToken') {
            formData[key] = value;
        }
    });

    return jQuery.ajax({
        type: 'POST',
        url: url,
        data: formData,
        contentType: 'application/x-www-form-urlencoded; charset=utf-8',
        dataType: 'json'
    }).success(callback).error(function (jqhr, text) {
        alert(text);
    });
}

/**
 * Various global utilities
 */
if (!String.prototype.startsWith) {
    String.prototype.startsWith = function (str) {
        return !this.indexOf(str);
    }
}