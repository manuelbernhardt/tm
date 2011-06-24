/*************/
/* DataTable */
/*************/

/**
 * Selection handler for a table row (the table must have as first column an "id" column, typically hidden)
 * @param tableSelector the table selector
 * @param handler the handler to execute on selection
 */
function handleRowSelection(tableSelector, handler) {
    $(tableSelector + " tbody").click(function(event) {
        var dataTable = $(tableSelector).dataTable();
        $(dataTable.fnSettings().aoData).each(function () {
            $(this.nTr).removeClass('row_selected');
        });
        $(event.target.parentNode).addClass('row_selected');

        var selectedRowId = getSelectedRowId(tableSelector);
        if (handler !== null) {
            handler(selectedRowId);
        }
    });
}

/**
 * Refreshes he contents of a table by calling fnDraw. If a partial refresh is done, the selection state is also kept
 * @param tableSelector the selector of the table
 * @param completeRefresh whether this should also re-draw pagination etc.
 */
function refreshTableContents(tableSelector, completeRefresh, selectRow) {
    var dataTable = $(tableSelector).dataTable();
    var selected = getSelectedRowId(tableSelector);
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
    if (completeRefresh) {
        $(dataTable.fnSettings().aoData).each(function () {
            $(this.nTr).removeClass('row_selected');
        });
    }
    dataTable.fnDraw(completeRefresh);
    // select row after full refresh
    if(selectRow && (selected != null || selected !== 'undefined')){
        dataTable.fnSettings().aoDrawCallback.push({
                    "fn": function () {
                        // re-select the selected row
                        fnSelectRow(dataTable, selected);
                    },
                    "sName": "user"
                });
    }
}

/**
 * Gets the ID of the currently selected row
 * @param tableSelector a selector that gets the datatable
 */
function getSelectedRowId(tableSelector) {
    var table = $(tableSelector).dataTable();
    return getSelectedRowColumn(table, 0);
}

function getSelectedRowColumn(dataTable, columnIndex) {
    var anSelected = fnGetSelected(dataTable);
    var tr = anSelected[0];
    return $($(tr).children('td')[columnIndex]).text();
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
 * Programmatically selects a row given an ID (the table must have as first column an "id" column, typically hidden)
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

// hides pagination if all results fit one page

function handlePagination(tableId, data){

       if($(tableId).find("tr:not(.ui-widget-header)").length <= data._iDisplayLength && data._iDisplayLength > data._iDisplayStart ||  data._iDisplayLength==false){
           $(tableId + '_paginate')[0].style.display = "none";
       } else {
          $(tableId + '_paginate')[0].style.display = "block";
       }
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

function getSelectedNode(treeId) {
    var tree = $.jstree._reference($('#' + treeId));
    if (typeof tree.get_selected().attr('id') !== 'undefined') {
        return tree.get_selected();
    }
    return null;
}

function getSelectedNodeName(treeId) {
    var tree = $.jstree._reference($('#' + treeId));
    if (typeof tree.get_selected().attr('id') !== 'undefined') {
        return tree.get_selected().children("a").text().trim();
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
 *
 * Configuration parameters:
 * - loadAction: URL of the action that will render field valuen given a list of fields
 * - submissionParameters: additional parameters sent upon form submission
 * - submissionCallback: callback ran after form submission
 *
 * Methods:
 *
 * - load(objectId, submissionParameters)
 *   loads the form data given an objectId. it is possible to pass additional submission parameters
 *
 * - submitCreateForm(submissionParameters)
 *   submits a new form, eventually with additional parameters
 *
 */
(function($) {

    var methods = {
        init : function(options) {
            return this.each(function() {

                var $this = $(this);
                var data = $this.data('oxForm');
                var authToken = $this.find('input[name="authenticityToken"]');
                if (!data) {
                    if (options.loadAction) {
                        $this.data('oxForm', {
                                    'loadAction': options.loadAction,
                                    'submissionCallback': options.submissionCallback,
                                    'submissionParameters': options.submissionParameters ? options.submissionParameters : {},
                                    'fields': getFields($this),
                                    'viewModel': {
                                        'authenticityToken': (typeof authToken !== 'undefined' ? authToken.val() : ''),
                                        'submitForm': function(formElement) {
                                            if ($(formElement).validate({meta: 'validate'}).form()) {
                                                var oxFormData = $(formElement).data('oxForm');
                                                var additionalData = null;
                                                if (typeof oxFormData.submissionParameters !== 'undefined') {
                                                    additionalData = $.type(oxFormData.submissionParameters) === 'function' ? oxFormData.submissionParameters.call() : oxFormData.submissionParameters;
                                                }
                                                $.postKnockoutJSJson($(formElement).attr('action'), $(formElement).attr('id'), this, additionalData, function(submitData) {
                                                    var sCallback = oxFormData.submissionCallback;
                                                    if (typeof sCallback == 'function') {
                                                        sCallback.call();
                                                    }
                                                    $('#' + $(formElement).attr('id') + '_submit').button('disable');
                                                });
                                            }
                                        }
                                    }
                                });
                    } else {
                        $this.data('oxForm', {
                                    'submissionCallback': options.submissionCallback,
                                    'submissionParameters': options.submissionParameters ? options.submissionParameters : {}
                                });
                    }
                }
                return $this;
            });
        },
        load : function(id, submissionParameters) {
            return this.each(function() {
                var form = this;
                var $this = $(this);
                var oxFormData = $this.data('oxForm');
                if (!oxFormData.loadAction) {
                    alert("Error: Cannot use load() method when no loadAction is passed at initialization");
                    return;
                }
                if (typeof submissionParameters !== 'undefined') {
                    $.extend(oxFormData.submissionParameters, submissionParameters);
                }
                var query = {"baseObjectId": id, "fields": oxFormData.fields };
                $.getJSON(oxFormData.loadAction, query, function(data) {
                    if (!ko.mapping.isMapped(oxFormData.viewModel)) {
                        $.extend(oxFormData.viewModel, ko.mapping.fromJS(data));
                        ko.applyBindings(oxFormData.viewModel, form);
                    } else {
                        ko.mapping.updateFromJS(oxFormData.viewModel, data);
                    }
                    $this.find('input[type="submit"]').button('disable')
                });
            });
        },
        submitCreateForm : function(submissionParameters) {
            var $this = $(this);
            var oxFormData = $this.data('oxForm');
            if ($this.validate({meta: 'validate'}).form()) {
                if (typeof submissionParameters !== 'undefined') {
                    $.extend(oxFormData.submissionParameters, submissionParameters);
                }
                $this.ajaxSubmit({
                            success: function() {
                                if (typeof oxFormData.submissionCallback == 'function') {
                                    oxFormData.submissionCallback.call();
                                }
                                $('#' + $this.attr('id') + '_submit').button('disable');
                                $this.resetForm();
                            },
                            data: $.type(oxFormData.submissionParameters) === 'function' ? oxFormData.submissionParameters.call() : oxFormData.submissionParameters
                        });
                return true;
            } else {
                return false;
            }
        },
        getFieldId: function(fieldName) {
            return $(this).attr('id') + '_' + fieldName.replace(/\./g, '_');
        },
        destroy : function() {

            return this.each(function() {

                var $this = $(this);
                var data = $this.data('oxForm');

                data.oxForm.remove();
                $this.removeData('oxForm');

            })

        }
    };

    $.fn.oxForm = function(method) {

        // Method calling logic
        if (methods[method]) {
            return methods[ method ].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || ! method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.loadForm');
        }

    };

})(jQuery);


/**
 * Helper method to fetch all fields for the ox.forms.
 * The first field is the id of the form.
 *
 * @param form the form to harvest the fields of
 */
function getFields(form) {
    var fields = [];
    fields.push(form.attr('id'));
    $.each(form.find('.oxfield'), function(index, el) {
        fields.push($(el).attr('name'));
    });
    $.each(form.find('.oxdisplayfield'), function(index, el) {
        fields.push($(el).attr('id').substring(form.attr('id').length + 1).replace(/_/g, '.'));
    });
    return fields;
}

/**
 * Post knockoutJS form data as JSON string
 * @param url the URL to submit to
 * @param formId ID of the form to submit
 * @param viewModelData the data object, of which keys are of the sort value_formId_some_thing
 * @param callback the callback to run after successful execution
 */
$.postKnockoutJSJson = function (url, formId, viewModelData, additionalData, callback) {
    var formData = {};
    if (additionalData) {
        $.extend(formData, additionalData);
    }
    $.each(ko.toJS(viewModelData), function(key, value) {
        if (key.indexOf('value_') == 0) {
            formData[key.substring(7 + formId.length).replace(/_/g, '.')] = value;
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
            })
            .success(callback)
            .error(errorHandler);
};

/**
 * Custom knockoutJS handler for seamlessly handling tags binding. This handler makes it possible to
 * integrate knockoutJS and the jquery-token-plugin and this binds token values using a viewModel & updates them accordingly.
 */
ko.bindingHandlers.tags = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        viewModel.lock = false;
        var modelValue = valueAccessor();

        $(element).change(function() {
            if (!viewModel.lock) {
                var tokens = $(element).tokenInput('get');
                if (typeof tokens !== 'undefined') {
                    if (ko.isWriteableObservable(modelValue)) {
                        var existing = ko.utils.unwrapObservable(modelValue);
                        var existingNames = [];
                        $.each(existing, function(index, el) {
                            existingNames.push(typeof el.name == 'function' ? el.name() : el.name);
                        });
                        var updatedNames = [];
                        $.each(tokens, function(index, el) {
                            updatedNames.push(el.name);
                        });

                        // remove elements
                        $.each(existing, function(index, el) {
                            var name = typeof el.name == 'function' ? el.name() : el.name;
                            if ($.inArray(name, updatedNames) < 0) {
                                modelValue.remove(el);
                                tokens.splice(index, 1);
                            }
                        });
                        // add new
                        $.each(tokens, function(index, el) {
                            if ($.inArray(el.name, existingNames) < 0) {
                                modelValue.push({id: el.id, name: el.name});
                            }
                        });
                    }

                }
            }
        });


    },
    update:
            function(element, valueAccessor, allBindingsAccessor, viewModel) {
                viewModel.lock = true;
                $(element).tokenInput('clearAll');
                var value = valueAccessor();
                var tokens = ko.utils.unwrapObservable(value);
                $(tokens).each(function() {
                    var token = ko.utils.unwrapObservable(this);
                    $(element).tokenInput('add', typeof token.id == 'function' ? token.id() : token.id, typeof token.name == 'function' ? token.name() : token.name);
                });
                viewModel.lock = false;
            }
};


function toggleFilter(buttonId, divId) {
    $(document).ready(function() {
        $(buttonId).attr('style', 'float:right');
        $(divId).attr('style', 'display:none');
    });
    $(buttonId).button({icons: {primary: 'ui-icon-carat-1-e'}});

    $(buttonId).click(function() {
        $(buttonId + ' span.ui-icon').toggleClass('ui-icon-carat-1-s ui-icon-carat-1-e', 'switch');
        $(divId).slideToggle('slow');
    });
}

/**
 * Displays an error message in an error dialog
 * @param text the text to display
 * @param title the title of the error dialog
 */
function errorMessage(text, title) {
    if (title == null) {
        title = 'An error has occured';
    }

    var errorHtml = "<p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em'></span>" + text + "</p>";

    $('#errorDialog').html(errorHtml);
    $('#errorDialog').dialog({
                autoOpen: true,
                height: 200,
                width: 300,
                modal: true,
                title: title,
                resizable: false,
                buttons:{
                    "Close": function() {
                        $(this).dialog('close');
                    }
                }
            });
}

/**
 * Handler function for jQuery ajax requests that displays the correct error dialog.
 * This closure can be passed directly to a jQuery error() handler like e.g.
 *
 *    $.post(...).succes(...).error(errorHandler)
 *
 * @param xhr the XHR request object returned by a jQuery AJAX call
 * @param textStatus the status of the call
 * @param errorThrown the error thrown by the request
 */
function errorHandler(xhr, textStatus, errorThrown) {
    var text;
    if (xhr.status == 0) {
        text = "Connection with the Test Management server failed";
    } else if (xhr.status == 404 && xhr.responseText.indexOf("UNEXPECTED") != -1) {
        text = "Unexpected error.";
    } else if (xhr.status == 500 && xhr.responseText.indexOf("UNEXPECTED") != -1) {
        text = errorThrown;
    } else {
        text = xhr.responseText;
    }

    errorMessage(text);
}

/**
 * Confirmation dialog for deletion of objects
 * @param dialogId the ID of the dialog div in the page
 * @param type the type of the object (used in the title)
 * @param name the name of the object selected for deletion (used in the message)
 * @param callback a callback to execute after the object was deleted
 */
function deletionConfirmation(dialogId, type, name, callback) {
    $('#' + dialogId).dialog({
                autoOpen: true,
                width: 400,
                modal: true,
                buttons: {
                    "Confirm": function() {
                        if (typeof callback == 'function') {
                            callback.call();
                        }
                        $(this).dialog("close");
                    },
                    "Cancel": function() {

                        $(this).dialog("close");
                    }

                }
            });
    $('#' + dialogId).html(type + " '" + name + "' will be removed. Are you sure?");
}

/**
 * Initializes styles for all buttons in the application
 */
function applyButtonStyles() {
    $('.button-add').button({icons: {primary: 'ui-icon-plus'}});
    $('.button-edit').button({icons: {primary: 'ui-icon-pencil'}, disabled:true});
    $('.button-delete').button({icons: {primary: 'ui-icon-trash'}, text:false,  disabled:true});
    $('.button-add-folder').button({icons: {primary: 'ui-icon-plus', secondary: 'ui-icon-folder-open'}, text: false});
    $('.button-play').button({icons: {primary: 'ui-icon-play'}, disabled: true});
    applyDefectCommentsButtonStyles();
}

function applyDefectCommentsButtonStyles() {

    $('.button-edit-comment').button({icons: {primary: 'ui-icon-pencil'}, disabled:false});
    $('.button-delete-comment').button({icons: {primary: 'ui-icon-trash'}, disabled:false});
}

/**
 * jQuery plugin for filters
 */
(function($) {

    var methods = {
        init : function(options) {
            return this.each(function() {

                var $this = $(this);
                var data = $this.data('oxFilter');

                var settings = {
                    filterTemplate:
                            '<div style="width:100%; text-align:right;">' +
                                    '<button id="filterExpand" data-bind="enable: filterId != null">Expand filter</button>' +
                                    '<select title="Select a filter" id="filterSelect" data-bind="options:availableFilters, optionsValue: \'filterId\', optionsText: \'name\', optionsCaption: \'Select filter\' "></select>' +
                                    '</div>' +
                                    '<div id="filterSaveDialog" title="Save filter">' +
                                    '<label for="filterName" class="name">Name</label>' +
                                    '<input id="filterName" name="name"/>' +
                                    '</div>'
                };

                $.extend(settings, options);

                if (!data) {
                    $this.data('oxFilter', settings);
                }

                // initialize filter template
                $.tmpl(settings.filterTemplate).insertBefore($this);
                $this.hide();

                // filter loading
                var filterViewModel = {};
                var filterSelectionViewModel = {};
                loadFilters(settings.filterLoadAction(), filterSelectionViewModel);


                // initialize filter saving dialog
                $('#filterSaveDialog').dialog({
                            autoOpen: false,
                            modal: true,
                            resizable: false,
                            draggable: false,
                            buttons: {
                                "Confirm": function() {
                                    var dialog = $(this);
                                    var filterParams = settings.filterParameters.call();
                                    $.post(settings.filterSaveAction(), $.extend({"name": $('#filterName').val()}, filterParams))
                                            .success(function() {
                                                loadFilters(settings.filterLoadAction(), filterSelectionViewModel);
                                                dialog.dialog("close");
                                            })
                                            .error(errorHandler, function() {
                                                dialog.dialog("close");
                                            });
                                },
                                "Cancel": function() {
                                    $(this).dialog("close");
                                }

                            }
                        });

                // initialize buttons
                $('#saveFilter').button().click(function() {
                    $('#filterSaveDialog').dialog('open');
                });
                // changing button style to fit select input
                $('#filterExpand').css("height", "19px");
                $('#filterExpand').css("margin-bottom", "-0.4em");

                $('#filterExpand').button({icons: {primary: 'ui-icon-carat-1-e'}, text: false}).click(function() {
                    $('#filterExpand span.ui-icon').toggleClass('ui-icon-carat-1-s ui-icon-carat-1-e', 'switch');
                    $this.slideToggle('slow');
                });
                $('#filterExpand').children(".ui-button-text").attr("style", "padding:0.1em 0 0 0");

                // filter selection handling
                $('#filterSelect').button().change(function(data) {
                    if ($(this).val() != null && $(this).val() != '' && $(this).val() != 'customFilter') {
                        $.getJSON(settings.filterLoadByIdAction(), {id: $(this).val()},
                                function(jsonFilter) {
                                    var form = document.getElementById(settings.filterFormId);
                                    if (!ko.mapping.isMapped(filterViewModel)) {
                                        filterViewModel = ko.mapping.fromJS(jsonFilter);
                                        ko.applyBindings(filterViewModel, form);
                                    } else {
                                        ko.mapping.updateFromJS(filterViewModel, jsonFilter);
                                    }
                                    if (typeof settings.onSelect == 'function') {
                                        settings.onSelect.call();
                                    }
                                }).error(errorHandler);
                    }

                    if ($(this).val() == '') {
                        $this.slideUp('slow');
                    }

                    //clear the form in case a filter was previously applied
                    if ($(this).val() == 'customFilter') {
                        $(':input', '#' + settings.filterFormId)
                                .not(':button, :submit, :reset, :hidden')
                                .val('')
                                .removeAttr('checked')
                                .removeAttr('selected');
                        $this.slideDown('slow');
                    }
                });

                return $this;
            });
        }
    };

    $.fn.oxFilter = function(method) {

        // Method calling logic
        if (methods[method]) {
            return methods[ method ].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || ! method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.oxFilter');
        }

    };

})(jQuery);

function loadFilters(url, filterSelectionViewModel) {
    $.getJSON(url,
            function(json) {
                var form = document.getElementById('filterSelect');
                if (!ko.mapping.isMapped(filterSelectionViewModel)) {
                    filterSelectionViewModel = ko.mapping.fromJS(json);
                    ko.applyBindings(filterSelectionViewModel, form);
                }
                else {
                    ko.mapping.updateFromJS(filterSelectionViewModel, json);
                }
                $('#filterSelect').append("<option value='customFilter'>Create new filter</option>");
            }).error(errorHandler);

}

(function($) {

    var methods = {
        init : function(options) {
            return this.each(function() {
                var menu = $(this).next().menu({
                            select: function(event, ui) {
                                $(this).hide();
                                document.location = ui.item.children()[0].href;
                            }
                        });
                menu.hide();
                $(this).click(function(event) {
                    var menu = $(this).next();

                    if (menu.is(":visible")) {
                        menu.hide();
                        return false;
                    }

                    if ($('.ui-menu').size() > 1) {
                        $('.ui-menu').hide();
                    }

                    menu.menu("blur").show().position({
                                my: "right top",
                                at: "right bottom",
                                of: this
                            });
                    
                    if($(this).width()>menu.width()){
                        menu.width($(this).width()-5);
                    }
                    $(this).attr('style', 'text-align:center');
                    $(document).one("click", function() {
                        menu.hide();
                    });
                    return false;
                });
            });
        }
    };

    $.fn.oxMenu = function(method) {

        // Method calling logic
        if (methods[method]) {
            return methods[ method ].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || ! method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.oxMenu');
        }

    };

})(jQuery);
