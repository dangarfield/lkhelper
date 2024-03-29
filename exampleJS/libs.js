var Handlebars = {};
(function (Handlebars, undefined) {
    Handlebars.VERSION = "1.0.0";
    Handlebars.COMPILER_REVISION = 4;
    Handlebars.REVISION_CHANGES = {
        1: "<= 1.0.rc.2",
        2: "== 1.0.0-rc.3",
        3: "== 1.0.0-rc.4",
        4: ">= 1.0.0"
    };
    Handlebars.helpers = {};
    Handlebars.partials = {};
    var toString = Object.prototype.toString,
        functionType = "[object Function]",
        objectType = "[object Object]";
    Handlebars.registerHelper = function (name, fn, inverse) {
        if (toString.call(name) === objectType) {
            if (inverse || fn) {
                throw new Handlebars.Exception("Arg not supported with multiple helpers")
            }
            Handlebars.Utils.extend(this.helpers, name)
        } else {
            if (inverse) {
                fn.not = inverse
            }
            this.helpers[name] = fn
        }
    };
    Handlebars.registerPartial = function (name, str) {
        if (toString.call(name) === objectType) {
            Handlebars.Utils.extend(this.partials, name)
        } else {
            this.partials[name] = str
        }
    };
    Handlebars.registerHelper("helperMissing", function (arg) {
        if (arguments.length === 2) {
            return undefined
        } else {
            throw new Error("Missing helper: '" + arg + "'")
        }
    });
    Handlebars.registerHelper("blockHelperMissing", function (context, options) {
        var inverse = options.inverse || function () {}, fn = options.fn;
        var type = toString.call(context);
        if (type === functionType) {
            context = context.call(this)
        }
        if (context === true) {
            return fn(this)
        } else if (context === false || context == null) {
            return inverse(this)
        } else if (type === "[object Array]") {
            if (context.length > 0) {
                return Handlebars.helpers.each(context, options)
            } else {
                return inverse(this)
            }
        } else {
            return fn(context)
        }
    });
    Handlebars.K = function () {};
    Handlebars.createFrame = Object.create || function (object) {
        Handlebars.K.prototype = object;
        var obj = new Handlebars.K;
        Handlebars.K.prototype = null;
        return obj
    };
    Handlebars.logger = {
        DEBUG: 0,
        INFO: 1,
        WARN: 2,
        ERROR: 3,
        level: 3,
        methodMap: {
            0: "debug",
            1: "info",
            2: "warn",
            3: "error"
        },
        log: function (level, obj) {
            if (Handlebars.logger.level <= level) {
                var method = Handlebars.logger.methodMap[level];
                if (typeof console !== "undefined" && console[method]) {
                    console[method].call(console, obj)
                }
            }
        }
    };
    Handlebars.log = function (level, obj) {
        Handlebars.logger.log(level, obj)
    };
    Handlebars.registerHelper("each", function (context, options) {
        var fn = options.fn,
            inverse = options.inverse;
        var i = 0,
            ret = "",
            data;
        var type = toString.call(context);
        if (type === functionType) {
            context = context.call(this)
        }
        if (options.data) {
            data = Handlebars.createFrame(options.data)
        }
        if (context && typeof context === "object") {
            if (context instanceof Array) {
                for (var j = context.length; i < j; i++) {
                    if (data) {
                        data.index = i
                    }
                    ret = ret + fn(context[i], {
                        data: data
                    })
                }
            } else {
                for (var key in context) {
                    if (context.hasOwnProperty(key)) {
                        if (data) {
                            data.key = key
                        }
                        ret = ret + fn(context[key], {
                            data: data
                        });
                        i++
                    }
                }
            }
        }
        if (i === 0) {
            ret = inverse(this)
        }
        return ret
    });
    Handlebars.registerHelper("if", function (conditional, options) {
        var type = toString.call(conditional);
        if (type === functionType) {
            conditional = conditional.call(this)
        }
        if (!conditional || Handlebars.Utils.isEmpty(conditional)) {
            return options.inverse(this)
        } else {
            return options.fn(this)
        }
    });
    Handlebars.registerHelper("unless", function (conditional, options) {
        return Handlebars.helpers["if"].call(this, conditional, {
            fn: options.inverse,
            inverse: options.fn
        })
    });
    Handlebars.registerHelper("with", function (context, options) {
        var type = toString.call(context);
        if (type === functionType) {
            context = context.call(this)
        }
        if (!Handlebars.Utils.isEmpty(context)) return options.fn(context)
    });
    Handlebars.registerHelper("log", function (context, options) {
        var level = options.data && options.data.level != null ? parseInt(options.data.level, 10) : 1;
        Handlebars.log(level, context)
    });
    var handlebars = function () {
        var parser = {
            trace: function trace() {},
            yy: {},
            symbols_: {
                error: 2,
                root: 3,
                program: 4,
                EOF: 5,
                simpleInverse: 6,
                statements: 7,
                statement: 8,
                openInverse: 9,
                closeBlock: 10,
                openBlock: 11,
                mustache: 12,
                partial: 13,
                CONTENT: 14,
                COMMENT: 15,
                OPEN_BLOCK: 16,
                inMustache: 17,
                CLOSE: 18,
                OPEN_INVERSE: 19,
                OPEN_ENDBLOCK: 20,
                path: 21,
                OPEN: 22,
                OPEN_UNESCAPED: 23,
                CLOSE_UNESCAPED: 24,
                OPEN_PARTIAL: 25,
                partialName: 26,
                params: 27,
                hash: 28,
                dataName: 29,
                param: 30,
                STRING: 31,
                INTEGER: 32,
                BOOLEAN: 33,
                hashSegments: 34,
                hashSegment: 35,
                ID: 36,
                EQUALS: 37,
                DATA: 38,
                pathSegments: 39,
                SEP: 40,
                $accept: 0,
                $end: 1
            },
            terminals_: {
                2: "error",
                5: "EOF",
                14: "CONTENT",
                15: "COMMENT",
                16: "OPEN_BLOCK",
                18: "CLOSE",
                19: "OPEN_INVERSE",
                20: "OPEN_ENDBLOCK",
                22: "OPEN",
                23: "OPEN_UNESCAPED",
                24: "CLOSE_UNESCAPED",
                25: "OPEN_PARTIAL",
                31: "STRING",
                32: "INTEGER",
                33: "BOOLEAN",
                36: "ID",
                37: "EQUALS",
                38: "DATA",
                40: "SEP"
            },
            productions_: [0, [3, 2],
                [4, 2],
                [4, 3],
                [4, 2],
                [4, 1],
                [4, 1],
                [4, 0],
                [7, 1],
                [7, 2],
                [8, 3],
                [8, 3],
                [8, 1],
                [8, 1],
                [8, 1],
                [8, 1],
                [11, 3],
                [9, 3],
                [10, 3],
                [12, 3],
                [12, 3],
                [13, 3],
                [13, 4],
                [6, 2],
                [17, 3],
                [17, 2],
                [17, 2],
                [17, 1],
                [17, 1],
                [27, 2],
                [27, 1],
                [30, 1],
                [30, 1],
                [30, 1],
                [30, 1],
                [30, 1],
                [28, 1],
                [34, 2],
                [34, 1],
                [35, 3],
                [35, 3],
                [35, 3],
                [35, 3],
                [35, 3],
                [26, 1],
                [26, 1],
                [26, 1],
                [29, 2],
                [21, 1],
                [39, 3],
                [39, 1]
            ],
            performAction: function anonymous(yytext, yyleng, yylineno, yy, yystate, $$, _$) {
                var $0 = $$.length - 1;
                switch (yystate) {
                case 1:
                    return $$[$0 - 1];
                    break;
                case 2:
                    this.$ = new yy.ProgramNode([], $$[$0]);
                    break;
                case 3:
                    this.$ = new yy.ProgramNode($$[$0 - 2], $$[$0]);
                    break;
                case 4:
                    this.$ = new yy.ProgramNode($$[$0 - 1], []);
                    break;
                case 5:
                    this.$ = new yy.ProgramNode($$[$0]);
                    break;
                case 6:
                    this.$ = new yy.ProgramNode([], []);
                    break;
                case 7:
                    this.$ = new yy.ProgramNode([]);
                    break;
                case 8:
                    this.$ = [$$[$0]];
                    break;
                case 9:
                    $$[$0 - 1].push($$[$0]);
                    this.$ = $$[$0 - 1];
                    break;
                case 10:
                    this.$ = new yy.BlockNode($$[$0 - 2], $$[$0 - 1].inverse, $$[$0 - 1], $$[$0]);
                    break;
                case 11:
                    this.$ = new yy.BlockNode($$[$0 - 2], $$[$0 - 1], $$[$0 - 1].inverse, $$[$0]);
                    break;
                case 12:
                    this.$ = $$[$0];
                    break;
                case 13:
                    this.$ = $$[$0];
                    break;
                case 14:
                    this.$ = new yy.ContentNode($$[$0]);
                    break;
                case 15:
                    this.$ = new yy.CommentNode($$[$0]);
                    break;
                case 16:
                    this.$ = new yy.MustacheNode($$[$0 - 1][0], $$[$0 - 1][1]);
                    break;
                case 17:
                    this.$ = new yy.MustacheNode($$[$0 - 1][0], $$[$0 - 1][1]);
                    break;
                case 18:
                    this.$ = $$[$0 - 1];
                    break;
                case 19:
                    this.$ = new yy.MustacheNode($$[$0 - 1][0], $$[$0 - 1][1], $$[$0 - 2][2] === "&");
                    break;
                case 20:
                    this.$ = new yy.MustacheNode($$[$0 - 1][0], $$[$0 - 1][1], true);
                    break;
                case 21:
                    this.$ = new yy.PartialNode($$[$0 - 1]);
                    break;
                case 22:
                    this.$ = new yy.PartialNode($$[$0 - 2], $$[$0 - 1]);
                    break;
                case 23:
                    break;
                case 24:
                    this.$ = [
                        [$$[$0 - 2]].concat($$[$0 - 1]), $$[$0]
                    ];
                    break;
                case 25:
                    this.$ = [
                        [$$[$0 - 1]].concat($$[$0]), null
                    ];
                    break;
                case 26:
                    this.$ = [
                        [$$[$0 - 1]], $$[$0]
                    ];
                    break;
                case 27:
                    this.$ = [
                        [$$[$0]], null
                    ];
                    break;
                case 28:
                    this.$ = [
                        [$$[$0]], null
                    ];
                    break;
                case 29:
                    $$[$0 - 1].push($$[$0]);
                    this.$ = $$[$0 - 1];
                    break;
                case 30:
                    this.$ = [$$[$0]];
                    break;
                case 31:
                    this.$ = $$[$0];
                    break;
                case 32:
                    this.$ = new yy.StringNode($$[$0]);
                    break;
                case 33:
                    this.$ = new yy.IntegerNode($$[$0]);
                    break;
                case 34:
                    this.$ = new yy.BooleanNode($$[$0]);
                    break;
                case 35:
                    this.$ = $$[$0];
                    break;
                case 36:
                    this.$ = new yy.HashNode($$[$0]);
                    break;
                case 37:
                    $$[$0 - 1].push($$[$0]);
                    this.$ = $$[$0 - 1];
                    break;
                case 38:
                    this.$ = [$$[$0]];
                    break;
                case 39:
                    this.$ = [$$[$0 - 2], $$[$0]];
                    break;
                case 40:
                    this.$ = [$$[$0 - 2], new yy.StringNode($$[$0])];
                    break;
                case 41:
                    this.$ = [$$[$0 - 2], new yy.IntegerNode($$[$0])];
                    break;
                case 42:
                    this.$ = [$$[$0 - 2], new yy.BooleanNode($$[$0])];
                    break;
                case 43:
                    this.$ = [$$[$0 - 2], $$[$0]];
                    break;
                case 44:
                    this.$ = new yy.PartialNameNode($$[$0]);
                    break;
                case 45:
                    this.$ = new yy.PartialNameNode(new yy.StringNode($$[$0]));
                    break;
                case 46:
                    this.$ = new yy.PartialNameNode(new yy.IntegerNode($$[$0]));
                    break;
                case 47:
                    this.$ = new yy.DataNode($$[$0]);
                    break;
                case 48:
                    this.$ = new yy.IdNode($$[$0]);
                    break;
                case 49:
                    $$[$0 - 2].push({
                        part: $$[$0],
                        separator: $$[$0 - 1]
                    });
                    this.$ = $$[$0 - 2];
                    break;
                case 50:
                    this.$ = [{
                        part: $$[$0]
                    }];
                    break
                }
            },
            table: [{
                3: 1,
                4: 2,
                5: [2, 7],
                6: 3,
                7: 4,
                8: 6,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 5],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                1: [3]
            }, {
                5: [1, 17]
            }, {
                5: [2, 6],
                7: 18,
                8: 6,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 19],
                20: [2, 6],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                5: [2, 5],
                6: 20,
                8: 21,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 5],
                20: [2, 5],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                17: 23,
                18: [1, 22],
                21: 24,
                29: 25,
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                5: [2, 8],
                14: [2, 8],
                15: [2, 8],
                16: [2, 8],
                19: [2, 8],
                20: [2, 8],
                22: [2, 8],
                23: [2, 8],
                25: [2, 8]
            }, {
                4: 29,
                6: 3,
                7: 4,
                8: 6,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 5],
                20: [2, 7],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                4: 30,
                6: 3,
                7: 4,
                8: 6,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 5],
                20: [2, 7],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                5: [2, 12],
                14: [2, 12],
                15: [2, 12],
                16: [2, 12],
                19: [2, 12],
                20: [2, 12],
                22: [2, 12],
                23: [2, 12],
                25: [2, 12]
            }, {
                5: [2, 13],
                14: [2, 13],
                15: [2, 13],
                16: [2, 13],
                19: [2, 13],
                20: [2, 13],
                22: [2, 13],
                23: [2, 13],
                25: [2, 13]
            }, {
                5: [2, 14],
                14: [2, 14],
                15: [2, 14],
                16: [2, 14],
                19: [2, 14],
                20: [2, 14],
                22: [2, 14],
                23: [2, 14],
                25: [2, 14]
            }, {
                5: [2, 15],
                14: [2, 15],
                15: [2, 15],
                16: [2, 15],
                19: [2, 15],
                20: [2, 15],
                22: [2, 15],
                23: [2, 15],
                25: [2, 15]
            }, {
                17: 31,
                21: 24,
                29: 25,
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                17: 32,
                21: 24,
                29: 25,
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                17: 33,
                21: 24,
                29: 25,
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                21: 35,
                26: 34,
                31: [1, 36],
                32: [1, 37],
                36: [1, 28],
                39: 26
            }, {
                1: [2, 1]
            }, {
                5: [2, 2],
                8: 21,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 19],
                20: [2, 2],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                17: 23,
                21: 24,
                29: 25,
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                5: [2, 4],
                7: 38,
                8: 6,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 19],
                20: [2, 4],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                5: [2, 9],
                14: [2, 9],
                15: [2, 9],
                16: [2, 9],
                19: [2, 9],
                20: [2, 9],
                22: [2, 9],
                23: [2, 9],
                25: [2, 9]
            }, {
                5: [2, 23],
                14: [2, 23],
                15: [2, 23],
                16: [2, 23],
                19: [2, 23],
                20: [2, 23],
                22: [2, 23],
                23: [2, 23],
                25: [2, 23]
            }, {
                18: [1, 39]
            }, {
                18: [2, 27],
                21: 44,
                24: [2, 27],
                27: 40,
                28: 41,
                29: 48,
                30: 42,
                31: [1, 45],
                32: [1, 46],
                33: [1, 47],
                34: 43,
                35: 49,
                36: [1, 50],
                38: [1, 27],
                39: 26
            }, {
                18: [2, 28],
                24: [2, 28]
            }, {
                18: [2, 48],
                24: [2, 48],
                31: [2, 48],
                32: [2, 48],
                33: [2, 48],
                36: [2, 48],
                38: [2, 48],
                40: [1, 51]
            }, {
                21: 52,
                36: [1, 28],
                39: 26
            }, {
                18: [2, 50],
                24: [2, 50],
                31: [2, 50],
                32: [2, 50],
                33: [2, 50],
                36: [2, 50],
                38: [2, 50],
                40: [2, 50]
            }, {
                10: 53,
                20: [1, 54]
            }, {
                10: 55,
                20: [1, 54]
            }, {
                18: [1, 56]
            }, {
                18: [1, 57]
            }, {
                24: [1, 58]
            }, {
                18: [1, 59],
                21: 60,
                36: [1, 28],
                39: 26
            }, {
                18: [2, 44],
                36: [2, 44]
            }, {
                18: [2, 45],
                36: [2, 45]
            }, {
                18: [2, 46],
                36: [2, 46]
            }, {
                5: [2, 3],
                8: 21,
                9: 7,
                11: 8,
                12: 9,
                13: 10,
                14: [1, 11],
                15: [1, 12],
                16: [1, 13],
                19: [1, 19],
                20: [2, 3],
                22: [1, 14],
                23: [1, 15],
                25: [1, 16]
            }, {
                14: [2, 17],
                15: [2, 17],
                16: [2, 17],
                19: [2, 17],
                20: [2, 17],
                22: [2, 17],
                23: [2, 17],
                25: [2, 17]
            }, {
                18: [2, 25],
                21: 44,
                24: [2, 25],
                28: 61,
                29: 48,
                30: 62,
                31: [1, 45],
                32: [1, 46],
                33: [1, 47],
                34: 43,
                35: 49,
                36: [1, 50],
                38: [1, 27],
                39: 26
            }, {
                18: [2, 26],
                24: [2, 26]
            }, {
                18: [2, 30],
                24: [2, 30],
                31: [2, 30],
                32: [2, 30],
                33: [2, 30],
                36: [2, 30],
                38: [2, 30]
            }, {
                18: [2, 36],
                24: [2, 36],
                35: 63,
                36: [1, 64]
            }, {
                18: [2, 31],
                24: [2, 31],
                31: [2, 31],
                32: [2, 31],
                33: [2, 31],
                36: [2, 31],
                38: [2, 31]
            }, {
                18: [2, 32],
                24: [2, 32],
                31: [2, 32],
                32: [2, 32],
                33: [2, 32],
                36: [2, 32],
                38: [2, 32]
            }, {
                18: [2, 33],
                24: [2, 33],
                31: [2, 33],
                32: [2, 33],
                33: [2, 33],
                36: [2, 33],
                38: [2, 33]
            }, {
                18: [2, 34],
                24: [2, 34],
                31: [2, 34],
                32: [2, 34],
                33: [2, 34],
                36: [2, 34],
                38: [2, 34]
            }, {
                18: [2, 35],
                24: [2, 35],
                31: [2, 35],
                32: [2, 35],
                33: [2, 35],
                36: [2, 35],
                38: [2, 35]
            }, {
                18: [2, 38],
                24: [2, 38],
                36: [2, 38]
            }, {
                18: [2, 50],
                24: [2, 50],
                31: [2, 50],
                32: [2, 50],
                33: [2, 50],
                36: [2, 50],
                37: [1, 65],
                38: [2, 50],
                40: [2, 50]
            }, {
                36: [1, 66]
            }, {
                18: [2, 47],
                24: [2, 47],
                31: [2, 47],
                32: [2, 47],
                33: [2, 47],
                36: [2, 47],
                38: [2, 47]
            }, {
                5: [2, 10],
                14: [2, 10],
                15: [2, 10],
                16: [2, 10],
                19: [2, 10],
                20: [2, 10],
                22: [2, 10],
                23: [2, 10],
                25: [2, 10]
            }, {
                21: 67,
                36: [1, 28],
                39: 26
            }, {
                5: [2, 11],
                14: [2, 11],
                15: [2, 11],
                16: [2, 11],
                19: [2, 11],
                20: [2, 11],
                22: [2, 11],
                23: [2, 11],
                25: [2, 11]
            }, {
                14: [2, 16],
                15: [2, 16],
                16: [2, 16],
                19: [2, 16],
                20: [2, 16],
                22: [2, 16],
                23: [2, 16],
                25: [2, 16]
            }, {
                5: [2, 19],
                14: [2, 19],
                15: [2, 19],
                16: [2, 19],
                19: [2, 19],
                20: [2, 19],
                22: [2, 19],
                23: [2, 19],
                25: [2, 19]
            }, {
                5: [2, 20],
                14: [2, 20],
                15: [2, 20],
                16: [2, 20],
                19: [2, 20],
                20: [2, 20],
                22: [2, 20],
                23: [2, 20],
                25: [2, 20]
            }, {
                5: [2, 21],
                14: [2, 21],
                15: [2, 21],
                16: [2, 21],
                19: [2, 21],
                20: [2, 21],
                22: [2, 21],
                23: [2, 21],
                25: [2, 21]
            }, {
                18: [1, 68]
            }, {
                18: [2, 24],
                24: [2, 24]
            }, {
                18: [2, 29],
                24: [2, 29],
                31: [2, 29],
                32: [2, 29],
                33: [2, 29],
                36: [2, 29],
                38: [2, 29]
            }, {
                18: [2, 37],
                24: [2, 37],
                36: [2, 37]
            }, {
                37: [1, 65]
            }, {
                21: 69,
                29: 73,
                31: [1, 70],
                32: [1, 71],
                33: [1, 72],
                36: [1, 28],
                38: [1, 27],
                39: 26
            }, {
                18: [2, 49],
                24: [2, 49],
                31: [2, 49],
                32: [2, 49],
                33: [2, 49],
                36: [2, 49],
                38: [2, 49],
                40: [2, 49]
            }, {
                18: [1, 74]
            }, {
                5: [2, 22],
                14: [2, 22],
                15: [2, 22],
                16: [2, 22],
                19: [2, 22],
                20: [2, 22],
                22: [2, 22],
                23: [2, 22],
                25: [2, 22]
            }, {
                18: [2, 39],
                24: [2, 39],
                36: [2, 39]
            }, {
                18: [2, 40],
                24: [2, 40],
                36: [2, 40]
            }, {
                18: [2, 41],
                24: [2, 41],
                36: [2, 41]
            }, {
                18: [2, 42],
                24: [2, 42],
                36: [2, 42]
            }, {
                18: [2, 43],
                24: [2, 43],
                36: [2, 43]
            }, {
                5: [2, 18],
                14: [2, 18],
                15: [2, 18],
                16: [2, 18],
                19: [2, 18],
                20: [2, 18],
                22: [2, 18],
                23: [2, 18],
                25: [2, 18]
            }],
            defaultActions: {
                17: [2, 1]
            },
            parseError: function parseError(str, hash) {
                throw new Error(str)
            },
            parse: function parse(input) {
                var self = this,
                    stack = [0],
                    vstack = [null],
                    lstack = [],
                    table = this.table,
                    yytext = "",
                    yylineno = 0,
                    yyleng = 0,
                    recovering = 0,
                    TERROR = 2,
                    EOF = 1;
                this.lexer.setInput(input);
                this.lexer.yy = this.yy;
                this.yy.lexer = this.lexer;
                this.yy.parser = this;
                if (typeof this.lexer.yylloc == "undefined") this.lexer.yylloc = {};
                var yyloc = this.lexer.yylloc;
                lstack.push(yyloc);
                var ranges = this.lexer.options && this.lexer.options.ranges;
                if (typeof this.yy.parseError === "function") this.parseError = this.yy.parseError;

                function popStack(n) {
                    stack.length = stack.length - 2 * n;
                    vstack.length = vstack.length - n;
                    lstack.length = lstack.length - n
                }

                function lex() {
                    var token;
                    token = self.lexer.lex() || 1;
                    if (typeof token !== "number") {
                        token = self.symbols_[token] || token
                    }
                    return token
                }
                var symbol, preErrorSymbol, state, action, a, r, yyval = {}, p, len, newState, expected;
                while (true) {
                    state = stack[stack.length - 1];
                    if (this.defaultActions[state]) {
                        action = this.defaultActions[state]
                    } else {
                        if (symbol === null || typeof symbol == "undefined") {
                            symbol = lex()
                        }
                        action = table[state] && table[state][symbol]
                    } if (typeof action === "undefined" || !action.length || !action[0]) {
                        var errStr = "";
                        if (!recovering) {
                            expected = [];
                            for (p in table[state])
                                if (this.terminals_[p] && p > 2) {
                                    expected.push("'" + this.terminals_[p] + "'")
                                }
                            if (this.lexer.showPosition) {
                                errStr = "Parse error on line " + (yylineno + 1) + ":\n" + this.lexer.showPosition() + "\nExpecting " + expected.join(", ") + ", got '" + (this.terminals_[symbol] || symbol) + "'"
                            } else {
                                errStr = "Parse error on line " + (yylineno + 1) + ": Unexpected " + (symbol == 1 ? "end of input" : "'" + (this.terminals_[symbol] || symbol) + "'")
                            }
                            this.parseError(errStr, {
                                text: this.lexer.match,
                                token: this.terminals_[symbol] || symbol,
                                line: this.lexer.yylineno,
                                loc: yyloc,
                                expected: expected
                            })
                        }
                    }
                    if (action[0] instanceof Array && action.length > 1) {
                        throw new Error("Parse Error: multiple actions possible at state: " + state + ", token: " + symbol)
                    }
                    switch (action[0]) {
                    case 1:
                        stack.push(symbol);
                        vstack.push(this.lexer.yytext);
                        lstack.push(this.lexer.yylloc);
                        stack.push(action[1]);
                        symbol = null;
                        if (!preErrorSymbol) {
                            yyleng = this.lexer.yyleng;
                            yytext = this.lexer.yytext;
                            yylineno = this.lexer.yylineno;
                            yyloc = this.lexer.yylloc;
                            if (recovering > 0) recovering--
                        } else {
                            symbol = preErrorSymbol;
                            preErrorSymbol = null
                        }
                        break;
                    case 2:
                        len = this.productions_[action[1]][1];
                        yyval.$ = vstack[vstack.length - len];
                        yyval._$ = {
                            first_line: lstack[lstack.length - (len || 1)].first_line,
                            last_line: lstack[lstack.length - 1].last_line,
                            first_column: lstack[lstack.length - (len || 1)].first_column,
                            last_column: lstack[lstack.length - 1].last_column
                        };
                        if (ranges) {
                            yyval._$.range = [lstack[lstack.length - (len || 1)].range[0], lstack[lstack.length - 1].range[1]]
                        }
                        r = this.performAction.call(yyval, yytext, yyleng, yylineno, this.yy, action[1], vstack, lstack);
                        if (typeof r !== "undefined") {
                            return r
                        }
                        if (len) {
                            stack = stack.slice(0, -1 * len * 2);
                            vstack = vstack.slice(0, -1 * len);
                            lstack = lstack.slice(0, -1 * len)
                        }
                        stack.push(this.productions_[action[1]][0]);
                        vstack.push(yyval.$);
                        lstack.push(yyval._$);
                        newState = table[stack[stack.length - 2]][stack[stack.length - 1]];
                        stack.push(newState);
                        break;
                    case 3:
                        return true
                    }
                }
                return true
            }
        };
        var lexer = function () {
            var lexer = {
                EOF: 1,
                parseError: function parseError(str, hash) {
                    if (this.yy.parser) {
                        this.yy.parser.parseError(str, hash)
                    } else {
                        throw new Error(str)
                    }
                },
                setInput: function (input) {
                    this._input = input;
                    this._more = this._less = this.done = false;
                    this.yylineno = this.yyleng = 0;
                    this.yytext = this.matched = this.match = "";
                    this.conditionStack = ["INITIAL"];
                    this.yylloc = {
                        first_line: 1,
                        first_column: 0,
                        last_line: 1,
                        last_column: 0
                    };
                    if (this.options.ranges) this.yylloc.range = [0, 0];
                    this.offset = 0;
                    return this
                },
                input: function () {
                    var ch = this._input[0];
                    this.yytext += ch;
                    this.yyleng++;
                    this.offset++;
                    this.match += ch;
                    this.matched += ch;
                    var lines = ch.match(/(?:\r\n?|\n).*/g);
                    if (lines) {
                        this.yylineno++;
                        this.yylloc.last_line++
                    } else {
                        this.yylloc.last_column++
                    } if (this.options.ranges) this.yylloc.range[1]++;
                    this._input = this._input.slice(1);
                    return ch
                },
                unput: function (ch) {
                    var len = ch.length;
                    var lines = ch.split(/(?:\r\n?|\n)/g);
                    this._input = ch + this._input;
                    this.yytext = this.yytext.substr(0, this.yytext.length - len - 1);
                    this.offset -= len;
                    var oldLines = this.match.split(/(?:\r\n?|\n)/g);
                    this.match = this.match.substr(0, this.match.length - 1);
                    this.matched = this.matched.substr(0, this.matched.length - 1);
                    if (lines.length - 1) this.yylineno -= lines.length - 1;
                    var r = this.yylloc.range;
                    this.yylloc = {
                        first_line: this.yylloc.first_line,
                        last_line: this.yylineno + 1,
                        first_column: this.yylloc.first_column,
                        last_column: lines ? (lines.length === oldLines.length ? this.yylloc.first_column : 0) + oldLines[oldLines.length - lines.length].length - lines[0].length : this.yylloc.first_column - len
                    };
                    if (this.options.ranges) {
                        this.yylloc.range = [r[0], r[0] + this.yyleng - len]
                    }
                    return this
                },
                more: function () {
                    this._more = true;
                    return this
                },
                less: function (n) {
                    this.unput(this.match.slice(n))
                },
                pastInput: function () {
                    var past = this.matched.substr(0, this.matched.length - this.match.length);
                    return (past.length > 20 ? "..." : "") + past.substr(-20).replace(/\n/g, "")
                },
                upcomingInput: function () {
                    var next = this.match;
                    if (next.length < 20) {
                        next += this._input.substr(0, 20 - next.length)
                    }
                    return (next.substr(0, 20) + (next.length > 20 ? "..." : "")).replace(/\n/g, "")
                },
                showPosition: function () {
                    var pre = this.pastInput();
                    var c = new Array(pre.length + 1).join("-");
                    return pre + this.upcomingInput() + "\n" + c + "^"
                },
                next: function () {
                    if (this.done) {
                        return this.EOF
                    }
                    if (!this._input) this.done = true;
                    var token, match, tempMatch, index, col, lines;
                    if (!this._more) {
                        this.yytext = "";
                        this.match = ""
                    }
                    var rules = this._currentRules();
                    for (var i = 0; i < rules.length; i++) {
                        tempMatch = this._input.match(this.rules[rules[i]]);
                        if (tempMatch && (!match || tempMatch[0].length > match[0].length)) {
                            match = tempMatch;
                            index = i;
                            if (!this.options.flex) break
                        }
                    }
                    if (match) {
                        lines = match[0].match(/(?:\r\n?|\n).*/g);
                        if (lines) this.yylineno += lines.length;
                        this.yylloc = {
                            first_line: this.yylloc.last_line,
                            last_line: this.yylineno + 1,
                            first_column: this.yylloc.last_column,
                            last_column: lines ? lines[lines.length - 1].length - lines[lines.length - 1].match(/\r?\n?/)[0].length : this.yylloc.last_column + match[0].length
                        };
                        this.yytext += match[0];
                        this.match += match[0];
                        this.matches = match;
                        this.yyleng = this.yytext.length;
                        if (this.options.ranges) {
                            this.yylloc.range = [this.offset, this.offset += this.yyleng]
                        }
                        this._more = false;
                        this._input = this._input.slice(match[0].length);
                        this.matched += match[0];
                        token = this.performAction.call(this, this.yy, this, rules[index], this.conditionStack[this.conditionStack.length - 1]);
                        if (this.done && this._input) this.done = false;
                        if (token) return token;
                        else return
                    }
                    if (this._input === "") {
                        return this.EOF
                    } else {
                        return this.parseError("Lexical error on line " + (this.yylineno + 1) + ". Unrecognized text.\n" + this.showPosition(), {
                            text: "",
                            token: null,
                            line: this.yylineno
                        })
                    }
                },
                lex: function lex() {
                    var r = this.next();
                    if (typeof r !== "undefined") {
                        return r
                    } else {
                        return this.lex()
                    }
                },
                begin: function begin(condition) {
                    this.conditionStack.push(condition)
                },
                popState: function popState() {
                    return this.conditionStack.pop()
                },
                _currentRules: function _currentRules() {
                    return this.conditions[this.conditionStack[this.conditionStack.length - 1]].rules
                },
                topState: function () {
                    return this.conditionStack[this.conditionStack.length - 2]
                },
                pushState: function begin(condition) {
                    this.begin(condition)
                }
            };
            lexer.options = {};
            lexer.performAction = function anonymous(yy, yy_, $avoiding_name_collisions, YY_START) {
                var YYSTATE = YY_START;
                switch ($avoiding_name_collisions) {
                case 0:
                    yy_.yytext = "\\";
                    return 14;
                    break;
                case 1:
                    if (yy_.yytext.slice(-1) !== "\\") this.begin("mu");
                    if (yy_.yytext.slice(-1) === "\\") yy_.yytext = yy_.yytext.substr(0, yy_.yyleng - 1), this.begin("emu");
                    if (yy_.yytext) return 14;
                    break;
                case 2:
                    return 14;
                    break;
                case 3:
                    if (yy_.yytext.slice(-1) !== "\\") this.popState();
                    if (yy_.yytext.slice(-1) === "\\") yy_.yytext = yy_.yytext.substr(0, yy_.yyleng - 1);
                    return 14;
                    break;
                case 4:
                    yy_.yytext = yy_.yytext.substr(0, yy_.yyleng - 4);
                    this.popState();
                    return 15;
                    break;
                case 5:
                    return 25;
                    break;
                case 6:
                    return 16;
                    break;
                case 7:
                    return 20;
                    break;
                case 8:
                    return 19;
                    break;
                case 9:
                    return 19;
                    break;
                case 10:
                    return 23;
                    break;
                case 11:
                    return 22;
                    break;
                case 12:
                    this.popState();
                    this.begin("com");
                    break;
                case 13:
                    yy_.yytext = yy_.yytext.substr(3, yy_.yyleng - 5);
                    this.popState();
                    return 15;
                    break;
                case 14:
                    return 22;
                    break;
                case 15:
                    return 37;
                    break;
                case 16:
                    return 36;
                    break;
                case 17:
                    return 36;
                    break;
                case 18:
                    return 40;
                    break;
                case 19:
                    break;
                case 20:
                    this.popState();
                    return 24;
                    break;
                case 21:
                    this.popState();
                    return 18;
                    break;
                case 22:
                    yy_.yytext = yy_.yytext.substr(1, yy_.yyleng - 2).replace(/\\"/g, '"');
                    return 31;
                    break;
                case 23:
                    yy_.yytext = yy_.yytext.substr(1, yy_.yyleng - 2).replace(/\\'/g, "'");
                    return 31;
                    break;
                case 24:
                    return 38;
                    break;
                case 25:
                    return 33;
                    break;
                case 26:
                    return 33;
                    break;
                case 27:
                    return 32;
                    break;
                case 28:
                    return 36;
                    break;
                case 29:
                    yy_.yytext = yy_.yytext.substr(1, yy_.yyleng - 2);
                    return 36;
                    break;
                case 30:
                    return "INVALID";
                    break;
                case 31:
                    return 5;
                    break
                }
            };
            lexer.rules = [/^(?:\\\\(?=(\{\{)))/, /^(?:[^\x00]*?(?=(\{\{)))/, /^(?:[^\x00]+)/, /^(?:[^\x00]{2,}?(?=(\{\{|$)))/, /^(?:[\s\S]*?--\}\})/, /^(?:\{\{>)/, /^(?:\{\{#)/, /^(?:\{\{\/)/, /^(?:\{\{\^)/, /^(?:\{\{\s*else\b)/, /^(?:\{\{\{)/, /^(?:\{\{&)/, /^(?:\{\{!--)/, /^(?:\{\{![\s\S]*?\}\})/, /^(?:\{\{)/, /^(?:=)/, /^(?:\.(?=[}\/ ]))/, /^(?:\.\.)/, /^(?:[\/.])/, /^(?:\s+)/, /^(?:\}\}\})/, /^(?:\}\})/, /^(?:"(\\["]|[^"])*")/, /^(?:'(\\[']|[^'])*')/, /^(?:@)/, /^(?:true(?=[}\s]))/, /^(?:false(?=[}\s]))/, /^(?:-?[0-9]+(?=[}\s]))/, /^(?:[^\s!"#%-,\.\/;->@\[-\^`\{-~]+(?=[=}\s\/.]))/, /^(?:\[[^\]]*\])/, /^(?:.)/, /^(?:$)/];
            lexer.conditions = {
                mu: {
                    rules: [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31],
                    inclusive: false
                },
                emu: {
                    rules: [3],
                    inclusive: false
                },
                com: {
                    rules: [4],
                    inclusive: false
                },
                INITIAL: {
                    rules: [0, 1, 2, 31],
                    inclusive: true
                }
            };
            return lexer
        }();
        parser.lexer = lexer;

        function Parser() {
            this.yy = {}
        }
        Parser.prototype = parser;
        parser.Parser = Parser;
        return new Parser
    }();
    Handlebars.Parser = handlebars;
    Handlebars.parse = function (input) {
        if (input.constructor === Handlebars.AST.ProgramNode) {
            return input
        }
        Handlebars.Parser.yy = Handlebars.AST;
        return Handlebars.Parser.parse(input)
    };
    Handlebars.AST = {};
    Handlebars.AST.ProgramNode = function (statements, inverse) {
        this.type = "program";
        this.statements = statements;
        if (inverse) {
            this.inverse = new Handlebars.AST.ProgramNode(inverse)
        }
    };
    Handlebars.AST.MustacheNode = function (rawParams, hash, unescaped) {
        this.type = "mustache";
        this.escaped = !unescaped;
        this.hash = hash;
        var id = this.id = rawParams[0];
        var params = this.params = rawParams.slice(1);
        var eligibleHelper = this.eligibleHelper = id.isSimple;
        this.isHelper = eligibleHelper && (params.length || hash)
    };
    Handlebars.AST.PartialNode = function (partialName, context) {
        this.type = "partial";
        this.partialName = partialName;
        this.context = context
    };
    Handlebars.AST.BlockNode = function (mustache, program, inverse, close) {
        var verifyMatch = function (open, close) {
            if (open.original !== close.original) {
                throw new Handlebars.Exception(open.original + " doesn't match " + close.original)
            }
        };
        verifyMatch(mustache.id, close);
        this.type = "block";
        this.mustache = mustache;
        this.program = program;
        this.inverse = inverse;
        if (this.inverse && !this.program) {
            this.isInverse = true
        }
    };
    Handlebars.AST.ContentNode = function (string) {
        this.type = "content";
        this.string = string
    };
    Handlebars.AST.HashNode = function (pairs) {
        this.type = "hash";
        this.pairs = pairs
    };
    Handlebars.AST.IdNode = function (parts) {
        this.type = "ID";
        var original = "",
            dig = [],
            depth = 0;
        for (var i = 0, l = parts.length; i < l; i++) {
            var part = parts[i].part;
            original += (parts[i].separator || "") + part;
            if (part === ".." || part === "." || part === "this") {
                if (dig.length > 0) {
                    throw new Handlebars.Exception("Invalid path: " + original)
                } else if (part === "..") {
                    depth++
                } else {
                    this.isScoped = true
                }
            } else {
                dig.push(part)
            }
        }
        this.original = original;
        this.parts = dig;
        this.string = dig.join(".");
        this.depth = depth;
        this.isSimple = parts.length === 1 && !this.isScoped && depth === 0;
        this.stringModeValue = this.string
    };
    Handlebars.AST.PartialNameNode = function (name) {
        this.type = "PARTIAL_NAME";
        this.name = name.original
    };
    Handlebars.AST.DataNode = function (id) {
        this.type = "DATA";
        this.id = id
    };
    Handlebars.AST.StringNode = function (string) {
        this.type = "STRING";
        this.original = this.string = this.stringModeValue = string
    };
    Handlebars.AST.IntegerNode = function (integer) {
        this.type = "INTEGER";
        this.original = this.integer = integer;
        this.stringModeValue = Number(integer)
    };
    Handlebars.AST.BooleanNode = function (bool) {
        this.type = "BOOLEAN";
        this.bool = bool;
        this.stringModeValue = bool === "true"
    };
    Handlebars.AST.CommentNode = function (comment) {
        this.type = "comment";
        this.comment = comment
    };
    var errorProps = ["description", "fileName", "lineNumber", "message", "name", "number", "stack"];
    Handlebars.Exception = function (message) {
        var tmp = Error.prototype.constructor.apply(this, arguments);
        for (var idx = 0; idx < errorProps.length; idx++) {
            this[errorProps[idx]] = tmp[errorProps[idx]]
        }
    };
    Handlebars.Exception.prototype = new Error;
    Handlebars.SafeString = function (string) {
        this.string = string
    };
    Handlebars.SafeString.prototype.toString = function () {
        return this.string.toString()
    };
    var escape = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#x27;",
        "`": "&#x60;"
    };
    var badChars = /[&<>"'`]/g;
    var possible = /[&<>"'`]/;
    var escapeChar = function (chr) {
        return escape[chr] || "&amp;"
    };
    Handlebars.Utils = {
        extend: function (obj, value) {
            for (var key in value) {
                if (value.hasOwnProperty(key)) {
                    obj[key] = value[key]
                }
            }
        },
        escapeExpression: function (string) {
            if (string instanceof Handlebars.SafeString) {
                return string.toString()
            } else if (string == null || string === false) {
                return ""
            }
            string = string.toString();
            if (!possible.test(string)) {
                return string
            }
            return string.replace(badChars, escapeChar)
        },
        isEmpty: function (value) {
            if (!value && value !== 0) {
                return true
            } else if (toString.call(value) === "[object Array]" && value.length === 0) {
                return true
            } else {
                return false
            }
        }
    };
    var Compiler = Handlebars.Compiler = function () {};
    var JavaScriptCompiler = Handlebars.JavaScriptCompiler = function () {};
    Compiler.prototype = {
        compiler: Compiler,
        disassemble: function () {
            var opcodes = this.opcodes,
                opcode, out = [],
                params, param;
            for (var i = 0, l = opcodes.length; i < l; i++) {
                opcode = opcodes[i];
                if (opcode.opcode === "DECLARE") {
                    out.push("DECLARE " + opcode.name + "=" + opcode.value)
                } else {
                    params = [];
                    for (var j = 0; j < opcode.args.length; j++) {
                        param = opcode.args[j];
                        if (typeof param === "string") {
                            param = '"' + param.replace("\n", "\\n") + '"'
                        }
                        params.push(param)
                    }
                    out.push(opcode.opcode + " " + params.join(" "))
                }
            }
            return out.join("\n")
        },
        equals: function (other) {
            var len = this.opcodes.length;
            if (other.opcodes.length !== len) {
                return false
            }
            for (var i = 0; i < len; i++) {
                var opcode = this.opcodes[i],
                    otherOpcode = other.opcodes[i];
                if (opcode.opcode !== otherOpcode.opcode || opcode.args.length !== otherOpcode.args.length) {
                    return false
                }
                for (var j = 0; j < opcode.args.length; j++) {
                    if (opcode.args[j] !== otherOpcode.args[j]) {
                        return false
                    }
                }
            }
            len = this.children.length;
            if (other.children.length !== len) {
                return false
            }
            for (i = 0; i < len; i++) {
                if (!this.children[i].equals(other.children[i])) {
                    return false
                }
            }
            return true
        },
        guid: 0,
        compile: function (program, options) {
            this.children = [];
            this.depths = {
                list: []
            };
            this.options = options;
            var knownHelpers = this.options.knownHelpers;
            this.options.knownHelpers = {
                helperMissing: true,
                blockHelperMissing: true,
                each: true,
                "if": true,
                unless: true,
                "with": true,
                log: true
            };
            if (knownHelpers) {
                for (var name in knownHelpers) {
                    this.options.knownHelpers[name] = knownHelpers[name]
                }
            }
            return this.program(program)
        },
        accept: function (node) {
            return this[node.type](node)
        },
        program: function (program) {
            var statements = program.statements,
                statement;
            this.opcodes = [];
            for (var i = 0, l = statements.length; i < l; i++) {
                statement = statements[i];
                this[statement.type](statement)
            }
            this.isSimple = l === 1;
            this.depths.list = this.depths.list.sort(function (a, b) {
                return a - b
            });
            return this
        },
        compileProgram: function (program) {
            var result = (new this.compiler).compile(program, this.options);
            var guid = this.guid++,
                depth;
            this.usePartial = this.usePartial || result.usePartial;
            this.children[guid] = result;
            for (var i = 0, l = result.depths.list.length; i < l; i++) {
                depth = result.depths.list[i];
                if (depth < 2) {
                    continue
                } else {
                    this.addDepth(depth - 1)
                }
            }
            return guid
        },
        block: function (block) {
            var mustache = block.mustache,
                program = block.program,
                inverse = block.inverse;
            if (program) {
                program = this.compileProgram(program)
            }
            if (inverse) {
                inverse = this.compileProgram(inverse)
            }
            var type = this.classifyMustache(mustache);
            if (type === "helper") {
                this.helperMustache(mustache, program, inverse)
            } else if (type === "simple") {
                this.simpleMustache(mustache);
                this.opcode("pushProgram", program);
                this.opcode("pushProgram", inverse);
                this.opcode("emptyHash");
                this.opcode("blockValue")
            } else {
                this.ambiguousMustache(mustache, program, inverse);
                this.opcode("pushProgram", program);
                this.opcode("pushProgram", inverse);
                this.opcode("emptyHash");
                this.opcode("ambiguousBlockValue")
            }
            this.opcode("append")
        },
        hash: function (hash) {
            var pairs = hash.pairs,
                pair, val;
            this.opcode("pushHash");
            for (var i = 0, l = pairs.length; i < l; i++) {
                pair = pairs[i];
                val = pair[1];
                if (this.options.stringParams) {
                    if (val.depth) {
                        this.addDepth(val.depth)
                    }
                    this.opcode("getContext", val.depth || 0);
                    this.opcode("pushStringParam", val.stringModeValue, val.type)
                } else {
                    this.accept(val)
                }
                this.opcode("assignToHash", pair[0])
            }
            this.opcode("popHash")
        },
        partial: function (partial) {
            var partialName = partial.partialName;
            this.usePartial = true;
            if (partial.context) {
                this.ID(partial.context)
            } else {
                this.opcode("push", "depth0")
            }
            this.opcode("invokePartial", partialName.name);
            this.opcode("append")
        },
        content: function (content) {
            this.opcode("appendContent", content.string)
        },
        mustache: function (mustache) {
            var options = this.options;
            var type = this.classifyMustache(mustache);
            if (type === "simple") {
                this.simpleMustache(mustache)
            } else if (type === "helper") {
                this.helperMustache(mustache)
            } else {
                this.ambiguousMustache(mustache)
            } if (mustache.escaped && !options.noEscape) {
                this.opcode("appendEscaped")
            } else {
                this.opcode("append")
            }
        },
        ambiguousMustache: function (mustache, program, inverse) {
            var id = mustache.id,
                name = id.parts[0],
                isBlock = program != null || inverse != null;
            this.opcode("getContext", id.depth);
            this.opcode("pushProgram", program);
            this.opcode("pushProgram", inverse);
            this.opcode("invokeAmbiguous", name, isBlock)
        },
        simpleMustache: function (mustache) {
            var id = mustache.id;
            if (id.type === "DATA") {
                this.DATA(id)
            } else if (id.parts.length) {
                this.ID(id)
            } else {
                this.addDepth(id.depth);
                this.opcode("getContext", id.depth);
                this.opcode("pushContext")
            }
            this.opcode("resolvePossibleLambda")
        },
        helperMustache: function (mustache, program, inverse) {
            var params = this.setupFullMustacheParams(mustache, program, inverse),
                name = mustache.id.parts[0];
            if (this.options.knownHelpers[name]) {
                this.opcode("invokeKnownHelper", params.length, name)
            } else if (this.options.knownHelpersOnly) {
                throw new Error("You specified knownHelpersOnly, but used the unknown helper " + name)
            } else {
                this.opcode("invokeHelper", params.length, name)
            }
        },
        ID: function (id) {
            this.addDepth(id.depth);
            this.opcode("getContext", id.depth);
            var name = id.parts[0];
            if (!name) {
                this.opcode("pushContext")
            } else {
                this.opcode("lookupOnContext", id.parts[0])
            }
            for (var i = 1, l = id.parts.length; i < l; i++) {
                this.opcode("lookup", id.parts[i])
            }
        },
        DATA: function (data) {
            this.options.data = true;
            if (data.id.isScoped || data.id.depth) {
                throw new Handlebars.Exception("Scoped data references are not supported: " + data.original)
            }
            this.opcode("lookupData");
            var parts = data.id.parts;
            for (var i = 0, l = parts.length; i < l; i++) {
                this.opcode("lookup", parts[i])
            }
        },
        STRING: function (string) {
            this.opcode("pushString", string.string)
        },
        INTEGER: function (integer) {
            this.opcode("pushLiteral", integer.integer)
        },
        BOOLEAN: function (bool) {
            this.opcode("pushLiteral", bool.bool)
        },
        comment: function () {},
        opcode: function (name) {
            this.opcodes.push({
                opcode: name,
                args: [].slice.call(arguments, 1)
            })
        },
        declare: function (name, value) {
            this.opcodes.push({
                opcode: "DECLARE",
                name: name,
                value: value
            })
        },
        addDepth: function (depth) {
            if (isNaN(depth)) {
                throw new Error("EWOT")
            }
            if (depth === 0) {
                return
            }
            if (!this.depths[depth]) {
                this.depths[depth] = true;
                this.depths.list.push(depth)
            }
        },
        classifyMustache: function (mustache) {
            var isHelper = mustache.isHelper;
            var isEligible = mustache.eligibleHelper;
            var options = this.options;
            if (isEligible && !isHelper) {
                var name = mustache.id.parts[0];
                if (options.knownHelpers[name]) {
                    isHelper = true
                } else if (options.knownHelpersOnly) {
                    isEligible = false
                }
            }
            if (isHelper) {
                return "helper"
            } else if (isEligible) {
                return "ambiguous"
            } else {
                return "simple"
            }
        },
        pushParams: function (params) {
            var i = params.length,
                param;
            while (i--) {
                param = params[i];
                if (this.options.stringParams) {
                    if (param.depth) {
                        this.addDepth(param.depth)
                    }
                    this.opcode("getContext", param.depth || 0);
                    this.opcode("pushStringParam", param.stringModeValue, param.type)
                } else {
                    this[param.type](param)
                }
            }
        },
        setupMustacheParams: function (mustache) {
            var params = mustache.params;
            this.pushParams(params);
            if (mustache.hash) {
                this.hash(mustache.hash)
            } else {
                this.opcode("emptyHash")
            }
            return params
        },
        setupFullMustacheParams: function (mustache, program, inverse) {
            var params = mustache.params;
            this.pushParams(params);
            this.opcode("pushProgram", program);
            this.opcode("pushProgram", inverse);
            if (mustache.hash) {
                this.hash(mustache.hash)
            } else {
                this.opcode("emptyHash")
            }
            return params
        }
    };
    var Literal = function (value) {
        this.value = value
    };
    JavaScriptCompiler.prototype = {
        nameLookup: function (parent, name) {
            if (/^[0-9]+$/.test(name)) {
                return parent + "[" + name + "]"
            } else if (JavaScriptCompiler.isValidJavaScriptVariableName(name)) {
                return parent + "." + name
            } else {
                return parent + "['" + name + "']"
            }
        },
        appendToBuffer: function (string) {
            if (this.environment.isSimple) {
                return "return " + string + ";"
            } else {
                return {
                    appendToBuffer: true,
                    content: string,
                    toString: function () {
                        return "buffer += " + string + ";"
                    }
                }
            }
        },
        initializeBuffer: function () {
            return this.quotedString("")
        },
        namespace: "Handlebars",
        compile: function (environment, options, context, asObject) {
            this.environment = environment;
            this.options = options || {};
            Handlebars.log(Handlebars.logger.DEBUG, this.environment.disassemble() + "\n\n");
            this.name = this.environment.name;
            this.isChild = !! context;
            this.context = context || {
                programs: [],
                environments: [],
                aliases: {}
            };
            this.preamble();
            this.stackSlot = 0;
            this.stackVars = [];
            this.registers = {
                list: []
            };
            this.compileStack = [];
            this.inlineStack = [];
            this.compileChildren(environment, options);
            var opcodes = environment.opcodes,
                opcode;
            this.i = 0;
            for (l = opcodes.length; this.i < l; this.i++) {
                opcode = opcodes[this.i];
                if (opcode.opcode === "DECLARE") {
                    this[opcode.name] = opcode.value
                } else {
                    this[opcode.opcode].apply(this, opcode.args)
                }
            }
            return this.createFunctionContext(asObject)
        },
        nextOpcode: function () {
            var opcodes = this.environment.opcodes;
            return opcodes[this.i + 1]
        },
        eat: function () {
            this.i = this.i + 1
        },
        preamble: function () {
            var out = [];
            if (!this.isChild) {
                var namespace = this.namespace;
                var copies = "helpers = this.merge(helpers, " + namespace + ".helpers);";
                if (this.environment.usePartial) {
                    copies = copies + " partials = this.merge(partials, " + namespace + ".partials);"
                }
                if (this.options.data) {
                    copies = copies + " data = data || {};"
                }
                out.push(copies)
            } else {
                out.push("")
            } if (!this.environment.isSimple) {
                out.push(", buffer = " + this.initializeBuffer())
            } else {
                out.push("")
            }
            this.lastContext = 0;
            this.source = out
        },
        createFunctionContext: function (asObject) {
            var locals = this.stackVars.concat(this.registers.list);
            if (locals.length > 0) {
                this.source[1] = this.source[1] + ", " + locals.join(", ")
            }
            if (!this.isChild) {
                for (var alias in this.context.aliases) {
                    if (this.context.aliases.hasOwnProperty(alias)) {
                        this.source[1] = this.source[1] + ", " + alias + "=" + this.context.aliases[alias]
                    }
                }
            }
            if (this.source[1]) {
                this.source[1] = "var " + this.source[1].substring(2) + ";"
            }
            if (!this.isChild) {
                this.source[1] += "\n" + this.context.programs.join("\n") + "\n"
            }
            if (!this.environment.isSimple) {
                this.source.push("return buffer;")
            }
            var params = this.isChild ? ["depth0", "data"] : ["Handlebars", "depth0", "helpers", "partials", "data"];
            for (var i = 0, l = this.environment.depths.list.length; i < l; i++) {
                params.push("depth" + this.environment.depths.list[i])
            }
            var source = this.mergeSource();
            if (!this.isChild) {
                var revision = Handlebars.COMPILER_REVISION,
                    versions = Handlebars.REVISION_CHANGES[revision];
                source = "this.compilerInfo = [" + revision + ",'" + versions + "'];\n" + source
            }
            if (asObject) {
                params.push(source);
                return Function.apply(this, params)
            } else {
                var functionSource = "function " + (this.name || "") + "(" + params.join(",") + ") {\n  " + source + "}";
                Handlebars.log(Handlebars.logger.DEBUG, functionSource + "\n\n");
                return functionSource
            }
        },
        mergeSource: function () {
            var source = "",
                buffer;
            for (var i = 0, len = this.source.length; i < len; i++) {
                var line = this.source[i];
                if (line.appendToBuffer) {
                    if (buffer) {
                        buffer = buffer + "\n    + " + line.content
                    } else {
                        buffer = line.content
                    }
                } else {
                    if (buffer) {
                        source += "buffer += " + buffer + ";\n  ";
                        buffer = undefined
                    }
                    source += line + "\n  "
                }
            }
            return source
        },
        blockValue: function () {
            this.context.aliases.blockHelperMissing = "helpers.blockHelperMissing";
            var params = ["depth0"];
            this.setupParams(0, params);
            this.replaceStack(function (current) {
                params.splice(1, 0, current);
                return "blockHelperMissing.call(" + params.join(", ") + ")"
            })
        },
        ambiguousBlockValue: function () {
            this.context.aliases.blockHelperMissing = "helpers.blockHelperMissing";
            var params = ["depth0"];
            this.setupParams(0, params);
            var current = this.topStack();
            params.splice(1, 0, current);
            params[params.length - 1] = "options";
            this.source.push("if (!" + this.lastHelper + ") { " + current + " = blockHelperMissing.call(" + params.join(", ") + "); }")
        },
        appendContent: function (content) {
            this.source.push(this.appendToBuffer(this.quotedString(content)))
        },
        append: function () {
            this.flushInline();
            var local = this.popStack();
            this.source.push("if(" + local + " || " + local + " === 0) { " + this.appendToBuffer(local) + " }");
            if (this.environment.isSimple) {
                this.source.push("else { " + this.appendToBuffer("''") + " }")
            }
        },
        appendEscaped: function () {
            this.context.aliases.escapeExpression = "this.escapeExpression";
            this.source.push(this.appendToBuffer("escapeExpression(" + this.popStack() + ")"))
        },
        getContext: function (depth) {
            if (this.lastContext !== depth) {
                this.lastContext = depth
            }
        },
        lookupOnContext: function (name) {
            this.push(this.nameLookup("depth" + this.lastContext, name, "context"))
        },
        pushContext: function () {
            this.pushStackLiteral("depth" + this.lastContext)
        },
        resolvePossibleLambda: function () {
            this.context.aliases.functionType = '"function"';
            this.replaceStack(function (current) {
                return "typeof " + current + " === functionType ? " + current + ".apply(depth0) : " + current
            })
        },
        lookup: function (name) {
            this.replaceStack(function (current) {
                return current + " == null || " + current + " === false ? " + current + " : " + this.nameLookup(current, name, "context")
            })
        },
        lookupData: function (id) {
            this.push("data")
        },
        pushStringParam: function (string, type) {
            this.pushStackLiteral("depth" + this.lastContext);
            this.pushString(type);
            if (typeof string === "string") {
                this.pushString(string)
            } else {
                this.pushStackLiteral(string)
            }
        },
        emptyHash: function () {
            this.pushStackLiteral("{}");
            if (this.options.stringParams) {
                this.register("hashTypes", "{}");
                this.register("hashContexts", "{}")
            }
        },
        pushHash: function () {
            this.hash = {
                values: [],
                types: [],
                contexts: []
            }
        },
        popHash: function () {
            var hash = this.hash;
            this.hash = undefined;
            if (this.options.stringParams) {
                this.register("hashContexts", "{" + hash.contexts.join(",") + "}");
                this.register("hashTypes", "{" + hash.types.join(",") + "}")
            }
            this.push("{\n    " + hash.values.join(",\n    ") + "\n  }")
        },
        pushString: function (string) {
            this.pushStackLiteral(this.quotedString(string))
        },
        push: function (expr) {
            this.inlineStack.push(expr);
            return expr
        },
        pushLiteral: function (value) {
            this.pushStackLiteral(value)
        },
        pushProgram: function (guid) {
            if (guid != null) {
                this.pushStackLiteral(this.programExpression(guid))
            } else {
                this.pushStackLiteral(null)
            }
        },
        invokeHelper: function (paramSize, name) {
            this.context.aliases.helperMissing = "helpers.helperMissing";
            var helper = this.lastHelper = this.setupHelper(paramSize, name, true);
            var nonHelper = this.nameLookup("depth" + this.lastContext, name, "context");
            this.push(helper.name + " || " + nonHelper);
            this.replaceStack(function (name) {
                return name + " ? " + name + ".call(" + helper.callParams + ") " + ": helperMissing.call(" + helper.helperMissingParams + ")"
            })
        },
        invokeKnownHelper: function (paramSize, name) {
            var helper = this.setupHelper(paramSize, name);
            this.push(helper.name + ".call(" + helper.callParams + ")")
        },
        invokeAmbiguous: function (name, helperCall) {
            this.context.aliases.functionType = '"function"';
            this.pushStackLiteral("{}");
            var helper = this.setupHelper(0, name, helperCall);
            var helperName = this.lastHelper = this.nameLookup("helpers", name, "helper");
            var nonHelper = this.nameLookup("depth" + this.lastContext, name, "context");
            var nextStack = this.nextStack();
            this.source.push("if (" + nextStack + " = " + helperName + ") { " + nextStack + " = " + nextStack + ".call(" + helper.callParams + "); }");
            this.source.push("else { " + nextStack + " = " + nonHelper + "; " + nextStack + " = typeof " + nextStack + " === functionType ? " + nextStack + ".apply(depth0) : " + nextStack + "; }")
        },
        invokePartial: function (name) {
            var params = [this.nameLookup("partials", name, "partial"), "'" + name + "'", this.popStack(), "helpers", "partials"];
            if (this.options.data) {
                params.push("data")
            }
            this.context.aliases.self = "this";
            this.push("self.invokePartial(" + params.join(", ") + ")")
        },
        assignToHash: function (key) {
            var value = this.popStack(),
                context, type;
            if (this.options.stringParams) {
                type = this.popStack();
                context = this.popStack()
            }
            var hash = this.hash;
            if (context) {
                hash.contexts.push("'" + key + "': " + context)
            }
            if (type) {
                hash.types.push("'" + key + "': " + type)
            }
            hash.values.push("'" + key + "': (" + value + ")")
        },
        compiler: JavaScriptCompiler,
        compileChildren: function (environment, options) {
            var children = environment.children,
                child, compiler;
            for (var i = 0, l = children.length; i < l; i++) {
                child = children[i];
                compiler = new this.compiler;
                var index = this.matchExistingProgram(child);
                if (index == null) {
                    this.context.programs.push("");
                    index = this.context.programs.length;
                    child.index = index;
                    child.name = "program" + index;
                    this.context.programs[index] = compiler.compile(child, options, this.context);
                    this.context.environments[index] = child
                } else {
                    child.index = index;
                    child.name = "program" + index
                }
            }
        },
        matchExistingProgram: function (child) {
            for (var i = 0, len = this.context.environments.length; i < len; i++) {
                var environment = this.context.environments[i];
                if (environment && environment.equals(child)) {
                    return i
                }
            }
        },
        programExpression: function (guid) {
            this.context.aliases.self = "this";
            if (guid == null) {
                return "self.noop"
            }
            var child = this.environment.children[guid],
                depths = child.depths.list,
                depth;
            var programParams = [child.index, child.name, "data"];
            for (var i = 0, l = depths.length; i < l; i++) {
                depth = depths[i];
                if (depth === 1) {
                    programParams.push("depth0")
                } else {
                    programParams.push("depth" + (depth - 1))
                }
            }
            return (depths.length === 0 ? "self.program(" : "self.programWithDepth(") + programParams.join(", ") + ")"
        },
        register: function (name, val) {
            this.useRegister(name);
            this.source.push(name + " = " + val + ";")
        },
        useRegister: function (name) {
            if (!this.registers[name]) {
                this.registers[name] = true;
                this.registers.list.push(name)
            }
        },
        pushStackLiteral: function (item) {
            return this.push(new Literal(item))
        },
        pushStack: function (item) {
            this.flushInline();
            var stack = this.incrStack();
            if (item) {
                this.source.push(stack + " = " + item + ";")
            }
            this.compileStack.push(stack);
            return stack
        },
        replaceStack: function (callback) {
            var prefix = "",
                inline = this.isInline(),
                stack;
            if (inline) {
                var top = this.popStack(true);
                if (top instanceof Literal) {
                    stack = top.value
                } else {
                    var name = this.stackSlot ? this.topStackName() : this.incrStack();
                    prefix = "(" + this.push(name) + " = " + top + "),";
                    stack = this.topStack()
                }
            } else {
                stack = this.topStack()
            }
            var item = callback.call(this, stack);
            if (inline) {
                if (this.inlineStack.length || this.compileStack.length) {
                    this.popStack()
                }
                this.push("(" + prefix + item + ")")
            } else {
                if (!/^stack/.test(stack)) {
                    stack = this.nextStack()
                }
                this.source.push(stack + " = (" + prefix + item + ");")
            }
            return stack
        },
        nextStack: function () {
            return this.pushStack()
        },
        incrStack: function () {
            this.stackSlot++;
            if (this.stackSlot > this.stackVars.length) {
                this.stackVars.push("stack" + this.stackSlot)
            }
            return this.topStackName()
        },
        topStackName: function () {
            return "stack" + this.stackSlot
        },
        flushInline: function () {
            var inlineStack = this.inlineStack;
            if (inlineStack.length) {
                this.inlineStack = [];
                for (var i = 0, len = inlineStack.length; i < len; i++) {
                    var entry = inlineStack[i];
                    if (entry instanceof Literal) {
                        this.compileStack.push(entry)
                    } else {
                        this.pushStack(entry)
                    }
                }
            }
        },
        isInline: function () {
            return this.inlineStack.length
        },
        popStack: function (wrapped) {
            var inline = this.isInline(),
                item = (inline ? this.inlineStack : this.compileStack).pop();
            if (!wrapped && item instanceof Literal) {
                return item.value
            } else {
                if (!inline) {
                    this.stackSlot--
                }
                return item
            }
        },
        topStack: function (wrapped) {
            var stack = this.isInline() ? this.inlineStack : this.compileStack,
                item = stack[stack.length - 1];
            if (!wrapped && item instanceof Literal) {
                return item.value
            } else {
                return item
            }
        },
        quotedString: function (str) {
            return '"' + str.replace(/\\/g, "\\\\").replace(/"/g, '\\"').replace(/\n/g, "\\n").replace(/\r/g, "\\r").replace(/\u2028/g, "\\u2028").replace(/\u2029/g, "\\u2029") + '"'
        },
        setupHelper: function (paramSize, name, missingParams) {
            var params = [];
            this.setupParams(paramSize, params, missingParams);
            var foundHelper = this.nameLookup("helpers", name, "helper");
            return {
                params: params,
                name: foundHelper,
                callParams: ["depth0"].concat(params).join(", "),
                helperMissingParams: missingParams && ["depth0", this.quotedString(name)].concat(params).join(", ")
            }
        },
        setupParams: function (paramSize, params, useRegister) {
            var options = [],
                contexts = [],
                types = [],
                param, inverse, program;
            options.push("hash:" + this.popStack());
            inverse = this.popStack();
            program = this.popStack();
            if (program || inverse) {
                if (!program) {
                    this.context.aliases.self = "this";
                    program = "self.noop"
                }
                if (!inverse) {
                    this.context.aliases.self = "this";
                    inverse = "self.noop"
                }
                options.push("inverse:" + inverse);
                options.push("fn:" + program)
            }
            for (var i = 0; i < paramSize; i++) {
                param = this.popStack();
                params.push(param);
                if (this.options.stringParams) {
                    types.push(this.popStack());
                    contexts.push(this.popStack())
                }
            }
            if (this.options.stringParams) {
                options.push("contexts:[" + contexts.join(",") + "]");
                options.push("types:[" + types.join(",") + "]");
                options.push("hashContexts:hashContexts");
                options.push("hashTypes:hashTypes")
            }
            if (this.options.data) {
                options.push("data:data")
            }
            options = "{" + options.join(",") + "}";
            if (useRegister) {
                this.register("options", options);
                params.push("options")
            } else {
                params.push(options)
            }
            return params.join(", ")
        }
    };
    var reservedWords = ("break else new var" + " case finally return void" + " catch for switch while" + " continue function this with" + " default if throw" + " delete in try" + " do instanceof typeof" + " abstract enum int short" + " boolean export interface static" + " byte extends long super" + " char final native synchronized" + " class float package throws" + " const goto private transient" + " debugger implements protected volatile" + " double import public let yield").split(" ");
    var compilerWords = JavaScriptCompiler.RESERVED_WORDS = {};
    for (var i = 0, l = reservedWords.length; i < l; i++) {
        compilerWords[reservedWords[i]] = true
    }
    JavaScriptCompiler.isValidJavaScriptVariableName = function (name) {
        if (!JavaScriptCompiler.RESERVED_WORDS[name] && /^[a-zA-Z_$][0-9a-zA-Z_$]+$/.test(name)) {
            return true
        }
        return false
    };
    Handlebars.precompile = function (input, options) {
        if (input == null || typeof input !== "string" && input.constructor !== Handlebars.AST.ProgramNode) {
            throw new Handlebars.Exception("You must pass a string or Handlebars AST to Handlebars.precompile. You passed " + input)
        }
        options = options || {};
        if (!("data" in options)) {
            options.data = true
        }
        var ast = Handlebars.parse(input);
        var environment = (new Compiler).compile(ast, options);
        return (new JavaScriptCompiler).compile(environment, options)
    };
    Handlebars.compile = function (input, options) {
        if (input == null || typeof input !== "string" && input.constructor !== Handlebars.AST.ProgramNode) {
            throw new Handlebars.Exception("You must pass a string or Handlebars AST to Handlebars.compile. You passed " + input)
        }
        options = options || {};
        if (!("data" in options)) {
            options.data = true
        }
        var compiled;

        function compile() {
            var ast = Handlebars.parse(input);
            var environment = (new Compiler).compile(ast, options);
            var templateSpec = (new JavaScriptCompiler).compile(environment, options, undefined, true);
            return Handlebars.template(templateSpec)
        }
        return function (context, options) {
            if (!compiled) {
                compiled = compile()
            }
            return compiled.call(this, context, options)
        }
    };
    Handlebars.VM = {
        template: function (templateSpec) {
            var container = {
                escapeExpression: Handlebars.Utils.escapeExpression,
                invokePartial: Handlebars.VM.invokePartial,
                programs: [],
                program: function (i, fn, data) {
                    var programWrapper = this.programs[i];
                    if (data) {
                        programWrapper = Handlebars.VM.program(i, fn, data)
                    } else if (!programWrapper) {
                        programWrapper = this.programs[i] = Handlebars.VM.program(i, fn)
                    }
                    return programWrapper
                },
                merge: function (param, common) {
                    var ret = param || common;
                    if (param && common) {
                        ret = {};
                        Handlebars.Utils.extend(ret, common);
                        Handlebars.Utils.extend(ret, param)
                    }
                    return ret
                },
                programWithDepth: Handlebars.VM.programWithDepth,
                noop: Handlebars.VM.noop,
                compilerInfo: null
            };
            return function (context, options) {
                options = options || {};
                var result = templateSpec.call(container, Handlebars, context, options.helpers, options.partials, options.data);
                var compilerInfo = container.compilerInfo || [],
                    compilerRevision = compilerInfo[0] || 1,
                    currentRevision = Handlebars.COMPILER_REVISION;
                if (compilerRevision !== currentRevision) {
                    if (compilerRevision < currentRevision) {
                        var runtimeVersions = Handlebars.REVISION_CHANGES[currentRevision],
                            compilerVersions = Handlebars.REVISION_CHANGES[compilerRevision];
                        throw "Template was precompiled with an older version of Handlebars than the current runtime. " + "Please update your precompiler to a newer version (" + runtimeVersions + ") or downgrade your runtime to an older version (" + compilerVersions + ")."
                    } else {
                        throw "Template was precompiled with a newer version of Handlebars than the current runtime. " + "Please update your runtime to a newer version (" + compilerInfo[1] + ")."
                    }
                }
                return result
            }
        },
        programWithDepth: function (i, fn, data) {
            var args = Array.prototype.slice.call(arguments, 3);
            var program = function (context, options) {
                options = options || {};
                return fn.apply(this, [context, options.data || data].concat(args))
            };
            program.program = i;
            program.depth = args.length;
            return program
        },
        program: function (i, fn, data) {
            var program = function (context, options) {
                options = options || {};
                return fn(context, options.data || data)
            };
            program.program = i;
            program.depth = 0;
            return program
        },
        noop: function () {
            return ""
        },
        invokePartial: function (partial, name, context, helpers, partials, data) {
            var options = {
                helpers: helpers,
                partials: partials,
                data: data
            };
            if (partial === undefined) {
                throw new Handlebars.Exception("The partial " + name + " could not be found")
            } else if (partial instanceof Function) {
                return partial(context, options)
            } else if (!Handlebars.compile) {
                throw new Handlebars.Exception("The partial " + name + " could not be compiled when running in runtime-only mode")
            } else {
                partials[name] = Handlebars.compile(partial, {
                    data: data !== undefined
                });
                return partials[name](context, options)
            }
        }
    };
    Handlebars.template = Handlebars.VM.template
})(Handlebars);
(function () {
    var cache = {};
    var ctx = null,
        usingWebAudio = true,
        noAudio = false;
    if (typeof AudioContext !== "undefined") {
        ctx = new AudioContext
    } else if (typeof webkitAudioContext !== "undefined") {
        ctx = new webkitAudioContext
    } else if (typeof Audio !== "undefined") {
        usingWebAudio = false
    } else {
        usingWebAudio = false;
        noAudio = true
    } if (usingWebAudio) {
        var masterGain = typeof ctx.createGain === "undefined" ? ctx.createGainNode() : ctx.createGain();
        masterGain.gain.value = 1;
        masterGain.connect(ctx.destination)
    }
    var HowlerGlobal = function () {
        this._volume = 1;
        this._muted = false;
        this.usingWebAudio = usingWebAudio
    };
    HowlerGlobal.prototype = {
        volume: function (vol) {
            var self = this;
            vol = parseFloat(vol, 10);
            if (vol && vol >= 0 && vol <= 1) {
                self._volume = vol;
                if (usingWebAudio) {
                    masterGain.gain.value = vol
                }
                for (var key in cache) {
                    if (cache.hasOwnProperty(key) && cache[key]._webAudio === false) {
                        for (var i = 0; i < cache[key]._audioNode.length; i++) {
                            cache[key]._audioNode[i].volume = cache[key]._volume * self._volume
                        }
                    }
                }
                return self
            }
            return usingWebAudio ? masterGain.gain.value : self._volume
        },
        mute: function () {
            var self = this;
            self._muted = true;
            if (usingWebAudio) {
                masterGain.gain.value = 0
            }
            for (var key in cache) {
                if (cache.hasOwnProperty(key) && cache[key]._webAudio === false) {
                    for (var i = 0; i < cache[key]._audioNode.length; i++) {
                        cache[key]._audioNode[i].volume = 0
                    }
                }
            }
            return self
        },
        unmute: function () {
            var self = this;
            self._muted = false;
            if (usingWebAudio) {
                masterGain.gain.value = self._volume
            }
            for (var key in cache) {
                if (cache.hasOwnProperty(key) && cache[key]._webAudio === false) {
                    for (var i = 0; i < cache[key]._audioNode.length; i++) {
                        cache[key]._audioNode[i].volume = cache[key]._volume * self._volume
                    }
                }
            }
            return self
        }
    };
    window.Howler = new HowlerGlobal;
    var audioTest = null;
    if (!noAudio) {
        audioTest = new Audio;
        var codecs = {
            mp3: !! audioTest.canPlayType("audio/mpeg;").replace(/^no$/, ""),
            ogg: !! audioTest.canPlayType('audio/ogg; codecs="vorbis"').replace(/^no$/, ""),
            wav: !! audioTest.canPlayType('audio/wav; codecs="1"').replace(/^no$/, ""),
            m4a: !! (audioTest.canPlayType("audio/x-m4a;") || audioTest.canPlayType("audio/aac;")).replace(/^no$/, ""),
            webm: !! audioTest.canPlayType('audio/webm; codecs="vorbis"').replace(/^no$/, "")
        }
    }
    var Howl = window.Howl = function (o) {
        var self = this;
        self._autoplay = o.autoplay || false;
        self._buffer = o.buffer || false;
        self._duration = o.duration || 0;
        self._loop = o.loop || false;
        self._loaded = false;
        self._sprite = o.sprite || {};
        self._src = o.src || "";
        self._pos3d = o.pos3d || [0, 0, -.5];
        self._volume = o.volume || 1;
        self._urls = o.urls || [];
        self._onload = [o.onload || function () {}];
        self._onend = [o.onend || function () {}];
        self._onpause = [o.onpause || function () {}];
        self._onplay = [o.onplay || function () {}];
        self._onendTimer = [];
        self._webAudio = usingWebAudio && !self._buffer;
        self._audioNode = [];
        if (self._webAudio) {
            self._setupAudioNode()
        }
        self.load()
    };
    Howl.prototype = {
        load: function () {
            var self = this,
                url = null;
            if (noAudio) {
                return
            }
            for (var i = 0; i < self._urls.length; i++) {
                var ext = self._urls[i].toLowerCase().match(/.+\.([^?]+)(\?|$)/),
                    canPlay = false;
                ext = ext && ext.length >= 2 ? ext[1] : self._urls[i].toLowerCase().match(/data\:audio\/([^?]+);/)[1];
                switch (ext) {
                case "mp3":
                    canPlay = codecs.mp3;
                    break;
                case "ogg":
                    canPlay = codecs.ogg;
                    break;
                case "wav":
                    canPlay = codecs.wav;
                    break;
                case "m4a":
                    canPlay = codecs.m4a;
                    break;
                case "weba":
                    canPlay = codecs.webm;
                    break
                }
                if (canPlay === true) {
                    url = self._urls[i];
                    break
                }
            }
            if (!url) {
                return
            }
            self._src = url;
            if (self._webAudio) {
                loadBuffer(self, url)
            } else {
                var newNode = new Audio;
                self._audioNode.push(newNode);
                newNode.src = url;
                newNode._pos = 0;
                newNode.preload = "auto";
                newNode.volume = Howler._muted ? 0 : self._volume * Howler.volume();
                cache[url] = self;
                var listener = function () {
                    self._duration = newNode.duration;
                    if (Object.getOwnPropertyNames(self._sprite).length === 0) {
                        self._sprite = {
                            _default: [0, self._duration * 1e3]
                        }
                    }
                    if (!self._loaded) {
                        self._loaded = true;
                        self.on("load")
                    }
                    if (self._autoplay) {
                        self.play()
                    }
                    newNode.removeEventListener("canplaythrough", listener, false)
                };
                newNode.addEventListener("canplaythrough", listener, false);
                newNode.load()
            }
            return self
        },
        urls: function (urls) {
            var self = this;
            if (urls) {
                self._urls = urls;
                self.stop();
                self.load();
                return self
            } else {
                return self._urls
            }
        },
        play: function (sprite, callback) {
            var self = this;
            if (!sprite) {
                sprite = "_default"
            }
            if (!self._loaded) {
                self.on("load", function () {
                    self.play(sprite, callback)
                });
                return self
            }
            if (!self._sprite[sprite]) {
                if (typeof callback === "function") callback();
                return self
            }
            self._inactiveNode(function (node) {
                node._sprite = sprite;
                var pos = node._pos > 0 ? node._pos : self._sprite[sprite][0] / 1e3,
                    duration = self._sprite[sprite][1] / 1e3 - node._pos;
                var loop = !! (self._loop || self._sprite[sprite][2]);
                var soundId = typeof callback === "string" ? callback : Math.round(Date.now() * Math.random()) + "",
                    timerId;
                (function () {
                    var data = {
                        id: soundId,
                        sprite: sprite,
                        loop: loop
                    };
                    timerId = setTimeout(function () {
                        if (!self._webAudio && loop) {
                            self.stop(data.id, data.timer).play(sprite, data.id)
                        }
                        if (self._webAudio && !loop) {
                            self._nodeById(data.id).paused = true
                        }
                        if (!self._webAudio && !loop) {
                            self.stop(data.id, data.timer)
                        }
                        self.on("end")
                    }, duration * 1e3);
                    self._onendTimer.push(timerId);
                    data.timer = self._onendTimer[self._onendTimer.length - 1]
                })();
                if (self._webAudio) {
                    node.id = soundId;
                    node.paused = false;
                    refreshBuffer(self, [loop, pos, duration], soundId);
                    self._playStart = ctx.currentTime;
                    if (typeof node.bufferSource.start === "undefined") {
                        node.bufferSource.noteGrainOn(0, pos, duration)
                    } else {
                        node.bufferSource.start(0, pos, duration)
                    }
                } else {
                    if (node.readyState === 4) {
                        node.id = soundId;
                        node.currentTime = pos;
                        node.play()
                    } else {
                        self._clearEndTimer(timerId);
                        (function () {
                            var sound = self,
                                playSprite = sprite,
                                fn = callback,
                                newNode = node;
                            var listener = function () {
                                sound.play(playSprite, fn);
                                newNode.removeEventListener("canplaythrough", listener, false)
                            };
                            newNode.addEventListener("canplaythrough", listener, false)
                        })();
                        return self
                    }
                }
                self.on("play");
                if (typeof callback === "function") callback(soundId);
                return self
            })
        },
        pause: function (id, timerId) {
            var self = this;
            if (!self._loaded) {
                self.on("play", function () {
                    self.pause(id)
                });
                return self
            }
            self._clearEndTimer(timerId || 0);
            var activeNode = id ? self._nodeById(id) : self._activeNode();
            if (activeNode) {
                if (self._webAudio) {
                    if (!activeNode.bufferSource) {
                        return self
                    }
                    activeNode.paused = true;
                    activeNode._pos += ctx.currentTime - self._playStart;
                    if (typeof activeNode.bufferSource.stop === "undefined") {
                        activeNode.bufferSource.noteOff(0)
                    } else {
                        activeNode.bufferSource.stop(0)
                    }
                } else {
                    activeNode._pos = activeNode.currentTime;
                    activeNode.pause()
                }
            }
            self.on("pause");
            return self
        },
        stop: function (id, timerId) {
            var self = this;
            if (!self._loaded) {
                self.on("play", function () {
                    self.stop(id)
                });
                return self
            }
            self._clearEndTimer(timerId || 0);
            var activeNode = id ? self._nodeById(id) : self._activeNode();
            if (activeNode) {
                activeNode._pos = 0;
                if (self._webAudio) {
                    if (!activeNode.bufferSource) {
                        return self
                    }
                    activeNode.paused = true;
                    if (typeof activeNode.bufferSource.stop === "undefined") {
                        activeNode.bufferSource.noteOff(0)
                    } else {
                        activeNode.bufferSource.stop(0)
                    }
                } else {
                    activeNode.pause();
                    activeNode.currentTime = 0
                }
            }
            return self
        },
        mute: function (id) {
            var self = this;
            if (!self._loaded) {
                self.on("play", function () {
                    self.mute(id)
                });
                return self
            }
            var activeNode = id ? self._nodeById(id) : self._activeNode();
            if (activeNode) {
                if (self._webAudio) {
                    activeNode.gain.value = 0
                } else {
                    activeNode.volume = 0
                }
            }
            return self
        },
        unmute: function (id) {
            var self = this;
            if (!self._loaded) {
                self.on("play", function () {
                    self.unmute(id)
                });
                return self
            }
            var activeNode = id ? self._nodeById(id) : self._activeNode();
            if (activeNode) {
                if (self._webAudio) {
                    activeNode.gain.value = self._volume
                } else {
                    activeNode.volume = self._volume
                }
            }
            return self
        },
        volume: function (vol, id) {
            var self = this;
            vol = parseFloat(vol, 10);
            if (!self._loaded) {
                self.on("play", function () {
                    self.volume(vol, id)
                });
                return self
            }
            if (vol >= 0 && vol <= 1) {
                self._volume = vol;
                var activeNode = id ? self._nodeById(id) : self._activeNode();
                if (activeNode) {
                    if (self._webAudio) {
                        activeNode.gain.value = vol
                    } else {
                        activeNode.volume = vol * Howler.volume()
                    }
                }
                return self
            } else {
                return self._volume
            }
        },
        loop: function (loop) {
            var self = this;
            if (typeof loop === "boolean") {
                self._loop = loop;
                return self
            } else {
                return self._loop
            }
        },
        sprite: function (sprite) {
            var self = this;
            if (typeof sprite === "object") {
                self._sprite = sprite;
                return self
            } else {
                return self._sprite
            }
        },
        pos: function (pos, id) {
            var self = this;
            if (!self._loaded) {
                self.on("load", function () {
                    self.pos(pos)
                });
                return self
            }
            var activeNode = id ? self._nodeById(id) : self._activeNode();
            if (activeNode) {
                if (self._webAudio) {
                    if (pos >= 0) {
                        activeNode._pos = pos;
                        self.pause(id).play(activeNode._sprite, id);
                        return self
                    } else {
                        return activeNode._pos + (ctx.currentTime - self._playStart)
                    }
                } else {
                    if (pos >= 0) {
                        activeNode.currentTime = pos;
                        return self
                    } else {
                        return activeNode.currentTime
                    }
                }
            }
        },
        pos3d: function (x, y, z, id) {
            var self = this;
            y = typeof y === "undefined" || !y ? 0 : y;
            z = typeof z === "undefined" || !z ? -.5 : z;
            if (!self._loaded) {
                self.on("play", function () {
                    self.pos3d(x, y, z, id)
                });
                return self
            }
            if (x >= 0 || x < 0) {
                if (self._webAudio) {
                    var activeNode = id ? self._nodeById(id) : self._activeNode();
                    if (activeNode) {
                        self._pos3d = [x, y, z];
                        activeNode.panner.setPosition(x, y, z)
                    }
                }
            } else {
                return self._pos3d
            }
            return self
        },
        fadeIn: function (to, len, callback) {
            var self = this,
                dist = to,
                iterations = dist / .01,
                hold = len / iterations;
            if (!self._loaded) {
                self.on("load", function () {
                    self.fadeIn(to, len, callback)
                });
                return self
            }
            self.volume(0).play();
            for (var i = 1; i <= iterations; i++) {
                (function () {
                    var vol = Math.round(1e3 * (self._volume + .01 * i)) / 1e3,
                        toVol = to;
                    setTimeout(function () {
                        self.volume(vol);
                        if (vol === toVol) {
                            if (callback) callback()
                        }
                    }, hold * i)
                })()
            }
            return self
        },
        fadeOut: function (to, len, callback, id) {
            var self = this,
                dist = self._volume - to,
                iterations = dist / .01,
                hold = len / iterations;
            if (!self._loaded) {
                self.on("play", function () {
                    self.fadeOut(to, len, callback, id)
                });
                return self
            }
            for (var i = 1; i <= iterations; i++) {
                (function () {
                    var vol = Math.round(1e3 * (self._volume - .01 * i)) / 1e3,
                        toVol = to;
                    setTimeout(function () {
                        self.volume(vol, id);
                        if (vol === toVol) {
                            if (callback) callback();
                            self.pause(id);
                            self.on("end")
                        }
                    }, hold * i)
                })()
            }
            return self
        },
        _nodeById: function (id) {
            var self = this,
                node = self._audioNode[0];
            for (var i = 0; i < self._audioNode.length; i++) {
                if (self._audioNode[i].id === id) {
                    node = self._audioNode[i];
                    break
                }
            }
            return node
        },
        _activeNode: function () {
            var self = this,
                node = null;
            for (var i = 0; i < self._audioNode.length; i++) {
                if (!self._audioNode[i].paused) {
                    node = self._audioNode[i];
                    break
                }
            }
            self._drainPool();
            return node
        },
        _inactiveNode: function (callback) {
            var self = this,
                node = null;
            for (var i = 0; i < self._audioNode.length; i++) {
                if (self._audioNode[i].paused && self._audioNode[i].readyState === 4) {
                    callback(self._audioNode[i]);
                    node = true;
                    break
                }
            }
            self._drainPool();
            if (node) {
                return
            }
            var newNode;
            if (self._webAudio) {
                newNode = self._setupAudioNode();
                callback(newNode)
            } else {
                self.load();
                newNode = self._audioNode[self._audioNode.length - 1];
                newNode.addEventListener("loadedmetadata", function () {
                    callback(newNode)
                })
            }
        },
        _drainPool: function () {
            var self = this,
                inactive = 0,
                i;
            for (i = 0; i < self._audioNode.length; i++) {
                if (self._audioNode[i].paused) {
                    inactive++
                }
            }
            for (i = 0; i < self._audioNode.length; i++) {
                if (inactive <= 5) {
                    break
                }
                if (self._audioNode[i].paused) {
                    inactive--;
                    self._audioNode.splice(i, 1)
                }
            }
        },
        _clearEndTimer: function (timerId) {
            var self = this,
                timer = self._onendTimer.indexOf(timerId);
            timer = timer >= 0 ? timer : 0;
            if (self._onendTimer[timer]) {
                clearTimeout(self._onendTimer[timer]);
                self._onendTimer.splice(timer, 1)
            }
        },
        _setupAudioNode: function () {
            var self = this,
                node = self._audioNode,
                index = self._audioNode.length;
            node[index] = typeof ctx.createGain === "undefined" ? ctx.createGainNode() : ctx.createGain();
            node[index].gain.value = self._volume;
            node[index].paused = true;
            node[index]._pos = 0;
            node[index].readyState = 4;
            node[index].connect(masterGain);
            node[index].panner = ctx.createPanner();
            node[index].panner.setPosition(self._pos3d[0], self._pos3d[1], self._pos3d[2]);
            node[index].panner.connect(node[index]);
            return node[index]
        },
        on: function (event, fn) {
            var self = this,
                events = self["_on" + event];
            if (fn) {
                events.push(fn)
            } else {
                for (var i = 0; i < events.length; i++) {
                    events[i].call(self)
                }
            }
            return self
        },
        off: function (event, fn) {
            var self = this,
                events = self["_on" + event],
                fnString = fn.toString();
            for (var i = 0; i < events.length; i++) {
                if (fnString === events[i].toString()) {
                    events.splice(i, 1);
                    break
                }
            }
            return self
        }
    };
    if (usingWebAudio) {
        var loadBuffer = function (obj, url) {
            if (url in cache) {
                obj._duration = cache[url].duration;
                loadSound(obj)
            } else {
                var xhr = new XMLHttpRequest;
                xhr.open("GET", url, true);
                xhr.responseType = "arraybuffer";
                xhr.onload = function () {
                    ctx.decodeAudioData(xhr.response, function (buffer) {
                        if (buffer) {
                            cache[url] = buffer;
                            loadSound(obj, buffer)
                        }
                    })
                };
                xhr.onerror = function () {
                    if (obj._webAudio) {
                        obj._buffer = true;
                        obj._webAudio = false;
                        obj._audioNode = [];
                        delete obj._gainNode;
                        obj.load()
                    }
                };
                try {
                    xhr.send()
                } catch (e) {
                    xhr.onerror()
                }
            }
        };
        var loadSound = function (obj, buffer) {
            obj._duration = buffer ? buffer.duration : obj._duration;
            if (Object.getOwnPropertyNames(obj._sprite).length === 0) {
                obj._sprite = {
                    _default: [0, obj._duration * 1e3]
                }
            }
            if (!obj._loaded) {
                obj._loaded = true;
                obj.on("load")
            }
            if (obj._autoplay) {
                obj.play()
            }
        };
        var refreshBuffer = function (obj, loop, id) {
            var node = obj._nodeById(id);
            node.bufferSource = ctx.createBufferSource();
            node.bufferSource.buffer = cache[obj._src];
            node.bufferSource.connect(node.panner);
            node.bufferSource.loop = loop[0];
            if (loop[0]) {
                node.bufferSource.loopStart = loop[1];
                node.bufferSource.loopEnd = loop[1] + loop[2]
            }
        }
    }
    if (typeof define === "function" && define.amd) {
        define("Howler", function () {
            return Howler
        });
        define("Howl", function () {
            return Howl
        })
    }
})();
(function (e, undefined) {
    var t, n, r = typeof undefined,
        i = e.location,
        o = e.document,
        s = o.documentElement,
        a = e.jQuery,
        u = e.$,
        l = {}, c = [],
        p = "2.0.3",
        f = c.concat,
        h = c.push,
        d = c.slice,
        g = c.indexOf,
        m = l.toString,
        y = l.hasOwnProperty,
        v = p.trim,
        x = function (e, n) {
            return new x.fn.init(e, n, t)
        }, b = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source,
        w = /\S+/g,
        T = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/,
        C = /^<(\w+)\s*\/?>(?:<\/\1>|)$/,
        k = /^-ms-/,
        N = /-([\da-z])/gi,
        E = function (e, t) {
            return t.toUpperCase()
        }, S = function () {
            o.removeEventListener("DOMContentLoaded", S, !1), e.removeEventListener("load", S, !1), x.ready()
        };
    x.fn = x.prototype = {
        jquery: p,
        constructor: x,
        init: function (e, t, n) {
            var r, i;
            if (!e) return this;
            if ("string" == typeof e) {
                if (r = "<" === e.charAt(0) && ">" === e.charAt(e.length - 1) && e.length >= 3 ? [null, e, null] : T.exec(e), !r || !r[1] && t) return !t || t.jquery ? (t || n).find(e) : this.constructor(t).find(e);
                if (r[1]) {
                    if (t = t instanceof x ? t[0] : t, x.merge(this, x.parseHTML(r[1], t && t.nodeType ? t.ownerDocument || t : o, !0)), C.test(r[1]) && x.isPlainObject(t))
                        for (r in t) x.isFunction(this[r]) ? this[r](t[r]) : this.attr(r, t[r]);
                    return this
                }
                return i = o.getElementById(r[2]), i && i.parentNode && (this.length = 1, this[0] = i), this.context = o, this.selector = e, this
            }
            return e.nodeType ? (this.context = this[0] = e, this.length = 1, this) : x.isFunction(e) ? n.ready(e) : (e.selector !== undefined && (this.selector = e.selector, this.context = e.context), x.makeArray(e, this))
        },
        selector: "",
        length: 0,
        toArray: function () {
            return d.call(this)
        },
        get: function (e) {
            return null == e ? this.toArray() : 0 > e ? this[this.length + e] : this[e]
        },
        pushStack: function (e) {
            var t = x.merge(this.constructor(), e);
            return t.prevObject = this, t.context = this.context, t
        },
        each: function (e, t) {
            return x.each(this, e, t)
        },
        ready: function (e) {
            return x.ready.promise().done(e), this
        },
        slice: function () {
            return this.pushStack(d.apply(this, arguments))
        },
        first: function () {
            return this.eq(0)
        },
        last: function () {
            return this.eq(-1)
        },
        eq: function (e) {
            var t = this.length,
                n = +e + (0 > e ? t : 0);
            return this.pushStack(n >= 0 && t > n ? [this[n]] : [])
        },
        map: function (e) {
            return this.pushStack(x.map(this, function (t, n) {
                return e.call(t, n, t)
            }))
        },
        end: function () {
            return this.prevObject || this.constructor(null)
        },
        push: h,
        sort: [].sort,
        splice: [].splice
    }, x.fn.init.prototype = x.fn, x.extend = x.fn.extend = function () {
        var e, t, n, r, i, o, s = arguments[0] || {}, a = 1,
            u = arguments.length,
            l = !1;
        for ("boolean" == typeof s && (l = s, s = arguments[1] || {}, a = 2), "object" == typeof s || x.isFunction(s) || (s = {}), u === a && (s = this, --a); u > a; a++)
            if (null != (e = arguments[a]))
                for (t in e) n = s[t], r = e[t], s !== r && (l && r && (x.isPlainObject(r) || (i = x.isArray(r))) ? (i ? (i = !1, o = n && x.isArray(n) ? n : []) : o = n && x.isPlainObject(n) ? n : {}, s[t] = x.extend(l, o, r)) : r !== undefined && (s[t] = r));
        return s
    }, x.extend({
        expando: "jQuery" + (p + Math.random()).replace(/\D/g, ""),
        noConflict: function (t) {
            return e.$ === x && (e.$ = u), t && e.jQuery === x && (e.jQuery = a), x
        },
        isReady: !1,
        readyWait: 1,
        holdReady: function (e) {
            e ? x.readyWait++ : x.ready(!0)
        },
        ready: function (e) {
            (e === !0 ? --x.readyWait : x.isReady) || (x.isReady = !0, e !== !0 && --x.readyWait > 0 || (n.resolveWith(o, [x]), x.fn.trigger && x(o).trigger("ready").off("ready")))
        },
        isFunction: function (e) {
            return "function" === x.type(e)
        },
        isArray: Array.isArray,
        isWindow: function (e) {
            return null != e && e === e.window
        },
        isNumeric: function (e) {
            return !isNaN(parseFloat(e)) && isFinite(e)
        },
        type: function (e) {
            return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? l[m.call(e)] || "object" : typeof e
        },
        isPlainObject: function (e) {
            if ("object" !== x.type(e) || e.nodeType || x.isWindow(e)) return !1;
            try {
                if (e.constructor && !y.call(e.constructor.prototype, "isPrototypeOf")) return !1
            } catch (t) {
                return !1
            }
            return !0
        },
        isEmptyObject: function (e) {
            var t;
            for (t in e) return !1;
            return !0
        },
        error: function (e) {
            throw Error(e)
        },
        parseHTML: function (e, t, n) {
            if (!e || "string" != typeof e) return null;
            "boolean" == typeof t && (n = t, t = !1), t = t || o;
            var r = C.exec(e),
                i = !n && [];
            return r ? [t.createElement(r[1])] : (r = x.buildFragment([e], t, i), i && x(i).remove(), x.merge([], r.childNodes))
        },
        parseJSON: JSON.parse,
        parseXML: function (e) {
            var t, n;
            if (!e || "string" != typeof e) return null;
            try {
                n = new DOMParser, t = n.parseFromString(e, "text/xml")
            } catch (r) {
                t = undefined
            }
            return (!t || t.getElementsByTagName("parsererror").length) && x.error("Invalid XML: " + e), t
        },
        noop: function () {},
        globalEval: function (e) {
            var t, n = eval;
            e = x.trim(e), e && (1 === e.indexOf("use strict") ? (t = o.createElement("script"), t.text = e, o.head.appendChild(t).parentNode.removeChild(t)) : n(e))
        },
        camelCase: function (e) {
            return e.replace(k, "ms-").replace(N, E)
        },
        nodeName: function (e, t) {
            return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
        },
        each: function (e, t, n) {
            var r, i = 0,
                o = e.length,
                s = j(e);
            if (n) {
                if (s) {
                    for (; o > i; i++)
                        if (r = t.apply(e[i], n), r === !1) break
                } else
                    for (i in e)
                        if (r = t.apply(e[i], n), r === !1) break
            } else if (s) {
                for (; o > i; i++)
                    if (r = t.call(e[i], i, e[i]), r === !1) break
            } else
                for (i in e)
                    if (r = t.call(e[i], i, e[i]), r === !1) break; return e
        },
        trim: function (e) {
            return null == e ? "" : v.call(e)
        },
        makeArray: function (e, t) {
            var n = t || [];
            return null != e && (j(Object(e)) ? x.merge(n, "string" == typeof e ? [e] : e) : h.call(n, e)), n
        },
        inArray: function (e, t, n) {
            return null == t ? -1 : g.call(t, e, n)
        },
        merge: function (e, t) {
            var n = t.length,
                r = e.length,
                i = 0;
            if ("number" == typeof n)
                for (; n > i; i++) e[r++] = t[i];
            else
                while (t[i] !== undefined) e[r++] = t[i++];
            return e.length = r, e
        },
        grep: function (e, t, n) {
            var r, i = [],
                o = 0,
                s = e.length;
            for (n = !! n; s > o; o++) r = !! t(e[o], o), n !== r && i.push(e[o]);
            return i
        },
        map: function (e, t, n) {
            var r, i = 0,
                o = e.length,
                s = j(e),
                a = [];
            if (s)
                for (; o > i; i++) r = t(e[i], i, n), null != r && (a[a.length] = r);
            else
                for (i in e) r = t(e[i], i, n), null != r && (a[a.length] = r);
            return f.apply([], a)
        },
        guid: 1,
        proxy: function (e, t) {
            var n, r, i;
            return "string" == typeof t && (n = e[t], t = e, e = n), x.isFunction(e) ? (r = d.call(arguments, 2), i = function () {
                return e.apply(t || this, r.concat(d.call(arguments)))
            }, i.guid = e.guid = e.guid || x.guid++, i) : undefined
        },
        access: function (e, t, n, r, i, o, s) {
            var a = 0,
                u = e.length,
                l = null == n;
            if ("object" === x.type(n)) {
                i = !0;
                for (a in n) x.access(e, t, a, n[a], !0, o, s)
            } else if (r !== undefined && (i = !0, x.isFunction(r) || (s = !0), l && (s ? (t.call(e, r), t = null) : (l = t, t = function (e, t, n) {
                return l.call(x(e), n)
            })), t))
                for (; u > a; a++) t(e[a], n, s ? r : r.call(e[a], a, t(e[a], n)));
            return i ? e : l ? t.call(e) : u ? t(e[0], n) : o
        },
        now: Date.now,
        swap: function (e, t, n, r) {
            var i, o, s = {};
            for (o in t) s[o] = e.style[o], e.style[o] = t[o];
            i = n.apply(e, r || []);
            for (o in t) e.style[o] = s[o];
            return i
        }
    }), x.ready.promise = function (t) {
        return n || (n = x.Deferred(), "complete" === o.readyState ? setTimeout(x.ready) : (o.addEventListener("DOMContentLoaded", S, !1), e.addEventListener("load", S, !1))), n.promise(t)
    }, x.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (e, t) {
        l["[object " + t + "]"] = t.toLowerCase()
    });

    function j(e) {
        var t = e.length,
            n = x.type(e);
        return x.isWindow(e) ? !1 : 1 === e.nodeType && t ? !0 : "array" === n || "function" !== n && (0 === t || "number" == typeof t && t > 0 && t - 1 in e)
    }
    t = x(o),
    function (e, undefined) {
        var t, n, r, i, o, s, a, u, l, c, p, f, h, d, g, m, y, v = "sizzle" + -new Date,
            b = e.document,
            w = 0,
            T = 0,
            C = st(),
            k = st(),
            N = st(),
            E = !1,
            S = function (e, t) {
                return e === t ? (E = !0, 0) : 0
            }, j = typeof undefined,
            D = 1 << 31,
            A = {}.hasOwnProperty,
            L = [],
            q = L.pop,
            H = L.push,
            O = L.push,
            F = L.slice,
            P = L.indexOf || function (e) {
                var t = 0,
                    n = this.length;
                for (; n > t; t++)
                    if (this[t] === e) return t;
                return -1
            }, R = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
            M = "[\\x20\\t\\r\\n\\f]",
            W = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",
            $ = W.replace("w", "w#"),
            B = "\\[" + M + "*(" + W + ")" + M + "*(?:([*^$|!~]?=)" + M + "*(?:(['\"])((?:\\\\.|[^\\\\])*?)\\3|(" + $ + ")|)|)" + M + "*\\]",
            I = ":(" + W + ")(?:\\(((['\"])((?:\\\\.|[^\\\\])*?)\\3|((?:\\\\.|[^\\\\()[\\]]|" + B.replace(3, 8) + ")*)|.*)\\)|)",
            z = RegExp("^" + M + "+|((?:^|[^\\\\])(?:\\\\.)*)" + M + "+$", "g"),
            _ = RegExp("^" + M + "*," + M + "*"),
            X = RegExp("^" + M + "*([>+~]|" + M + ")" + M + "*"),
            U = RegExp(M + "*[+~]"),
            Y = RegExp("=" + M + "*([^\\]'\"]*)" + M + "*\\]", "g"),
            V = RegExp(I),
            G = RegExp("^" + $ + "$"),
            J = {
                ID: RegExp("^#(" + W + ")"),
                CLASS: RegExp("^\\.(" + W + ")"),
                TAG: RegExp("^(" + W.replace("w", "w*") + ")"),
                ATTR: RegExp("^" + B),
                PSEUDO: RegExp("^" + I),
                CHILD: RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + M + "*(even|odd|(([+-]|)(\\d*)n|)" + M + "*(?:([+-]|)" + M + "*(\\d+)|))" + M + "*\\)|)", "i"),
                bool: RegExp("^(?:" + R + ")$", "i"),
                needsContext: RegExp("^" + M + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + M + "*((?:-\\d)?\\d*)" + M + "*\\)|)(?=[^-]|$)", "i")
            }, Q = /^[^{]+\{\s*\[native \w/,
            K = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,
            Z = /^(?:input|select|textarea|button)$/i,
            et = /^h\d$/i,
            tt = /'|\\/g,
            nt = RegExp("\\\\([\\da-f]{1,6}" + M + "?|(" + M + ")|.)", "ig"),
            rt = function (e, t, n) {
                var r = "0x" + t - 65536;
                return r !== r || n ? t : 0 > r ? String.fromCharCode(r + 65536) : String.fromCharCode(55296 | r >> 10, 56320 | 1023 & r)
            };
        try {
            O.apply(L = F.call(b.childNodes), b.childNodes), L[b.childNodes.length].nodeType
        } catch (it) {
            O = {
                apply: L.length ? function (e, t) {
                    H.apply(e, F.call(t))
                } : function (e, t) {
                    var n = e.length,
                        r = 0;
                    while (e[n++] = t[r++]);
                    e.length = n - 1
                }
            }
        }

        function ot(e, t, r, i) {
            var o, s, a, u, l, f, g, m, x, w;
            if ((t ? t.ownerDocument || t : b) !== p && c(t), t = t || p, r = r || [], !e || "string" != typeof e) return r;
            if (1 !== (u = t.nodeType) && 9 !== u) return [];
            if (h && !i) {
                if (o = K.exec(e))
                    if (a = o[1]) {
                        if (9 === u) {
                            if (s = t.getElementById(a), !s || !s.parentNode) return r;
                            if (s.id === a) return r.push(s), r
                        } else if (t.ownerDocument && (s = t.ownerDocument.getElementById(a)) && y(t, s) && s.id === a) return r.push(s), r
                    } else {
                        if (o[2]) return O.apply(r, t.getElementsByTagName(e)), r;
                        if ((a = o[3]) && n.getElementsByClassName && t.getElementsByClassName) return O.apply(r, t.getElementsByClassName(a)), r
                    }
                if (n.qsa && (!d || !d.test(e))) {
                    if (m = g = v, x = t, w = 9 === u && e, 1 === u && "object" !== t.nodeName.toLowerCase()) {
                        f = gt(e), (g = t.getAttribute("id")) ? m = g.replace(tt, "\\$&") : t.setAttribute("id", m), m = "[id='" + m + "'] ", l = f.length;
                        while (l--) f[l] = m + mt(f[l]);
                        x = U.test(e) && t.parentNode || t, w = f.join(",")
                    }
                    if (w) try {
                        return O.apply(r, x.querySelectorAll(w)), r
                    } catch (T) {} finally {
                        g || t.removeAttribute("id")
                    }
                }
            }
            return kt(e.replace(z, "$1"), t, r, i)
        }

        function st() {
            var e = [];

            function t(n, r) {
                return e.push(n += " ") > i.cacheLength && delete t[e.shift()], t[n] = r
            }
            return t
        }

        function at(e) {
            return e[v] = !0, e
        }

        function ut(e) {
            var t = p.createElement("div");
            try {
                return !!e(t)
            } catch (n) {
                return !1
            } finally {
                t.parentNode && t.parentNode.removeChild(t), t = null
            }
        }

        function lt(e, t) {
            var n = e.split("|"),
                r = e.length;
            while (r--) i.attrHandle[n[r]] = t
        }

        function ct(e, t) {
            var n = t && e,
                r = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || D) - (~e.sourceIndex || D);
            if (r) return r;
            if (n)
                while (n = n.nextSibling)
                    if (n === t) return -1;
            return e ? 1 : -1
        }

        function pt(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return "input" === n && t.type === e
            }
        }

        function ft(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return ("input" === n || "button" === n) && t.type === e
            }
        }

        function ht(e) {
            return at(function (t) {
                return t = +t, at(function (n, r) {
                    var i, o = e([], n.length, t),
                        s = o.length;
                    while (s--) n[i = o[s]] && (n[i] = !(r[i] = n[i]))
                })
            })
        }
        s = ot.isXML = function (e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return t ? "HTML" !== t.nodeName : !1
        }, n = ot.support = {}, c = ot.setDocument = function (e) {
            var t = e ? e.ownerDocument || e : b,
                r = t.defaultView;
            return t !== p && 9 === t.nodeType && t.documentElement ? (p = t, f = t.documentElement, h = !s(t), r && r.attachEvent && r !== r.top && r.attachEvent("onbeforeunload", function () {
                c()
            }), n.attributes = ut(function (e) {
                return e.className = "i", !e.getAttribute("className")
            }), n.getElementsByTagName = ut(function (e) {
                return e.appendChild(t.createComment("")), !e.getElementsByTagName("*").length
            }), n.getElementsByClassName = ut(function (e) {
                return e.innerHTML = "<div class='a'></div><div class='a i'></div>", e.firstChild.className = "i", 2 === e.getElementsByClassName("i").length
            }), n.getById = ut(function (e) {
                return f.appendChild(e).id = v, !t.getElementsByName || !t.getElementsByName(v).length
            }), n.getById ? (i.find.ID = function (e, t) {
                if (typeof t.getElementById !== j && h) {
                    var n = t.getElementById(e);
                    return n && n.parentNode ? [n] : []
                }
            }, i.filter.ID = function (e) {
                var t = e.replace(nt, rt);
                return function (e) {
                    return e.getAttribute("id") === t
                }
            }) : (delete i.find.ID, i.filter.ID = function (e) {
                var t = e.replace(nt, rt);
                return function (e) {
                    var n = typeof e.getAttributeNode !== j && e.getAttributeNode("id");
                    return n && n.value === t
                }
            }), i.find.TAG = n.getElementsByTagName ? function (e, t) {
                return typeof t.getElementsByTagName !== j ? t.getElementsByTagName(e) : undefined
            } : function (e, t) {
                var n, r = [],
                    i = 0,
                    o = t.getElementsByTagName(e);
                if ("*" === e) {
                    while (n = o[i++]) 1 === n.nodeType && r.push(n);
                    return r
                }
                return o
            }, i.find.CLASS = n.getElementsByClassName && function (e, t) {
                return typeof t.getElementsByClassName !== j && h ? t.getElementsByClassName(e) : undefined
            }, g = [], d = [], (n.qsa = Q.test(t.querySelectorAll)) && (ut(function (e) {
                e.innerHTML = "<select><option selected=''></option></select>", e.querySelectorAll("[selected]").length || d.push("\\[" + M + "*(?:value|" + R + ")"), e.querySelectorAll(":checked").length || d.push(":checked")
            }), ut(function (e) {
                var n = t.createElement("input");
                n.setAttribute("type", "hidden"), e.appendChild(n).setAttribute("t", ""), e.querySelectorAll("[t^='']").length && d.push("[*^$]=" + M + "*(?:''|\"\")"), e.querySelectorAll(":enabled").length || d.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), d.push(",.*:")
            })), (n.matchesSelector = Q.test(m = f.webkitMatchesSelector || f.mozMatchesSelector || f.oMatchesSelector || f.msMatchesSelector)) && ut(function (e) {
                n.disconnectedMatch = m.call(e, "div"), m.call(e, "[s!='']:x"), g.push("!=", I)
            }), d = d.length && RegExp(d.join("|")), g = g.length && RegExp(g.join("|")), y = Q.test(f.contains) || f.compareDocumentPosition ? function (e, t) {
                var n = 9 === e.nodeType ? e.documentElement : e,
                    r = t && t.parentNode;
                return e === r || !(!r || 1 !== r.nodeType || !(n.contains ? n.contains(r) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(r)))
            } : function (e, t) {
                if (t)
                    while (t = t.parentNode)
                        if (t === e) return !0;
                return !1
            }, S = f.compareDocumentPosition ? function (e, r) {
                if (e === r) return E = !0, 0;
                var i = r.compareDocumentPosition && e.compareDocumentPosition && e.compareDocumentPosition(r);
                return i ? 1 & i || !n.sortDetached && r.compareDocumentPosition(e) === i ? e === t || y(b, e) ? -1 : r === t || y(b, r) ? 1 : l ? P.call(l, e) - P.call(l, r) : 0 : 4 & i ? -1 : 1 : e.compareDocumentPosition ? -1 : 1
            } : function (e, n) {
                var r, i = 0,
                    o = e.parentNode,
                    s = n.parentNode,
                    a = [e],
                    u = [n];
                if (e === n) return E = !0, 0;
                if (!o || !s) return e === t ? -1 : n === t ? 1 : o ? -1 : s ? 1 : l ? P.call(l, e) - P.call(l, n) : 0;
                if (o === s) return ct(e, n);
                r = e;
                while (r = r.parentNode) a.unshift(r);
                r = n;
                while (r = r.parentNode) u.unshift(r);
                while (a[i] === u[i]) i++;
                return i ? ct(a[i], u[i]) : a[i] === b ? -1 : u[i] === b ? 1 : 0
            }, t) : p
        }, ot.matches = function (e, t) {
            return ot(e, null, null, t)
        }, ot.matchesSelector = function (e, t) {
            if ((e.ownerDocument || e) !== p && c(e), t = t.replace(Y, "='$1']"), !(!n.matchesSelector || !h || g && g.test(t) || d && d.test(t))) try {
                var r = m.call(e, t);
                if (r || n.disconnectedMatch || e.document && 11 !== e.document.nodeType) return r
            } catch (i) {}
            return ot(t, p, null, [e]).length > 0
        }, ot.contains = function (e, t) {
            return (e.ownerDocument || e) !== p && c(e), y(e, t)
        }, ot.attr = function (e, t) {
            (e.ownerDocument || e) !== p && c(e);
            var r = i.attrHandle[t.toLowerCase()],
                o = r && A.call(i.attrHandle, t.toLowerCase()) ? r(e, t, !h) : undefined;
            return o === undefined ? n.attributes || !h ? e.getAttribute(t) : (o = e.getAttributeNode(t)) && o.specified ? o.value : null : o
        }, ot.error = function (e) {
            throw Error("Syntax error, unrecognized expression: " + e)
        }, ot.uniqueSort = function (e) {
            var t, r = [],
                i = 0,
                o = 0;
            if (E = !n.detectDuplicates, l = !n.sortStable && e.slice(0), e.sort(S), E) {
                while (t = e[o++]) t === e[o] && (i = r.push(o));
                while (i--) e.splice(r[i], 1)
            }
            return e
        }, o = ot.getText = function (e) {
            var t, n = "",
                r = 0,
                i = e.nodeType;
            if (i) {
                if (1 === i || 9 === i || 11 === i) {
                    if ("string" == typeof e.textContent) return e.textContent;
                    for (e = e.firstChild; e; e = e.nextSibling) n += o(e)
                } else if (3 === i || 4 === i) return e.nodeValue
            } else
                for (; t = e[r]; r++) n += o(t);
            return n
        }, i = ot.selectors = {
            cacheLength: 50,
            createPseudo: at,
            match: J,
            attrHandle: {},
            find: {},
            relative: {
                ">": {
                    dir: "parentNode",
                    first: !0
                },
                " ": {
                    dir: "parentNode"
                },
                "+": {
                    dir: "previousSibling",
                    first: !0
                },
                "~": {
                    dir: "previousSibling"
                }
            },
            preFilter: {
                ATTR: function (e) {
                    return e[1] = e[1].replace(nt, rt), e[3] = (e[4] || e[5] || "").replace(nt, rt), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                },
                CHILD: function (e) {
                    return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || ot.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && ot.error(e[0]), e
                },
                PSEUDO: function (e) {
                    var t, n = !e[5] && e[2];
                    return J.CHILD.test(e[0]) ? null : (e[3] && e[4] !== undefined ? e[2] = e[4] : n && V.test(n) && (t = gt(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                }
            },
            filter: {
                TAG: function (e) {
                    var t = e.replace(nt, rt).toLowerCase();
                    return "*" === e ? function () {
                        return !0
                    } : function (e) {
                        return e.nodeName && e.nodeName.toLowerCase() === t
                    }
                },
                CLASS: function (e) {
                    var t = C[e + " "];
                    return t || (t = RegExp("(^|" + M + ")" + e + "(" + M + "|$)")) && C(e, function (e) {
                        return t.test("string" == typeof e.className && e.className || typeof e.getAttribute !== j && e.getAttribute("class") || "")
                    })
                },
                ATTR: function (e, t, n) {
                    return function (r) {
                        var i = ot.attr(r, e);
                        return null == i ? "!=" === t : t ? (i += "", "=" === t ? i === n : "!=" === t ? i !== n : "^=" === t ? n && 0 === i.indexOf(n) : "*=" === t ? n && i.indexOf(n) > -1 : "$=" === t ? n && i.slice(-n.length) === n : "~=" === t ? (" " + i + " ").indexOf(n) > -1 : "|=" === t ? i === n || i.slice(0, n.length + 1) === n + "-" : !1) : !0
                    }
                },
                CHILD: function (e, t, n, r, i) {
                    var o = "nth" !== e.slice(0, 3),
                        s = "last" !== e.slice(-4),
                        a = "of-type" === t;
                    return 1 === r && 0 === i ? function (e) {
                        return !!e.parentNode
                    } : function (t, n, u) {
                        var l, c, p, f, h, d, g = o !== s ? "nextSibling" : "previousSibling",
                            m = t.parentNode,
                            y = a && t.nodeName.toLowerCase(),
                            x = !u && !a;
                        if (m) {
                            if (o) {
                                while (g) {
                                    p = t;
                                    while (p = p[g])
                                        if (a ? p.nodeName.toLowerCase() === y : 1 === p.nodeType) return !1;
                                    d = g = "only" === e && !d && "nextSibling"
                                }
                                return !0
                            }
                            if (d = [s ? m.firstChild : m.lastChild], s && x) {
                                c = m[v] || (m[v] = {}), l = c[e] || [], h = l[0] === w && l[1], f = l[0] === w && l[2], p = h && m.childNodes[h];
                                while (p = ++h && p && p[g] || (f = h = 0) || d.pop())
                                    if (1 === p.nodeType && ++f && p === t) {
                                        c[e] = [w, h, f];
                                        break
                                    }
                            } else if (x && (l = (t[v] || (t[v] = {}))[e]) && l[0] === w) f = l[1];
                            else
                                while (p = ++h && p && p[g] || (f = h = 0) || d.pop())
                                    if ((a ? p.nodeName.toLowerCase() === y : 1 === p.nodeType) && ++f && (x && ((p[v] || (p[v] = {}))[e] = [w, f]), p === t)) break; return f -= i, f === r || 0 === f % r && f / r >= 0
                        }
                    }
                },
                PSEUDO: function (e, t) {
                    var n, r = i.pseudos[e] || i.setFilters[e.toLowerCase()] || ot.error("unsupported pseudo: " + e);
                    return r[v] ? r(t) : r.length > 1 ? (n = [e, e, "", t], i.setFilters.hasOwnProperty(e.toLowerCase()) ? at(function (e, n) {
                        var i, o = r(e, t),
                            s = o.length;
                        while (s--) i = P.call(e, o[s]), e[i] = !(n[i] = o[s])
                    }) : function (e) {
                        return r(e, 0, n)
                    }) : r
                }
            },
            pseudos: {
                not: at(function (e) {
                    var t = [],
                        n = [],
                        r = a(e.replace(z, "$1"));
                    return r[v] ? at(function (e, t, n, i) {
                        var o, s = r(e, null, i, []),
                            a = e.length;
                        while (a--)(o = s[a]) && (e[a] = !(t[a] = o))
                    }) : function (e, i, o) {
                        return t[0] = e, r(t, null, o, n), !n.pop()
                    }
                }),
                has: at(function (e) {
                    return function (t) {
                        return ot(e, t).length > 0
                    }
                }),
                contains: at(function (e) {
                    return function (t) {
                        return (t.textContent || t.innerText || o(t)).indexOf(e) > -1
                    }
                }),
                lang: at(function (e) {
                    return G.test(e || "") || ot.error("unsupported lang: " + e), e = e.replace(nt, rt).toLowerCase(),
                    function (t) {
                        var n;
                        do
                            if (n = h ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang")) return n = n.toLowerCase(), n === e || 0 === n.indexOf(e + "-"); while ((t = t.parentNode) && 1 === t.nodeType);
                        return !1
                    }
                }),
                target: function (t) {
                    var n = e.location && e.location.hash;
                    return n && n.slice(1) === t.id
                },
                root: function (e) {
                    return e === f
                },
                focus: function (e) {
                    return e === p.activeElement && (!p.hasFocus || p.hasFocus()) && !! (e.type || e.href || ~e.tabIndex)
                },
                enabled: function (e) {
                    return e.disabled === !1
                },
                disabled: function (e) {
                    return e.disabled === !0
                },
                checked: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && !! e.checked || "option" === t && !! e.selected
                },
                selected: function (e) {
                    return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
                },
                empty: function (e) {
                    for (e = e.firstChild; e; e = e.nextSibling)
                        if (e.nodeName > "@" || 3 === e.nodeType || 4 === e.nodeType) return !1;
                    return !0
                },
                parent: function (e) {
                    return !i.pseudos.empty(e)
                },
                header: function (e) {
                    return et.test(e.nodeName)
                },
                input: function (e) {
                    return Z.test(e.nodeName)
                },
                button: function (e) {
                    var t = e.nodeName.toLowerCase();
                    return "input" === t && "button" === e.type || "button" === t
                },
                text: function (e) {
                    var t;
                    return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || t.toLowerCase() === e.type)
                },
                first: ht(function () {
                    return [0]
                }),
                last: ht(function (e, t) {
                    return [t - 1]
                }),
                eq: ht(function (e, t, n) {
                    return [0 > n ? n + t : n]
                }),
                even: ht(function (e, t) {
                    var n = 0;
                    for (; t > n; n += 2) e.push(n);
                    return e
                }),
                odd: ht(function (e, t) {
                    var n = 1;
                    for (; t > n; n += 2) e.push(n);
                    return e
                }),
                lt: ht(function (e, t, n) {
                    var r = 0 > n ? n + t : n;
                    for (; --r >= 0;) e.push(r);
                    return e
                }),
                gt: ht(function (e, t, n) {
                    var r = 0 > n ? n + t : n;
                    for (; t > ++r;) e.push(r);
                    return e
                })
            }
        }, i.pseudos.nth = i.pseudos.eq;
        for (t in {
            radio: !0,
            checkbox: !0,
            file: !0,
            password: !0,
            image: !0
        }) i.pseudos[t] = pt(t);
        for (t in {
            submit: !0,
            reset: !0
        }) i.pseudos[t] = ft(t);

        function dt() {}
        dt.prototype = i.filters = i.pseudos, i.setFilters = new dt;

        function gt(e, t) {
            var n, r, o, s, a, u, l, c = k[e + " "];
            if (c) return t ? 0 : c.slice(0);
            a = e, u = [], l = i.preFilter;
            while (a) {
                (!n || (r = _.exec(a))) && (r && (a = a.slice(r[0].length) || a), u.push(o = [])), n = !1, (r = X.exec(a)) && (n = r.shift(), o.push({
                    value: n,
                    type: r[0].replace(z, " ")
                }), a = a.slice(n.length));
                for (s in i.filter)!(r = J[s].exec(a)) || l[s] && !(r = l[s](r)) || (n = r.shift(), o.push({
                    value: n,
                    type: s,
                    matches: r
                }), a = a.slice(n.length));
                if (!n) break
            }
            return t ? a.length : a ? ot.error(e) : k(e, u).slice(0)
        }

        function mt(e) {
            var t = 0,
                n = e.length,
                r = "";
            for (; n > t; t++) r += e[t].value;
            return r
        }

        function yt(e, t, n) {
            var i = t.dir,
                o = n && "parentNode" === i,
                s = T++;
            return t.first ? function (t, n, r) {
                while (t = t[i])
                    if (1 === t.nodeType || o) return e(t, n, r)
            } : function (t, n, a) {
                var u, l, c, p = w + " " + s;
                if (a) {
                    while (t = t[i])
                        if ((1 === t.nodeType || o) && e(t, n, a)) return !0
                } else
                    while (t = t[i])
                        if (1 === t.nodeType || o)
                            if (c = t[v] || (t[v] = {}), (l = c[i]) && l[0] === p) {
                                if ((u = l[1]) === !0 || u === r) return u === !0
                            } else if (l = c[i] = [p], l[1] = e(t, n, a) || r, l[1] === !0) return !0
            }
        }

        function vt(e) {
            return e.length > 1 ? function (t, n, r) {
                var i = e.length;
                while (i--)
                    if (!e[i](t, n, r)) return !1;
                return !0
            } : e[0]
        }

        function xt(e, t, n, r, i) {
            var o, s = [],
                a = 0,
                u = e.length,
                l = null != t;
            for (; u > a; a++)(o = e[a]) && (!n || n(o, r, i)) && (s.push(o), l && t.push(a));
            return s
        }

        function bt(e, t, n, r, i, o) {
            return r && !r[v] && (r = bt(r)), i && !i[v] && (i = bt(i, o)), at(function (o, s, a, u) {
                var l, c, p, f = [],
                    h = [],
                    d = s.length,
                    g = o || Ct(t || "*", a.nodeType ? [a] : a, []),
                    m = !e || !o && t ? g : xt(g, f, e, a, u),
                    y = n ? i || (o ? e : d || r) ? [] : s : m;
                if (n && n(m, y, a, u), r) {
                    l = xt(y, h), r(l, [], a, u), c = l.length;
                    while (c--)(p = l[c]) && (y[h[c]] = !(m[h[c]] = p))
                }
                if (o) {
                    if (i || e) {
                        if (i) {
                            l = [], c = y.length;
                            while (c--)(p = y[c]) && l.push(m[c] = p);
                            i(null, y = [], l, u)
                        }
                        c = y.length;
                        while (c--)(p = y[c]) && (l = i ? P.call(o, p) : f[c]) > -1 && (o[l] = !(s[l] = p))
                    }
                } else y = xt(y === s ? y.splice(d, y.length) : y), i ? i(null, s, y, u) : O.apply(s, y)
            })
        }

        function wt(e) {
            var t, n, r, o = e.length,
                s = i.relative[e[0].type],
                a = s || i.relative[" "],
                l = s ? 1 : 0,
                c = yt(function (e) {
                    return e === t
                }, a, !0),
                p = yt(function (e) {
                    return P.call(t, e) > -1
                }, a, !0),
                f = [
                    function (e, n, r) {
                        return !s && (r || n !== u) || ((t = n).nodeType ? c(e, n, r) : p(e, n, r))
                    }
                ];
            for (; o > l; l++)
                if (n = i.relative[e[l].type]) f = [yt(vt(f), n)];
                else {
                    if (n = i.filter[e[l].type].apply(null, e[l].matches), n[v]) {
                        for (r = ++l; o > r; r++)
                            if (i.relative[e[r].type]) break;
                        return bt(l > 1 && vt(f), l > 1 && mt(e.slice(0, l - 1).concat({
                            value: " " === e[l - 2].type ? "*" : ""
                        })).replace(z, "$1"), n, r > l && wt(e.slice(l, r)), o > r && wt(e = e.slice(r)), o > r && mt(e))
                    }
                    f.push(n)
                }
            return vt(f)
        }

        function Tt(e, t) {
            var n = 0,
                o = t.length > 0,
                s = e.length > 0,
                a = function (a, l, c, f, h) {
                    var d, g, m, y = [],
                        v = 0,
                        x = "0",
                        b = a && [],
                        T = null != h,
                        C = u,
                        k = a || s && i.find.TAG("*", h && l.parentNode || l),
                        N = w += null == C ? 1 : Math.random() || .1;
                    for (T && (u = l !== p && l, r = n); null != (d = k[x]); x++) {
                        if (s && d) {
                            g = 0;
                            while (m = e[g++])
                                if (m(d, l, c)) {
                                    f.push(d);
                                    break
                                }
                            T && (w = N, r = ++n)
                        }
                        o && ((d = !m && d) && v--, a && b.push(d))
                    }
                    if (v += x, o && x !== v) {
                        g = 0;
                        while (m = t[g++]) m(b, y, l, c);
                        if (a) {
                            if (v > 0)
                                while (x--) b[x] || y[x] || (y[x] = q.call(f));
                            y = xt(y)
                        }
                        O.apply(f, y), T && !a && y.length > 0 && v + t.length > 1 && ot.uniqueSort(f)
                    }
                    return T && (w = N, u = C), b
                };
            return o ? at(a) : a
        }
        a = ot.compile = function (e, t) {
            var n, r = [],
                i = [],
                o = N[e + " "];
            if (!o) {
                t || (t = gt(e)), n = t.length;
                while (n--) o = wt(t[n]), o[v] ? r.push(o) : i.push(o);
                o = N(e, Tt(i, r))
            }
            return o
        };

        function Ct(e, t, n) {
            var r = 0,
                i = t.length;
            for (; i > r; r++) ot(e, t[r], n);
            return n
        }

        function kt(e, t, r, o) {
            var s, u, l, c, p, f = gt(e);
            if (!o && 1 === f.length) {
                if (u = f[0] = f[0].slice(0), u.length > 2 && "ID" === (l = u[0]).type && n.getById && 9 === t.nodeType && h && i.relative[u[1].type]) {
                    if (t = (i.find.ID(l.matches[0].replace(nt, rt), t) || [])[0], !t) return r;
                    e = e.slice(u.shift().value.length)
                }
                s = J.needsContext.test(e) ? 0 : u.length;
                while (s--) {
                    if (l = u[s], i.relative[c = l.type]) break;
                    if ((p = i.find[c]) && (o = p(l.matches[0].replace(nt, rt), U.test(u[0].type) && t.parentNode || t))) {
                        if (u.splice(s, 1), e = o.length && mt(u), !e) return O.apply(r, o), r;
                        break
                    }
                }
            }
            return a(e, f)(o, t, !h, r, U.test(e)), r
        }
        n.sortStable = v.split("").sort(S).join("") === v, n.detectDuplicates = E, c(), n.sortDetached = ut(function (e) {
            return 1 & e.compareDocumentPosition(p.createElement("div"))
        }), ut(function (e) {
            return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
        }) || lt("type|href|height|width", function (e, t, n) {
            return n ? undefined : e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
        }), n.attributes && ut(function (e) {
            return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
        }) || lt("value", function (e, t, n) {
            return n || "input" !== e.nodeName.toLowerCase() ? undefined : e.defaultValue
        }), ut(function (e) {
            return null == e.getAttribute("disabled")
        }) || lt(R, function (e, t, n) {
            var r;
            return n ? undefined : (r = e.getAttributeNode(t)) && r.specified ? r.value : e[t] === !0 ? t.toLowerCase() : null
        }), x.find = ot, x.expr = ot.selectors, x.expr[":"] = x.expr.pseudos, x.unique = ot.uniqueSort, x.text = ot.getText, x.isXMLDoc = ot.isXML, x.contains = ot.contains
    }(e);
    var D = {};

    function A(e) {
        var t = D[e] = {};
        return x.each(e.match(w) || [], function (e, n) {
            t[n] = !0
        }), t
    }
    x.Callbacks = function (e) {
        e = "string" == typeof e ? D[e] || A(e) : x.extend({}, e);
        var t, n, r, i, o, s, a = [],
            u = !e.once && [],
            l = function (p) {
                for (t = e.memory && p, n = !0, s = i || 0, i = 0, o = a.length, r = !0; a && o > s; s++)
                    if (a[s].apply(p[0], p[1]) === !1 && e.stopOnFalse) {
                        t = !1;
                        break
                    }
                r = !1, a && (u ? u.length && l(u.shift()) : t ? a = [] : c.disable())
            }, c = {
                add: function () {
                    if (a) {
                        var n = a.length;
                        (function s(t) {
                            x.each(t, function (t, n) {
                                var r = x.type(n);
                                "function" === r ? e.unique && c.has(n) || a.push(n) : n && n.length && "string" !== r && s(n)
                            })
                        })(arguments), r ? o = a.length : t && (i = n, l(t))
                    }
                    return this
                },
                remove: function () {
                    return a && x.each(arguments, function (e, t) {
                        var n;
                        while ((n = x.inArray(t, a, n)) > -1) a.splice(n, 1), r && (o >= n && o--, s >= n && s--)
                    }), this
                },
                has: function (e) {
                    return e ? x.inArray(e, a) > -1 : !(!a || !a.length)
                },
                empty: function () {
                    return a = [], o = 0, this
                },
                disable: function () {
                    return a = u = t = undefined, this
                },
                disabled: function () {
                    return !a
                },
                lock: function () {
                    return u = undefined, t || c.disable(), this
                },
                locked: function () {
                    return !u
                },
                fireWith: function (e, t) {
                    return !a || n && !u || (t = t || [], t = [e, t.slice ? t.slice() : t], r ? u.push(t) : l(t)), this
                },
                fire: function () {
                    return c.fireWith(this, arguments), this
                },
                fired: function () {
                    return !!n
                }
            };
        return c
    }, x.extend({
        Deferred: function (e) {
            var t = [
                ["resolve", "done", x.Callbacks("once memory"), "resolved"],
                ["reject", "fail", x.Callbacks("once memory"), "rejected"],
                ["notify", "progress", x.Callbacks("memory")]
            ],
                n = "pending",
                r = {
                    state: function () {
                        return n
                    },
                    always: function () {
                        return i.done(arguments).fail(arguments), this
                    },
                    then: function () {
                        var e = arguments;
                        return x.Deferred(function (n) {
                            x.each(t, function (t, o) {
                                var s = o[0],
                                    a = x.isFunction(e[t]) && e[t];
                                i[o[1]](function () {
                                    var e = a && a.apply(this, arguments);
                                    e && x.isFunction(e.promise) ? e.promise().done(n.resolve).fail(n.reject).progress(n.notify) : n[s + "With"](this === r ? n.promise() : this, a ? [e] : arguments)
                                })
                            }), e = null
                        }).promise()
                    },
                    promise: function (e) {
                        return null != e ? x.extend(e, r) : r
                    }
                }, i = {};
            return r.pipe = r.then, x.each(t, function (e, o) {
                var s = o[2],
                    a = o[3];
                r[o[1]] = s.add, a && s.add(function () {
                    n = a
                }, t[1 ^ e][2].disable, t[2][2].lock), i[o[0]] = function () {
                    return i[o[0] + "With"](this === i ? r : this, arguments), this
                }, i[o[0] + "With"] = s.fireWith
            }), r.promise(i), e && e.call(i, i), i
        },
        when: function (e) {
            var t = 0,
                n = d.call(arguments),
                r = n.length,
                i = 1 !== r || e && x.isFunction(e.promise) ? r : 0,
                o = 1 === i ? e : x.Deferred(),
                s = function (e, t, n) {
                    return function (r) {
                        t[e] = this, n[e] = arguments.length > 1 ? d.call(arguments) : r, n === a ? o.notifyWith(t, n) : --i || o.resolveWith(t, n)
                    }
                }, a, u, l;
            if (r > 1)
                for (a = Array(r), u = Array(r), l = Array(r); r > t; t++) n[t] && x.isFunction(n[t].promise) ? n[t].promise().done(s(t, l, n)).fail(o.reject).progress(s(t, u, a)) : --i;
            return i || o.resolveWith(l, n), o.promise()
        }
    }), x.support = function (t) {
        var n = o.createElement("input"),
            r = o.createDocumentFragment(),
            i = o.createElement("div"),
            s = o.createElement("select"),
            a = s.appendChild(o.createElement("option"));
        return n.type ? (n.type = "checkbox", t.checkOn = "" !== n.value, t.optSelected = a.selected, t.reliableMarginRight = !0, t.boxSizingReliable = !0, t.pixelPosition = !1, n.checked = !0, t.noCloneChecked = n.cloneNode(!0).checked, s.disabled = !0, t.optDisabled = !a.disabled, n = o.createElement("input"), n.value = "t", n.type = "radio", t.radioValue = "t" === n.value, n.setAttribute("checked", "t"), n.setAttribute("name", "t"), r.appendChild(n), t.checkClone = r.cloneNode(!0).cloneNode(!0).lastChild.checked, t.focusinBubbles = "onfocusin" in e, i.style.backgroundClip = "content-box", i.cloneNode(!0).style.backgroundClip = "", t.clearCloneStyle = "content-box" === i.style.backgroundClip, x(function () {
            var n, r, s = "padding:0;margin:0;border:0;display:block;-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box",
                a = o.getElementsByTagName("body")[0];
            a && (n = o.createElement("div"), n.style.cssText = "border:0;width:0;height:0;position:absolute;top:0;left:-9999px;margin-top:1px", a.appendChild(n).appendChild(i), i.innerHTML = "", i.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;padding:1px;border:1px;display:block;width:4px;margin-top:1%;position:absolute;top:1%", x.swap(a, null != a.style.zoom ? {
                zoom: 1
            } : {}, function () {
                t.boxSizing = 4 === i.offsetWidth
            }), e.getComputedStyle && (t.pixelPosition = "1%" !== (e.getComputedStyle(i, null) || {}).top, t.boxSizingReliable = "4px" === (e.getComputedStyle(i, null) || {
                width: "4px"
            }).width, r = i.appendChild(o.createElement("div")), r.style.cssText = i.style.cssText = s, r.style.marginRight = r.style.width = "0", i.style.width = "1px", t.reliableMarginRight = !parseFloat((e.getComputedStyle(r, null) || {}).marginRight)), a.removeChild(n))
        }), t) : t
    }({});
    var L, q, H = /(?:\{[\s\S]*\}|\[[\s\S]*\])$/,
        O = /([A-Z])/g;

    function F() {
        Object.defineProperty(this.cache = {}, 0, {
            get: function () {
                return {}
            }
        }), this.expando = x.expando + Math.random()
    }
    F.uid = 1, F.accepts = function (e) {
        return e.nodeType ? 1 === e.nodeType || 9 === e.nodeType : !0
    }, F.prototype = {
        key: function (e) {
            if (!F.accepts(e)) return 0;
            var t = {}, n = e[this.expando];
            if (!n) {
                n = F.uid++;
                try {
                    t[this.expando] = {
                        value: n
                    }, Object.defineProperties(e, t)
                } catch (r) {
                    t[this.expando] = n, x.extend(e, t)
                }
            }
            return this.cache[n] || (this.cache[n] = {}), n
        },
        set: function (e, t, n) {
            var r, i = this.key(e),
                o = this.cache[i];
            if ("string" == typeof t) o[t] = n;
            else if (x.isEmptyObject(o)) x.extend(this.cache[i], t);
            else
                for (r in t) o[r] = t[r];
            return o
        },
        get: function (e, t) {
            var n = this.cache[this.key(e)];
            return t === undefined ? n : n[t]
        },
        access: function (e, t, n) {
            var r;
            return t === undefined || t && "string" == typeof t && n === undefined ? (r = this.get(e, t), r !== undefined ? r : this.get(e, x.camelCase(t))) : (this.set(e, t, n), n !== undefined ? n : t)
        },
        remove: function (e, t) {
            var n, r, i, o = this.key(e),
                s = this.cache[o];
            if (t === undefined) this.cache[o] = {};
            else {
                x.isArray(t) ? r = t.concat(t.map(x.camelCase)) : (i = x.camelCase(t), t in s ? r = [t, i] : (r = i, r = r in s ? [r] : r.match(w) || [])), n = r.length;
                while (n--) delete s[r[n]]
            }
        },
        hasData: function (e) {
            return !x.isEmptyObject(this.cache[e[this.expando]] || {})
        },
        discard: function (e) {
            e[this.expando] && delete this.cache[e[this.expando]]
        }
    }, L = new F, q = new F, x.extend({
        acceptData: F.accepts,
        hasData: function (e) {
            return L.hasData(e) || q.hasData(e)
        },
        data: function (e, t, n) {
            return L.access(e, t, n)
        },
        removeData: function (e, t) {
            L.remove(e, t)
        },
        _data: function (e, t, n) {
            return q.access(e, t, n)
        },
        _removeData: function (e, t) {
            q.remove(e, t)
        }
    }), x.fn.extend({
        data: function (e, t) {
            var n, r, i = this[0],
                o = 0,
                s = null;
            if (e === undefined) {
                if (this.length && (s = L.get(i), 1 === i.nodeType && !q.get(i, "hasDataAttrs"))) {
                    for (n = i.attributes; n.length > o; o++) r = n[o].name, 0 === r.indexOf("data-") && (r = x.camelCase(r.slice(5)), P(i, r, s[r]));
                    q.set(i, "hasDataAttrs", !0)
                }
                return s
            }
            return "object" == typeof e ? this.each(function () {
                L.set(this, e)
            }) : x.access(this, function (t) {
                var n, r = x.camelCase(e);
                if (i && t === undefined) {
                    if (n = L.get(i, e), n !== undefined) return n;
                    if (n = L.get(i, r), n !== undefined) return n;
                    if (n = P(i, r, undefined), n !== undefined) return n
                } else this.each(function () {
                    var n = L.get(this, r);
                    L.set(this, r, t), -1 !== e.indexOf("-") && n !== undefined && L.set(this, e, t)
                })
            }, null, t, arguments.length > 1, null, !0)
        },
        removeData: function (e) {
            return this.each(function () {
                L.remove(this, e)
            })
        }
    });

    function P(e, t, n) {
        var r;
        if (n === undefined && 1 === e.nodeType)
            if (r = "data-" + t.replace(O, "-$1").toLowerCase(), n = e.getAttribute(r), "string" == typeof n) {
                try {
                    n = "true" === n ? !0 : "false" === n ? !1 : "null" === n ? null : +n + "" === n ? +n : H.test(n) ? JSON.parse(n) : n
                } catch (i) {}
                L.set(e, t, n)
            } else n = undefined;
        return n
    }
    x.extend({
        queue: function (e, t, n) {
            var r;
            return e ? (t = (t || "fx") + "queue", r = q.get(e, t), n && (!r || x.isArray(n) ? r = q.access(e, t, x.makeArray(n)) : r.push(n)), r || []) : undefined
        },
        dequeue: function (e, t) {
            t = t || "fx";
            var n = x.queue(e, t),
                r = n.length,
                i = n.shift(),
                o = x._queueHooks(e, t),
                s = function () {
                    x.dequeue(e, t)
                };
            "inprogress" === i && (i = n.shift(), r--), i && ("fx" === t && n.unshift("inprogress"), delete o.stop, i.call(e, s, o)), !r && o && o.empty.fire()
        },
        _queueHooks: function (e, t) {
            var n = t + "queueHooks";
            return q.get(e, n) || q.access(e, n, {
                empty: x.Callbacks("once memory").add(function () {
                    q.remove(e, [t + "queue", n])
                })
            })
        }
    }), x.fn.extend({
        queue: function (e, t) {
            var n = 2;
            return "string" != typeof e && (t = e, e = "fx", n--), n > arguments.length ? x.queue(this[0], e) : t === undefined ? this : this.each(function () {
                var n = x.queue(this, e, t);
                x._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && x.dequeue(this, e)
            })
        },
        dequeue: function (e) {
            return this.each(function () {
                x.dequeue(this, e)
            })
        },
        delay: function (e, t) {
            return e = x.fx ? x.fx.speeds[e] || e : e, t = t || "fx", this.queue(t, function (t, n) {
                var r = setTimeout(t, e);
                n.stop = function () {
                    clearTimeout(r)
                }
            })
        },
        clearQueue: function (e) {
            return this.queue(e || "fx", [])
        },
        promise: function (e, t) {
            var n, r = 1,
                i = x.Deferred(),
                o = this,
                s = this.length,
                a = function () {
                    --r || i.resolveWith(o, [o])
                };
            "string" != typeof e && (t = e, e = undefined), e = e || "fx";
            while (s--) n = q.get(o[s], e + "queueHooks"), n && n.empty && (r++, n.empty.add(a));
            return a(), i.promise(t)
        }
    });
    var R, M, W = /[\t\r\n\f]/g,
        $ = /\r/g,
        B = /^(?:input|select|textarea|button)$/i;
    x.fn.extend({
        attr: function (e, t) {
            return x.access(this, x.attr, e, t, arguments.length > 1)
        },
        removeAttr: function (e) {
            return this.each(function () {
                x.removeAttr(this, e)
            })
        },
        prop: function (e, t) {
            return x.access(this, x.prop, e, t, arguments.length > 1)
        },
        removeProp: function (e) {
            return this.each(function () {
                delete this[x.propFix[e] || e]
            })
        },
        addClass: function (e) {
            var t, n, r, i, o, s = 0,
                a = this.length,
                u = "string" == typeof e && e;
            if (x.isFunction(e)) return this.each(function (t) {
                x(this).addClass(e.call(this, t, this.className))
            });
            if (u)
                for (t = (e || "").match(w) || []; a > s; s++)
                    if (n = this[s], r = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(W, " ") : " ")) {
                        o = 0;
                        while (i = t[o++]) 0 > r.indexOf(" " + i + " ") && (r += i + " ");
                        n.className = x.trim(r)
                    }
            return this
        },
        removeClass: function (e) {
            var t, n, r, i, o, s = 0,
                a = this.length,
                u = 0 === arguments.length || "string" == typeof e && e;
            if (x.isFunction(e)) return this.each(function (t) {
                x(this).removeClass(e.call(this, t, this.className))
            });
            if (u)
                for (t = (e || "").match(w) || []; a > s; s++)
                    if (n = this[s], r = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(W, " ") : "")) {
                        o = 0;
                        while (i = t[o++])
                            while (r.indexOf(" " + i + " ") >= 0) r = r.replace(" " + i + " ", " ");
                        n.className = e ? x.trim(r) : ""
                    }
            return this
        },
        toggleClass: function (e, t) {
            var n = typeof e;
            return "boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : x.isFunction(e) ? this.each(function (n) {
                x(this).toggleClass(e.call(this, n, this.className, t), t)
            }) : this.each(function () {
                if ("string" === n) {
                    var t, i = 0,
                        o = x(this),
                        s = e.match(w) || [];
                    while (t = s[i++]) o.hasClass(t) ? o.removeClass(t) : o.addClass(t)
                } else(n === r || "boolean" === n) && (this.className && q.set(this, "__className__", this.className), this.className = this.className || e === !1 ? "" : q.get(this, "__className__") || "")
            })
        },
        hasClass: function (e) {
            var t = " " + e + " ",
                n = 0,
                r = this.length;
            for (; r > n; n++)
                if (1 === this[n].nodeType && (" " + this[n].className + " ").replace(W, " ").indexOf(t) >= 0) return !0;
            return !1
        },
        val: function (e) {
            var t, n, r, i = this[0]; {
                if (arguments.length) return r = x.isFunction(e), this.each(function (n) {
                    var i;
                    1 === this.nodeType && (i = r ? e.call(this, n, x(this).val()) : e, null == i ? i = "" : "number" == typeof i ? i += "" : x.isArray(i) && (i = x.map(i, function (e) {
                        return null == e ? "" : e + ""
                    })), t = x.valHooks[this.type] || x.valHooks[this.nodeName.toLowerCase()], t && "set" in t && t.set(this, i, "value") !== undefined || (this.value = i))
                });
                if (i) return t = x.valHooks[i.type] || x.valHooks[i.nodeName.toLowerCase()], t && "get" in t && (n = t.get(i, "value")) !== undefined ? n : (n = i.value, "string" == typeof n ? n.replace($, "") : null == n ? "" : n)
            }
        }
    }), x.extend({
        valHooks: {
            option: {
                get: function (e) {
                    var t = e.attributes.value;
                    return !t || t.specified ? e.value : e.text
                }
            },
            select: {
                get: function (e) {
                    var t, n, r = e.options,
                        i = e.selectedIndex,
                        o = "select-one" === e.type || 0 > i,
                        s = o ? null : [],
                        a = o ? i + 1 : r.length,
                        u = 0 > i ? a : o ? i : 0;
                    for (; a > u; u++)
                        if (n = r[u], !(!n.selected && u !== i || (x.support.optDisabled ? n.disabled : null !== n.getAttribute("disabled")) || n.parentNode.disabled && x.nodeName(n.parentNode, "optgroup"))) {
                            if (t = x(n).val(), o) return t;
                            s.push(t)
                        }
                    return s
                },
                set: function (e, t) {
                    var n, r, i = e.options,
                        o = x.makeArray(t),
                        s = i.length;
                    while (s--) r = i[s], (r.selected = x.inArray(x(r).val(), o) >= 0) && (n = !0);
                    return n || (e.selectedIndex = -1), o
                }
            }
        },
        attr: function (e, t, n) {
            var i, o, s = e.nodeType;
            if (e && 3 !== s && 8 !== s && 2 !== s) return typeof e.getAttribute === r ? x.prop(e, t, n) : (1 === s && x.isXMLDoc(e) || (t = t.toLowerCase(), i = x.attrHooks[t] || (x.expr.match.bool.test(t) ? M : R)), n === undefined ? i && "get" in i && null !== (o = i.get(e, t)) ? o : (o = x.find.attr(e, t), null == o ? undefined : o) : null !== n ? i && "set" in i && (o = i.set(e, n, t)) !== undefined ? o : (e.setAttribute(t, n + ""), n) : (x.removeAttr(e, t), undefined))
        },
        removeAttr: function (e, t) {
            var n, r, i = 0,
                o = t && t.match(w);
            if (o && 1 === e.nodeType)
                while (n = o[i++]) r = x.propFix[n] || n, x.expr.match.bool.test(n) && (e[r] = !1), e.removeAttribute(n)
        },
        attrHooks: {
            type: {
                set: function (e, t) {
                    if (!x.support.radioValue && "radio" === t && x.nodeName(e, "input")) {
                        var n = e.value;
                        return e.setAttribute("type", t), n && (e.value = n), t
                    }
                }
            }
        },
        propFix: {
            "for": "htmlFor",
            "class": "className"
        },
        prop: function (e, t, n) {
            var r, i, o, s = e.nodeType;
            if (e && 3 !== s && 8 !== s && 2 !== s) return o = 1 !== s || !x.isXMLDoc(e), o && (t = x.propFix[t] || t, i = x.propHooks[t]), n !== undefined ? i && "set" in i && (r = i.set(e, n, t)) !== undefined ? r : e[t] = n : i && "get" in i && null !== (r = i.get(e, t)) ? r : e[t]
        },
        propHooks: {
            tabIndex: {
                get: function (e) {
                    return e.hasAttribute("tabindex") || B.test(e.nodeName) || e.href ? e.tabIndex : -1
                }
            }
        }
    }), M = {
        set: function (e, t, n) {
            return t === !1 ? x.removeAttr(e, n) : e.setAttribute(n, n), n
        }
    }, x.each(x.expr.match.bool.source.match(/\w+/g), function (e, t) {
        var n = x.expr.attrHandle[t] || x.find.attr;
        x.expr.attrHandle[t] = function (e, t, r) {
            var i = x.expr.attrHandle[t],
                o = r ? undefined : (x.expr.attrHandle[t] = undefined) != n(e, t, r) ? t.toLowerCase() : null;
            return x.expr.attrHandle[t] = i, o
        }
    }), x.support.optSelected || (x.propHooks.selected = {
        get: function (e) {
            var t = e.parentNode;
            return t && t.parentNode && t.parentNode.selectedIndex, null
        }
    }), x.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
        x.propFix[this.toLowerCase()] = this
    }), x.each(["radio", "checkbox"], function () {
        x.valHooks[this] = {
            set: function (e, t) {
                return x.isArray(t) ? e.checked = x.inArray(x(e).val(), t) >= 0 : undefined
            }
        }, x.support.checkOn || (x.valHooks[this].get = function (e) {
            return null === e.getAttribute("value") ? "on" : e.value
        })
    });
    var I = /^key/,
        z = /^(?:mouse|contextmenu)|click/,
        _ = /^(?:focusinfocus|focusoutblur)$/,
        X = /^([^.]*)(?:\.(.+)|)$/;

    function U() {
        return !0
    }

    function Y() {
        return !1
    }

    function V() {
        try {
            return o.activeElement
        } catch (e) {}
    }
    x.event = {
        global: {},
        add: function (e, t, n, i, o) {
            var s, a, u, l, c, p, f, h, d, g, m, y = q.get(e);
            if (y) {
                n.handler && (s = n, n = s.handler, o = s.selector), n.guid || (n.guid = x.guid++), (l = y.events) || (l = y.events = {}), (a = y.handle) || (a = y.handle = function (e) {
                    return typeof x === r || e && x.event.triggered === e.type ? undefined : x.event.dispatch.apply(a.elem, arguments)
                }, a.elem = e), t = (t || "").match(w) || [""], c = t.length;
                while (c--) u = X.exec(t[c]) || [], d = m = u[1], g = (u[2] || "").split(".").sort(), d && (f = x.event.special[d] || {}, d = (o ? f.delegateType : f.bindType) || d, f = x.event.special[d] || {}, p = x.extend({
                    type: d,
                    origType: m,
                    data: i,
                    handler: n,
                    guid: n.guid,
                    selector: o,
                    needsContext: o && x.expr.match.needsContext.test(o),
                    namespace: g.join(".")
                }, s), (h = l[d]) || (h = l[d] = [], h.delegateCount = 0, f.setup && f.setup.call(e, i, g, a) !== !1 || e.addEventListener && e.addEventListener(d, a, !1)), f.add && (f.add.call(e, p), p.handler.guid || (p.handler.guid = n.guid)), o ? h.splice(h.delegateCount++, 0, p) : h.push(p), x.event.global[d] = !0);
                e = null
            }
        },
        remove: function (e, t, n, r, i) {
            var o, s, a, u, l, c, p, f, h, d, g, m = q.hasData(e) && q.get(e);
            if (m && (u = m.events)) {
                t = (t || "").match(w) || [""], l = t.length;
                while (l--)
                    if (a = X.exec(t[l]) || [], h = g = a[1], d = (a[2] || "").split(".").sort(), h) {
                        p = x.event.special[h] || {}, h = (r ? p.delegateType : p.bindType) || h, f = u[h] || [], a = a[2] && RegExp("(^|\\.)" + d.join("\\.(?:.*\\.|)") + "(\\.|$)"), s = o = f.length;
                        while (o--) c = f[o], !i && g !== c.origType || n && n.guid !== c.guid || a && !a.test(c.namespace) || r && r !== c.selector && ("**" !== r || !c.selector) || (f.splice(o, 1), c.selector && f.delegateCount--, p.remove && p.remove.call(e, c));
                        s && !f.length && (p.teardown && p.teardown.call(e, d, m.handle) !== !1 || x.removeEvent(e, h, m.handle), delete u[h])
                    } else
                        for (h in u) x.event.remove(e, h + t[l], n, r, !0);
                x.isEmptyObject(u) && (delete m.handle, q.remove(e, "events"))
            }
        },
        trigger: function (t, n, r, i) {
            var s, a, u, l, c, p, f, h = [r || o],
                d = y.call(t, "type") ? t.type : t,
                g = y.call(t, "namespace") ? t.namespace.split(".") : [];
            if (a = u = r = r || o, 3 !== r.nodeType && 8 !== r.nodeType && !_.test(d + x.event.triggered) && (d.indexOf(".") >= 0 && (g = d.split("."), d = g.shift(), g.sort()), c = 0 > d.indexOf(":") && "on" + d, t = t[x.expando] ? t : new x.Event(d, "object" == typeof t && t), t.isTrigger = i ? 2 : 3, t.namespace = g.join("."), t.namespace_re = t.namespace ? RegExp("(^|\\.)" + g.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = undefined, t.target || (t.target = r), n = null == n ? [t] : x.makeArray(n, [t]), f = x.event.special[d] || {}, i || !f.trigger || f.trigger.apply(r, n) !== !1)) {
                if (!i && !f.noBubble && !x.isWindow(r)) {
                    for (l = f.delegateType || d, _.test(l + d) || (a = a.parentNode); a; a = a.parentNode) h.push(a), u = a;
                    u === (r.ownerDocument || o) && h.push(u.defaultView || u.parentWindow || e)
                }
                s = 0;
                while ((a = h[s++]) && !t.isPropagationStopped()) t.type = s > 1 ? l : f.bindType || d, p = (q.get(a, "events") || {})[t.type] && q.get(a, "handle"), p && p.apply(a, n), p = c && a[c], p && x.acceptData(a) && p.apply && p.apply(a, n) === !1 && t.preventDefault();
                return t.type = d, i || t.isDefaultPrevented() || f._default && f._default.apply(h.pop(), n) !== !1 || !x.acceptData(r) || c && x.isFunction(r[d]) && !x.isWindow(r) && (u = r[c], u && (r[c] = null), x.event.triggered = d, r[d](), x.event.triggered = undefined, u && (r[c] = u)), t.result
            }
        },
        dispatch: function (e) {
            e = x.event.fix(e);
            var t, n, r, i, o, s = [],
                a = d.call(arguments),
                u = (q.get(this, "events") || {})[e.type] || [],
                l = x.event.special[e.type] || {};
            if (a[0] = e, e.delegateTarget = this, !l.preDispatch || l.preDispatch.call(this, e) !== !1) {
                s = x.event.handlers.call(this, e, u), t = 0;
                while ((i = s[t++]) && !e.isPropagationStopped()) {
                    e.currentTarget = i.elem, n = 0;
                    while ((o = i.handlers[n++]) && !e.isImmediatePropagationStopped())(!e.namespace_re || e.namespace_re.test(o.namespace)) && (e.handleObj = o, e.data = o.data, r = ((x.event.special[o.origType] || {}).handle || o.handler).apply(i.elem, a), r !== undefined && (e.result = r) === !1 && (e.preventDefault(), e.stopPropagation()))
                }
                return l.postDispatch && l.postDispatch.call(this, e), e.result
            }
        },
        handlers: function (e, t) {
            var n, r, i, o, s = [],
                a = t.delegateCount,
                u = e.target;
            if (a && u.nodeType && (!e.button || "click" !== e.type))
                for (; u !== this; u = u.parentNode || this)
                    if (u.disabled !== !0 || "click" !== e.type) {
                        for (r = [], n = 0; a > n; n++) o = t[n], i = o.selector + " ", r[i] === undefined && (r[i] = o.needsContext ? x(i, this).index(u) >= 0 : x.find(i, this, null, [u]).length), r[i] && r.push(o);
                        r.length && s.push({
                            elem: u,
                            handlers: r
                        })
                    }
            return t.length > a && s.push({
                elem: this,
                handlers: t.slice(a)
            }), s
        },
        props: "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
        fixHooks: {},
        keyHooks: {
            props: "char charCode key keyCode".split(" "),
            filter: function (e, t) {
                return null == e.which && (e.which = null != t.charCode ? t.charCode : t.keyCode), e
            }
        },
        mouseHooks: {
            props: "button buttons clientX clientY offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
            filter: function (e, t) {
                var n, r, i, s = t.button;
                return null == e.pageX && null != t.clientX && (n = e.target.ownerDocument || o, r = n.documentElement, i = n.body, e.pageX = t.clientX + (r && r.scrollLeft || i && i.scrollLeft || 0) - (r && r.clientLeft || i && i.clientLeft || 0), e.pageY = t.clientY + (r && r.scrollTop || i && i.scrollTop || 0) - (r && r.clientTop || i && i.clientTop || 0)), e.which || s === undefined || (e.which = 1 & s ? 1 : 2 & s ? 3 : 4 & s ? 2 : 0), e
            }
        },
        fix: function (e) {
            if (e[x.expando]) return e;
            var t, n, r, i = e.type,
                s = e,
                a = this.fixHooks[i];
            a || (this.fixHooks[i] = a = z.test(i) ? this.mouseHooks : I.test(i) ? this.keyHooks : {}), r = a.props ? this.props.concat(a.props) : this.props, e = new x.Event(s), t = r.length;
            while (t--) n = r[t], e[n] = s[n];
            return e.target || (e.target = o), 3 === e.target.nodeType && (e.target = e.target.parentNode), a.filter ? a.filter(e, s) : e
        },
        special: {
            load: {
                noBubble: !0
            },
            focus: {
                trigger: function () {
                    return this !== V() && this.focus ? (this.focus(), !1) : undefined
                },
                delegateType: "focusin"
            },
            blur: {
                trigger: function () {
                    return this === V() && this.blur ? (this.blur(), !1) : undefined
                },
                delegateType: "focusout"
            },
            click: {
                trigger: function () {
                    return "checkbox" === this.type && this.click && x.nodeName(this, "input") ? (this.click(), !1) : undefined
                },
                _default: function (e) {
                    return x.nodeName(e.target, "a")
                }
            },
            beforeunload: {
                postDispatch: function (e) {
                    e.result !== undefined && (e.originalEvent.returnValue = e.result)
                }
            }
        },
        simulate: function (e, t, n, r) {
            var i = x.extend(new x.Event, n, {
                type: e,
                isSimulated: !0,
                originalEvent: {}
            });
            r ? x.event.trigger(i, null, t) : x.event.dispatch.call(t, i), i.isDefaultPrevented() && n.preventDefault()
        }
    }, x.removeEvent = function (e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n, !1)
    }, x.Event = function (e, t) {
        return this instanceof x.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || e.getPreventDefault && e.getPreventDefault() ? U : Y) : this.type = e, t && x.extend(this, t), this.timeStamp = e && e.timeStamp || x.now(), this[x.expando] = !0, undefined) : new x.Event(e, t)
    }, x.Event.prototype = {
        isDefaultPrevented: Y,
        isPropagationStopped: Y,
        isImmediatePropagationStopped: Y,
        preventDefault: function () {
            var e = this.originalEvent;
            this.isDefaultPrevented = U, e && e.preventDefault && e.preventDefault()
        },
        stopPropagation: function () {
            var e = this.originalEvent;
            this.isPropagationStopped = U, e && e.stopPropagation && e.stopPropagation()
        },
        stopImmediatePropagation: function () {
            this.isImmediatePropagationStopped = U, this.stopPropagation()
        }
    }, x.each({
        mouseenter: "mouseover",
        mouseleave: "mouseout"
    }, function (e, t) {
        x.event.special[e] = {
            delegateType: t,
            bindType: t,
            handle: function (e) {
                var n, r = this,
                    i = e.relatedTarget,
                    o = e.handleObj;
                return (!i || i !== r && !x.contains(r, i)) && (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t), n
            }
        }
    }), x.support.focusinBubbles || x.each({
        focus: "focusin",
        blur: "focusout"
    }, function (e, t) {
        var n = 0,
            r = function (e) {
                x.event.simulate(t, e.target, x.event.fix(e), !0)
            };
        x.event.special[t] = {
            setup: function () {
                0 === n++ && o.addEventListener(e, r, !0)
            },
            teardown: function () {
                0 === --n && o.removeEventListener(e, r, !0)
            }
        }
    }), x.fn.extend({
        on: function (e, t, n, r, i) {
            var o, s;
            if ("object" == typeof e) {
                "string" != typeof t && (n = n || t, t = undefined);
                for (s in e) this.on(s, t, n, e[s], i);
                return this
            }
            if (null == n && null == r ? (r = t, n = t = undefined) : null == r && ("string" == typeof t ? (r = n, n = undefined) : (r = n, n = t, t = undefined)), r === !1) r = Y;
            else if (!r) return this;
            return 1 === i && (o = r, r = function (e) {
                return x().off(e), o.apply(this, arguments)
            }, r.guid = o.guid || (o.guid = x.guid++)), this.each(function () {
                x.event.add(this, e, r, n, t)
            })
        },
        one: function (e, t, n, r) {
            return this.on(e, t, n, r, 1)
        },
        off: function (e, t, n) {
            var r, i;
            if (e && e.preventDefault && e.handleObj) return r = e.handleObj, x(e.delegateTarget).off(r.namespace ? r.origType + "." + r.namespace : r.origType, r.selector, r.handler), this;
            if ("object" == typeof e) {
                for (i in e) this.off(i, t, e[i]);
                return this
            }
            return (t === !1 || "function" == typeof t) && (n = t, t = undefined), n === !1 && (n = Y), this.each(function () {
                x.event.remove(this, e, n, t)
            })
        },
        trigger: function (e, t) {
            return this.each(function () {
                x.event.trigger(e, t, this)
            })
        },
        triggerHandler: function (e, t) {
            var n = this[0];
            return n ? x.event.trigger(e, t, n, !0) : undefined
        }
    });
    var G = /^.[^:#\[\.,]*$/,
        J = /^(?:parents|prev(?:Until|All))/,
        Q = x.expr.match.needsContext,
        K = {
            children: !0,
            contents: !0,
            next: !0,
            prev: !0
        };
    x.fn.extend({
        find: function (e) {
            var t, n = [],
                r = this,
                i = r.length;
            if ("string" != typeof e) return this.pushStack(x(e).filter(function () {
                for (t = 0; i > t; t++)
                    if (x.contains(r[t], this)) return !0
            }));
            for (t = 0; i > t; t++) x.find(e, r[t], n);
            return n = this.pushStack(i > 1 ? x.unique(n) : n), n.selector = this.selector ? this.selector + " " + e : e, n
        },
        has: function (e) {
            var t = x(e, this),
                n = t.length;
            return this.filter(function () {
                var e = 0;
                for (; n > e; e++)
                    if (x.contains(this, t[e])) return !0
            })
        },
        not: function (e) {
            return this.pushStack(et(this, e || [], !0))
        },
        filter: function (e) {
            return this.pushStack(et(this, e || [], !1))
        },
        is: function (e) {
            return !!et(this, "string" == typeof e && Q.test(e) ? x(e) : e || [], !1).length
        },
        closest: function (e, t) {
            var n, r = 0,
                i = this.length,
                o = [],
                s = Q.test(e) || "string" != typeof e ? x(e, t || this.context) : 0;
            for (; i > r; r++)
                for (n = this[r]; n && n !== t; n = n.parentNode)
                    if (11 > n.nodeType && (s ? s.index(n) > -1 : 1 === n.nodeType && x.find.matchesSelector(n, e))) {
                        n = o.push(n);
                        break
                    }
            return this.pushStack(o.length > 1 ? x.unique(o) : o)
        },
        index: function (e) {
            return e ? "string" == typeof e ? g.call(x(e), this[0]) : g.call(this, e.jquery ? e[0] : e) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
        },
        add: function (e, t) {
            var n = "string" == typeof e ? x(e, t) : x.makeArray(e && e.nodeType ? [e] : e),
                r = x.merge(this.get(), n);
            return this.pushStack(x.unique(r))
        },
        addBack: function (e) {
            return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
        }
    });

    function Z(e, t) {
        while ((e = e[t]) && 1 !== e.nodeType);
        return e
    }
    x.each({
        parent: function (e) {
            var t = e.parentNode;
            return t && 11 !== t.nodeType ? t : null
        },
        parents: function (e) {
            return x.dir(e, "parentNode")
        },
        parentsUntil: function (e, t, n) {
            return x.dir(e, "parentNode", n)
        },
        next: function (e) {
            return Z(e, "nextSibling")
        },
        prev: function (e) {
            return Z(e, "previousSibling")
        },
        nextAll: function (e) {
            return x.dir(e, "nextSibling")
        },
        prevAll: function (e) {
            return x.dir(e, "previousSibling")
        },
        nextUntil: function (e, t, n) {
            return x.dir(e, "nextSibling", n)
        },
        prevUntil: function (e, t, n) {
            return x.dir(e, "previousSibling", n)
        },
        siblings: function (e) {
            return x.sibling((e.parentNode || {}).firstChild, e)
        },
        children: function (e) {
            return x.sibling(e.firstChild)
        },
        contents: function (e) {
            return e.contentDocument || x.merge([], e.childNodes)
        }
    }, function (e, t) {
        x.fn[e] = function (n, r) {
            var i = x.map(this, t, n);
            return "Until" !== e.slice(-5) && (r = n), r && "string" == typeof r && (i = x.filter(r, i)), this.length > 1 && (K[e] || x.unique(i), J.test(e) && i.reverse()), this.pushStack(i)
        }
    }), x.extend({
        filter: function (e, t, n) {
            var r = t[0];
            return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === r.nodeType ? x.find.matchesSelector(r, e) ? [r] : [] : x.find.matches(e, x.grep(t, function (e) {
                return 1 === e.nodeType
            }))
        },
        dir: function (e, t, n) {
            var r = [],
                i = n !== undefined;
            while ((e = e[t]) && 9 !== e.nodeType)
                if (1 === e.nodeType) {
                    if (i && x(e).is(n)) break;
                    r.push(e)
                }
            return r
        },
        sibling: function (e, t) {
            var n = [];
            for (; e; e = e.nextSibling) 1 === e.nodeType && e !== t && n.push(e);
            return n
        }
    });

    function et(e, t, n) {
        if (x.isFunction(t)) return x.grep(e, function (e, r) {
            return !!t.call(e, r, e) !== n
        });
        if (t.nodeType) return x.grep(e, function (e) {
            return e === t !== n
        });
        if ("string" == typeof t) {
            if (G.test(t)) return x.filter(t, e, n);
            t = x.filter(t, e)
        }
        return x.grep(e, function (e) {
            return g.call(t, e) >= 0 !== n
        })
    }
    var tt = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi,
        nt = /<([\w:]+)/,
        rt = /<|&#?\w+;/,
        it = /<(?:script|style|link)/i,
        ot = /^(?:checkbox|radio)$/i,
        st = /checked\s*(?:[^=]|=\s*.checked.)/i,
        at = /^$|\/(?:java|ecma)script/i,
        ut = /^true\/(.*)/,
        lt = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g,
        ct = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            thead: [1, "<table>", "</table>"],
            col: [2, "<table><colgroup>", "</colgroup></table>"],
            tr: [2, "<table><tbody>", "</tbody></table>"],
            td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
            _default: [0, "", ""]
        };
    ct.optgroup = ct.option, ct.tbody = ct.tfoot = ct.colgroup = ct.caption = ct.thead, ct.th = ct.td, x.fn.extend({
        text: function (e) {
            return x.access(this, function (e) {
                return e === undefined ? x.text(this) : this.empty().append((this[0] && this[0].ownerDocument || o).createTextNode(e))
            }, null, e, arguments.length)
        },
        append: function () {
            return this.domManip(arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = pt(this, e);
                    t.appendChild(e)
                }
            })
        },
        prepend: function () {
            return this.domManip(arguments, function (e) {
                if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                    var t = pt(this, e);
                    t.insertBefore(e, t.firstChild)
                }
            })
        },
        before: function () {
            return this.domManip(arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this)
            })
        },
        after: function () {
            return this.domManip(arguments, function (e) {
                this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
            })
        },
        remove: function (e, t) {
            var n, r = e ? x.filter(e, this) : this,
                i = 0;
            for (; null != (n = r[i]); i++) t || 1 !== n.nodeType || x.cleanData(mt(n)), n.parentNode && (t && x.contains(n.ownerDocument, n) && dt(mt(n, "script")), n.parentNode.removeChild(n));
            return this
        },
        empty: function () {
            var e, t = 0;
            for (; null != (e = this[t]); t++) 1 === e.nodeType && (x.cleanData(mt(e, !1)), e.textContent = "");
            return this
        },
        clone: function (e, t) {
            return e = null == e ? !1 : e, t = null == t ? e : t, this.map(function () {
                return x.clone(this, e, t)
            })
        },
        html: function (e) {
            return x.access(this, function (e) {
                var t = this[0] || {}, n = 0,
                    r = this.length;
                if (e === undefined && 1 === t.nodeType) return t.innerHTML;
                if ("string" == typeof e && !it.test(e) && !ct[(nt.exec(e) || ["", ""])[1].toLowerCase()]) {
                    e = e.replace(tt, "<$1></$2>");
                    try {
                        for (; r > n; n++) t = this[n] || {}, 1 === t.nodeType && (x.cleanData(mt(t, !1)), t.innerHTML = e);
                        t = 0
                    } catch (i) {}
                }
                t && this.empty().append(e)
            }, null, e, arguments.length)
        },
        replaceWith: function () {
            var e = x.map(this, function (e) {
                return [e.nextSibling, e.parentNode]
            }),
                t = 0;
            return this.domManip(arguments, function (n) {
                var r = e[t++],
                    i = e[t++];
                i && (r && r.parentNode !== i && (r = this.nextSibling), x(this).remove(), i.insertBefore(n, r))
            }, !0), t ? this : this.remove()
        },
        detach: function (e) {
            return this.remove(e, !0)
        },
        domManip: function (e, t, n) {
            e = f.apply([], e);
            var r, i, o, s, a, u, l = 0,
                c = this.length,
                p = this,
                h = c - 1,
                d = e[0],
                g = x.isFunction(d);
            if (g || !(1 >= c || "string" != typeof d || x.support.checkClone) && st.test(d)) return this.each(function (r) {
                var i = p.eq(r);
                g && (e[0] = d.call(this, r, i.html())), i.domManip(e, t, n)
            });
            if (c && (r = x.buildFragment(e, this[0].ownerDocument, !1, !n && this), i = r.firstChild, 1 === r.childNodes.length && (r = i), i)) {
                for (o = x.map(mt(r, "script"), ft), s = o.length; c > l; l++) a = r, l !== h && (a = x.clone(a, !0, !0), s && x.merge(o, mt(a, "script"))), t.call(this[l], a, l);
                if (s)
                    for (u = o[o.length - 1].ownerDocument, x.map(o, ht), l = 0; s > l; l++) a = o[l], at.test(a.type || "") && !q.access(a, "globalEval") && x.contains(u, a) && (a.src ? x._evalUrl(a.src) : x.globalEval(a.textContent.replace(lt, "")))
            }
            return this
        }
    }), x.each({
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function (e, t) {
        x.fn[e] = function (e) {
            var n, r = [],
                i = x(e),
                o = i.length - 1,
                s = 0;
            for (; o >= s; s++) n = s === o ? this : this.clone(!0), x(i[s])[t](n), h.apply(r, n.get());
            return this.pushStack(r)
        }
    }), x.extend({
        clone: function (e, t, n) {
            var r, i, o, s, a = e.cloneNode(!0),
                u = x.contains(e.ownerDocument, e);
            if (!(x.support.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || x.isXMLDoc(e)))
                for (s = mt(a), o = mt(e), r = 0, i = o.length; i > r; r++) yt(o[r], s[r]);
            if (t)
                if (n)
                    for (o = o || mt(e), s = s || mt(a), r = 0, i = o.length; i > r; r++) gt(o[r], s[r]);
                else gt(e, a);
            return s = mt(a, "script"), s.length > 0 && dt(s, !u && mt(e, "script")), a
        },
        buildFragment: function (e, t, n, r) {
            var i, o, s, a, u, l, c = 0,
                p = e.length,
                f = t.createDocumentFragment(),
                h = [];
            for (; p > c; c++)
                if (i = e[c], i || 0 === i)
                    if ("object" === x.type(i)) x.merge(h, i.nodeType ? [i] : i);
                    else if (rt.test(i)) {
                o = o || f.appendChild(t.createElement("div")), s = (nt.exec(i) || ["", ""])[1].toLowerCase(), a = ct[s] || ct._default, o.innerHTML = a[1] + i.replace(tt, "<$1></$2>") + a[2], l = a[0];
                while (l--) o = o.lastChild;
                x.merge(h, o.childNodes), o = f.firstChild, o.textContent = ""
            } else h.push(t.createTextNode(i));
            f.textContent = "", c = 0;
            while (i = h[c++])
                if ((!r || -1 === x.inArray(i, r)) && (u = x.contains(i.ownerDocument, i), o = mt(f.appendChild(i), "script"), u && dt(o), n)) {
                    l = 0;
                    while (i = o[l++]) at.test(i.type || "") && n.push(i)
                }
            return f
        },
        cleanData: function (e) {
            var t, n, r, i, o, s, a = x.event.special,
                u = 0;
            for (;
                (n = e[u]) !== undefined; u++) {
                if (F.accepts(n) && (o = n[q.expando], o && (t = q.cache[o]))) {
                    if (r = Object.keys(t.events || {}), r.length)
                        for (s = 0;
                            (i = r[s]) !== undefined; s++) a[i] ? x.event.remove(n, i) : x.removeEvent(n, i, t.handle);
                    q.cache[o] && delete q.cache[o]
                }
                delete L.cache[n[L.expando]]
            }
        },
        _evalUrl: function (e) {
            return x.ajax({
                url: e,
                type: "GET",
                dataType: "script",
                async: !1,
                global: !1,
                "throws": !0
            })
        }
    });

    function pt(e, t) {
        return x.nodeName(e, "table") && x.nodeName(1 === t.nodeType ? t : t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e.appendChild(e.ownerDocument.createElement("tbody")) : e
    }

    function ft(e) {
        return e.type = (null !== e.getAttribute("type")) + "/" + e.type, e
    }

    function ht(e) {
        var t = ut.exec(e.type);
        return t ? e.type = t[1] : e.removeAttribute("type"), e
    }

    function dt(e, t) {
        var n = e.length,
            r = 0;
        for (; n > r; r++) q.set(e[r], "globalEval", !t || q.get(t[r], "globalEval"))
    }

    function gt(e, t) {
        var n, r, i, o, s, a, u, l;
        if (1 === t.nodeType) {
            if (q.hasData(e) && (o = q.access(e), s = q.set(t, o), l = o.events)) {
                delete s.handle, s.events = {};
                for (i in l)
                    for (n = 0, r = l[i].length; r > n; n++) x.event.add(t, i, l[i][n])
            }
            L.hasData(e) && (a = L.access(e), u = x.extend({}, a), L.set(t, u))
        }
    }

    function mt(e, t) {
        var n = e.getElementsByTagName ? e.getElementsByTagName(t || "*") : e.querySelectorAll ? e.querySelectorAll(t || "*") : [];
        return t === undefined || t && x.nodeName(e, t) ? x.merge([e], n) : n
    }

    function yt(e, t) {
        var n = t.nodeName.toLowerCase();
        "input" === n && ot.test(e.type) ? t.checked = e.checked : ("input" === n || "textarea" === n) && (t.defaultValue = e.defaultValue)
    }
    x.fn.extend({
        wrapAll: function (e) {
            var t;
            return x.isFunction(e) ? this.each(function (t) {
                x(this).wrapAll(e.call(this, t))
            }) : (this[0] && (t = x(e, this[0].ownerDocument).eq(0).clone(!0), this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                var e = this;
                while (e.firstElementChild) e = e.firstElementChild;
                return e
            }).append(this)), this)
        },
        wrapInner: function (e) {
            return x.isFunction(e) ? this.each(function (t) {
                x(this).wrapInner(e.call(this, t))
            }) : this.each(function () {
                var t = x(this),
                    n = t.contents();
                n.length ? n.wrapAll(e) : t.append(e)
            })
        },
        wrap: function (e) {
            var t = x.isFunction(e);
            return this.each(function (n) {
                x(this).wrapAll(t ? e.call(this, n) : e)
            })
        },
        unwrap: function () {
            return this.parent().each(function () {
                x.nodeName(this, "body") || x(this).replaceWith(this.childNodes)
            }).end()
        }
    });
    var vt, xt, bt = /^(none|table(?!-c[ea]).+)/,
        wt = /^margin/,
        Tt = RegExp("^(" + b + ")(.*)$", "i"),
        Ct = RegExp("^(" + b + ")(?!px)[a-z%]+$", "i"),
        kt = RegExp("^([+-])=(" + b + ")", "i"),
        Nt = {
            BODY: "block"
        }, Et = {
            position: "absolute",
            visibility: "hidden",
            display: "block"
        }, St = {
            letterSpacing: 0,
            fontWeight: 400
        }, jt = ["Top", "Right", "Bottom", "Left"],
        Dt = ["Webkit", "O", "Moz", "ms"];

    function At(e, t) {
        if (t in e) return t;
        var n = t.charAt(0).toUpperCase() + t.slice(1),
            r = t,
            i = Dt.length;
        while (i--)
            if (t = Dt[i] + n, t in e) return t;
        return r
    }

    function Lt(e, t) {
        return e = t || e, "none" === x.css(e, "display") || !x.contains(e.ownerDocument, e)
    }

    function qt(t) {
        return e.getComputedStyle(t, null)
    }

    function Ht(e, t) {
        var n, r, i, o = [],
            s = 0,
            a = e.length;
        for (; a > s; s++) r = e[s], r.style && (o[s] = q.get(r, "olddisplay"), n = r.style.display, t ? (o[s] || "none" !== n || (r.style.display = ""), "" === r.style.display && Lt(r) && (o[s] = q.access(r, "olddisplay", Rt(r.nodeName)))) : o[s] || (i = Lt(r), (n && "none" !== n || !i) && q.set(r, "olddisplay", i ? n : x.css(r, "display"))));
        for (s = 0; a > s; s++) r = e[s], r.style && (t && "none" !== r.style.display && "" !== r.style.display || (r.style.display = t ? o[s] || "" : "none"));
        return e
    }
    x.fn.extend({
        css: function (e, t) {
            return x.access(this, function (e, t, n) {
                var r, i, o = {}, s = 0;
                if (x.isArray(t)) {
                    for (r = qt(e), i = t.length; i > s; s++) o[t[s]] = x.css(e, t[s], !1, r);
                    return o
                }
                return n !== undefined ? x.style(e, t, n) : x.css(e, t)
            }, e, t, arguments.length > 1)
        },
        show: function () {
            return Ht(this, !0)
        },
        hide: function () {
            return Ht(this)
        },
        toggle: function (e) {
            return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function () {
                Lt(this) ? x(this).show() : x(this).hide()
            })
        }
    }), x.extend({
        cssHooks: {
            opacity: {
                get: function (e, t) {
                    if (t) {
                        var n = vt(e, "opacity");
                        return "" === n ? "1" : n
                    }
                }
            }
        },
        cssNumber: {
            columnCount: !0,
            fillOpacity: !0,
            fontWeight: !0,
            lineHeight: !0,
            opacity: !0,
            order: !0,
            orphans: !0,
            widows: !0,
            zIndex: !0,
            zoom: !0
        },
        cssProps: {
            "float": "cssFloat"
        },
        style: function (e, t, n, r) {
            if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                var i, o, s, a = x.camelCase(t),
                    u = e.style;
                return t = x.cssProps[a] || (x.cssProps[a] = At(u, a)), s = x.cssHooks[t] || x.cssHooks[a], n === undefined ? s && "get" in s && (i = s.get(e, !1, r)) !== undefined ? i : u[t] : (o = typeof n, "string" === o && (i = kt.exec(n)) && (n = (i[1] + 1) * i[2] + parseFloat(x.css(e, t)), o = "number"), null == n || "number" === o && isNaN(n) || ("number" !== o || x.cssNumber[a] || (n += "px"), x.support.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (u[t] = "inherit"), s && "set" in s && (n = s.set(e, n, r)) === undefined || (u[t] = n)), undefined)
            }
        },
        css: function (e, t, n, r) {
            var i, o, s, a = x.camelCase(t);
            return t = x.cssProps[a] || (x.cssProps[a] = At(e.style, a)), s = x.cssHooks[t] || x.cssHooks[a], s && "get" in s && (i = s.get(e, !0, n)), i === undefined && (i = vt(e, t, r)), "normal" === i && t in St && (i = St[t]), "" === n || n ? (o = parseFloat(i), n === !0 || x.isNumeric(o) ? o || 0 : i) : i
        }
    }), vt = function (e, t, n) {
        var r, i, o, s = n || qt(e),
            a = s ? s.getPropertyValue(t) || s[t] : undefined,
            u = e.style;
        return s && ("" !== a || x.contains(e.ownerDocument, e) || (a = x.style(e, t)), Ct.test(a) && wt.test(t) && (r = u.width, i = u.minWidth, o = u.maxWidth, u.minWidth = u.maxWidth = u.width = a, a = s.width, u.width = r, u.minWidth = i, u.maxWidth = o)), a
    };

    function Ot(e, t, n) {
        var r = Tt.exec(t);
        return r ? Math.max(0, r[1] - (n || 0)) + (r[2] || "px") : t
    }

    function Ft(e, t, n, r, i) {
        var o = n === (r ? "border" : "content") ? 4 : "width" === t ? 1 : 0,
            s = 0;
        for (; 4 > o; o += 2) "margin" === n && (s += x.css(e, n + jt[o], !0, i)), r ? ("content" === n && (s -= x.css(e, "padding" + jt[o], !0, i)), "margin" !== n && (s -= x.css(e, "border" + jt[o] + "Width", !0, i))) : (s += x.css(e, "padding" + jt[o], !0, i), "padding" !== n && (s += x.css(e, "border" + jt[o] + "Width", !0, i)));
        return s
    }

    function Pt(e, t, n) {
        var r = !0,
            i = "width" === t ? e.offsetWidth : e.offsetHeight,
            o = qt(e),
            s = x.support.boxSizing && "border-box" === x.css(e, "boxSizing", !1, o);
        if (0 >= i || null == i) {
            if (i = vt(e, t, o), (0 > i || null == i) && (i = e.style[t]), Ct.test(i)) return i;
            r = s && (x.support.boxSizingReliable || i === e.style[t]), i = parseFloat(i) || 0
        }
        return i + Ft(e, t, n || (s ? "border" : "content"), r, o) + "px"
    }

    function Rt(e) {
        var t = o,
            n = Nt[e];
        return n || (n = Mt(e, t), "none" !== n && n || (xt = (xt || x("<iframe frameborder='0' width='0' height='0'/>").css("cssText", "display:block !important")).appendTo(t.documentElement), t = (xt[0].contentWindow || xt[0].contentDocument).document, t.write("<!doctype html><html><body>"), t.close(), n = Mt(e, t), xt.detach()), Nt[e] = n), n
    }

    function Mt(e, t) {
        var n = x(t.createElement(e)).appendTo(t.body),
            r = x.css(n[0], "display");
        return n.remove(), r
    }
    x.each(["height", "width"], function (e, t) {
        x.cssHooks[t] = {
            get: function (e, n, r) {
                return n ? 0 === e.offsetWidth && bt.test(x.css(e, "display")) ? x.swap(e, Et, function () {
                    return Pt(e, t, r)
                }) : Pt(e, t, r) : undefined
            },
            set: function (e, n, r) {
                var i = r && qt(e);
                return Ot(e, n, r ? Ft(e, t, r, x.support.boxSizing && "border-box" === x.css(e, "boxSizing", !1, i), i) : 0)
            }
        }
    }), x(function () {
        x.support.reliableMarginRight || (x.cssHooks.marginRight = {
            get: function (e, t) {
                return t ? x.swap(e, {
                    display: "inline-block"
                }, vt, [e, "marginRight"]) : undefined
            }
        }), !x.support.pixelPosition && x.fn.position && x.each(["top", "left"], function (e, t) {
            x.cssHooks[t] = {
                get: function (e, n) {
                    return n ? (n = vt(e, t), Ct.test(n) ? x(e).position()[t] + "px" : n) : undefined
                }
            }
        })
    }), x.expr && x.expr.filters && (x.expr.filters.hidden = function (e) {
        return 0 >= e.offsetWidth && 0 >= e.offsetHeight
    }, x.expr.filters.visible = function (e) {
        return !x.expr.filters.hidden(e)
    }), x.each({
        margin: "",
        padding: "",
        border: "Width"
    }, function (e, t) {
        x.cssHooks[e + t] = {
            expand: function (n) {
                var r = 0,
                    i = {}, o = "string" == typeof n ? n.split(" ") : [n];
                for (; 4 > r; r++) i[e + jt[r] + t] = o[r] || o[r - 2] || o[0];
                return i
            }
        }, wt.test(e) || (x.cssHooks[e + t].set = Ot)
    });
    var Wt = /%20/g,
        $t = /\[\]$/,
        Bt = /\r?\n/g,
        It = /^(?:submit|button|image|reset|file)$/i,
        zt = /^(?:input|select|textarea|keygen)/i;
    x.fn.extend({
        serialize: function () {
            return x.param(this.serializeArray())
        },
        serializeArray: function () {
            return this.map(function () {
                var e = x.prop(this, "elements");
                return e ? x.makeArray(e) : this
            }).filter(function () {
                var e = this.type;
                return this.name && !x(this).is(":disabled") && zt.test(this.nodeName) && !It.test(e) && (this.checked || !ot.test(e))
            }).map(function (e, t) {
                var n = x(this).val();
                return null == n ? null : x.isArray(n) ? x.map(n, function (e) {
                    return {
                        name: t.name,
                        value: e.replace(Bt, "\r\n")
                    }
                }) : {
                    name: t.name,
                    value: n.replace(Bt, "\r\n")
                }
            }).get()
        }
    }), x.param = function (e, t) {
        var n, r = [],
            i = function (e, t) {
                t = x.isFunction(t) ? t() : null == t ? "" : t, r[r.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
            };
        if (t === undefined && (t = x.ajaxSettings && x.ajaxSettings.traditional), x.isArray(e) || e.jquery && !x.isPlainObject(e)) x.each(e, function () {
            i(this.name, this.value)
        });
        else
            for (n in e) _t(n, e[n], t, i);
        return r.join("&").replace(Wt, "+")
    };

    function _t(e, t, n, r) {
        var i;
        if (x.isArray(t)) x.each(t, function (t, i) {
            n || $t.test(e) ? r(e, i) : _t(e + "[" + ("object" == typeof i ? t : "") + "]", i, n, r)
        });
        else if (n || "object" !== x.type(t)) r(e, t);
        else
            for (i in t) _t(e + "[" + i + "]", t[i], n, r)
    }
    x.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (e, t) {
        x.fn[t] = function (e, n) {
            return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }
    }), x.fn.extend({
        hover: function (e, t) {
            return this.mouseenter(e).mouseleave(t || e)
        },
        bind: function (e, t, n) {
            return this.on(e, null, t, n)
        },
        unbind: function (e, t) {
            return this.off(e, null, t)
        },
        delegate: function (e, t, n, r) {
            return this.on(t, e, n, r)
        },
        undelegate: function (e, t, n) {
            return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
        }
    });
    var Xt, Ut, Yt = x.now(),
        Vt = /\?/,
        Gt = /#.*$/,
        Jt = /([?&])_=[^&]*/,
        Qt = /^(.*?):[ \t]*([^\r\n]*)$/gm,
        Kt = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/,
        Zt = /^(?:GET|HEAD)$/,
        en = /^\/\//,
        tn = /^([\w.+-]+:)(?:\/\/([^\/?#:]*)(?::(\d+)|)|)/,
        nn = x.fn.load,
        rn = {}, on = {}, sn = "*/".concat("*");
    try {
        Ut = i.href
    } catch (an) {
        Ut = o.createElement("a"), Ut.href = "", Ut = Ut.href
    }
    Xt = tn.exec(Ut.toLowerCase()) || [];

    function un(e) {
        return function (t, n) {
            "string" != typeof t && (n = t, t = "*");
            var r, i = 0,
                o = t.toLowerCase().match(w) || [];
            if (x.isFunction(n))
                while (r = o[i++]) "+" === r[0] ? (r = r.slice(1) || "*", (e[r] = e[r] || []).unshift(n)) : (e[r] = e[r] || []).push(n)
        }
    }

    function ln(e, t, n, r) {
        var i = {}, o = e === on;

        function s(a) {
            var u;
            return i[a] = !0, x.each(e[a] || [], function (e, a) {
                var l = a(t, n, r);
                return "string" != typeof l || o || i[l] ? o ? !(u = l) : undefined : (t.dataTypes.unshift(l), s(l), !1)
            }), u
        }
        return s(t.dataTypes[0]) || !i["*"] && s("*")
    }

    function cn(e, t) {
        var n, r, i = x.ajaxSettings.flatOptions || {};
        for (n in t) t[n] !== undefined && ((i[n] ? e : r || (r = {}))[n] = t[n]);
        return r && x.extend(!0, e, r), e
    }
    x.fn.load = function (e, t, n) {
        if ("string" != typeof e && nn) return nn.apply(this, arguments);
        var r, i, o, s = this,
            a = e.indexOf(" ");
        return a >= 0 && (r = e.slice(a), e = e.slice(0, a)), x.isFunction(t) ? (n = t, t = undefined) : t && "object" == typeof t && (i = "POST"), s.length > 0 && x.ajax({
            url: e,
            type: i,
            dataType: "html",
            data: t
        }).done(function (e) {
            o = arguments, s.html(r ? x("<div>").append(x.parseHTML(e)).find(r) : e)
        }).complete(n && function (e, t) {
            s.each(n, o || [e.responseText, t, e])
        }), this
    }, x.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (e, t) {
        x.fn[t] = function (e) {
            return this.on(t, e)
        }
    }), x.extend({
        active: 0,
        lastModified: {},
        etag: {},
        ajaxSettings: {
            url: Ut,
            type: "GET",
            isLocal: Kt.test(Xt[1]),
            global: !0,
            processData: !0,
            async: !0,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            accepts: {
                "*": sn,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
            contents: {
                xml: /xml/,
                html: /html/,
                json: /json/
            },
            responseFields: {
                xml: "responseXML",
                text: "responseText",
                json: "responseJSON"
            },
            converters: {
                "* text": String,
                "text html": !0,
                "text json": x.parseJSON,
                "text xml": x.parseXML
            },
            flatOptions: {
                url: !0,
                context: !0
            }
        },
        ajaxSetup: function (e, t) {
            return t ? cn(cn(e, x.ajaxSettings), t) : cn(x.ajaxSettings, e)
        },
        ajaxPrefilter: un(rn),
        ajaxTransport: un(on),
        ajax: function (e, t) {
            "object" == typeof e && (t = e, e = undefined), t = t || {};
            var n, r, i, o, s, a, u, l, c = x.ajaxSetup({}, t),
                p = c.context || c,
                f = c.context && (p.nodeType || p.jquery) ? x(p) : x.event,
                h = x.Deferred(),
                d = x.Callbacks("once memory"),
                g = c.statusCode || {}, m = {}, y = {}, v = 0,
                b = "canceled",
                T = {
                    readyState: 0,
                    getResponseHeader: function (e) {
                        var t;
                        if (2 === v) {
                            if (!o) {
                                o = {};
                                while (t = Qt.exec(i)) o[t[1].toLowerCase()] = t[2]
                            }
                            t = o[e.toLowerCase()]
                        }
                        return null == t ? null : t
                    },
                    getAllResponseHeaders: function () {
                        return 2 === v ? i : null
                    },
                    setRequestHeader: function (e, t) {
                        var n = e.toLowerCase();
                        return v || (e = y[n] = y[n] || e, m[e] = t), this
                    },
                    overrideMimeType: function (e) {
                        return v || (c.mimeType = e), this
                    },
                    statusCode: function (e) {
                        var t;
                        if (e)
                            if (2 > v)
                                for (t in e) g[t] = [g[t], e[t]];
                            else T.always(e[T.status]);
                        return this
                    },
                    abort: function (e) {
                        var t = e || b;
                        return n && n.abort(t), k(0, t), this
                    }
                };
            if (h.promise(T).complete = d.add, T.success = T.done, T.error = T.fail, c.url = ((e || c.url || Ut) + "").replace(Gt, "").replace(en, Xt[1] + "//"), c.type = t.method || t.type || c.method || c.type, c.dataTypes = x.trim(c.dataType || "*").toLowerCase().match(w) || [""], null == c.crossDomain && (a = tn.exec(c.url.toLowerCase()), c.crossDomain = !(!a || a[1] === Xt[1] && a[2] === Xt[2] && (a[3] || ("http:" === a[1] ? "80" : "443")) === (Xt[3] || ("http:" === Xt[1] ? "80" : "443")))), c.data && c.processData && "string" != typeof c.data && (c.data = x.param(c.data, c.traditional)), ln(rn, c, t, T), 2 === v) return T;
            u = c.global, u && 0 === x.active++ && x.event.trigger("ajaxStart"), c.type = c.type.toUpperCase(), c.hasContent = !Zt.test(c.type), r = c.url, c.hasContent || (c.data && (r = c.url += (Vt.test(r) ? "&" : "?") + c.data, delete c.data), c.cache === !1 && (c.url = Jt.test(r) ? r.replace(Jt, "$1_=" + Yt++) : r + (Vt.test(r) ? "&" : "?") + "_=" + Yt++)), c.ifModified && (x.lastModified[r] && T.setRequestHeader("If-Modified-Since", x.lastModified[r]), x.etag[r] && T.setRequestHeader("If-None-Match", x.etag[r])), (c.data && c.hasContent && c.contentType !== !1 || t.contentType) && T.setRequestHeader("Content-Type", c.contentType), T.setRequestHeader("Accept", c.dataTypes[0] && c.accepts[c.dataTypes[0]] ? c.accepts[c.dataTypes[0]] + ("*" !== c.dataTypes[0] ? ", " + sn + "; q=0.01" : "") : c.accepts["*"]);
            for (l in c.headers) T.setRequestHeader(l, c.headers[l]);
            if (c.beforeSend && (c.beforeSend.call(p, T, c) === !1 || 2 === v)) return T.abort();
            b = "abort";
            for (l in {
                success: 1,
                error: 1,
                complete: 1
            }) T[l](c[l]);
            if (n = ln(on, c, t, T)) {
                T.readyState = 1, u && f.trigger("ajaxSend", [T, c]), c.async && c.timeout > 0 && (s = setTimeout(function () {
                    T.abort("timeout")
                }, c.timeout));
                try {
                    v = 1, n.send(m, k)
                } catch (C) {
                    if (!(2 > v)) throw C;
                    k(-1, C)
                }
            } else k(-1, "No Transport");

            function k(e, t, o, a) {
                var l, m, y, b, w, C = t;
                2 !== v && (v = 2, s && clearTimeout(s), n = undefined, i = a || "", T.readyState = e > 0 ? 4 : 0, l = e >= 200 && 300 > e || 304 === e, o && (b = pn(c, T, o)), b = fn(c, b, T, l), l ? (c.ifModified && (w = T.getResponseHeader("Last-Modified"), w && (x.lastModified[r] = w), w = T.getResponseHeader("etag"), w && (x.etag[r] = w)), 204 === e || "HEAD" === c.type ? C = "nocontent" : 304 === e ? C = "notmodified" : (C = b.state, m = b.data, y = b.error, l = !y)) : (y = C, (e || !C) && (C = "error", 0 > e && (e = 0))), T.status = e, T.statusText = (t || C) + "", l ? h.resolveWith(p, [m, C, T]) : h.rejectWith(p, [T, C, y]), T.statusCode(g), g = undefined, u && f.trigger(l ? "ajaxSuccess" : "ajaxError", [T, c, l ? m : y]), d.fireWith(p, [T, C]), u && (f.trigger("ajaxComplete", [T, c]), --x.active || x.event.trigger("ajaxStop")))
            }
            return T
        },
        getJSON: function (e, t, n) {
            return x.get(e, t, n, "json")
        },
        getScript: function (e, t) {
            return x.get(e, undefined, t, "script")
        }
    }), x.each(["get", "post"], function (e, t) {
        x[t] = function (e, n, r, i) {
            return x.isFunction(n) && (i = i || r, r = n, n = undefined), x.ajax({
                url: e,
                type: t,
                dataType: i,
                data: n,
                success: r
            })
        }
    });

    function pn(e, t, n) {
        var r, i, o, s, a = e.contents,
            u = e.dataTypes;
        while ("*" === u[0]) u.shift(), r === undefined && (r = e.mimeType || t.getResponseHeader("Content-Type"));
        if (r)
            for (i in a)
                if (a[i] && a[i].test(r)) {
                    u.unshift(i);
                    break
                }
        if (u[0] in n) o = u[0];
        else {
            for (i in n) {
                if (!u[0] || e.converters[i + " " + u[0]]) {
                    o = i;
                    break
                }
                s || (s = i)
            }
            o = o || s
        }
        return o ? (o !== u[0] && u.unshift(o), n[o]) : undefined
    }

    function fn(e, t, n, r) {
        var i, o, s, a, u, l = {}, c = e.dataTypes.slice();
        if (c[1])
            for (s in e.converters) l[s.toLowerCase()] = e.converters[s];
        o = c.shift();
        while (o)
            if (e.responseFields[o] && (n[e.responseFields[o]] = t), !u && r && e.dataFilter && (t = e.dataFilter(t, e.dataType)), u = o, o = c.shift())
                if ("*" === o) o = u;
                else if ("*" !== u && u !== o) {
            if (s = l[u + " " + o] || l["* " + o], !s)
                for (i in l)
                    if (a = i.split(" "), a[1] === o && (s = l[u + " " + a[0]] || l["* " + a[0]])) {
                        s === !0 ? s = l[i] : l[i] !== !0 && (o = a[0], c.unshift(a[1]));
                        break
                    }
            if (s !== !0)
                if (s && e["throws"]) t = s(t);
                else try {
                    t = s(t)
                } catch (p) {
                    return {
                        state: "parsererror",
                        error: s ? p : "No conversion from " + u + " to " + o
                    }
                }
        }
        return {
            state: "success",
            data: t
        }
    }
    x.ajaxSetup({
        accepts: {
            script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"
        },
        contents: {
            script: /(?:java|ecma)script/
        },
        converters: {
            "text script": function (e) {
                return x.globalEval(e), e
            }
        }
    }), x.ajaxPrefilter("script", function (e) {
        e.cache === undefined && (e.cache = !1), e.crossDomain && (e.type = "GET")
    }), x.ajaxTransport("script", function (e) {
        if (e.crossDomain) {
            var t, n;
            return {
                send: function (r, i) {
                    t = x("<script>").prop({
                        async: !0,
                        charset: e.scriptCharset,
                        src: e.url
                    }).on("load error", n = function (e) {
                        t.remove(), n = null, e && i("error" === e.type ? 404 : 200, e.type)
                    }), o.head.appendChild(t[0])
                },
                abort: function () {
                    n && n()
                }
            }
        }
    });
    var hn = [],
        dn = /(=)\?(?=&|$)|\?\?/;
    x.ajaxSetup({
        jsonp: "callback",
        jsonpCallback: function () {
            var e = hn.pop() || x.expando + "_" + Yt++;
            return this[e] = !0, e
        }
    }), x.ajaxPrefilter("json jsonp", function (t, n, r) {
        var i, o, s, a = t.jsonp !== !1 && (dn.test(t.url) ? "url" : "string" == typeof t.data && !(t.contentType || "").indexOf("application/x-www-form-urlencoded") && dn.test(t.data) && "data");
        return a || "jsonp" === t.dataTypes[0] ? (i = t.jsonpCallback = x.isFunction(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, a ? t[a] = t[a].replace(dn, "$1" + i) : t.jsonp !== !1 && (t.url += (Vt.test(t.url) ? "&" : "?") + t.jsonp + "=" + i), t.converters["script json"] = function () {
            return s || x.error(i + " was not called"), s[0]
        }, t.dataTypes[0] = "json", o = e[i], e[i] = function () {
            s = arguments
        }, r.always(function () {
            e[i] = o, t[i] && (t.jsonpCallback = n.jsonpCallback, hn.push(i)), s && x.isFunction(o) && o(s[0]), s = o = undefined
        }), "script") : undefined
    }), x.ajaxSettings.xhr = function () {
        try {
            return new XMLHttpRequest
        } catch (e) {}
    };
    var gn = x.ajaxSettings.xhr(),
        mn = {
            0: 200,
            1223: 204
        }, yn = 0,
        vn = {};
    e.ActiveXObject && x(e).on("unload", function () {
        for (var e in vn) vn[e]();
        vn = undefined
    }), x.support.cors = !! gn && "withCredentials" in gn, x.support.ajax = gn = !! gn, x.ajaxTransport(function (e) {
        var t;
        return x.support.cors || gn && !e.crossDomain ? {
            send: function (n, r) {
                var i, o, s = e.xhr();
                if (s.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields)
                    for (i in e.xhrFields) s[i] = e.xhrFields[i];
                e.mimeType && s.overrideMimeType && s.overrideMimeType(e.mimeType), e.crossDomain || n["X-Requested-With"] || (n["X-Requested-With"] = "XMLHttpRequest");
                for (i in n) s.setRequestHeader(i, n[i]);
                t = function (e) {
                    return function () {
                        t && (delete vn[o], t = s.onload = s.onerror = null, "abort" === e ? s.abort() : "error" === e ? r(s.status || 404, s.statusText) : r(mn[s.status] || s.status, s.statusText, "string" == typeof s.responseText ? {
                            text: s.responseText
                        } : undefined, s.getAllResponseHeaders()))
                    }
                }, s.onload = t(), s.onerror = t("error"), t = vn[o = yn++] = t("abort"), s.send(e.hasContent && e.data || null)
            },
            abort: function () {
                t && t()
            }
        } : undefined
    });
    var xn, bn, wn = /^(?:toggle|show|hide)$/,
        Tn = RegExp("^(?:([+-])=|)(" + b + ")([a-z%]*)$", "i"),
        Cn = /queueHooks$/,
        kn = [An],
        Nn = {
            "*": [
                function (e, t) {
                    var n = this.createTween(e, t),
                        r = n.cur(),
                        i = Tn.exec(t),
                        o = i && i[3] || (x.cssNumber[e] ? "" : "px"),
                        s = (x.cssNumber[e] || "px" !== o && +r) && Tn.exec(x.css(n.elem, e)),
                        a = 1,
                        u = 20;
                    if (s && s[3] !== o) {
                        o = o || s[3], i = i || [], s = +r || 1;
                        do a = a || ".5", s /= a, x.style(n.elem, e, s + o); while (a !== (a = n.cur() / r) && 1 !== a && --u)
                    }
                    return i && (s = n.start = +s || +r || 0, n.unit = o, n.end = i[1] ? s + (i[1] + 1) * i[2] : +i[2]), n
                }
            ]
        };

    function En() {
        return setTimeout(function () {
            xn = undefined
        }), xn = x.now()
    }

    function Sn(e, t, n) {
        var r, i = (Nn[t] || []).concat(Nn["*"]),
            o = 0,
            s = i.length;
        for (; s > o; o++)
            if (r = i[o].call(n, t, e)) return r
    }

    function jn(e, t, n) {
        var r, i, o = 0,
            s = kn.length,
            a = x.Deferred().always(function () {
                delete u.elem
            }),
            u = function () {
                if (i) return !1;
                var t = xn || En(),
                    n = Math.max(0, l.startTime + l.duration - t),
                    r = n / l.duration || 0,
                    o = 1 - r,
                    s = 0,
                    u = l.tweens.length;
                for (; u > s; s++) l.tweens[s].run(o);
                return a.notifyWith(e, [l, o, n]), 1 > o && u ? n : (a.resolveWith(e, [l]), !1)
            }, l = a.promise({
                elem: e,
                props: x.extend({}, t),
                opts: x.extend(!0, {
                    specialEasing: {}
                }, n),
                originalProperties: t,
                originalOptions: n,
                startTime: xn || En(),
                duration: n.duration,
                tweens: [],
                createTween: function (t, n) {
                    var r = x.Tween(e, l.opts, t, n, l.opts.specialEasing[t] || l.opts.easing);
                    return l.tweens.push(r), r
                },
                stop: function (t) {
                    var n = 0,
                        r = t ? l.tweens.length : 0;
                    if (i) return this;
                    for (i = !0; r > n; n++) l.tweens[n].run(1);
                    return t ? a.resolveWith(e, [l, t]) : a.rejectWith(e, [l, t]), this
                }
            }),
            c = l.props;
        for (Dn(c, l.opts.specialEasing); s > o; o++)
            if (r = kn[o].call(l, e, c, l.opts)) return r;
        return x.map(c, Sn, l), x.isFunction(l.opts.start) && l.opts.start.call(e, l), x.fx.timer(x.extend(u, {
            elem: e,
            anim: l,
            queue: l.opts.queue
        })), l.progress(l.opts.progress).done(l.opts.done, l.opts.complete).fail(l.opts.fail).always(l.opts.always)
    }

    function Dn(e, t) {
        var n, r, i, o, s;
        for (n in e)
            if (r = x.camelCase(n), i = t[r], o = e[n], x.isArray(o) && (i = o[1], o = e[n] = o[0]), n !== r && (e[r] = o, delete e[n]), s = x.cssHooks[r], s && "expand" in s) {
                o = s.expand(o), delete e[r];
                for (n in o) n in e || (e[n] = o[n], t[n] = i)
            } else t[r] = i
    }
    x.Animation = x.extend(jn, {
        tweener: function (e, t) {
            x.isFunction(e) ? (t = e, e = ["*"]) : e = e.split(" ");
            var n, r = 0,
                i = e.length;
            for (; i > r; r++) n = e[r], Nn[n] = Nn[n] || [], Nn[n].unshift(t)
        },
        prefilter: function (e, t) {
            t ? kn.unshift(e) : kn.push(e)
        }
    });

    function An(e, t, n) {
        var r, i, o, s, a, u, l = this,
            c = {}, p = e.style,
            f = e.nodeType && Lt(e),
            h = q.get(e, "fxshow");
        n.queue || (a = x._queueHooks(e, "fx"), null == a.unqueued && (a.unqueued = 0, u = a.empty.fire, a.empty.fire = function () {
            a.unqueued || u()
        }), a.unqueued++, l.always(function () {
            l.always(function () {
                a.unqueued--, x.queue(e, "fx").length || a.empty.fire()
            })
        })), 1 === e.nodeType && ("height" in t || "width" in t) && (n.overflow = [p.overflow, p.overflowX, p.overflowY], "inline" === x.css(e, "display") && "none" === x.css(e, "float") && (p.display = "inline-block")), n.overflow && (p.overflow = "hidden", l.always(function () {
            p.overflow = n.overflow[0], p.overflowX = n.overflow[1], p.overflowY = n.overflow[2]
        }));
        for (r in t)
            if (i = t[r], wn.exec(i)) {
                if (delete t[r], o = o || "toggle" === i, i === (f ? "hide" : "show")) {
                    if ("show" !== i || !h || h[r] === undefined) continue;
                    f = !0
                }
                c[r] = h && h[r] || x.style(e, r)
            }
        if (!x.isEmptyObject(c)) {
            h ? "hidden" in h && (f = h.hidden) : h = q.access(e, "fxshow", {}), o && (h.hidden = !f), f ? x(e).show() : l.done(function () {
                x(e).hide()
            }), l.done(function () {
                var t;
                q.remove(e, "fxshow");
                for (t in c) x.style(e, t, c[t])
            });
            for (r in c) s = Sn(f ? h[r] : 0, r, l), r in h || (h[r] = s.start, f && (s.end = s.start, s.start = "width" === r || "height" === r ? 1 : 0))
        }
    }

    function Ln(e, t, n, r, i) {
        return new Ln.prototype.init(e, t, n, r, i)
    }
    x.Tween = Ln, Ln.prototype = {
        constructor: Ln,
        init: function (e, t, n, r, i, o) {
            this.elem = e, this.prop = n, this.easing = i || "swing", this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = o || (x.cssNumber[n] ? "" : "px")
        },
        cur: function () {
            var e = Ln.propHooks[this.prop];
            return e && e.get ? e.get(this) : Ln.propHooks._default.get(this)
        },
        run: function (e) {
            var t, n = Ln.propHooks[this.prop];
            return this.pos = t = this.options.duration ? x.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : Ln.propHooks._default.set(this), this
        }
    }, Ln.prototype.init.prototype = Ln.prototype, Ln.propHooks = {
        _default: {
            get: function (e) {
                var t;
                return null == e.elem[e.prop] || e.elem.style && null != e.elem.style[e.prop] ? (t = x.css(e.elem, e.prop, ""), t && "auto" !== t ? t : 0) : e.elem[e.prop]
            },
            set: function (e) {
                x.fx.step[e.prop] ? x.fx.step[e.prop](e) : e.elem.style && (null != e.elem.style[x.cssProps[e.prop]] || x.cssHooks[e.prop]) ? x.style(e.elem, e.prop, e.now + e.unit) : e.elem[e.prop] = e.now
            }
        }
    }, Ln.propHooks.scrollTop = Ln.propHooks.scrollLeft = {
        set: function (e) {
            e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
        }
    }, x.each(["toggle", "show", "hide"], function (e, t) {
        var n = x.fn[t];
        x.fn[t] = function (e, r, i) {
            return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(qn(t, !0), e, r, i)
        }
    }), x.fn.extend({
        fadeTo: function (e, t, n, r) {
            return this.filter(Lt).css("opacity", 0).show().end().animate({
                opacity: t
            }, e, n, r)
        },
        animate: function (e, t, n, r) {
            var i = x.isEmptyObject(e),
                o = x.speed(t, n, r),
                s = function () {
                    var t = jn(this, x.extend({}, e), o);
                    (i || q.get(this, "finish")) && t.stop(!0)
                };
            return s.finish = s, i || o.queue === !1 ? this.each(s) : this.queue(o.queue, s)
        },
        stop: function (e, t, n) {
            var r = function (e) {
                var t = e.stop;
                delete e.stop, t(n)
            };
            return "string" != typeof e && (n = t, t = e, e = undefined), t && e !== !1 && this.queue(e || "fx", []), this.each(function () {
                var t = !0,
                    i = null != e && e + "queueHooks",
                    o = x.timers,
                    s = q.get(this);
                if (i) s[i] && s[i].stop && r(s[i]);
                else
                    for (i in s) s[i] && s[i].stop && Cn.test(i) && r(s[i]);
                for (i = o.length; i--;) o[i].elem !== this || null != e && o[i].queue !== e || (o[i].anim.stop(n), t = !1, o.splice(i, 1));
                (t || !n) && x.dequeue(this, e)
            })
        },
        finish: function (e) {
            return e !== !1 && (e = e || "fx"), this.each(function () {
                var t, n = q.get(this),
                    r = n[e + "queue"],
                    i = n[e + "queueHooks"],
                    o = x.timers,
                    s = r ? r.length : 0;
                for (n.finish = !0, x.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = o.length; t--;) o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                for (t = 0; s > t; t++) r[t] && r[t].finish && r[t].finish.call(this);
                delete n.finish
            })
        }
    });

    function qn(e, t) {
        var n, r = {
                height: e
            }, i = 0;
        for (t = t ? 1 : 0; 4 > i; i += 2 - t) n = jt[i], r["margin" + n] = r["padding" + n] = e;
        return t && (r.opacity = r.width = e), r
    }
    x.each({
        slideDown: qn("show"),
        slideUp: qn("hide"),
        slideToggle: qn("toggle"),
        fadeIn: {
            opacity: "show"
        },
        fadeOut: {
            opacity: "hide"
        },
        fadeToggle: {
            opacity: "toggle"
        }
    }, function (e, t) {
        x.fn[e] = function (e, n, r) {
            return this.animate(t, e, n, r)
        }
    }), x.speed = function (e, t, n) {
        var r = e && "object" == typeof e ? x.extend({}, e) : {
            complete: n || !n && t || x.isFunction(e) && e,
            duration: e,
            easing: n && t || t && !x.isFunction(t) && t
        };
        return r.duration = x.fx.off ? 0 : "number" == typeof r.duration ? r.duration : r.duration in x.fx.speeds ? x.fx.speeds[r.duration] : x.fx.speeds._default, (null == r.queue || r.queue === !0) && (r.queue = "fx"), r.old = r.complete, r.complete = function () {
            x.isFunction(r.old) && r.old.call(this), r.queue && x.dequeue(this, r.queue)
        }, r
    }, x.easing = {
        linear: function (e) {
            return e
        },
        swing: function (e) {
            return .5 - Math.cos(e * Math.PI) / 2
        }
    }, x.timers = [], x.fx = Ln.prototype.init, x.fx.tick = function () {
        var e, t = x.timers,
            n = 0;
        for (xn = x.now(); t.length > n; n++) e = t[n], e() || t[n] !== e || t.splice(n--, 1);
        t.length || x.fx.stop(), xn = undefined
    }, x.fx.timer = function (e) {
        e() && x.timers.push(e) && x.fx.start()
    }, x.fx.interval = 13, x.fx.start = function () {
        bn || (bn = setInterval(x.fx.tick, x.fx.interval))
    }, x.fx.stop = function () {
        clearInterval(bn), bn = null
    }, x.fx.speeds = {
        slow: 600,
        fast: 200,
        _default: 400
    }, x.fx.step = {}, x.expr && x.expr.filters && (x.expr.filters.animated = function (e) {
        return x.grep(x.timers, function (t) {
            return e === t.elem
        }).length
    }), x.fn.offset = function (e) {
        if (arguments.length) return e === undefined ? this : this.each(function (t) {
            x.offset.setOffset(this, e, t)
        });
        var t, n, i = this[0],
            o = {
                top: 0,
                left: 0
            }, s = i && i.ownerDocument;
        if (s) return t = s.documentElement, x.contains(t, i) ? (typeof i.getBoundingClientRect !== r && (o = i.getBoundingClientRect()), n = Hn(s), {
            top: o.top + n.pageYOffset - t.clientTop,
            left: o.left + n.pageXOffset - t.clientLeft
        }) : o
    }, x.offset = {
        setOffset: function (e, t, n) {
            var r, i, o, s, a, u, l, c = x.css(e, "position"),
                p = x(e),
                f = {};
            "static" === c && (e.style.position = "relative"), a = p.offset(), o = x.css(e, "top"), u = x.css(e, "left"), l = ("absolute" === c || "fixed" === c) && (o + u).indexOf("auto") > -1, l ? (r = p.position(), s = r.top, i = r.left) : (s = parseFloat(o) || 0, i = parseFloat(u) || 0), x.isFunction(t) && (t = t.call(e, n, a)), null != t.top && (f.top = t.top - a.top + s), null != t.left && (f.left = t.left - a.left + i), "using" in t ? t.using.call(e, f) : p.css(f)
        }
    }, x.fn.extend({
        position: function () {
            if (this[0]) {
                var e, t, n = this[0],
                    r = {
                        top: 0,
                        left: 0
                    };
                return "fixed" === x.css(n, "position") ? t = n.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), x.nodeName(e[0], "html") || (r = e.offset()), r.top += x.css(e[0], "borderTopWidth", !0), r.left += x.css(e[0], "borderLeftWidth", !0)), {
                    top: t.top - r.top - x.css(n, "marginTop", !0),
                    left: t.left - r.left - x.css(n, "marginLeft", !0)
                }
            }
        },
        offsetParent: function () {
            return this.map(function () {
                var e = this.offsetParent || s;
                while (e && !x.nodeName(e, "html") && "static" === x.css(e, "position")) e = e.offsetParent;
                return e || s
            })
        }
    }), x.each({
        scrollLeft: "pageXOffset",
        scrollTop: "pageYOffset"
    }, function (t, n) {
        var r = "pageYOffset" === n;
        x.fn[t] = function (i) {
            return x.access(this, function (t, i, o) {
                var s = Hn(t);
                return o === undefined ? s ? s[n] : t[i] : (s ? s.scrollTo(r ? e.pageXOffset : o, r ? o : e.pageYOffset) : t[i] = o, undefined)
            }, t, i, arguments.length, null)
        }
    });

    function Hn(e) {
        return x.isWindow(e) ? e : 9 === e.nodeType && e.defaultView
    }
    x.each({
        Height: "height",
        Width: "width"
    }, function (e, t) {
        x.each({
            padding: "inner" + e,
            content: t,
            "": "outer" + e
        }, function (n, r) {
            x.fn[r] = function (r, i) {
                var o = arguments.length && (n || "boolean" != typeof r),
                    s = n || (r === !0 || i === !0 ? "margin" : "border");
                return x.access(this, function (t, n, r) {
                    var i;
                    return x.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (i = t.documentElement, Math.max(t.body["scroll" + e], i["scroll" + e], t.body["offset" + e], i["offset" + e], i["client" + e])) : r === undefined ? x.css(t, n, s) : x.style(t, n, r, s)
                }, t, o ? r : undefined, o, null)
            }
        })
    }), x.fn.size = function () {
        return this.length
    }, x.fn.andSelf = x.fn.addBack, "object" == typeof module && module && "object" == typeof module.exports ? module.exports = x : "function" == typeof define && define.amd && define("jquery", [], function () {
        return x
    }), "object" == typeof e && "object" == typeof e.document && (e.jQuery = e.$ = x)
})(window);
(function ($) {
    var formats = {
        b: function (val) {
            return parseInt(val, 10).toString(2)
        },
        c: function (val) {
            return String.fromCharCode(parseInt(val, 10))
        },
        d: function (val) {
            return parseInt(val, 10)
        },
        u: function (val) {
            return Math.abs(val)
        },
        f: function (val, p) {
            p = parseInt(p, 10);
            val = parseFloat(val);
            if (isNaN(p && val)) {
                return NaN
            }
            return p && val.toFixed(p) || val
        },
        o: function (val) {
            return parseInt(val, 10).toString(8)
        },
        s: function (val) {
            return val
        },
        "@": function (val) {
            return val
        },
        x: function (val) {
            return ("" + parseInt(val, 10).toString(16)).toLowerCase()
        },
        X: function (val) {
            return ("" + parseInt(val, 10).toString(16)).toUpperCase()
        }
    };
    var re = /%(?:(\d+)?(?:\.(\d+))?|\(([^)]+)\))([%bcdufos@xX])/g;
    var dispatch = function (data) {
        if (data.length == 1 && typeof data[0] == "object") {
            data = data[0];
            return function (match, w, p, lbl, fmt, off, str) {
                return formats[fmt](data[lbl])
            }
        } else {
            var idx = 0;
            return function (match, w, p, lbl, fmt, off, str) {
                if (fmt == "%") {
                    return "%"
                }
                var i = 0;
                return formats[fmt](data[idx++], p)
            }
        }
    };
    $.extend({
        sprintf: function (format) {
            var argv = Array.apply(null, arguments).slice(1);
            format = format.replace(/\$(@|d)/g, "@");
            var temp_format = format.match(/%\d+(@|d)/g);
            var temp_number_argv = new Array;
            var number = -1,
                reg = "",
                arg = 0;
            if (temp_format) {
                for (var i = 0; i < temp_format.length; ++i) {
                    number = parseInt(temp_format[i].match(/\d/));
                    temp_number_argv.push(number);
                    reg = new RegExp(temp_format[i]);
                    arg = argv[number - 1];
                    format = format.replace(reg, arg)
                }
                for (var j = 0; j < temp_number_argv.length; j++) {
                    argv.splice(temp_number_argv[j] - 1)
                }
            }
            return format.replace(re, dispatch(argv))
        },
        vsprintf: function (format, data) {
            return format.replace(re, dispatch(data))
        }
    })
})(jQuery);
(function ($) {
    var rotateLeft = function (lValue, iShiftBits) {
        return lValue << iShiftBits | lValue >>> 32 - iShiftBits
    };
    var lsbHex = function (value) {
        var string = "";
        var i;
        var vh;
        var vl;
        for (i = 0; i <= 6; i += 2) {
            vh = value >>> i * 4 + 4 & 15;
            vl = value >>> i * 4 & 15;
            string += vh.toString(16) + vl.toString(16)
        }
        return string
    };
    var cvtHex = function (value) {
        var string = "";
        var i;
        var v;
        for (i = 7; i >= 0; i--) {
            v = value >>> i * 4 & 15;
            string += v.toString(16)
        }
        return string
    };
    var uTF8Encode = function (string) {
        string = string.replace(/\x0d\x0a/g, "\n");
        var output = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                output += String.fromCharCode(c)
            } else if (c > 127 && c < 2048) {
                output += String.fromCharCode(c >> 6 | 192);
                output += String.fromCharCode(c & 63 | 128)
            } else {
                output += String.fromCharCode(c >> 12 | 224);
                output += String.fromCharCode(c >> 6 & 63 | 128);
                output += String.fromCharCode(c & 63 | 128)
            }
        }
        return output
    };
    $.extend({
        sha1: function (string) {
            var blockstart;
            var i, j;
            var W = new Array(80);
            var H0 = 1732584193;
            var H1 = 4023233417;
            var H2 = 2562383102;
            var H3 = 271733878;
            var H4 = 3285377520;
            var A, B, C, D, E;
            var tempValue;
            string = uTF8Encode(string);
            var stringLength = string.length;
            var wordArray = new Array;
            for (i = 0; i < stringLength - 3; i += 4) {
                j = string.charCodeAt(i) << 24 | string.charCodeAt(i + 1) << 16 | string.charCodeAt(i + 2) << 8 | string.charCodeAt(i + 3);
                wordArray.push(j)
            }
            switch (stringLength % 4) {
            case 0:
                i = 2147483648;
                break;
            case 1:
                i = string.charCodeAt(stringLength - 1) << 24 | 8388608;
                break;
            case 2:
                i = string.charCodeAt(stringLength - 2) << 24 | string.charCodeAt(stringLength - 1) << 16 | 32768;
                break;
            case 3:
                i = string.charCodeAt(stringLength - 3) << 24 | string.charCodeAt(stringLength - 2) << 16 | string.charCodeAt(stringLength - 1) << 8 | 128;
                break
            }
            wordArray.push(i);
            while (wordArray.length % 16 != 14) wordArray.push(0);
            wordArray.push(stringLength >>> 29);
            wordArray.push(stringLength << 3 & 4294967295);
            for (blockstart = 0; blockstart < wordArray.length; blockstart += 16) {
                for (i = 0; i < 16; i++) W[i] = wordArray[blockstart + i];
                for (i = 16; i <= 79; i++) W[i] = rotateLeft(W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16], 1);
                A = H0;
                B = H1;
                C = H2;
                D = H3;
                E = H4;
                for (i = 0; i <= 19; i++) {
                    tempValue = rotateLeft(A, 5) + (B & C | ~B & D) + E + W[i] + 1518500249 & 4294967295;
                    E = D;
                    D = C;
                    C = rotateLeft(B, 30);
                    B = A;
                    A = tempValue
                }
                for (i = 20; i <= 39; i++) {
                    tempValue = rotateLeft(A, 5) + (B ^ C ^ D) + E + W[i] + 1859775393 & 4294967295;
                    E = D;
                    D = C;
                    C = rotateLeft(B, 30);
                    B = A;
                    A = tempValue
                }
                for (i = 40; i <= 59; i++) {
                    tempValue = rotateLeft(A, 5) + (B & C | B & D | C & D) + E + W[i] + 2400959708 & 4294967295;
                    E = D;
                    D = C;
                    C = rotateLeft(B, 30);
                    B = A;
                    A = tempValue
                }
                for (i = 60; i <= 79; i++) {
                    tempValue = rotateLeft(A, 5) + (B ^ C ^ D) + E + W[i] + 3395469782 & 4294967295;
                    E = D;
                    D = C;
                    C = rotateLeft(B, 30);
                    B = A;
                    A = tempValue
                }
                H0 = H0 + A & 4294967295;
                H1 = H1 + B & 4294967295;
                H2 = H2 + C & 4294967295;
                H3 = H3 + D & 4294967295;
                H4 = H4 + E & 4294967295
            }
            var tempValue = cvtHex(H0) + cvtHex(H1) + cvtHex(H2) + cvtHex(H3) + cvtHex(H4);
            return tempValue.toLowerCase()
        }
    })
})(jQuery);
var Sha256 = {};
Sha256.hash = function (msg, utf8encode) {
    utf8encode = typeof utf8encode == "undefined" ? true : utf8encode;
    if (utf8encode) msg = Utf8.encode(msg);
    var K = [1116352408, 1899447441, 3049323471, 3921009573, 961987163, 1508970993, 2453635748, 2870763221, 3624381080, 310598401, 607225278, 1426881987, 1925078388, 2162078206, 2614888103, 3248222580, 3835390401, 4022224774, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, 2554220882, 2821834349, 2952996808, 3210313671, 3336571891, 3584528711, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, 2177026350, 2456956037, 2730485921, 2820302411, 3259730800, 3345764771, 3516065817, 3600352804, 4094571909, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, 2227730452, 2361852424, 2428436474, 2756734187, 3204031479, 3329325298];
    var H = [1779033703, 3144134277, 1013904242, 2773480762, 1359893119, 2600822924, 528734635, 1541459225];
    msg += String.fromCharCode(128);
    var l = msg.length / 4 + 2;
    var N = Math.ceil(l / 16);
    var M = new Array(N);
    for (var i = 0; i < N; i++) {
        M[i] = new Array(16);
        for (var j = 0; j < 16; j++) {
            M[i][j] = msg.charCodeAt(i * 64 + j * 4) << 24 | msg.charCodeAt(i * 64 + j * 4 + 1) << 16 | msg.charCodeAt(i * 64 + j * 4 + 2) << 8 | msg.charCodeAt(i * 64 + j * 4 + 3)
        }
    }
    M[N - 1][14] = (msg.length - 1) * 8 / Math.pow(2, 32);
    M[N - 1][14] = Math.floor(M[N - 1][14]);
    M[N - 1][15] = (msg.length - 1) * 8 & 4294967295;
    var W = new Array(64);
    var a, b, c, d, e, f, g, h;
    for (var i = 0; i < N; i++) {
        for (var t = 0; t < 16; t++) W[t] = M[i][t];
        for (var t = 16; t < 64; t++) W[t] = Sha256.sigma1(W[t - 2]) + W[t - 7] + Sha256.sigma0(W[t - 15]) + W[t - 16] & 4294967295;
        a = H[0];
        b = H[1];
        c = H[2];
        d = H[3];
        e = H[4];
        f = H[5];
        g = H[6];
        h = H[7];
        for (var t = 0; t < 64; t++) {
            var T1 = h + Sha256.Sigma1(e) + Sha256.Ch(e, f, g) + K[t] + W[t];
            var T2 = Sha256.Sigma0(a) + Sha256.Maj(a, b, c);
            h = g;
            g = f;
            f = e;
            e = d + T1 & 4294967295;
            d = c;
            c = b;
            b = a;
            a = T1 + T2 & 4294967295
        }
        H[0] = H[0] + a & 4294967295;
        H[1] = H[1] + b & 4294967295;
        H[2] = H[2] + c & 4294967295;
        H[3] = H[3] + d & 4294967295;
        H[4] = H[4] + e & 4294967295;
        H[5] = H[5] + f & 4294967295;
        H[6] = H[6] + g & 4294967295;
        H[7] = H[7] + h & 4294967295
    }
    return Sha256.toHexStr(H[0]) + Sha256.toHexStr(H[1]) + Sha256.toHexStr(H[2]) + Sha256.toHexStr(H[3]) + Sha256.toHexStr(H[4]) + Sha256.toHexStr(H[5]) + Sha256.toHexStr(H[6]) + Sha256.toHexStr(H[7])
};
Sha256.ROTR = function (n, x) {
    return x >>> n | x << 32 - n
};
Sha256.Sigma0 = function (x) {
    return Sha256.ROTR(2, x) ^ Sha256.ROTR(13, x) ^ Sha256.ROTR(22, x)
};
Sha256.Sigma1 = function (x) {
    return Sha256.ROTR(6, x) ^ Sha256.ROTR(11, x) ^ Sha256.ROTR(25, x)
};
Sha256.sigma0 = function (x) {
    return Sha256.ROTR(7, x) ^ Sha256.ROTR(18, x) ^ x >>> 3
};
Sha256.sigma1 = function (x) {
    return Sha256.ROTR(17, x) ^ Sha256.ROTR(19, x) ^ x >>> 10
};
Sha256.Ch = function (x, y, z) {
    return x & y ^ ~x & z
};
Sha256.Maj = function (x, y, z) {
    return x & y ^ x & z ^ y & z
};
Sha256.toHexStr = function (n) {
    var s = "",
        v;
    for (var i = 7; i >= 0; i--) {
        v = n >>> i * 4 & 15;
        s += v.toString(16)
    }
    return s
};
var Utf8 = {};
Utf8.encode = function (strUni) {
    var strUtf = strUni.replace(/[\u0080-\u07ff]/g, function (c) {
        var cc = c.charCodeAt(0);
        return String.fromCharCode(192 | cc >> 6, 128 | cc & 63)
    });
    strUtf = strUtf.replace(/[\u0800-\uffff]/g, function (c) {
        var cc = c.charCodeAt(0);
        return String.fromCharCode(224 | cc >> 12, 128 | cc >> 6 & 63, 128 | cc & 63)
    });
    return strUtf
};
Utf8.decode = function (strUtf) {
    var strUni = strUtf.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g, function (c) {
        var cc = (c.charCodeAt(0) & 15) << 12 | (c.charCodeAt(1) & 63) << 6 | c.charCodeAt(2) & 63;
        return String.fromCharCode(cc)
    });
    strUni = strUni.replace(/[\u00c0-\u00df][\u0080-\u00bf]/g, function (c) {
        var cc = (c.charCodeAt(0) & 31) << 6 | c.charCodeAt(1) & 63;
        return String.fromCharCode(cc)
    });
    return strUni
};