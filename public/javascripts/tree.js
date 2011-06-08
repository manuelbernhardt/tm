(function($) {

    $.fn.tree = function(options) {

        var settings = {};

        var defaults = {
            "plugins" : [ "json_data", "ui", "types", "themeroller", "search"],
            "json_data" : {
                "ajax" : {
                    "url" : treeChildrenRouteAction(),
                    "data" : function (n) {
                        return {
                            "id" : n.attr ? extractId(n.attr("id")) : -1,
                            "treeId" : treeId,
                            "type" : n.attr ? n.attr("rel") : "",
                            "args" : options.args ? (typeof options.args == 'function' ? options.args.call() : options.args) : {}
                        };
                    }
                }
            },
            "themeroller": {
                "item": ""
            },
            "types" : options.types,
            "ui": {
                "select_limit": 1
            },
            "search": {
                "case_insensitive": true,
                "show_only_matches": true
            }
        };

        var multiPurposePlugins = ["crrm", "hotkeys", "contextmenu"];
        var multiPurpose = {
            "crrm": {
                "move": {
                    "always_copy": "multitree"
                }
            },
            "hotkeys": {
                "Ctrl+x" : function() {
                    this.cut(null)
                },
                "Ctrl+v" : function() {
                    this.paste(null)
                }
            }
        };

        $.extend(settings, defaults);

        if (options) {
            $.extend(settings, options);
            if (options.plugins) {
                settings.plugins = $.merge(options.plugins, defaults.plugins);
            }
            if(options.types.multipurpose) {
                $.extend(settings, multiPurpose);
                settings.plugins = $.merge(settings.plugins, multiPurposePlugins);
            }
        }

        var treeId = this.attr("id");

        this.jstree(settings).bind("create.jstree",
                function (e, data) {
                    $.post(treeCreateRouteAction(),
                    {
                        "treeId" : treeId,
                        "parentId": data.rslt.parent.attr ? extractId(data.rslt.parent.attr("id")) : -1,
                        "parentType": data.rslt.parent.attr ? data.rslt.parent.attr("rel") : "",
                        "position" : data.rslt.position,
                        "name" : data.rslt.name,
                        "type" : data.rslt.obj.attr("rel"),
                        "args" : data.rslt.obj.data("args") ? data.rslt.obj.data("args") : null

                    },
                            function (r) {
                                if (r.status) {
                                    $(data.rslt.obj).attr("id", "node_" + r.rel + "_" + r.id);
                                    $(data.rslt.obj).attr("rel", r.rel);
                                }
                                else {
                                    $.jstree.rollback(data.rlbk);
                                }
                            }
                            );
                }).bind("delete_node.jstree",
                function (e, data) {
                    data.rslt.obj.each(function () {
                        $.ajax({
                            async : false,
                            type: 'DELETE',
                            url: treeRemoveRouteAction(),
                            data : {
                                "treeId" : treeId,
                                "id" : extractId(this.id),
                                "parentId": data.rslt.parent.attr ? extractId(data.rslt.parent.attr("id")) : -1,
                                "type" : data.rslt.obj.attr("rel"),
                                "args" : data.rslt.obj.data("args") ? data.rslt.obj.data("args") : null

                            },
                            success : function (r) {
                                if (!r.status) {
                                    if(typeof options.onDeleteFailed === 'function') {
                                        options.onDeleteFailed.call();
                                    }
                                    data.inst.refresh();
                                }
                            }
                        });
                    });
                }).bind("rename.jstree",
                function (e, data) {
                    $.post(treeRenameRouteAction(),
                    {
                        "treeId" : treeId,
                        "id" : extractId(data.rslt.obj.attr("id")),
                        "name" : data.rslt.new_name,
                        "type" : data.rslt.obj.attr("rel")
                    },
                            function (r) {
                                if (!r.status) {
                                    $.jstree.rollback(data.rlbk);
                                } else {
                                    if(typeof options.onRename === 'function') {
                                        options.onRename.call(data.rslt.new_name, data.rslt.new_name);
                                    }
                                }
                            }
                            );
                }).bind("move_node.jstree", function (e, data) {
            data.rslt.o.each(function (i) {
                $.ajax({
                    async : false,
                    type: 'POST',
                    url: treeMoveRouteAction(),
                    data : {
                        "treeId" : treeId,
                        "id" : extractId($(this).attr("id")),
                        "type" : $(this).attr("rel"),
                        "target" : extractId(data.rslt.np.attr("id")),
                        "targetType" : data.rslt.np.attr("rel"),
                        "position" : data.rslt.cp + i,
                        "name" : data.rslt.name,
                        "copy" : data.rslt.cy ? true : false
                    },
                    success : function (r) {
                        if (!r.status) {
                            $.jstree.rollback(data.rlbk);
                        }
                        else {
                            $(data.rslt.oc).attr("id", "node_" + r.type + "_" + r.id);
                            if (data.rslt.cy && $(data.rslt.oc).children("UL").length) {
                                data.inst.refresh(data.inst._get_parent(data.rslt.oc));
                            }
                        }
                    }
                });
            })
        });

        return this;
    }
})(jQuery);