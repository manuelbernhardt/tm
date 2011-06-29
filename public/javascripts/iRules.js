var data = $(document).data('iRules');

if (typeof data === 'undefined') {
    data = {
            interactionGroups: {}
        };
    $(document).data('iRules', data);
}

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
    }
}

