function repositoryTree(route) {

    $("#repositoryTree").jstree({
        "plugins" : [ "json_data", "ui", "crrm",  "types", "contextmenu", "hotkeys", "themeroller" ],

        "json_data" : {
            "ajax" : {
                "url" : route,
                "data" : function (n) {
                    return {
                        "id" : n.attr ? extractId(n.attr("id")) : -1,
                        "type" : n.attr ? n.attr("rel") : "",
                        "treeId" : "repositoryTree"
                    };
                }
            }
        },
        "ui" : {
            "select_limit": 1
        },
        "themeroller": {
            "item": ""
        },
        "types" : {
            "max_depth" : -2,
            "max_children" : -2,
            "types" : {
                "default" : {
                    "valid_children" : "none",
                    "icon" : {
                        "image" : "/public/images/jstree/file.png" // TODO icon for test script
                    }
                },
                "scriptFolder" : {
                    "valid_children" : [ "default", "scriptFolder" ],
                    "icon" : {
                        "image" : "/public/images/jstree/folder.png" // TODO icon for release
                    },
                    "start_drag" : false,
                    "move_node" : false,
                    "delete_node" : true,
                    "remove" : true
                }
            }
        },
        "hotkeys": {
            "Ctrl+x" : function() {
                this.cut(null)
            },
            "Ctrl+v" : function() {
                this.paste(null)
            },
            "Ctrl+n" : function() {
                this.create_node(null, "after", {attr:{"rel":"default"}});
            }
        }
    });
}
