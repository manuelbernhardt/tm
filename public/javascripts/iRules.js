// next steps:
// registration of named rules
// usage of named rules in groups or standalone


var data = $(document).data('iRules');

if (typeof data === 'undefined') {
    data = {
            interactionGroups: {}
        };
    $(document).data('iRules', data);
}

var iRuleGroupClass = {
    rule: function(targetSelector, actionClosure) {
        iGroupRule(this.groupName, targetSelector, actionClosure);
        return this;
    }
};


function iRule(eventSourceSelector, eventType, conditionClosure, targetSelector, actionClosure) {
    $(eventSourceSelector).bind(eventType, function(event, data) {

        var call = (typeof conditionClosure === 'function' && conditionClosure(event, data)) || (typeof conditionClosure === 'boolean' && conditionClosure) || conditionClosure == null
        if (call) {
            // TODO check if the closure is valid
            actionClosure($(targetSelector), data, event);
        }
    });
}

function iGroupRule(groupName, targetSelector, actionClosure) {
    // TODO check if the group exists
    var group = data.interactionGroups[groupName];
    iRule(group.eventSourceSelector, group.eventType, group.conditionClosure, targetSelector, actionClosure);
}

function iGroup(groupName, eventSourceSelector, eventType, conditionClosure) {
    data.interactionGroups[groupName] = {
        eventSourceSelector: eventSourceSelector,
        eventType: eventType,
        conditionClosure: conditionClosure
    };
    iRuleGroupClass.groupName = groupName;
    return iRuleGroupClass;
}

// common functions
var $hide = function(targets) { targets.hide() };
var $show = function(targets) { targets.show() };
var $enableButton = function(targets) { targets.button('enable') };
var $openDialog = function(targets) { targets.dialog('open') };



